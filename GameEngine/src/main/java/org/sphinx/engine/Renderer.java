package org.sphinx.engine;

import org.sphinx.util.Debug;

import java.nio.Buffer;
import java.util.List;
import java.util.Objects;

import static org.lwjgl.opengl.GL40.*;


public class Renderer {
    private static Camera activeCamera = null;

    private static Texture loadTexture;

    public static void setActiveCamera(Camera activeCamera) {
        Renderer.activeCamera = activeCamera;
        Debug.log("Renderer----The currently active camera has been modified");
    }

    public static Camera getActiveCamera() {
        return activeCamera;
    }
    protected static void init(){
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        loadTexture = new Texture("/load.png",true);
        Debug.log("Renderer----Initialization successful");
    }
    protected static void render(){
        if(!SceneController.isLoadingNextScene){
            if (Objects.isNull(activeCamera)){return;}
            glClear(GL_COLOR_BUFFER_BIT);
            //skyboxDraw();
            for (int i = 0; i <= 20; i++){
                for(List<Component> renderList : Render.components.get(Render.class).values()){
                    for (Component component : renderList){
                        Render render = (Render) component;
                        if (render.getLayout() == i && render.getGameObject().isEnable()){
                            switch (render.type){
                                case painter -> renderPainter((Painter) render);
                                case sprite -> renderSprite((Sprite) render);
                            }
                        }
                    }
                }
            }
        }
        else {
            loadingDraw();
        }
        WindowController.getInstance().swapBuffer();
    }

    private static void viewportRender(){
        glLineWidth(3);
        glBegin(GL_LINE_LOOP);
        glColor4f(1,1,0,1);
        glVertex2d(0.5f,0.5f);
        glColor4f(1,0,1,1);
        glVertex2d(-0.5f,0.5f);
        glColor4f(0,1,0,1);
        glVertex2d(-0.5f,-0.5f);
        glColor4f(0,1,1,1);
        glVertex2d(0.5f,-0.5f);
        glEnd();
    }
    private static void renderPainter(Painter painter){
        ShaderProgram.defaultShader.bind();
        ShaderProgram.defaultShader.setUniform("UIsign", 1);
        switch (painter.type) {
            case UI ->
                    ShaderProgram.defaultShader.setUniform("matrix", Transformation.getUIMatrix(new Transform()), 16);
            case Item ->
                    ShaderProgram.defaultShader.setUniform("matrix", Transformation.getWorldMatrix(new Transform(), activeCamera), 16);
        }
        painter.draw();
        ShaderProgram.defaultShader.unbind();
    }
    private static void renderSprite(Sprite sprite){
        sprite.getMesh().bind();
        sprite.getShaderProgram().bind();
        sprite.getTexture().bind();
        switch (sprite.type){
            case UI ->
                sprite.getShaderProgram().setUniform("matrix", Transformation.getUIMatrix(sprite.getGameObject().getTransform().addedVector(sprite.offset)),16);
            case Item ->
                sprite.getShaderProgram().setUniform("matrix", Transformation.getWorldMatrix(sprite.getGameObject().getTransform().addedVector(sprite.offset),activeCamera),16);
        }
        if (sprite.isFlashing)
            sprite.getShaderProgram().setUniform("isFlash", 1);
        else
            sprite.getShaderProgram().setUniform("isFlash", 0);
        sprite.getShaderProgram().setUniform("UIsign", 0);

        glDrawArrays(GL_QUADS, 0,sprite.getMesh().getVertexCount());

        sprite.getTexture().unbind();
        sprite.getShaderProgram().unbind();
        sprite.getMesh().unbind();
    }

    private static void skyboxDraw(){
        glBegin(GL_POLYGON);
        glColor4f(0.2f,0.2f,0.5f,1);
        glVertex2d(1,1);
        glColor4f(0.2f,0.5f,0.2f,1);
        glVertex2d(-1,1);
        glColor4f(0.5f,0.2f,0.5f,1);
        glVertex2d(-1,-1);
        glColor4f(0.2f,0.5f,0.5f,1);
        glVertex2d(1f,-1);
        glEnd();
    }
    static void loadingDraw(){
        loadTexture.bind();
        glBegin(GL_QUADS);
        float v1 = (2f / WindowController.getInstance().getWindowWidth() * loadTexture.getWidth()) / 8;
        float v2 = (2f / WindowController.getInstance().getWindowHeight() * loadTexture.getHeight()) / 8;
        glTexCoord2f(0,1);
        glVertex2f(-v1,-v2);
        glTexCoord2f(1,1);
        glVertex2f(v1,-v2);
        glTexCoord2f(1,0);
        glVertex2f(v1,v2);
        glTexCoord2f(0,0);
        glVertex2f(-v1,v2);
        glEnd();
        loadTexture.unbind();
    }
}

package org.sphinx.engine;

import static org.lwjgl.opengl.GL30.*;

import java.awt.*;

public abstract class Painter extends Render{
    public UsageType type = UsageType.UI;
    private Color fillColor = new Color(1,1,1,1);
    private Color outLineColor = new Color(0,0,0,1);
    private int outLineWidth = 2;

    public Painter(GameObject gameObject){
        super(gameObject,Render.Type.painter);
    }

    public abstract void draw();

    protected void drawQuads(float x, float y, float width, float height){
        setColor(fillColor);
        glBegin(GL_QUADS);
        glVertex2f(x,y);
        glVertex2f(x + width,y);
        glVertex2f(x + width,y + height);
        glVertex2f(x,y + height);
        glEnd();
    }

    protected void drawQuadsOutLine(float x, float y, float width, float height){
        setColor(outLineColor);
        glLineWidth(outLineWidth);
        glBegin(GL_LINE_LOOP);
        glVertex2f(x,y);
        glVertex2f(x + width,y);
        glVertex2f(x + width,y + height);
        glVertex2f(x,y + height);
        glEnd();
    }
    protected void drawCircle(float x, float y,float r){
        setColor(fillColor);
        glBegin(GL_POLYGON);
        for (int i = 0; i < 80; i++) {
            glVertex2d(r*Math.cos(2 * Math.PI*i / 80), r*Math.sin(2 *  Math.PI*i / 80));
        }
        glEnd();
    }
    protected void drawCircleOutLine(float x, float y,float r){
        setColor(outLineColor);
        glLineWidth(outLineWidth);
        glBegin(GL_LINE_LOOP);
        for (int i = 0; i < 80; i++) {
            glVertex2d(r*Math.cos(2 * Math.PI*i / 80)+x, r*Math.sin(2 *  Math.PI*i / 80)+y);
        }
        glEnd();
    }

    public void setFillColor(Color color) {
        this.fillColor = color;
    }

    public void setFillColor(float red, float green, float blue, float alpha) {
        this.fillColor = new Color(red, green, blue, alpha);

    }

    public void setOutLineWidth(int width){
        this.outLineWidth = width;
    }

    public int getOutLineWidth() {
        return outLineWidth;
    }

    public void setOutLineColor(Color color) {
        this.outLineColor = color;
    }
    private void setColor(Color color){
        ShaderProgram.defaultShader.setUniform("UIcolor", color);
    }
}

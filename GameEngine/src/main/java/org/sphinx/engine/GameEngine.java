package org.sphinx.engine;
import org.lwjgl.glfw.GLFW;
import org.sphinx.util.Debug;

public class GameEngine {
    public static GameEngine gameEngine = null;
    private int fps = 30;
    private GameEngine(){}
    public static GameEngine getGameEngine(){
        if (gameEngine==null) {
            gameEngine = new GameEngine();
            Debug.log("Engine instance created successfully");
        }
        return gameEngine;
    }
    private final WindowController windowController = WindowController.getInstance();

    public void setWindowAttribute(int windowSizeWidth, int windowSizeHeight, String windowTitle){
        windowController.setWindowInfo(windowSizeWidth, windowSizeHeight, windowTitle);
    }

    public void init(){
        windowController.glInit();
        EventSystem.init();
        ShaderProgram.defaultShaderInit();
        Renderer.init();
        Text.init();
        Component.register(Animator.class,0);
        Component.register(Render.class,1);
        Component.register(Collider.class,2);
        Component.register(Rigidbody.class,3);
        Debug.log("Engine initialization successful");
    }
    private void loop(){
        Debug.log("Engine cycle start");
        while (!GLFW.glfwWindowShouldClose(windowController.window)){
            GLFW.glfwPollEvents();
            GameTimer.timerUpdate();
            Component.componentUpdate();
            GameObject.gameObjectsUpdate();
            GameObject.gameObjectStatusUpdate();
            Scene.finish();
            sleep();
        }
    }
    private void cleanup(){
        Debug.log("Start resource release");
        windowController.windowDestroy();
        SceneController.cleanScene();
    }
    private void sleep(){
        double onceLoopTime = GameTimer.onceLoopTime();
        double v = (1d / fps - onceLoopTime)*1000;
        v = v > 0 ? v : 0;
        try {
            Thread.sleep((int) v);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void setFps(int fps) {
        this.fps = fps;
    }

    public void start() {
       try {
           SceneController.loadSceneIndex(0);
           loop();
       }
       catch (Exception e){
           e.printStackTrace();
       }
       finally {
           cleanup();
       }
    }
}

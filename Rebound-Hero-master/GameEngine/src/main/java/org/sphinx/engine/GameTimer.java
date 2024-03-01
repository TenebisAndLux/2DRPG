package org.sphinx.engine;
import org.sphinx.util.Debug;

import static org.lwjgl.glfw.GLFW.*;
import java.util.ArrayList;
import java.util.List;

public class GameTimer {
    private static final List<GameTimer> GAME_TIMER_LIST = new ArrayList<>();
    public double time = 0;
    public static double deltaTime;
    private static double lastTimer = glfwGetTime();
    public boolean isFreeze = false;

    public GameTimer(){
        GAME_TIMER_LIST.add(this);
    }

    public void reset(){
        this.time = 0;
    }

    static void timerUpdate(){
        deltaTime = glfwGetTime() - lastTimer;
        lastTimer = glfwGetTime();
        for (GameTimer gameTimer : GAME_TIMER_LIST){
            if (!gameTimer.isFreeze)
                gameTimer.time += deltaTime;
        }
    }
    static double onceLoopTime(){
        return glfwGetTime() -lastTimer;
    }

    public static void destroyAllGameTimer(){
        Debug.log("Timer----Release timer");
        while (!GAME_TIMER_LIST.isEmpty()){
            GAME_TIMER_LIST.remove(0);
        }
    }

    public void destroy(){
        GAME_TIMER_LIST.remove(this);
    }
}

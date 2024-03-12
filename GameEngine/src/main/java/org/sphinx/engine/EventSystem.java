package org.sphinx.engine;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.sphinx.util.Debug;

import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.glfw.GLFW.*;

public class EventSystem {
    private static final Point cursorPos = new Point();
    static GLFWCursorPosCallback cursorPosCallback;
    static GLFWMouseButtonCallback mouseButtonCallback;
    static GLFWKeyCallback keyCallback;
    private static boolean mouseButton1 = false;
    private static boolean mouseButton2 = false;
    private static final Map<Integer,Integer> keyStatus = new HashMap<>();
    static void init(){
        for (int i = 32; i < 348; i++) {
            keyStatus.put(i,-1);
        }
        glfwSetCursorPosCallback(WindowController.getInstance().window, cursorPosCallback = new GLFWCursorPosCallback() {
            public void invoke(long window, double xpos, double ypos) {
                cursorPos.x = (int) xpos;
                cursorPos.y = (int) ypos;
            }
        });
        glfwSetMouseButtonCallback(WindowController.getInstance().window, mouseButtonCallback = new GLFWMouseButtonCallback() {
            @Override
            public void invoke(long window, int button, int action, int mods) {
                if (button == GLFW_MOUSE_BUTTON_1){
                    if (action == GLFW_PRESS){
                        mouseButton1 = true;
                    }
                    else if (action == GLFW_RELEASE){
                        mouseButton1 = false;
                    }
                }
                if (button == GLFW_MOUSE_BUTTON_2){
                    if (action == GLFW_PRESS){
                        mouseButton2 = true;
                    }
                    else if (action == GLFW_RELEASE){
                        mouseButton2 = false;
                    }
                }
            }
        });
        glfwSetKeyCallback(WindowController.getInstance().window, keyCallback = new GLFWKeyCallback(){
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
                keyStatus.put(key,action);
            }
        });
    }

    public static Point getCursorPos() {
        return cursorPos;
    }
    public static boolean getMouseButton1(){
        return mouseButton1;
    }
    public static boolean getMouseButton2(){
        return mouseButton2;
    }
    public static int getKeyStatus(int key){
        return keyStatus.getOrDefault(key, -1);
    }
    public static void execute(Class<?> clazz,GameObject gameObject,String ... strings){
        try {
            Method method = clazz.getDeclaredMethod(strings[0],GameObject.class,String[].class);
            method.setAccessible(true);
            method.invoke(null, gameObject, strings);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            Debug.err("NoMethod",e);
        }
    }
}

package org.sphinx.game;

import org.lwjgl.glfw.GLFW;
import org.sphinx.engine.*;
import org.sphinx.ui.Button;
import org.sphinx.ui.Canvas;
import org.sphinx.ui.Lable;

import java.awt.*;

class ExitMenuEvent implements GameEvent {
    GameObject gameObject;
    String[] command = new String[1];
    ExitMenuEvent(String command,GameObject gameObject){
        this.command[0] = command;
        this.gameObject = gameObject;
    }
    public static void Cancel(GameObject gameObject,String[] args){
        System.out.println("Cancel");
        ExitMenu exitMenu = (ExitMenu) GameObject.findGameObject("ExitMenu");
        exitMenu.isShouldClose = true;
        Player player = (Player) GameObject.findGameObjects("Player").get(0);
        player.isPlayerControl = true;

    }
    public static void GoBack(GameObject gameObject,String[] args){
        System.out.println("Back");
        SceneController.loadSceneIndex(0);
    }
    public static void Exit(GameObject gameObject,String[] args){
        System.out.println("Exit");
        WindowController.getInstance().setWindowShouldClose();
    }
    @Override
    public void run() {
        EventSystem.execute(this.getClass(),gameObject,command);
    }
}

public class ExitMenu extends GameObject{
    Canvas canvas, canvas1;
    Lable lable;
    Button button,button1,button2;
    boolean isShouldClose = true;
    boolean isDisable = true;
    GameTimer gameTimer;
    @Override
    public void start() {
        tag = "Menu";
        name = "ExitMenu";

        canvas = new Canvas(0,0, WindowController.getInstance().getWindowWidth(), WindowController.getInstance().getWindowHeight());
        canvas.fillColor = new Color(0,0,0,0);

        canvas1 = new Canvas((WindowController.getInstance().getWindowWidth()-400)/2,(WindowController.getInstance().getWindowHeight()-470)/2,400,470);
        canvas1.setLayout(18);
        canvas1.setOutLine(true);
        canvas1.setColor(new Color(16, 141, 173));
        canvas1.transform.position.y = -470;

        lable = new Lable(canvas1, 360,150);
        lable.setText("Game paused",50, Color.black,true);
        lable.transform.position = new Vector2D((canvas1.getWidth() - lable.getWidth())/2,(canvas1.getHeight() - lable.getHeight())/2f - 100);

        button = new Button(canvas1,150,50);
        button.transform.position = new Vector2D((canvas1.getWidth() - button.getWidth())/2,(canvas1.getHeight() - button.getHeight())/2f);
        button.setButtonColor(new Color(131, 174, 255),new Color(217, 229, 255),new Color(81, 137, 255));
        button.lable.setText("\n" + "Return",30,Color.white,true);

        button.setEvent(new ExitMenuEvent("Cancel",this));

        button1 = new Button(canvas1,150,50);
        button1.transform.position = new Vector2D((canvas1.getWidth() - button1.getWidth())/2,(canvas1.getHeight() - button1.getHeight())/2f + button.getHeight()+ 30);
        button1.setButtonColor(new Color(131, 174, 255),new Color(217, 229, 255),new Color(81, 137, 255));
        button1.lable.setText("Menu",30,Color.white,true);
        button1.setEvent(new ExitMenuEvent("GoBack",this));

        button2 = new Button(canvas1,150,50);
        button2.transform.position = new Vector2D((canvas1.getWidth() - button1.getWidth())/2,(canvas1.getHeight() - button1.getHeight())/2f + (button.getHeight()+30)*2);
        button2.setButtonColor(new Color(131, 174, 255),new Color(217, 229, 255),new Color(81, 137, 255));
        button2.lable.setText("Exit",30,Color.white,true);

        button2.setEvent(new ExitMenuEvent("Exit",this));
        gameTimer =  new GameTimer();
        gameTimer.isFreeze = true;
    }

    @Override
    public void update() {

        if (isShouldClose && canvas1.transform.position.y > -470){
            canvas1.transform.position.y -= 40;
        }
        else if (!isShouldClose && canvas1.transform.position.y < (WindowController.getInstance().getWindowHeight()-470)/2){
            canvas1.transform.position.y += 40;
        }
        if (isDisable) return;
        if (GLFW.glfwGetKey(WindowController.getInstance().window, GLFW.GLFW_KEY_ESCAPE)== GLFW.GLFW_PRESS){
            isShouldClose = false;
            Player player = (Player) GameObject.findGameObjects("Player").get(0);
            player.isPlayerControl = false;
        }
    }

    @Override
    public void enable() {

    }

    @Override
    public void disable() {

    }
}

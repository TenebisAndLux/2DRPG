package org.sphinx.game;

import org.sphinx.engine.*;
import org.sphinx.ui.Button;
import org.sphinx.ui.Canvas;
import org.sphinx.ui.Lable;

import java.awt.*;

class StartMenuEvent implements GameEvent{
    GameObject gameObject;
    String[] command = new String[1];
    StartMenuEvent(String command,GameObject gameObject){
        this.command[0] = command;
        this.gameObject = gameObject;
    }
    public static void Start(GameObject gameObject,String[] args){
        System.out.println("Start");
        GameCamera camera = (GameCamera) GameObject.findGameObjects("Camera").get(0);
        camera.isFlowing = true;
        StartMenu startMenu = (StartMenu) gameObject;
        startMenu.isStarted = true;
        Player player = (Player) GameObject.findGameObjects("Player").get(0);
        player.isPlayerControl = true;
        ExitMenu exitMenu = (ExitMenu) GameObject.findGameObject("ExitMenu");
        exitMenu.isDisable = false;
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
public class StartMenu extends GameObject {
    Canvas canvas;
    Lable lable;
    Button button,button1;
    boolean isStarted = false;
    GameTimer gameTimer;
    @Override
    public void start() {
        tag = "Menu";
        name = "StartMenu";

        canvas = new Canvas(0,0, WindowController.getInstance().getWindowWidth(), WindowController.getInstance().getWindowHeight());
        canvas.fillColor = new Color(0,0,0,0);
        lable = new Lable(canvas, 560,150);
        lable.setText("Rebound Hero",80, Color.black,true);
        lable.transform.position = new Vector2D((WindowController.getInstance().getWindowWidth() - lable.getWidth())/2,(WindowController.getInstance().getWindowHeight() - lable.getHeight())/2f - 100);
        button = new Button(canvas,150,50);
        button.transform.position = new Vector2D((WindowController.getInstance().getWindowWidth() - button.getWidth())/2,(WindowController.getInstance().getWindowHeight() - button.getHeight())/2f);
        button.setButtonColor(new Color(131, 174, 255),new Color(217, 229, 255),new Color(81, 137, 255));
        button.lable.setText("Play",30,Color.white,true);

        button.setEvent(new StartMenuEvent("Start",this));

        button1 = new Button(canvas,150,50);
        button1.transform.position = new Vector2D((WindowController.getInstance().getWindowWidth() - button1.getWidth())/2,(WindowController.getInstance().getWindowHeight() - button1.getHeight())/2f + button.getHeight()+ 30);
        button1.setButtonColor(new Color(131, 174, 255),new Color(217, 229, 255),new Color(81, 137, 255));
        button1.lable.setText("Exit",30,Color.white,true);

        button1.setEvent(new StartMenuEvent("Exit",this));
        gameTimer =  new GameTimer();
        gameTimer.isFreeze = true;
    }

    @Override
    public void update() {
        if (isStarted){
            gameTimer.isFreeze = false;
            canvas.transform.position.y-=10;
        }
        if (gameTimer.time > 1f) {
            GameCamera camera = (GameCamera) GameObject.findGameObjects("Camera").get(0);
            camera.isLookAt = true;
        }
        if (gameTimer.time > 6) {
            canvas.setActive(false);
        }
    }

    @Override
    public void enable() {

    }

    @Override
    public void disable() {

    }
}

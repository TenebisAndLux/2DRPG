package org.sphinx.game;

import org.sphinx.engine.*;
import org.sphinx.ui.Button;
import org.sphinx.ui.Canvas;
import org.sphinx.ui.Lable;

import java.awt.*;

    public class DirectStart extends GameObject {
        Canvas canvas;
        boolean isStarted = false;
        GameTimer gameTimer;
        @Override
        public void start() {
            tag = "DirectStart";
            name = "DirectStart";

            canvas = new Canvas(0,0, WindowController.getInstance().getWindowWidth(), WindowController.getInstance().getWindowHeight());
            canvas.fillColor = new Color(0,0,0,0);

            GameCamera camera = (GameCamera) GameObject.findGameObjects("Camera").get(0);
            camera.isFlowing = true;
            Player player = (Player) GameObject.findGameObjects("Player").get(0);
            player.isPlayerControl = true;

            gameTimer =  new GameTimer();
            gameTimer.isFreeze = false;
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


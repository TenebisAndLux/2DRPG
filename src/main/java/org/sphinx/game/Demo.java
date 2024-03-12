package org.sphinx.game;

import org.sphinx.engine.GameEngine;
import org.sphinx.engine.SceneController;
import org.sphinx.engine.WindowController;
import org.sphinx.util.Debug;

import javax.imageio.ImageIO;
import java.io.IOException;

class Demo{
    public static void main(String[] args) throws IOException {
        GameEngine gameEngine = GameEngine.getGameEngine();
        gameEngine.setWindowAttribute(1400,800,"GameDemo");
        gameEngine.setFps(60);
        gameEngine.init();
        WindowController.getInstance().setWindowIcon("/Image/icon.png");

        SceneController.register(new SecondScene());
        SceneController.register(new ThirdScene());
        SceneController.register(new FirstScene());

        gameEngine.start();
    }
}
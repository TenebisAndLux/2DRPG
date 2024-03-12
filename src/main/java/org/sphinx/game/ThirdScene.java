package org.sphinx.game;

import org.sphinx.engine.Camera;
import org.sphinx.engine.Renderer;
import org.sphinx.engine.Scene;
import org.sphinx.engine.Vector2D;
import org.sphinx.util.Utils;

public class ThirdScene implements Scene {
    @Override
    public void initScene() {
        GameCamera gameCamera = new GameCamera(1400, 800);
        gameCamera.setZoom(3.2f);
        Renderer.setActiveCamera(gameCamera);
        BackGround2 backGround2 = new BackGround2();
        backGround2.transform.position = new Vector2D(-80, 50);
        Player player = new Player();
        player.transform.position = new Vector2D(0,-650);
        gameCamera.target = player;
        Enemy enemy = new Enemy();
        EnemyController enemyController = new EnemyController();
        ExitMenu exitMenu = new ExitMenu();
        exitMenu.isDisable = false;
    }
}

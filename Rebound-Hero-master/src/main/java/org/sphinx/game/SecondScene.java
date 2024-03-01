package org.sphinx.game;

import org.sphinx.engine.Renderer;
import org.sphinx.engine.Scene;
import org.sphinx.engine.Vector2D;

public class SecondScene implements Scene {
    @Override
    public void initScene() {
        GameCamera gameCamera = new GameCamera(1400, 800);
        gameCamera.transform.position = new Vector2D(-4320.0444f, 3904.5388f);
        gameCamera.setZoom(4.55f);
        Renderer.setActiveCamera(gameCamera);
        BackGround1 backGround1 = new BackGround1();
        BackCloud backCloud = new BackCloud();

        Player player = new Player();
        player.transform.position = new Vector2D(-4520.0444f,-310.72577f);

        NPC npc = new NPC();

        gameCamera.target = player;
        gameCamera.backGround = backGround1.sprite;

        StartMenu startMenu = new StartMenu();
        ExitMenu exitMenu = new ExitMenu();
    }
}

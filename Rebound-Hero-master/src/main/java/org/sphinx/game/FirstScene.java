package org.sphinx.game;

import org.sphinx.engine.*;


public class FirstScene implements Scene{

    @Override
    public void initScene() {
        GameCamera gameCamera = new GameCamera(1400, 800);
        gameCamera.setZoom(4.55f);
        gameCamera.transform.position = new Vector2D(6900,-820);
        Renderer.setActiveCamera(gameCamera);
        BackGround1 backGround1 = new BackGround1();

        Player player = new Player();
        player.transform.position = new Vector2D(6900,-820);
        player.isPlayerControl = false;

        NPC npc = new NPC();

        gameCamera.target = player;
        gameCamera.backGround = backGround1.sprite;

        DirectStart directStart = new DirectStart();

        ExitMenu exitMenu = new ExitMenu();
        exitMenu.isDisable = false;
    }
}

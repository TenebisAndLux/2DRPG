package org.sphinx.game;

import org.sphinx.engine.*;

import java.util.Random;

class Cloud extends GameObject{
    float speed;
    Sprite sprite;
    @Override
    public void start() {
        this.name = "Cloud";
        this.tag = "BackGround";
        speed = new Random().nextFloat(3,10);
        String str = "/Image/BackGround/Cloud/ (" + new Random().nextInt(1,12) + ").png";
        sprite = new Sprite(this,str, Render.UsageType.Item);
        sprite.setLayout(15);
        transform.scale = new Vector2D(16,16);
        transform.position = new Vector2D(-4320.0444f+new Random().nextInt(-4000,4000),3904.5388f+new Random().nextInt(-1500,1500));
    }

    @Override
    public void update() {
        transform.position.x += speed;
        if (this.transform.position.x > -4320.0444f+4000){
            this.transform.position.x = -4320.0444f-4000;
        }
    }

    @Override
    public void enable() {

    }

    @Override
    public void disable() {

    }
}

public class BackCloud extends GameObject {
    Sprite sprite;

    @Override
    public void start() {
        this.name = "BackCloud";
        this.tag = "BackGround";
        sprite = new Sprite(this, "/Image/BackGround/BackSky.png", Render.UsageType.Item);
        transform.scale = new Vector2D(18,18);
        transform.position = new Vector2D(0,800);

        for (int i = 0; i < 20; i++) {
            Cloud cloud = new Cloud();

        }
    }

    @Override
    public void update() {

    }

    @Override
    public void enable() {

    }

    @Override
    public void disable() {

    }
}

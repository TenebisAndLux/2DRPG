package org.sphinx.game;

import org.sphinx.engine.*;
import org.sphinx.ui.Canvas;
import org.sphinx.ui.Lable;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BackGround2 extends GameObject implements Collision{
    Sprite sprite;
    Rigidbody rigidbody;
    BoxCollider collider;
    Lable lable;
    Canvas canvas;
    GameTimer gameTimer;
    List<BoxCollider> boxColliders;
    BoxCollider trigger;
    int frame = 0;
    @Override
    public void start() {
        this.name = "BackGround2";
        this.tag = "floor";
        sprite = new Sprite(this,"/Image/BackGround/ThirdMap.png", Render.UsageType.Item);
        transform.scale = new Vector2D(2.0f,2.4f);
        rigidbody = new Rigidbody(this);
        rigidbody.setGravity(false);
        gameTimer = new GameTimer();
        canvas = new Canvas(0,0,30,30);
        canvas.setColor(new Color(0,0,0,0));
        lable = new Lable(canvas,100,20);

        boxColliders = new ArrayList<>(2);
        boxColliders.add(0, new BoxCollider(this, rigidbody, new Vector2D(0, -1290), 9000, 250));
        boxColliders.add(1, new BoxCollider(this, rigidbody, new Vector2D(2450, -600), 250, 1050));
        boxColliders.add(2, new BoxCollider(this, rigidbody, new Vector2D(-2250, -600), 250, 1050));

        trigger = new BoxCollider(this,rigidbody,new Vector2D(-2200,-800),300,530);
        trigger.isTrigger = true;
    }

    @Override
    public void update() {
        frame++;
        if (gameTimer.time > 1){
            lable.setText("FPS : "+ (int)(frame / gameTimer.time),20, Color.white);
            gameTimer.time-=1;
            frame = 0;
        }
    }

    @Override
    public void enable() {

    }

    @Override
    public void disable() {

    }

    @Override
    public void onTriggerEnter(Collider collider) {
        if (collider.getGameObject().tag.equals("Player"))
            SceneController.loadNextScene();
    }

    @Override
    public void onTriggerUpdate(Collider collider) {

    }

    @Override
    public void onTriggerExit(Collider collider) {

    }

    @Override
    public void onCollisionEnter(Collider collider) {

    }
}

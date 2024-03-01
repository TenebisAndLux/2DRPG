package org.sphinx.game;

import org.sphinx.engine.*;
import org.sphinx.ui.Canvas;
import org.sphinx.ui.Lable;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BackGround1 extends GameObject implements Collision{
    Sprite sprite;
    Lable lable;
    Canvas canvas;
    GameTimer gameTimer = new GameTimer();
    List<BoxCollider> boxColliders;
    BoxCollider trigger;
    Rigidbody rigidbody;
    int frame = 0;

    @Override
    public void start() {
        sprite = new Sprite(this, "/Image/BackGround/Ground.jpg", Render.UsageType.Item);
        sprite.setLayout(1);
        transform.scale = new Vector2D(7,7);
        this.name = "BackGround1";
        this.tag = "floor";
        canvas = new Canvas(0,0,30,30);
        canvas.setColor(new Color(0,0,0,0));
        lable = new Lable(canvas,100,20);
        rigidbody = new Rigidbody(this);
        rigidbody.setGravity(false);

        boxColliders = new ArrayList<>(28);
        boxColliders.add(0, new BoxCollider(this, rigidbody, new Vector2D(-8950, -4010), 10, 9000));
        boxColliders.add(1, new BoxCollider(this, rigidbody, new Vector2D(-8940, -820), 1650, 1050));
        boxColliders.add(2, new BoxCollider(this, rigidbody, new Vector2D(-5640, -1690), 2750, 2216));
        boxColliders.add(3, new BoxCollider(this, rigidbody, new Vector2D(-3455, -1020), 1750, 936));
        boxColliders.add(4, new BoxCollider(this, rigidbody, new Vector2D(-2355, -430), 800, 800));
        boxColliders.add(5, new BoxCollider(this, rigidbody, new Vector2D(-1830, -430), 250, 250));
        boxColliders.add(6, new BoxCollider(this, rigidbody, new Vector2D(7850, -1685), 300, 5430));
        boxColliders.add(7, new BoxCollider(this, rigidbody, new Vector2D(-7720, -970), 775, 750));
        boxColliders.add(8, new BoxCollider(this, rigidbody, new Vector2D(-8000, -2030), 2000, 250));
        boxColliders.add(9, new BoxCollider(this, rigidbody, new Vector2D(-7045, -1300), 70, 40));
        boxColliders.add(10, new BoxCollider(this, rigidbody, new Vector2D(-8160, -1600), 10, 800));
        boxColliders.add(11, new BoxCollider(this, rigidbody, new Vector2D(-3970, -430), 800, 800));
        boxColliders.add(12, new BoxCollider(this, rigidbody, new Vector2D(-1580, -680), 250, 250));
        boxColliders.add(13, new BoxCollider(this, rigidbody, new Vector2D(-460, -940), 2000, 250));
        boxColliders.add(14, new BoxCollider(this, rigidbody, new Vector2D(-1035, 380), 800, 255));
        boxColliders.add(15, new BoxCollider(this, rigidbody, new Vector2D(1100, -1100), 800, 1600));
        boxColliders.add(16, new BoxCollider(this, rigidbody, new Vector2D(580, -680), 250, 250));
        boxColliders.add(17, new BoxCollider(this, rigidbody, new Vector2D(-820, 1450), 2500, 250));
        boxColliders.add(18, new BoxCollider(this, rigidbody, new Vector2D(-1830, 1200), 250, 250));
        boxColliders.add(19, new BoxCollider(this, rigidbody, new Vector2D(2830, -700), 2100, 800));
        boxColliders.add(20, new BoxCollider(this, rigidbody, new Vector2D(2350, -2040), 1850, 250));
        boxColliders.add(21, new BoxCollider(this, rigidbody, new Vector2D(3250, -1500), 250, 800));
        boxColliders.add(22, new BoxCollider(this, rigidbody, new Vector2D(1750, -770), 70, 40));
        boxColliders.add(23, new BoxCollider(this, rigidbody, new Vector2D(1525, -1260), 70, 40));
        boxColliders.add(24, new BoxCollider(this, rigidbody, new Vector2D(2450, -160), 800, 255));
        boxColliders.add(25, new BoxCollider(this, rigidbody, new Vector2D(2450, 100), 255, 255));
        boxColliders.add(26, new BoxCollider(this, rigidbody, new Vector2D(4010, -680), 255, 255));
        boxColliders.add(27, new BoxCollider(this, rigidbody, new Vector2D(4295, -950), 255, 255));
        boxColliders.add(28, new BoxCollider(this, rigidbody, new Vector2D(6130, -1220), 3350, 255));

        trigger = new BoxCollider(this,rigidbody,new Vector2D(7150,-820),300,530);
        trigger.isTrigger = true;
    }

    @Override
    public void update() {
        frame++;
        if (gameTimer.time > 1){
            lable.setText("FPS : "+ (int)(frame / gameTimer.time),20, Color.white);
            //System.out.println("FPS : "+ (int)(frame / gameTimer.time));
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
            SceneController.loadSceneIndex(1);
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

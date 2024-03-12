package org.sphinx.game;

import org.sphinx.engine.*;

import java.util.*;

public class Enemy extends GameObject implements Collision{
    static final int maxHp = 10;
    int mHp;
    Sprite sprite;
    Animator animator;
    Rigidbody rigidbody;
    CircleCollider collider;
    GameTimer protectedTimer;
    boolean isProtected = false;
    static final List<String> NAMES = Arrays.asList("Goblin","Mushroom","Skeleton");
    @Override
    public void start() {
        this.name = "Enemy";
        this.tag = "Enemy";

        transform.scale = new Vector2D(12,12);

        String str = "/Image/Monster/"+NAMES.get(new Random().nextInt(0, NAMES.size()));
        String str1 = str+"/Idle.png";
        String str2 = str+"/Take Hit.png";
        SplitTexture splitTexture = new SplitTexture(str1,4,1);
        SplitTexture splitTexture1 = new SplitTexture(str2,4,1);
        sprite = new Sprite(this,splitTexture.getTexture(0), Render.UsageType.Item);
        animator = new Animator(this,sprite);
        animator.createAction("Idle",0.2f, Animator.Policy.instant, Animator.Type.loop,splitTexture.getTextureList());
        animator.createAction("Hurt",0.2f, Animator.Policy.instant, Animator.Type.loop,splitTexture1.getTextureList());

        rigidbody = new Rigidbody(this);
        rigidbody.setMass(10);
        collider = new CircleCollider(this,rigidbody,new Vector2D(0,-225),80);

        protectedTimer = new GameTimer();
        protectedTimer.isFreeze = true;
    }

    @Override
    public void update() {
        if (protectedTimer.time > .2f){
            isProtected = false;
            protectedTimer.isFreeze = false;
            sprite.isFlashing =false;
        }

    }

    @Override
    public void enable() {
        int step = 500;
        int randLeft = new Random().nextInt(-2250,-250);
        int randRight= new Random().nextInt(250,2300);
        if ((new Random().nextInt()) % 2 == 0){
            transform.position = new Vector2D(randLeft - randLeft % step + 250,-700);
        } else {
            transform.position = new Vector2D(randRight - randRight % step + 250,-700);
        }
        mHp = maxHp;
        EnemyController.enemyCount++;
    }

    @Override
    public void disable() {
        EnemyController.enemyCount--;
    }

    public void hurt(Vector2D vector){
        if (!isProtected) {
            this.mHp--;
            isProtected = true;
            protectedTimer.isFreeze = false;
            protectedTimer.reset();
            this.rigidbody.addForce(vector.multiplied(.08f));
            sprite.isFlashing =true;
        }
        flip();
        if (mHp <= 0)
        {
            setActive(false);
        }

    }

    void flip(){
        if (GameObject.findGameObjects("Player").get(0).transform.position.x < transform.position.x)
            this.transform.scale.x = -Math.abs(this.transform.scale.x);
        else
            this.transform.scale.x = Math.abs(this.transform.scale.x);
    }

    @Override
    public void onTriggerEnter(Collider collider) {

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

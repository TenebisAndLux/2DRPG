package org.sphinx.game;

import org.lwjgl.glfw.GLFW;
import org.sphinx.engine.*;
import org.sphinx.util.Debug;

class FloorCheck extends GameObject implements Collision{
    CircleCollider collider;
    Rigidbody rigidbody;
    Player player;
    FloorCheck(Player player){
        this.player = player;
        setParent(player);
    }
    @Override
    public void start() {
        tag = "PlayerChild";
        name = "Player.FloorCheck";
        rigidbody =  new Rigidbody(this);
        rigidbody.setGravity(false);
        collider = new CircleCollider(this,rigidbody,50);
        collider.isTrigger = true;
        transform.position = new Vector2D(-88,-220);
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

    @Override
    public void onTriggerEnter(Collider collider) {
        if (collider.getGameObject().tag .equals("floor")) {
            player.isOnFloor = true;
        }
    }

    @Override
    public void onTriggerUpdate(Collider collider) {

    }

    @Override
    public void onTriggerExit(Collider collider) {
        if (collider.getGameObject().tag .equals("floor")) {
            player.isOnFloor = false;
        }
    }

    @Override
    public void onCollisionEnter(Collider collider) {

    }
}

class AttackCheck extends GameObject implements Collision{
    Player player;
    BoxCollider collider;
    Rigidbody rigidbody;
    GameTimer gameTimer;
    boolean isRight = true;
    boolean attack1 = true;
    AttackCheck(Player player){
        this.player = player;
        setParent(player);
    }

    @Override
    public void start() {
        tag = "PlayerChild";
        name = "Player.AttackCheck";
        rigidbody = new Rigidbody(this);
        rigidbody.setGravity(false);
        collider = new BoxCollider(this,rigidbody,330,450);
        collider.isTrigger = true;
        gameTimer = new GameTimer();
        setActive(false);
    }

    @Override
    public void update() {
        if (gameTimer.time > 0.6 && attack1){
            setActive(false);
        }
        if (gameTimer.time > 0.4 && !attack1){
            setActive(false);
        }
    }

    @Override
    public void enable() {
        gameTimer.reset();
        if (isRight){
            collider.setOffset(new Vector2D(200, 0));
        }
        else {
            collider.setOffset(new Vector2D(-360, 0));
        }
    }

    @Override
    public void disable() {
        player.isAttacking = false;
    }

    @Override
    public void onTriggerEnter(Collider collider) {
        if (collider.getGameObject().tag.equals("Enemy")){
            Enemy gameObject = (Enemy) collider.getGameObject();
            gameObject.hurt(collider.getGameObject().getPosition().added(player.getPosition().reversed()));
            //gameObject.hurt(new Vector2D(collider.getGameObject().getPosition().x,0).added(new Vector2D(player.getPosition().x,0).reversed()));
        }
    }

    @Override
    public void onTriggerUpdate(Collider collider) {
        if (collider.getGameObject().tag.equals("Enemy")){
            System.out.println("ha");
        }
    }

    @Override
    public void onTriggerExit(Collider collider) {

    }

    @Override
    public void onCollisionEnter(Collider collider) {

    }
}

public class Player extends GameObject implements Collision{

    Sprite sprite;
    Animator animator;
    Rigidbody rigidbody;
    CircleCollider collider;
    Boolean isTackling = false, isJumped = false , isMoving = false , isJumping = false, isOnFloor = false,isAttacking = false,isAttack1 =false, isAttack2 = false, isPlayerControl = true;
    GameTimer attackTimer;
    GameTimer tacklingTimer;
    float speed = 4;
    @Override
    public void start(){
        System.out.println("player.start()");
        tag = "Player";
        name = "Player";
        transform.scale = new Vector2D(12,12);

        SplitTexture splitTexture = new SplitTexture("/Image/Hero/Warrior_Sheet-Effect.png", 6, 17);
        sprite = new Sprite(this, splitTexture.getTexture(0), Render.UsageType.Item);
        sprite.setLayout(2);

        rigidbody = new Rigidbody(this);
        rigidbody.setGravity(true);
        rigidbody.setMass(10);
        collider = new CircleCollider(this,rigidbody,new Vector2D(-88,-180), 60);

        animator = new Animator(this,sprite);

        animator.createAction("idle", 0.2f, Animator.Policy.instant,Animator.Type.loop,splitTexture.getTextures(0,5));

        animator.createAction("running", 0.1f, Animator.Policy.instant,Animator.Type.loop,splitTexture.getTextures(6,14));
        animator.createAction("sliding", 0.1f, Animator.Policy.instant,Animator.Type.once,splitTexture.getTextureIndex(75,73,74,75));
        animator.createAction("jumping",0.1f,Animator.Policy.instant,Animator.Type.loop,splitTexture.getTextures(41,43));
        animator.createAction("falling-mid",0.05f,Animator.Policy.instant,Animator.Type.once,splitTexture.getTextures(44,45));
        animator.createAction("falling",0.1f,Animator.Policy.instant,Animator.Type.loop,splitTexture.getTextures(46,48));
        animator.createAction("tackling",0.1f,Animator.Policy.instant,Animator.Type.loop,splitTexture.getTextureIndex(84,85,86,87,89,90));

        animator.createAction("attack-1",0.1f,Animator.Policy.instant, Animator.Type.once,splitTexture.getTextureIndex(15,16,17,18,19,20,21,22,18));
        animator.createAction("attack-2",0.1f,Animator.Policy.instant, Animator.Type.once,splitTexture.getTextureIndex(22,23,24,25,25,15,16,17,18));
        FloorCheck floorCheck = new FloorCheck(this);
        AttackCheck attackCheck = new AttackCheck(this);
        attackTimer = new GameTimer();
        tacklingTimer = new GameTimer();
    }

    @Override
    public void update(){
        if(!isPlayerControl) return;
        move();
        jump();
        tackle();
        attack();
        animateSwitch();
        if (GLFW.glfwGetKey(WindowController.getInstance().window, GLFW.GLFW_KEY_K)== GLFW.GLFW_PRESS){

        } else if (GLFW.glfwGetKey(WindowController.getInstance().window, GLFW.GLFW_KEY_F1) == GLFW.GLFW_PRESS) {
            Debug.setDebugMod(true);
        } else if (GLFW.glfwGetKey(WindowController.getInstance().window, GLFW.GLFW_KEY_F2) == GLFW.GLFW_PRESS) {
            Debug.setDebugMod(false);
        }

        if (GLFW.glfwGetKey(WindowController.getInstance().window, GLFW.GLFW_KEY_H)==GLFW.GLFW_PRESS){
            rigidbody.setGravity(true);
        }
        if (GLFW.glfwGetKey(WindowController.getInstance().window, GLFW.GLFW_KEY_J)==GLFW.GLFW_PRESS){
            rigidbody.setGravity(false);
        }
        if (GLFW.glfwGetKey(WindowController.getInstance().window, GLFW.GLFW_KEY_F11)==GLFW.GLFW_PRESS){
            Debug.log("transform.position = " + transform.position);
        }
        if (GLFW.glfwGetKey(WindowController.getInstance().window, GLFW.GLFW_KEY_G)==GLFW.GLFW_PRESS){
            transform.position = new Vector2D(0,0);
        }
    }

    private void attack() {
        if (EventSystem.getMouseButton1() && isOnFloor && !isAttacking) {
            isAttacking = true;
            attackTimer.reset();
            if (!isAttack1){
                isAttack1 = true;
                isAttack2 = false;
            }
            else {
                isAttack1 = false;
                isAttack2 = true;
            }
            rigidbody.addForce(new Vector2D(Math.signum(transform.scale.x)*speed*3,0));
        }
        if (isAttacking) {
            ((AttackCheck) getChildren().get(1)).attack1 = isAttack1;
            if (isAttack1 && attackTimer.time > 0.3f) {
                getChildren().get(1).setActive(true);
            } else if (isAttack2) {
                getChildren().get(1).setActive(true);
            }
        }
        if ((isAttack1||isAttack2) && attackTimer.time > 1.2f) {
            isAttack1 = false;
            isAttack2 = false;
        }
    }

    private void tackle(){
        if (isAttacking) return;
        if (isJumping) return;

        if (GLFW.glfwGetKey(WindowController.getInstance().window, GLFW.GLFW_KEY_LEFT_CONTROL)==GLFW.GLFW_PRESS &&
                GLFW.glfwGetKey(WindowController.getInstance().window, GLFW.GLFW_KEY_A)==GLFW.GLFW_PRESS && !isTackling && isOnFloor){
            float progress = (float) Math.min(tacklingTimer.time / 0.6f, 1.0f);  // Определение прогресса перемещения
            float speed = 150 * progress;  // Изменение скорости в зависимости от прогресса перемещения
            rigidbody.addForce(new Vector2D(-speed, 0));
            isTackling = true;
            tacklingTimer.reset();
        }

        else if (GLFW.glfwGetKey(WindowController.getInstance().window, GLFW.GLFW_KEY_LEFT_CONTROL)==GLFW.GLFW_PRESS &&
                GLFW.glfwGetKey(WindowController.getInstance().window, GLFW.GLFW_KEY_D)==GLFW.GLFW_PRESS && !isTackling && isOnFloor){
            float progress = (float) Math.min(tacklingTimer.time / 0.6f, 1.0f);  // Определение прогресса перемещения
            float speed = 150 * progress;  // Изменение скорости в зависимости от прогресса перемещения
            rigidbody.addForce(new Vector2D(speed, 0));
            isTackling = true;
            tacklingTimer.reset();
        }

        else if (isTackling && tacklingTimer.time >= 0.6f) {
            isTackling = false;
        }
    }

    @Override
    public void enable() {
        System.out.println("player.enable()");
    }

    @Override
    public void disable() {

    }
    private void move(){
        isMoving = false;

        if (isAttacking) return;
        if (isTackling) return;

        Vector2D moveVector = new Vector2D();

        if (GLFW.glfwGetKey(WindowController.getInstance().window, GLFW.GLFW_KEY_A)==GLFW.GLFW_PRESS){
            moveVector.x = -1;
            flip(false);
            isMoving = true;
        }
        if (GLFW.glfwGetKey(WindowController.getInstance().window, GLFW.GLFW_KEY_D)==GLFW.GLFW_PRESS){
            moveVector.x = 1;
            flip(true);
            isMoving = true;
        }

        if (GLFW.glfwGetKey(WindowController.getInstance().window, GLFW.GLFW_KEY_LEFT_SHIFT)==GLFW.GLFW_PRESS) {
            moveVector.y = -1;
        }
        if (GLFW.glfwGetKey(WindowController.getInstance().window, GLFW.GLFW_KEY_UP)==GLFW.GLFW_PRESS) {
            moveVector.y = 1;
        }
        moveVector.normalize();
        if (moveVector.getLength() != 0)
            rigidbody.addForce( new Vector2D(moveVector.multiplied(speed)));

    }
    private void jump(){
        if (isAttacking) return;
        if (isTackling) return;

        if (GLFW.glfwGetKey(WindowController.getInstance().window, GLFW.GLFW_KEY_SPACE)==GLFW.GLFW_PRESS && !isJumped && isOnFloor){
            rigidbody.addForce(new Vector2D(0,150));
            isJumped = true;
            isJumping = true;
        }
        else if (isOnFloor){
            isJumped = false;
            isJumping = false;
        }
    }

    private void flip(boolean isRight){
        if(isRight) {
            sprite.offset = new Vector2D(0, 0);
            transform.scale.x = Math.abs(transform.scale.x);
        }
        else {
            sprite.offset = new Vector2D(-150, 0);
            transform.scale.x = -Math.abs(transform.scale.x);
        }
        ((AttackCheck) getChildren().get(1)).isRight = isRight;
    }
    private void animateSwitch() {
        if (isJumping || rigidbody.velocity.y < -10) {
            if (rigidbody.velocity.y > 0) {
                animator.setAction("jumping");
            } else {
                if (animator.getCurrentAction().equals("falling-mid") && animator.isFinished())
                    animator.setAction("falling");
                else if (!animator.getLastAction().equals("falling-mid"))
                    animator.setAction("falling-mid");
            }
        } else if ((animator.getCurrentAction().equals("falling") || animator.getCurrentAction().equals("falling-mid")) && isOnFloor) {
            animator.setAction("sliding");
        } else if (isMoving)
            animator.setAction("running");
        else if (animator.getCurrentAction().equals("running") && !isMoving) {
            animator.setAction("sliding");
        }

        if (isTackling)
            animator.setAction("tackling");
        else if (animator.getCurrentAction().equals("tackling") && !isTackling){
            animator.setAction("idle");
        }

        if(isAttacking)
            if (isAttack1){
                animator.setAction("attack-1");
            }
            else if (isAttack2){
                animator.setAction("attack-2");
            }
        if ((animator.getCurrentAction().equals("sliding") ||animator.getCurrentAction().equals("attack-1") ||  animator.getCurrentAction().equals("attack-2")) && animator.isFinished()) {
            animator.setAction("idle");
        }
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

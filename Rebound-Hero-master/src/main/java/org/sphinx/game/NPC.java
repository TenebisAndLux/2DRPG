package org.sphinx.game;

import org.lwjgl.glfw.GLFW;
import org.sphinx.engine.*;
import org.sphinx.ui.Canvas;
import org.sphinx.ui.Dialog;
import org.sphinx.ui.Lable;

import java.awt.*;


public class NPC extends GameObject{
    Sprite sprite;
    Animator animator;
    Canvas canvas;
    Lable lable;
    Dialog dialog;
    Rigidbody rigidbody;
    class Talk extends GameObject{

        @Override
        public void start() {

        }

        @Override
        public void update() {

        }

        @Override
        public void enable() {
            NPC.this.dialog.setText("%80%#40#Hey, Tenbris！%160% How are you doing?");
            canvas.setActive(true);
        }

        @Override
        public void disable() {
            NPC.this.dialog.setText("");
            canvas.setActive(false);
        }
    }
    Talk talk;

    boolean isCanTalk = false;
    @Override
    public void start() {
        SplitTexture splitTexture = new SplitTexture("/Image/NPC/HeroKnight.png",10,9);
        sprite = new Sprite(this,splitTexture.getTexture(0), Render.UsageType.Item);
        sprite.setLayout(16);
        animator = new Animator(this,sprite);
        animator.createAction("Idle",0.2f, Animator.Policy.instant, Animator.Type.loop,splitTexture.getTextures(0,8));
        transform.scale = new Vector2D(10,10);
        transform.position = new Vector2D(-5820.0444f,-310.72577f);

        talk = new Talk();
        talk.setActive(false);

        canvas = new Canvas(100,WindowController.getInstance().getWindowHeight()-330,WindowController.getInstance().getWindowWidth()-200, 300);
        canvas.setColor(new Color(0,0,0,0.3f));
        lable =new Lable(canvas,300,100);
        lable.setText("Hero guider",30,new Color(184, 134, 11));

        dialog = new Dialog(canvas, (int) canvas.getWidth(), (int) canvas.getHeight());
        dialog.transform.position = new Vector2D(20,35);

        rigidbody = new Rigidbody(this);
        rigidbody.setGravity(false);

        canvas.setActive(false);
   }

    @Override
    public void update() {
        if (GameObject.findGameObjects("Player").get(0).transform.position.x < transform.position.x)
            this.transform.scale.x = -Math.abs(this.transform.scale.x);
        else
            this.transform.scale.x = Math.abs(this.transform.scale.x);

        isCanTalk = getPosition().getDistance(GameObject.findGameObjects("Player").get(0).getPosition()) < 1000;
        if (isCanTalk){
            if (GLFW.glfwGetKey(WindowController.getInstance().window, GLFW.GLFW_KEY_E)== GLFW.GLFW_PRESS){
                talk.setActive(true);
            }
        }
        else {
            talk.setActive(false);
        }
    }

    @Override
    public void enable() {

    }

    @Override
    public void disable() {

    }

}

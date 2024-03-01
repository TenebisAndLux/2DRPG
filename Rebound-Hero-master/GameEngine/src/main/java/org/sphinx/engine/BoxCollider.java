package org.sphinx.engine;

import org.sphinx.util.Debug;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class BoxCollider extends Collider{
    float width;
    float height;
    public final Vector2D[] vectors = new Vector2D[4];
    public BoxCollider(Collision collision, Rigidbody rigidbody, float width, float height) {
        this(collision,rigidbody,new Vector2D(),width,height);
    }
    public BoxCollider(Collision collision, Rigidbody rigidbody, Vector2D offset, float width, float height) {
        super(collision, rigidbody, "Box");
        setOffset(offset);
        this.width = width;
        this.height = height;
        vectors[0] = new Vector2D(offset.x-width/2,offset.y - height / 2);
        vectors[1] = new Vector2D(offset.x+width/2,offset.y - height / 2);
        vectors[2] = new Vector2D(offset.x+width/2,offset.y + height / 2);
        vectors[3] = new Vector2D(offset.x-width/2,offset.y + height / 2);
        painter = new Painter(rigidbody.gameObject) {
            @Override
            public void draw() {
                if(!Debug.isIsDebugging()) return;
                this.type = UsageType.Item;
                setOutLineColor(Color.RED);
                this.setLayout(14);
                drawQuadsOutLine(rigidbody.getGameObject().getPosition().x + vectors[0].x, rigidbody.getGameObject().getPosition().y + vectors[0].y,width, height);
            }
        };
    }

    @Override
    protected void colliderUpdate() {
        vectors[0] = new Vector2D(offset.x-width/2,offset.y - height / 2);
        vectors[1] = new Vector2D(offset.x+width/2,offset.y - height / 2);
        vectors[2] = new Vector2D(offset.x+width/2,offset.y + height / 2);
        vectors[3] = new Vector2D(offset.x-width/2,offset.y + height / 2);
        for (List<Component> component : components.get(Collider.class).values()) {
            for (Component component1 : component) {
                Collider collider = (Collider) component1;
                if (collider.collision != this.collision && collider.gameObject.isEnable()){
                    switch (collider.currentType) {
                        case "Circle" -> circleCollided((CircleCollider) collider);
                        case "Box" -> boxCollided((BoxCollider) collider);
                    }
                }
            }
        }
    }

    private void boxCollided(BoxCollider collider) {

    }

    private void circleCollided(CircleCollider collider) {

    }

    public Vector2D[] getVectors() {
        Vector2D[] vectors= new Vector2D[4];
        for (int i = 0; i < this.vectors.length; i++) {
            vectors[i] = this.vectors[i].added(getGameObject().getPosition());
        }
        return vectors;
    }
}

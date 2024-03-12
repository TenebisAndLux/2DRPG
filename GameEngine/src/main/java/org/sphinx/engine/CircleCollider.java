package org.sphinx.engine;

import org.sphinx.util.Debug;

import java.awt.*;
import java.util.List;

public class CircleCollider extends Collider{
    public float r;

    public CircleCollider(Collision collision, Rigidbody rigidbody,float r) {
        this(collision,rigidbody,new Vector2D(),r);
    }
    public CircleCollider(Collision collision, Rigidbody rigidbody,Vector2D offset,float r) {
        super(collision, rigidbody, "Circle");
        this.r = r;
        setOffset(offset);
        painter = new Painter(rigidbody.gameObject) {
            @Override
            public void draw() {
                if (!Debug.isIsDebugging()) return;
                this.type = UsageType.Item;
                setOutLineColor(color);
                this.setLayout(14);
                drawCircleOutLine(rigidbody.gameObject.getPosition().x+ offset.x,rigidbody.gameObject.getPosition().y+ offset.y,r);
            }
        };
    }
    @Override
    protected void colliderUpdate() {
        OUT:
        for (List<Component> component : components.get(Collider.class).values()) {
            for (Component component1 : component) {
                Collider collider = (Collider) component1;
                if (collider.collision != this.collision&&!collider.gameObject.getAncestor().contains(this.gameObject) && collider.gameObject.isEnable()){
                    switch (collider.currentType) {
                        case "Circle" -> circleCollided((CircleCollider) collider);
                        case "Box" -> boxCollided((BoxCollider) collider);
                    }
                }
                ////////////
                if (SceneController.isLoadingNextScene)
                    break OUT;
                ///////////
            }
        }
    }
    private void circleCollided(CircleCollider collider){
        Vector2D currentCollider = this.gameObject.getPosition().added(offset);
        Vector2D currentColliderVector = currentCollider.added(this.rigidbody.velocity);
        Vector2D colliderVector = collider.gameObject.getPosition().added(collider.offset).added(collider.rigidbody.velocity);
        float distance = currentColliderVector.getDistance(colliderVector);
        if (distance <= this.r + collider.r) {
            collided(collider);
            if (!collider.isTrigger&&!isTrigger) {
                if (currentCollider.added(new Vector2D(this.rigidbody.velocity.x,0)).getDistance(colliderVector) <=this.r + collider.r){
                    rigidbody.velocity.x = 0;
                }
                if (currentCollider.added(new Vector2D(0,this.rigidbody.velocity.y)).getDistance(colliderVector) <=this.r + collider.r){
                    rigidbody.velocity.y = 0;
                }
            }
            color = Color.red;
        }
        else{
            shakeOff(collider);
            collider.shakeOff(this);/////////////
            color = Color.green;
        }
    }
    private void boxCollided(BoxCollider collider){
        Vector2D vector2D = this.gameObject.getPosition().added(offset).added(rigidbody.velocity);
        Vector2D vector2Dx = this.gameObject.getPosition().added(offset).added(new Vector2D(this.rigidbody.velocity.x,0));
        Vector2D vector2Dy = this.gameObject.getPosition().added(offset).added(new Vector2D(0,this.rigidbody.velocity.y));

        boolean sign = false;
        for (int i = 0; i < collider.getVectors().length; i++) {
            if (PointToSegDist(vector2D, collider.getVectors()[i], collider.getVectors()[(i + 1) % collider.getVectors().length]) < r) {
                color = Color.red;
                if (!(collider.isTrigger || isTrigger)) {
                    for (int j = 0; j < collider.getVectors().length; j++) {
                        if (PointToSegDist(vector2Dx, collider.getVectors()[j], collider.getVectors()[(j + 1) % collider.getVectors().length]) < r) {
                            rigidbody.velocity.x = 0;
                            break;
                        }
                    }
                    for (int j = 0; j < collider.getVectors().length; j++) {
                        if (PointToSegDist(vector2Dy, collider.getVectors()[j], collider.getVectors()[(j + 1) % collider.getVectors().length]) < r) {
                            rigidbody.velocity.y = 0;
                            break;
                        }
                    }
                }
                collided(collider);
                collider.shakeOff(this);/////////////
                sign = true;
            }
        }
        if (!sign){
            color = Color.green;
            shakeOff(collider);
            collider.shakeOff(this);/////////////
        }
    }

    double PointToSegDist(Vector2D origin, Vector2D a,Vector2D b)
    {
        double cross = (b.x - a.x) * (origin.x - a.x) + (b.y - a.y) * (origin.y - a.y);
        if (cross <= 0) return Math.sqrt((origin.x - a.x) * (origin.x - a.x) + (origin.y - a.y) * (origin.y - a.y));
        double d2 = (b.x - a.x) * (b.x - a.x) + (b.y - a.y) * (b.y - a.y);
        if (cross >= d2) return Math.sqrt((origin.x - b.x) * (origin.x - b.x) + (origin.y - b.y) * (origin.y - b.y));
        double r = cross / d2;
        double px = a.x + (b.x - a.x) * r;
        double py = a.y + (b.y - a.y) * r;
        return Math.sqrt((origin.x - px) * (origin.x - px) + (origin.y - py) * (origin.y - py));
    }
}

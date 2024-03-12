package org.sphinx.engine;

import org.sphinx.util.Debug;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class Collider extends Component {
    protected Rigidbody rigidbody;
    protected Collision collision;
    public boolean isTrigger = false;
    protected String currentType;
    protected Vector2D offset = new Vector2D();
    protected List<Collider> colliderInTriggerList = new ArrayList<>();
    public Painter painter;
    Color color = Color.GREEN;

    protected Collider(Collision collision,Rigidbody rigidbody,String currentType) {
        super(rigidbody.gameObject, Collider.class);
        this.rigidbody = rigidbody;
        this.collision = collision;
        this.currentType = currentType;
    }
    protected abstract void colliderUpdate();

    static void update(){
        OUT:
        for (List<Component> component : components.get(Collider.class).values()) {
            for (Component component1 : component) {
                if (component1.gameObject.isEnable()) {
                    ((Collider) component1).colliderUpdate();
                    for (Collider collider : ((Collider) component1).colliderInTriggerList) {
                        if (collider.gameObject.isEnable()) {
                            ((Collider) component1).collision.onTriggerUpdate(collider);
                            ////////////
                            if (SceneController.isLoadingNextScene)
                                break OUT;
                            ///////////
                        }
                    }
                }
            }
        }
    }
    protected void collided(Collider collider){
        if ((isTrigger && !colliderInTriggerList.contains(collider)) || (collider.isTrigger) && !collider.colliderInTriggerList.contains(this)) {
            collision.onTriggerEnter(collider);
            collider.collision.onTriggerEnter(this);
            colliderInTriggerList.add(collider);
            collider.colliderInTriggerList.add(this);
        }
        else {
            collision.onCollisionEnter(collider);
            collider.collision.onCollisionEnter(this);
        }
    }
    protected void shakeOff(Collider collider){
        if (colliderInTriggerList.contains(collider)){
            colliderInTriggerList.remove(collider);
            collision.onTriggerExit(collider);
        }
    }
    public void setOffset(Vector2D offset) {
        this.offset = offset;
    }
    static void destroyAllCollider(){
        Debug.log("Animator----releasing animator");
        components.get(Collider.class).clear();
    }
}




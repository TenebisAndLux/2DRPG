package org.sphinx.engine;

import java.util.List;

public class Rigidbody extends Component{
    private float Mass = 1;
    private boolean isGravity = true;
    public static Vector2D Gravity = new Vector2D(0,-9.81f/1.5f);
    public Vector2D velocity = new Vector2D();
    public Rigidbody(GameObject gameObject){
        super(gameObject, Rigidbody.class);
    }
    public void addForce(Vector2D force) {
        velocity.add(force);
    }

    public void setMass(float mass) {
        Mass = Math.max(1,Math.min(mass,1000));//10000
    }


    public void setGravity(boolean gravity) {
        isGravity = gravity;
    }

    private void rigidbodyUpdate(){
        gameObject.transform.position.add(velocity);
        if (Math.abs(velocity.getLength()) > 0.001)
            velocity.add(velocity.multiplied(-1/Mass));
        else
            velocity = new Vector2D(0,0);
        if (isGravity)
            addForce(Gravity);
    }
    static void update(){
        for (List<Component> componentList : components.get(Rigidbody.class).values()){
            ((Rigidbody) componentList.get(0)).rigidbodyUpdate();
        }
    }
}

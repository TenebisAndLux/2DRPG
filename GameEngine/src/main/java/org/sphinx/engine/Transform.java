package org.sphinx.engine;

public class Transform {
    public Vector2D position = new Vector2D();
    public float rotation = 0;
    public Vector2D scale = new Vector2D(1,1);
    public void addVector(Vector2D vector){
        this.position.x += vector.x;
        this.position.y += vector.y;
    }
    public Transform addedVector(Vector2D vector) {
        Transform transform = new Transform();
        transform.position = new Vector2D(position);
        transform.scale = new Vector2D(scale);
        transform.rotation = rotation;
        transform.position.x = this.position.x + vector.x;
        transform.position.y = this.position.y + vector.y;
        return transform;
    }

}

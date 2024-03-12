package org.sphinx.engine;

import static java.lang.Math.*;

public class Vector2D {
    public float x;
    public float y;
    public Vector2D(){
        x = 0;
        y = 0;
    }
    public Vector2D(float x, float y){
        this.x = x;
        this.y = y;
    }
    public Vector2D(Vector2D vector2D){
        this.x = vector2D.x;
        this.y = vector2D.y;
    }
    public void normalize(){
        float rad = (float)atan2(y,x);
        x = x!=0?(float)cos(rad):0;
        y = (float)sin(rad);
    }
    public Vector2D normalized(){
        Vector2D tempVector = new Vector2D(x,y);
        tempVector.normalize();
        return tempVector;
    }

    public void multiply(float num){
        this.x *= num;
        this.y *= num;
    }
    public Vector2D multiplied(float num){
        Vector2D tempVector = new Vector2D(x,y);
        tempVector.multiply(num);
        return tempVector;
    }
    public Vector2D lengthModify(float length){
        return this.normalized().multiplied(length);
    }
    public void reverse() {
        this.x = -this.x;
        this.y = -this.y;
    }
    public Vector2D reversed() {
        return new Vector2D(-this.x,-this.y);
    }
    public float getDistance(Vector2D vec){
        return (float) sqrt(pow(vec.x-x, 2)+pow(vec.y-y, 2));
    }
    public float getLength(){
        return this.getDistance(new Vector2D(0,0));
    }
    public void rotate(float angle){
        float length = getLength();
        float rad = (float) atan2(y,x);
        x = x!=0? (float) cos(rad+angle):0;
        y = (float) sin(rad+angle);
        this.multiply(length);
    }

    public Vector2D rotated(float angle){
        Vector2D tempVector = new Vector2D(x,y);
        tempVector.rotate(angle);
        return tempVector;

    }
    public void add(Vector2D vector) {
        this.x += vector.x;
        this.y += vector.y;
    }
    public Vector2D added(Vector2D vector) {
        return new Vector2D(this.x + vector.x, this.y + vector.y);
    }
    @Override
    public String toString(){
        return this.x+" "+this.y;
    }
}

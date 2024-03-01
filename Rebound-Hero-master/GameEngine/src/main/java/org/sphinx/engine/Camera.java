package org.sphinx.engine;

public class Camera extends GameObject{
    private int width;
    private int height;

    private float zoom = 1;

    public Camera(int width, int height){
        this.width = width;
        this.height = height;
        tag = "Camera";
        name = "Camera";
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setZoom(float zoom) {
        if (zoom < 0){
            this.zoom=0;
        }
        else {
            this.zoom = zoom;
        }
    }

    public void addZoom(float add) {
        zoom+=add;
        if (zoom < 0.000001){
            zoom = 0.000001f;
        }
    }

    public float getZoom() {
        return zoom;
    }
    @Override
    public void start() {
        System.out.println("camera.start()");
    }

    @Override
    public void update() {
        //System.out.println("camera.update()");
    }

    @Override
    public void enable() {

    }

    @Override
    public void disable() {

    }
}

package org.sphinx.ui;

import org.sphinx.engine.GameObject;

public abstract class UI extends GameObject {
    Canvas canvas;
    public int layout = 15;
    protected float width,height;
    UI(Canvas canvas){
        this.canvas = canvas;
        setParent(canvas);
        this.tag = "UI";
    }

    public void resetLayout() {
        if (canvas != null){
            this.layout = Math.min(canvas.layout + 1, 20);
        }
    }
    public Canvas getCanvas(){
        return canvas;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }
}

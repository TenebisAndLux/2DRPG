package org.sphinx.ui;

import org.sphinx.engine.Vector2D;

import java.awt.*;

public class ProgressBar extends UI{
    private float mValue = 0;
    private float minValue = 0;
    private float maxValue = 100;
    private final org.sphinx.ui.Canvas backGroundCanvas;
    private final org.sphinx.ui.Canvas foregroundCanvas;

    public enum Direction{
        horizontal,vertical
    }
    Direction direction = Direction.horizontal;
    public ProgressBar(int x,int y,int width,int height) {
        this(null, x, y, width, height);
    }
    public ProgressBar(org.sphinx.ui.Canvas canvas, int x, int y, int width, int height) {
        super(canvas);
        this.width = width;
        this.height = height;
        this.name = "ProgressBar";
        backGroundCanvas = new org.sphinx.ui.Canvas(x, y, width, height);
        foregroundCanvas = new Canvas(backGroundCanvas,0,0,0,0);
        backGroundCanvas.setOutLine(true);
        foregroundCanvas.setColor(Color.black);
    }
    public void setThreshold(float minValue,float maxValue) {
        if (minValue > maxValue) return;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.mValue = minValue;
    }
    public void setColor(Color foreColor,Color backColor){
        foregroundCanvas.setColor(foreColor);
        backGroundCanvas.setColor(backColor);
    }
    public void setValue(float value) {
        this.mValue =Math.min(Math.max(value, minValue),maxValue);
    }
    public void addValue(float value) {
        setValue(this.mValue + value);
    }

    public float getValue() {
        return mValue;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    @Override
    public void start() {

    }

    @Override
    public void update() {
        int outLineWidth = backGroundCanvas.render.toPainter().getOutLineWidth();
        switch (direction) {
            case horizontal -> {
                foregroundCanvas.transform.position = new Vector2D(outLineWidth / 2f, outLineWidth / 2f);
                foregroundCanvas.setSize((width - outLineWidth) / (maxValue - minValue) * (mValue-minValue), height - outLineWidth);
            }
            case vertical -> {
                foregroundCanvas.transform.position = new Vector2D(outLineWidth / 2f, (height - outLineWidth/2f) - ((height - outLineWidth) / (maxValue - minValue) * (mValue-minValue)));
                foregroundCanvas.setSize(width - outLineWidth, (height - outLineWidth) / (maxValue - minValue) * (mValue-minValue));
            }
        }
    }

    @Override
    public void enable() {

    }

    @Override
    public void disable() {

    }
}

package org.sphinx.ui;

import org.sphinx.engine.*;

import java.awt.*;

public class Canvas extends UI {
    public enum Type{
        sprite, painter
    }
    Type type;
    public Render render;
    public Color fillColor =new Color(255,255,255,255 );
    public Color outLineColor =new Color(0,0,0,255);
    private boolean isOutLine = false;

    public Canvas(int x, int y, int width, int height){
        this(null,x,y,width,height,Type.painter,null,null);
    }
    public Canvas(int x, int y, int width, int height,String path){
        this(null,x,y,width,height,Type.sprite,path,null);
    }
    public Canvas(int x, int y, int width, int height, Texture texture){
        this(null,x,y,width,height,Type.sprite,null,texture);
    }
    public Canvas(Canvas canvas, int x, int y, int width, int height){
        this(canvas,x,y,width,height,Type.painter,null,null);
    }
    public Canvas(Canvas canvas, int x, int y, int width, int height,String path){
        this(canvas,x,y,width,height,Type.sprite,path,null);
    }
    public Canvas(Canvas canvas, int x, int y, int width, int height, Texture texture){
        this(canvas,x,y,width,height,Type.sprite,null,texture);
    }
    private Canvas(Canvas canvas, int x, int y, float width, float height,Type type,String path,Texture texture){
        super(canvas);
        this.transform.position = new Vector2D(x, y);
        this.type = type;
        this.width = width;
        this.height = height;
        this.name = "Canvas";
        switch (type){
            case painter -> render = new Painter(this) {
                @Override
                public void draw() {
                    this.setFillColor(fillColor);
                    this.setOutLineColor(outLineColor);
                    drawQuads(getPosition().x, getPosition().y, Canvas.this.width, Canvas.this.height);
                    if (isOutLine){
                        drawQuadsOutLine(getPosition().x, getPosition().y, Canvas.this.width, Canvas.this.height);
                    }
                }
            };
            case sprite -> {
                if (path != null)
                    render = new Sprite(this,path, Render.UsageType.UI);
                else if (texture != null)
                    render = new Sprite(this,texture, Render.UsageType.UI);
            }
        }
        resetLayout();
        render.setLayout(this.layout);
    }

    public void setColor(Color color) {
        this.fillColor = color;
    }
    public void setFillColor(float red, float green, float blue, float alpha) {
        this.fillColor = new Color(red, green, blue, alpha);
    }
    public void setOutLineColor(float red, float green, float blue, float alpha) {
        this.outLineColor = new Color(red, green, blue, alpha);
    }

    public void setLayout(int layout){
        this.layout = layout;
        render.setLayout(this.layout);
    }

    public void setOutLine(boolean outLine) {
        isOutLine = outLine;
    }
    public float[] getSize(){
        return new float[]{width,height};
    }
    public void setSize(float width,float height){
        this.width = width;
        this.height = height;
    }
    @Override
    public void start() {


    }

    @Override
    public void update() {

    }

    @Override
    public void enable() {

    }

    @Override
    public void disable() {

    }
}

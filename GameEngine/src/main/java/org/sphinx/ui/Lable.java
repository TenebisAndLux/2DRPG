package org.sphinx.ui;

import org.sphinx.engine.Render;
import org.sphinx.engine.Sprite;
import org.sphinx.engine.Text;
import org.sphinx.engine.Vector2D;

import java.awt.*;


public class Lable extends UI {
    public Text text;
    Sprite sprite;
    public Lable(Canvas canvas, int width, int height){
        super(canvas);
        this.width = width;
        this.height = height;
        text = new Text(width,height);
        text.setStr("",80,Color.black);
        sprite = new Sprite(this,text.getTexture(), Render.UsageType.UI);
        sprite.offset = new Vector2D(text.getTexture().getWidth()/2f,text.getTexture().getHeight()/2f);
        this.name = "Lable";
        resetLayout();
        setLayout(layout);
    }
    public void setText(String str, int size, Color color) {
        this.text.setStr(str,size,0,size,color,Color.black);
    }
    public void setText(String str, int size, Color color,boolean isCenter) {
        if (isCenter)
            this.text.setStr(str,size, 0,text.getTexture().getHeight()/2+size/2,color,Color.black,true);
        else
            this.text.setStr(str,size,0,size,color,Color.black);
    }
    public void setLayout(int layout){
        sprite.setLayout(layout);
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

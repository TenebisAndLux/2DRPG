package org.sphinx.ui;

import org.sphinx.engine.EventSystem;
import java.awt.*;

public class Button extends org.sphinx.ui.Canvas {
    public Lable lable;
    private boolean isFocus = false;
    private boolean isHightLight = false;
    private Color color = new Color(255,153,51);
    private Color hightLightColor = new Color(255,204,153);
    private Color focusColor = new Color(255,128,0);
    private Runnable event;
    public Button(Canvas canvas, int width, int height){
        super(canvas,0,0,width,height);
        this.canvas = canvas;
        this.width = width;
        this.height = height;
        this.name = "Button";
        setOutLine(true);
        setOutLineColor(0,0,0,1);
        lable = new Lable(this,width,height);
        lable.setText("",60,Color.black,true);
        setLayout(layout);
    }

    public void setEvent(Runnable event) {
        this.event = event;
    }
    @Override
    public void update() {
        Point pos = EventSystem.getCursorPos();
        if (pos.x > this.getPosition().x && pos.x < this.getPosition().x + width &&
                pos.y > this.getPosition().y &&  pos.y < this.getPosition().y + height){
            isHightLight = true;
            if (EventSystem.getMouseButton1()){
                isFocus = true;
            }
            if (isFocus && !EventSystem.getMouseButton1()){
                isFocus = false;
                if (event!=null)
                    event.run();
            }
        }
        else {
            isHightLight = false;
            isFocus = false;
        }


        if (isFocus){
            setColor(focusColor);
        }
        else if (isHightLight){
            setColor(hightLightColor);
        }
        else {
            setColor(color);
        }
    }

    public void setButtonColor(Color normalColor,Color hightLightColor, Color focusColor) {
        this.color = normalColor;
        this.hightLightColor = hightLightColor;
        this.focusColor = focusColor;
    }
}

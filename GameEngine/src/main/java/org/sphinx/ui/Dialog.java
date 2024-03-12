package org.sphinx.ui;

import org.sphinx.engine.EventSystem;
import org.sphinx.engine.GameEvent;
import org.sphinx.engine.GameTimer;
import org.sphinx.engine.Text;
import org.sphinx.util.Debug;

import java.awt.*;

public class Dialog extends org.sphinx.ui.Canvas {
    Class<? extends GameEvent> gameEventClass = null;
    public Color textColor = new Color(255,255,255,255);
    public Lable lable;
    public int size = 30;
    int index = 0,indexX = 0,line = 0;
    double timeInterval = 0.05;
    String str = "";
    boolean isFreeze = false;
    public GameTimer timer = new GameTimer();
    public Dialog(Canvas canvas, int width, int height) {
        super(canvas,0, 0, width, height);
        this.name = "Dialog";
        fillColor = new Color(0,0,0,0);
        setColor(fillColor);
        lable = new Lable(this,width+10,height);
    }

    public void setGameEvent(Class<? extends GameEvent> gameEvent) {
        this.gameEventClass = gameEvent;
    }

    public void setText(String str, int size){
        this.size = size;
        setText(str);
    }
    public void setText(String str){
        this.str = str;
        index = 0;
        indexX = 0;
        line = 0;
        lable.setText("",1,Color.white);
    }
    public void newLine(){
        indexX = 0;
        line++;
    }
    private char getChar(int i){
        return str.charAt(i);
    }
    public void setFreeze(boolean bool){
        isFreeze = bool;
    }
    @Override
    public void update() {
        if (isFreeze){
            timer.reset();
            return;
        }
        if (timer.time > timeInterval) {
            if (index < str.length()) {
                char ch = getChar(index++);
                if (ch == '%') {
                    StringBuilder stringBuilder = new StringBuilder();
                    while (getChar(index) != '%') {
                        stringBuilder.append(getChar(index++));
                    }
                    index++;
                    timeInterval = Integer.valueOf(String.valueOf(stringBuilder)).doubleValue() / 1000;
                    Debug.log("Time interval : ", String.valueOf(timeInterval));
                } else if (ch == '#') {
                    StringBuilder stringBuilder = new StringBuilder();
                    while (getChar(index) != '#') {
                        stringBuilder.append(getChar(index++));
                    }
                    index++;
                    size = Integer.parseInt(String.valueOf(stringBuilder));
                    Debug.log("Font size : ", String.valueOf(size));
                } else if (ch == '@') {
                    StringBuilder stringBuilder = new StringBuilder();
                    while (getChar(index) != '@') {
                        stringBuilder.append(getChar(index++));
                    }
                    index++;
                    int sum = 0;
                    for (int i = 0; i < stringBuilder.length(); i++) {
                        Integer integer = Integer.valueOf(String.valueOf(stringBuilder.charAt(stringBuilder.length() - i - 1)), 16);
                        sum += integer * Math.pow(16, i);
                    }
                    textColor = new Color(sum);
                    Debug.log("Text color : ", String.valueOf(textColor));
                }else if(ch == '$'){
                    StringBuilder stringBuilder = new StringBuilder();
                    while (getChar(index) != '$') {
                        stringBuilder.append(getChar(index++));
                    }
                    index++;
                    String[] strings = stringBuilder.toString().split("/");
                    for(var var : strings) System.out.println(var);
                    if (gameEventClass != null)
                        EventSystem.execute(gameEventClass,this,strings);
                }
                else {
                    if (indexX + Text.getCharWidth(ch, size) >= getSize()[0]||ch == '\n')
                        newLine();
                    if (ch == '\n'|| ch == '\r') return;
                    if (ch != ' ') timer.reset();
                    lable.text.getTexture().updateTexture(indexX,line * size,Text.getCharWidth(ch, size),size,Text.getCharBuffer(ch,size,textColor));
                    indexX+=Text.getCharWidth(ch, size);
                }
            }
        }
    }

}

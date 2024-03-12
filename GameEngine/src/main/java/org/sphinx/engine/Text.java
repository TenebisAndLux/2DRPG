package org.sphinx.engine;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

public class Text {
    static File fontFile;
    static Font customFont;
    static FontMetrics fontMetrics;
    int width ,height;
    Texture texture;
    public Text(int width, int height){
       this.width = width;
       this.height = height;
       texture = new Texture(width,height,null);
    }
    public static void init() {
        try {
            customFont = Font.createFont(Font.TRUETYPE_FONT, Text.class.getResourceAsStream("/LanaPixel.ttf"));
            Text.getCharBuffer(' ',10,Color.white);  //！！！不能动！！！
        }
        catch (IOException | FontFormatException e){
            e.printStackTrace();
        }
    }
    public void setStr(String str, float size,Color color) {
        texture.updateTexture(width,height,getBuffer(str,size,0,(int) size,width,height,color,Color.black,false));
    }
    public void setStr(String str, float size, int x, int y,Color frontColor,Color backColor) {
        texture.updateTexture(width,height,getBuffer(str,size,x,y,width,height,frontColor,backColor,false));
    }
    public void setStr(String str, float size,Color color,boolean isCenter) {
        texture.updateTexture(width,height,getBuffer(str,size,0,(int) size,width,height,color,Color.black,isCenter));
    }
    public void setStr(String str, float size, int x, int y,Color frontColor,Color backColor,boolean isCenter) {
        texture.updateTexture(width, height, getBuffer(str, size, x, y, width, height, frontColor, backColor,isCenter));
    }
    private static ByteBuffer getBuffer(String str,float size,int x,int y,int width,int height,Color frontColor,Color backColor,boolean isCenter){
        Font font = customFont.deriveFont(size);
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = (Graphics2D) image.getGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setFont(font);
        g2d.setColor(frontColor);
        g2d.setBackground(backColor);
        if (isCenter) {
            fontMetrics = g2d.getFontMetrics(font);
            int stringWidth = fontMetrics.stringWidth(str);
            g2d.drawString(str, (width-stringWidth)/2, (int)(y*0.8));
        }
        else
            g2d.drawString(str, x, (int)(y*0.8));
        int[] pixels = image.getRGB(0, 0, width, height, null, 0, width);
        ByteBuffer buffer = ByteBuffer.allocateDirect(width * height * 4);
        for (int iy = 0; iy < height; iy++) {
            for (int ix = 0; ix < width; ix++) {
                int pixel = pixels[iy * width + ix];
                buffer.put((byte) ((pixel >> 16) & 0xFF));
                buffer.put((byte) ((pixel >> 8) & 0xFF));
                buffer.put((byte) (pixel & 0xFF));
                buffer.put((byte) ((pixel >> 24) & 0xFF));
            }
        }
        buffer.flip();
        return buffer;
    }
    public Texture getTexture() {
        return texture;
    }
    public static ByteBuffer getCharBuffer(String str,int size,Color color){
        return getBuffer(str,size,0,size,size,size,color,Color.black,false);
    }
    public static ByteBuffer getCharBuffer(char str,int size,Color color){
        return getBuffer(String.valueOf(str),size,0,size,getCharWidth(str,size),size,color,Color.black,false);
    }
    public static int getCharWidth(char str,int size){
        Font font = new Font("ha",Font.PLAIN,size);
        fontMetrics = Toolkit.getDefaultToolkit().getFontMetrics(font);
        //System.out.println("hahahah"+fontMetrics.charWidth(str)+" "+str);
        return fontMetrics.charWidth(str);
    }
}
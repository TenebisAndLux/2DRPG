package org.sphinx.engine;

import java.util.ArrayList;
import java.util.List;

public class SplitTexture {
    private final List<Texture> textures = new ArrayList<>();

    public SplitTexture(String path, int row, int col){
        for (int i = 0; i < row * col; i++){
            textures.add(new Texture(path, row, col,i));
        }
    }
    public Texture getTexture(int index){
        return textures.get(index);
    }

    public List<Texture> getTextures(int start,int end){
        List<Texture> tempTexture = new ArrayList<>();
        for (int i = start; i < end ; i++){
            tempTexture.add(textures.get(i));
        }
        return tempTexture;
    }

    public List<Texture> getTextureIndex(int ... indexes){
        List<Texture> tempTexture = new ArrayList<>();
        for (int i : indexes){
            tempTexture.add(textures.get(i));
        }
        return tempTexture;
    }

    public List<Texture> getTextureList() {
        return textures;
    }
}
package org.sphinx.engine;

import org.sphinx.util.Debug;

import java.util.*;

public abstract class Render extends Component{
    public enum UsageType{
        UI,Item
    }
    private int layout = 0;
    enum Type{
        sprite, painter
    }
    Type type;
    Render(GameObject gameObject,Type type){
        super(gameObject,Render.class);
        this.type = type;
    }

    public void setLayout(int layout){
        if (layout <= 20 && layout > 0){
            this.layout = layout;
        }
    }
    static void update(){
        Renderer.render();
    }

    public int getLayout() {
        return layout;
    }

    public Painter toPainter(){
        return this instanceof Painter ? (Painter) this : null;
    }

    public Sprite toSprite(){
        return this instanceof Sprite ? (Sprite) this : null;
    }


    static void destroyAllRender(){
        Debug.log("Rendering----Start releasing the rendering object");

        components.get(Render.class).forEach((id,renderList)->{
            renderList.forEach(component->{
                Render render = (Render) component;
                if (Objects.requireNonNull(render.type) == Type.sprite) {
                    render.toSprite().getMesh().destroy();
                }
            });
        });
        components.get(Render.class).clear();
    }

}

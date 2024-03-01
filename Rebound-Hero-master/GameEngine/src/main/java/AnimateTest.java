import org.sphinx.engine.*;

import java.util.ArrayList;
import java.util.List;

public class AnimateTest extends GameObject {
    List<Sprite> sprites;
    @Override
    public void start() {

        this.transform.scale = new Vector2D(6,6);
        SplitTexture splitTexture = new SplitTexture("/Image/Hero/Warrior_Sheet-Effect.png",6,17);
        sprites = new ArrayList<>(splitTexture.getTextureList().size());
        for (int i = 0; i < 102; i++) {
            sprites.add(new Sprite(this, splitTexture.getTextureList().get(i), Render.UsageType.Item));
            sprites.get(i).offset = new Vector2D(i*200,0);
        }
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

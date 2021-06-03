package game.gb.sprite;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import game.gb.base.Sprite;
import game.gb.math.Rect;

public class Background extends Sprite {
    public Background(Texture texture) {

        super(new TextureRegion(texture));
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setHeightProportion(1f);
        this.position.set(worldBounds.position);
    }
}


package game.gb.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import game.gb.base.Sprite;
import game.gb.math.Rect;

public class GameOver extends Sprite {
    private static final float HEIGHT = 0.05f;

    public GameOver(TextureAtlas atlas) {
        super(atlas.findRegion("message_game_over"));
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(HEIGHT);
        setTop(worldBounds.position.y + 0.2f);
    }
}

package game.gb.sprite;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import game.gb.base.ScaledButton;
import game.gb.math.Rect;
import game.gb.screen.GameScreen;

public class PlayButton extends ScaledButton {

    private static final float HEIGHT = 0.135f;
    private static final float PUDDING = 0.02f;

    private final Game game;

    public PlayButton(TextureAtlas atlas, Game game) {
        super(atlas.findRegion("btPlay"));
        this.game = game;
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setHeightProportion(HEIGHT);
        setBottom(worldBounds.getBottom() + PUDDING);
        setLeft(worldBounds.getLeft() + PUDDING);
    }

    @Override
    protected void action() {
        game.setScreen(new GameScreen());
    }
}

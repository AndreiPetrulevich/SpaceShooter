package game.gb.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import game.gb.base.ScaledButton;
import game.gb.math.Rect;


public class ButtonExit extends ScaledButton {

    private static final float HEIGHT = 0.1f;
    private static final float PUDDING = 0.02f;

    public ButtonExit(TextureAtlas atlas) {
        super(atlas.findRegion("btExit"));
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setHeightProportion(HEIGHT);
        setBottom(worldBounds.getBottom() + PUDDING);
        setRight(worldBounds.getRight() - PUDDING);
    }

    @Override
    protected void action() {
        Gdx.app.exit();
    }
}

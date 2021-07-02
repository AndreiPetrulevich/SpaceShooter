package game.gb.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import game.gb.base.ScaledButton;
import game.gb.math.Rect;
import game.gb.screen.GameScreen;

public class NewGame extends ScaledButton {

    private static final float HEIGHT = 0.03f;
    private static final float ANIMATE_INTERVAL = 0.1f;

    private GameScreen gameScreen;
    private float animateTimer;
    private boolean scaleUp = false;


    public NewGame(TextureAtlas atlas, GameScreen gameScreen) {
        super(atlas.findRegion("button_new_game"));
        this.gameScreen = gameScreen;
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setHeightProportion(HEIGHT);
        setTop(worldBounds.position.y - 0.2f);
    }

    @Override
    protected void action() {
        gameScreen.newGameStart();
    }

    @Override
    public void update(float delta) {
        animateTimer += delta;
        if (animateTimer >= ANIMATE_INTERVAL) {
            animateTimer = 0f;
            if (scaleUp) {
                scale += delta;
            } else {
                scale -= delta;
            }
            if (scale <= 0.9f) {
                scaleUp = true;
            }
            if (scale >= 1) {
                scaleUp = false;
            }
        }
    }
}

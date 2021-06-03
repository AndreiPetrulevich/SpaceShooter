package game.gb.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.sun.org.apache.bcel.internal.generic.BALOAD;
import game.gb.base.BaseScreen;
import game.gb.math.Rect;
import game.gb.sprite.Background;
import game.gb.sprite.Ball;

public class MenuScreen extends BaseScreen {

    private Texture bg;
    private Texture logo;
    private Background background;
    private Ball ball;

    @Override
    public void show() {
        super.show();
        bg = new Texture("textures/bg.png");
        logo = new Texture("ball.png");
        background = new Background(bg);
        ball = new Ball(logo);
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        ball.resize(worldBounds);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        ball.update(delta);

        ScreenUtils.clear(1, 1, 1, 1);
        batch.begin();
        background.draw(batch);
        ball.draw(batch);
        batch.end();
    }

    @Override
    public void dispose() {
        super.dispose();
        bg.dispose();
        logo.dispose();
    }

    @Override
    public boolean touchDown(Vector2 targetPosition, int pointer, int button) {
        ball.touchDown(targetPosition, pointer, button);
        return false;
    }
}

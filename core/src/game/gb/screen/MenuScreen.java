package game.gb.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import game.gb.base.BaseScreen;

public class MenuScreen extends BaseScreen {
    private final int ballSize = 100;
    private final int velocity = 200;
    private Texture img;
    private Vector2 position;
    private Vector2 targetPosition;
    private Vector2 moveOffset;

    @Override
    public void show() {
        super.show();
        img = new Texture("ball.png");
        position = new Vector2();
        targetPosition = new Vector2();
        moveOffset = new Vector2();
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        if (!targetPosition.epsilonEquals(position)) {
            Vector2 newPosition = position.cpy().mulAdd(moveOffset, delta * velocity);
            if (position.x < newPosition.x) {
                newPosition.x = Math.min(newPosition.x, targetPosition.x);
            } else {
                newPosition.x = Math.max(newPosition.x, targetPosition.x);
            }
            if (position.y < newPosition.y) {
                newPosition.y = Math.min(newPosition.y, targetPosition.y);
            } else {
                newPosition.y = Math.max(newPosition.y, targetPosition.y);
            }
            position = newPosition;
        }

        ScreenUtils.clear(1, 1, 1, 1);
        batch.begin();
        batch.draw(img, position.x, position.y, ballSize, ballSize);
        batch.end();
    }

    @Override
    public void dispose() {
        super.dispose();
        img.dispose();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        targetPosition.set(screenX - ballSize / 2, Gdx.graphics.getHeight() - screenY - ballSize / 2);
        moveOffset = targetPosition.cpy().sub(position).nor();
        return super.touchDown(screenX, screenY, pointer, button);
    }
}

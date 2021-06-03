package game.gb.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import game.gb.base.BaseScreen;

public class MenuScreen extends BaseScreen {
    private static final float V_LENGTH = 1.5f;
    private final float ballSize = 0.3f;
    private Texture img;
    private Vector2 position;
    private Vector2 targetPosition;
    private Vector2 moveOffset;
    private Vector2 tmp;

    @Override
    public void show() {
        super.show();
        img = new Texture("ball.png");
        position = new Vector2();
        targetPosition = new Vector2(0.5f, 0.5f);
        moveOffset = new Vector2();
        tmp = new Vector2();
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        ScreenUtils.clear(1, 1, 1, 1);
        batch.begin();
        batch.draw(img, position.x, position.y, ballSize, ballSize);
        batch.end();
        tmp.set(targetPosition);
        if(tmp.sub(position).len() <= moveOffset.len()) {
            position.set(targetPosition);
            moveOffset.setZero();
        } else {
            position.add(moveOffset);
        }

    }

    @Override
    public void dispose() {
        super.dispose();
        img.dispose();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        targetPosition.set(screenX - ballSize / 2, Gdx.graphics.getHeight() - screenY - ballSize / 2);
        moveOffset.set(targetPosition.cpy().sub(position).setLength(V_LENGTH));
        return false;
    }
}

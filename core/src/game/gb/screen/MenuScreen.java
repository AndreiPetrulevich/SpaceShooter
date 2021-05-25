package game.gb.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import game.gb.base.BaseScreen;

public class MenuScreen extends BaseScreen {

    private Texture img;
    private Vector2 position;
    private Vector2 vMove;

    @Override
    public void show() {
        super.show();
        img = new Texture("testBackground.jpg");
        position = new Vector2();
        vMove = new Vector2(1,0);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        position.add(vMove);
        ScreenUtils.clear(1, 0, 0, 1);
        batch.begin();
        batch.draw(img, position.x, position.y);
        batch.end();
    }

    @Override
    public void dispose() {
        super.dispose();
        img.dispose();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        position.set(screenX, Gdx.graphics.getHeight() - screenY);
        return super.touchDown(screenX, screenY, pointer, button);
    }
}

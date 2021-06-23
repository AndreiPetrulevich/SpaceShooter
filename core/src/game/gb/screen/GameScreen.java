package game.gb.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import game.gb.base.BaseScreen;
import game.gb.math.Rect;
import game.gb.pool.BulletPool;
import game.gb.sprite.Background;
import game.gb.sprite.SpaceShip;
import game.gb.sprite.Star;

public class GameScreen extends BaseScreen {

    private static final int STAR_COUNT = 64;

    private Texture bg;
    private TextureAtlas atlas;
    private Star[] stars;
    private Background background;
    private SpaceShip spaceShip;
    private BulletPool bullePool;

    private final Sound backgroundMusic = Gdx.audio.newSound(Gdx.files.internal("sounds/backgroundMusic.mp3"));


    @Override
    public void show() {
        super.show();
        bg = new Texture("textures/bg.png");
        background = new Background(bg);
        atlas = new TextureAtlas("textures/mainAtlas.tpack");
        stars = new Star[STAR_COUNT];
        for (int i = 0; i < stars.length; i++) {
            stars[i] = new Star(atlas);
        }
        bullePool = new BulletPool();
        spaceShip = new SpaceShip(atlas, bullePool);
        long id = backgroundMusic.play(0.1f);
        backgroundMusic.setLooping(id, true);
    }

    @Override
    public void render(float delta) {
        update(delta);
        freeAllDestroyed();
        draw();
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        for (Star star : stars) {
            star.resize(worldBounds);
        }
        spaceShip.resize(worldBounds);
    }

    @Override
    public void dispose() {
        super.dispose();
        bg.dispose();
        atlas.dispose();
        bullePool.dispose();
        backgroundMusic.dispose();
    }

    private void update(float delta) {
        for (Star star : stars) {
            star.update(delta);
        }
        spaceShip.update(delta);
        bullePool.updateActiveSprites(delta);
    }

    private void draw() {
        ScreenUtils.clear(1, 1, 1, 1);
        batch.begin();
        background.draw(batch);
        for (Star star : stars) {
            star.draw(batch);
        }
        spaceShip.draw(batch);
        bullePool.drawActiveSprites(batch);
        batch.end();
    }

    private void freeAllDestroyed() {
        bullePool.freeAllDestroyed();
    }

    @Override
    public boolean keyDown(int keycode) {
        spaceShip.keyDown(keycode);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        spaceShip.keyUp(keycode);
        return false;
    }

    @Override
    public boolean touchDown(Vector2 targetPosition, int pointer, int button) {
        spaceShip.touchDown(targetPosition, pointer, button);
        return false;
    }

    @Override
    public boolean touchUP(Vector2 targetPosition, int pointer, int button) {
        spaceShip.touchUp(targetPosition, pointer, button);
        return false;
    }

}

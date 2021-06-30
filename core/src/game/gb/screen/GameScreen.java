package game.gb.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import game.gb.base.BaseScreen;
import game.gb.math.Rect;
import game.gb.pool.BulletPool;
import game.gb.pool.EnemyPool;
import game.gb.sprite.Background;
import game.gb.sprite.EnemyShip;
import game.gb.sprite.SpaceShip;
import game.gb.sprite.Star;
import game.gb.utils.EnemyEmitter;

import java.util.List;

public class GameScreen extends BaseScreen {

    private static final int STAR_COUNT = 64;

    private Texture bg;
    private TextureAtlas atlas;
    private Star[] stars;
    private Background background;
    private SpaceShip spaceShip;
    private BulletPool bulletPool;
    private EnemyPool enemyPool;
    private EnemyEmitter enemyEmitter;

    private final Music backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/backgroundMusic.mp3"));
    private final Sound bulletSound = Gdx.audio.newSound(Gdx.files.internal("sounds/bullet.wav"));
    private Sound laserSound;


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
        bulletPool = new BulletPool();
        laserSound = Gdx.audio.newSound(Gdx.files.internal("sounds/laser.wav"));
        enemyPool = new EnemyPool(worldBounds, bulletPool, bulletSound);
        spaceShip = new SpaceShip(atlas, bulletPool, laserSound);
        enemyEmitter = new EnemyEmitter(worldBounds, enemyPool, atlas);
        backgroundMusic.setLooping(true);
        backgroundMusic.play();
    }

    @Override
    public void render(float delta) {
        update(delta);
        checkForCollision();
        freeAllDestroyed();
        draw();
    }

    private void checkForCollision() {
        List<EnemyShip> enemyShipList = enemyPool.getActiveObjects();
        for (EnemyShip enemyShip : enemyShipList) {
            if (enemyShip.isDestroyed()) {
                continue;
            }
            if (!enemyShip.isOutside(spaceShip)) {
                enemyShip.destroy();
            }
        }
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
        bulletPool.dispose();
        enemyPool.dispose();
        backgroundMusic.dispose();
        bulletSound.dispose();
        laserSound.dispose();
    }

    private void update(float delta) {
        for (Star star : stars) {
            star.update(delta);
        }
        spaceShip.update(delta);
        bulletPool.updateActiveSprites(delta);
        enemyPool.updateActiveSprites(delta);
        enemyEmitter.generate(delta);
    }

    private void draw() {
        ScreenUtils.clear(1, 1, 1, 1);
        batch.begin();
        background.draw(batch);
        for (Star star : stars) {
            star.draw(batch);
        }
        spaceShip.draw(batch);
        bulletPool.drawActiveSprites(batch);
        enemyPool.drawActiveSprites(batch);
        batch.end();
    }

    private void freeAllDestroyed() {
        bulletPool.freeAllDestroyed();
        enemyPool.freeAllDestroyed();
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

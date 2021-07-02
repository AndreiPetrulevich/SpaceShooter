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
import game.gb.pool.ExplosionPool;
import game.gb.sprite.*;
import game.gb.utils.EnemyEmitter;

import java.util.List;

public class GameScreen extends BaseScreen {

    private static final int STAR_COUNT = 64;

    private enum State {PLAYING, GAME_OVER};

    private Texture bg;
    private TextureAtlas atlas;
    private Star[] stars;
    private Background background;
    private SpaceShip spaceShip;
    private BulletPool bulletPool;
    private EnemyPool enemyPool;
    private ExplosionPool explosionPool;
    private EnemyEmitter enemyEmitter;
    private State state;

    private Music backgroundMusic;
    private final Sound bulletSound = Gdx.audio.newSound(Gdx.files.internal("sounds/bullet.wav"));
    private Sound laserSound;
    private Sound explosionSound;

    private GameOver gameOver;
    private NewGame newGame;


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
        explosionSound = Gdx.audio.newSound(Gdx.files.internal("sounds/explosion.wav"));
        explosionPool = new ExplosionPool(atlas, explosionSound);
        laserSound = Gdx.audio.newSound(Gdx.files.internal("sounds/laser.wav"));
        enemyPool = new EnemyPool(worldBounds, explosionPool, bulletPool, bulletSound);
        spaceShip = new SpaceShip(atlas, explosionPool, bulletPool, laserSound);
        enemyEmitter = new EnemyEmitter(worldBounds, enemyPool, atlas);
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/backgroundMusic.mp3"));
        backgroundMusic.setLooping(true);
        backgroundMusic.play();
        state = State.PLAYING;
        gameOver = new GameOver(atlas);
        newGame = new NewGame(atlas, this);
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
            float minDist = enemyShip.getHalfWidth() + spaceShip.getHalfWidth();
            if (enemyShip.position.dst(spaceShip.position) < minDist) {
                enemyShip.destroy();
                spaceShip.damage(enemyShip.getDamage() * 2);
            }
        }

        List<Bullet> bulletList = bulletPool.getActiveObjects();
        for (Bullet bullet : bulletList) {
            if (bullet.isDestroyed()) {
                continue;
            }
            if (bullet.getOwner() == spaceShip) {
                for (EnemyShip enemyShip : enemyShipList) {
                    if (enemyShip.isDestroyed()) {
                        continue;
                    }
                    if (enemyShip.isBulletCollision(bullet)) {
                        enemyShip.damage(bullet.getDamage());
                        bullet.destroy();
                    }
                }
            } else {
                if (spaceShip.isBulletCollision(bullet)) {
                    spaceShip.damage(bullet.getDamage());
                    bullet.destroy();
                }
            }
        }
        if (spaceShip.isDestroyed()) {
            state = State.GAME_OVER;
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
        gameOver.resize(worldBounds);
        newGame.resize(worldBounds);
    }

    @Override
    public void dispose() {
        super.dispose();
        bg.dispose();
        atlas.dispose();
        bulletPool.dispose();
        enemyPool.dispose();
        explosionPool.dispose();
        backgroundMusic.dispose();
        explosionSound.dispose();
        bulletSound.dispose();
        laserSound.dispose();
    }

    private void update(float delta) {
        for (Star star : stars) {
            star.update(delta);
        }
        explosionPool.updateActiveSprites(delta);

        if (state == State.PLAYING) {
            spaceShip.update(delta);
            bulletPool.updateActiveSprites(delta);
            enemyPool.updateActiveSprites(delta);
            enemyEmitter.generate(delta);
        } else if (state == State.GAME_OVER) {
            newGame.update(delta);
        }
    }

    private void draw() {
        ScreenUtils.clear(1, 1, 1, 1);
        batch.begin();
        background.draw(batch);
        for (Star star : stars) {
            star.draw(batch);
        }
        explosionPool.drawActiveSprites(batch);

        if (state == State.PLAYING) {
            spaceShip.draw(batch);
            bulletPool.drawActiveSprites(batch);
            enemyPool.drawActiveSprites(batch);
        } else {
            gameOver.draw(batch);
            newGame.draw(batch);
        }
        batch.end();
    }

    private void freeAllDestroyed() {
        bulletPool.freeAllDestroyed();
        enemyPool.freeAllDestroyed();
        explosionPool.freeAllDestroyed();
    }

    @Override
    public boolean keyDown(int keycode) {
        if (state == State.PLAYING) {
            spaceShip.keyDown(keycode);
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (state == State.PLAYING) {
            spaceShip.keyUp(keycode);
        }
        return false;
    }

    @Override
    public boolean touchDown(Vector2 targetPosition, int pointer, int button) {
        if (state == State.PLAYING) {
            spaceShip.touchDown(targetPosition, pointer, button);
        } else if (state == State.GAME_OVER){
            newGame.touchDown(targetPosition, pointer, button);
        }
        return false;
    }

    @Override
    public boolean touchUP(Vector2 targetPosition, int pointer, int button) {
        if (state == State.PLAYING) {
            spaceShip.touchUp(targetPosition, pointer, button);
        } else if (state == State.GAME_OVER){
            newGame.touchUp(targetPosition, pointer, button);
        }
        return false;
    }

    public void newGameStart() {
        spaceShip.newGameStart();
        bulletPool.freeAllActiveObjects();
        enemyPool.freeAllActiveObjects();
        explosionPool.freeAllActiveObjects();
    }
}

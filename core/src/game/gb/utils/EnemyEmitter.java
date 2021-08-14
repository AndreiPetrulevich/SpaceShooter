package game.gb.utils;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import game.gb.math.Rect;
import game.gb.math.Rnd;
import game.gb.pool.EnemyPool;
import game.gb.sprite.EnemyShip;

public class EnemyEmitter {

    private static final float GENERATE_INTERVAL = 4f;

    private static final float SMALL_ENEMY_HEIGHT = 0.1f;
    private static final float SMALL_ENEMY_BULLET_HEIGHT = 0.01f;
    private static final float SMALL_ENEMY_BULLET_VY = -0.4f;
    private static final int SMALL_ENEMY_DAMAGE = 1;
    private static final float SMALL_ENEMY_RELOAD_TIME = 1f;
    private static final int SMALL_ENEMY_HP = 5;

    private static final float MEDIUM_ENEMY_HEIGHT = 0.15f;
    private static final float MEDIUM_ENEMY_BULLET_HEIGHT = 0.02f;
    private static final float MEDIUM_ENEMY_BULLET_VY = -0.35f;
    private static final int MEDIUM_ENEMY_DAMAGE = 2;
    private static final float MEDIUM_ENEMY_RELOAD_TIME = 2f;
    private static final int MEDIUM_ENEMY_HP = 10;

    private static final float BIG_ENEMY_HEIGHT = 0.2f;
    private static final float BIG_ENEMY_BULLET_HEIGHT = 0.02f;
    private static final float BIG_ENEMY_BULLET_VY = -0.3f;
    private static final int BIG_ENEMY_DAMAGE = 3;
    private static final float BIG_ENEMY_RELOAD_TIME = 1.5f;
    private static final int BIG_ENEMY_HP = 15;

    private float generateTimer;

    private final TextureRegion[] smallEnemyRegions;
    private final TextureRegion[] mediumEnemyRegions;
    private final TextureRegion[] bigEnemyRegions;

    private final Vector2 smallEnemyVelocity;
    private final Vector2 mediumEnemyVelocity;
    private final Vector2 bigEnemyVelocity;

    private final TextureRegion bulletRegion;

    private final Rect worldBounds;
    private final EnemyPool enemyPool;

    private int level = 1;


    public EnemyEmitter(Rect worldBounds, EnemyPool enemyPool, TextureAtlas atlas) {
        this.enemyPool = enemyPool;
        this.bulletRegion = atlas.findRegion("bulletEnemy");
        this.worldBounds = worldBounds;
        smallEnemyRegions = Regions.split(atlas.findRegion("enemy0"),1, 2, 2);
        mediumEnemyRegions = Regions.split(atlas.findRegion("enemy1"),1, 2, 2);
        bigEnemyRegions = Regions.split(atlas.findRegion("enemy2"),1, 2, 2);
        smallEnemyVelocity = new Vector2(0, -0.2f);
        mediumEnemyVelocity = new Vector2(0, -0.03f);
        bigEnemyVelocity = new Vector2(0, -0.01f);
    }

    public int getLevel() {
        return level;
    }

    public void generate (float delta, int frags) {
        level = frags / 10 + 1;
        generateTimer += delta;
        if (generateTimer >= GENERATE_INTERVAL) {
            generateTimer = 0f;
            EnemyShip enemyShip = enemyPool.obtain();
            float type = (float) Math.random();
            if (type < 0.5f) {
                enemyShip.set(
                        smallEnemyRegions,
                        smallEnemyVelocity,
                        bulletRegion,
                        SMALL_ENEMY_BULLET_HEIGHT,
                        SMALL_ENEMY_BULLET_VY,
                        SMALL_ENEMY_DAMAGE * level,
                        SMALL_ENEMY_RELOAD_TIME,
                        SMALL_ENEMY_HEIGHT,
                        SMALL_ENEMY_HP
                );
            } else if (type < 0.8f) {
                enemyShip.set(
                        mediumEnemyRegions,
                        mediumEnemyVelocity,
                        bulletRegion,
                        MEDIUM_ENEMY_BULLET_HEIGHT,
                        MEDIUM_ENEMY_BULLET_VY,
                        MEDIUM_ENEMY_DAMAGE * level,
                        MEDIUM_ENEMY_RELOAD_TIME,
                        MEDIUM_ENEMY_HEIGHT,
                        MEDIUM_ENEMY_HP
                );
            } else {
                enemyShip.set(
                        bigEnemyRegions,
                        bigEnemyVelocity,
                        bulletRegion,
                        BIG_ENEMY_BULLET_HEIGHT,
                        BIG_ENEMY_BULLET_VY,
                        BIG_ENEMY_DAMAGE * level,
                        BIG_ENEMY_RELOAD_TIME,
                        BIG_ENEMY_HEIGHT,
                        BIG_ENEMY_HP
                );
            }

            float enemyHalfWidth = enemyShip.getHalfWidth();
            enemyShip.position.x = Rnd.nextFloat(worldBounds.getLeft() + enemyHalfWidth, worldBounds.getRight() - enemyHalfWidth);
            enemyShip.setBottom(worldBounds.getTop());
            enemyShip.setBulletPosition(enemyShip.position);
        }



    }

}

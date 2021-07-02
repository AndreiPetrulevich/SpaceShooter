package game.gb.pool;

import com.badlogic.gdx.audio.Sound;
import game.gb.base.SpritesPool;
import game.gb.math.Rect;
import game.gb.sprite.EnemyShip;

public class EnemyPool extends SpritesPool<EnemyShip> {


    private final Rect worldBounds;
    private final ExplosionPool explosionPool;
    private final BulletPool bulletPool;
    private final Sound bulletSound;

    public EnemyPool(Rect worldBounds, ExplosionPool explosionPool, BulletPool bulletPool, Sound bulletSound) {
        this.worldBounds = worldBounds;
        this.explosionPool = explosionPool;
        this.bulletPool = bulletPool;
        this.bulletSound = bulletSound;
    }

    @Override
    protected EnemyShip newObject() {
        return new EnemyShip(worldBounds, explosionPool, bulletPool, bulletSound);
    }
}

package game.gb.sprite;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import game.gb.base.Ship;
import game.gb.math.Rect;
import game.gb.pool.BulletPool;

public class EnemyShip extends Ship {

    public EnemyShip(Rect worldBounds, BulletPool bulletPool, Sound bulletSound) {
        this.worldBounds = worldBounds;
        this.bulletPool = bulletPool;
        this.bulletSound = bulletSound;
        this.bulletVelocity = new Vector2();
        this.bulletPosition = new Vector2();
        initialVelocity = new Vector2();
        velocity = new Vector2();
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        bulletPosition.set(position.x, position.y - getHalfHeight());
        if (getTop() < worldBounds.getTop()) {
            velocity.set(initialVelocity);
        } else {
            timer *= 0.8f;
        }
        if (worldBounds.isOutside(this)) {
            destroy();
        }
    }

    public void set(
            TextureRegion[] regions,
            Vector2 initialVelocity,
            TextureRegion bulletRegion,
            float bulletHeight,
            float bulletVelocityY,
            int damage,
            float reloadTime,
            float height,
            int hp
    ) {
        this.regions = regions;
        this.initialVelocity.set(initialVelocity);
        this.bulletRegion = bulletRegion;
        this.bulletHeight = bulletHeight;
        this.bulletVelocity.set(0, bulletVelocityY);
        this.damage = damage;
        this.reloadTime = reloadTime;
        setHeightProportion(height);
        this.hp = hp;
        velocity.set(0, -0.3f);
    }
}

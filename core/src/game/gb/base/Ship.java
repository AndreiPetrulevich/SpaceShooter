package game.gb.base;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import game.gb.math.Rect;
import game.gb.pool.BulletPool;
import game.gb.pool.ExplosionPool;
import game.gb.sprite.Bullet;
import game.gb.sprite.Explosion;

public class Ship extends Sprite{

    private static final float DAMAGE_ANIMATE_INTERVAL = 0.1f;

    protected Vector2 initialVelocity;
    protected Vector2 velocity;

    protected Rect worldBounds;
    protected ExplosionPool explosionPool;
    protected BulletPool bulletPool;
    protected TextureRegion bulletRegion;
    protected Vector2 bulletVelocity;
    protected Vector2 bulletPosition;
    protected Sound bulletSound;

    protected float timer;
    protected float reloadTime;

    protected float bulletHeight;
    protected int damage;
    protected int hp;

    protected float damageAnimateTimer = DAMAGE_ANIMATE_INTERVAL;

    public Ship() {
    }

    public Ship(TextureRegion region, int rows, int cols, int frames) {
        super(region, rows, cols, frames);
    }

    public void setBulletPosition(Vector2 bulletPosition) {
        this.bulletPosition.set(bulletPosition);
    }

    public int getHp() {
        return hp;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    @Override
    public void update(float delta) {
        position.mulAdd(velocity, delta);
        timer += delta;
        if (timer >= reloadTime) {
            timer = 0;
            shoot();
            }
        damageAnimateTimer += delta;
        if (damageAnimateTimer >= DAMAGE_ANIMATE_INTERVAL) {
            frame = 0;
        }
    }

    private void shoot() {
        Bullet bullet = bulletPool.obtain();
        bullet.set(this, bulletRegion, bulletPosition, bulletVelocity, worldBounds, damage, bulletHeight);
        bulletSound.play(0.05f);
    }

    public void damage(int damage) {
        hp -= damage;
        if(hp <= 0) {
            hp = 0;
            destroy();
        }
        frame = 1;
        damageAnimateTimer = 0f;
    }

    public int getDamage() {
        return damage;
    }

    @Override
    public void destroy() {
        super.destroy();
        bang();
    }

    private void bang() {
        Explosion explosion = explosionPool.obtain();
        explosion.set(this.position, getHeight());
    }
}

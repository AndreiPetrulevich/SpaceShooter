package game.gb.base;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import game.gb.math.Rect;
import game.gb.pool.BulletPool;
import game.gb.sprite.Bullet;

public class Ship extends Sprite{

    protected Vector2 initialVelocity;
    protected Vector2 velocity;

    protected Rect worldBounds;
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

    public Ship() {
    }

    public Ship(TextureRegion region, int rows, int cols, int frames) {
        super(region, rows, cols, frames);
    }

    @Override
    public void update(float delta) {
        position.mulAdd(velocity, delta);
        timer += delta;
        if (timer >= reloadTime) {
            timer = 0;
            shoot();
        }
    }

    private void shoot() {
        Bullet bullet = bulletPool.obtain();
        bullet.set(this, bulletRegion, bulletPosition, bulletVelocity, worldBounds, damage, bulletHeight);
        bulletSound.play(0.05f);
    }
}

package game.gb.pool;

import game.gb.base.SpritesPool;
import game.gb.sprite.Bullet;

public class BulletPool extends SpritesPool<Bullet> {
    @Override
    protected Bullet newObject() {
        return new Bullet();
    }
}

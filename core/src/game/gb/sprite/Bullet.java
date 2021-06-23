package game.gb.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import game.gb.base.Sprite;
import game.gb.math.Rect;

public class Bullet extends Sprite {

    private Rect worldBounds;
    private Vector2 velocity;
    private int damage;
    private Sprite owner;

    public Bullet() {
        regions = new TextureRegion[1];
        velocity = new Vector2();
    }

    public void set(
            Sprite owner,
            TextureRegion region,
            Vector2 initPosition,
            Vector2 initVelocity,
            Rect worldBounds,
            int damage,
            float height
    ) {
        this.owner = owner;
        this.regions[0] = region;
        this.position.set(initPosition);
        this.velocity.set(initVelocity);
        this.worldBounds = worldBounds;
        this.damage = damage;
        setHeightProportion(height);
    }

    @Override
    public void update(float delta) {
        position.mulAdd(velocity, delta);
        if (isOutside(worldBounds)) {
            destroy();
        }
    }

    public int getDamage() {
        return damage;
    }

    public Sprite getOwner() {
        return owner;
    }
}


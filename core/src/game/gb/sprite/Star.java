package game.gb.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import game.gb.base.Sprite;
import game.gb.math.Rect;
import game.gb.math.Rnd;

public class Star extends Sprite {

    private Vector2 moveOffset;
    private Rect worldBounds;

    public Star(TextureAtlas atlas) {
        super(atlas.findRegion("star"));
        moveOffset = new Vector2();
        float vx = Rnd.nextFloat(-0.0005f, 0.0005f);
        float vy = Rnd.nextFloat(-0.2f, -0.05f);
        moveOffset.set(vx, vy);

    }

    @Override
    public void update(float delta) {
        super.update(delta);
        position.mulAdd(moveOffset, delta);
        if (getRight() < worldBounds.getLeft()) {
            setLeft(worldBounds.getRight());
        }
        if (getLeft() > worldBounds.getRight()) {
            setRight(worldBounds.getLeft());
        }
        if (getTop() < worldBounds.getBottom()) {
            setBottom(worldBounds.getTop());
        }
        if (getBottom() > worldBounds.getTop()) {
            setTop(worldBounds.getBottom());
        }

        flicker();
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        this.worldBounds = worldBounds;
        setHeightProportion(Rnd.nextFloat(0.005f, 0.013f));
        float x = Rnd.nextFloat(worldBounds.getLeft(), worldBounds.getRight());
        float y = Rnd.nextFloat(worldBounds.getBottom(), worldBounds.getTop());
        position.set(x, y);
    }

    public void flicker() {
        float height = getHeight();
        height += 0.0001f;
        if (height >= 0.012f) {
            height = 0.008f;
        }
        setHeightProportion(height);
    }
}

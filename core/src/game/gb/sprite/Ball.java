package game.gb.sprite;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import game.gb.base.Sprite;
import game.gb.math.Rect;

public class Ball extends Sprite {

    private static final float V_LENGTH = 0.005f;
    private Vector2 targetPosition;
    private Vector2 moveOffset;
    private Vector2 tmp;

    public Ball(Texture texture) {
        super(new TextureRegion(texture));
        targetPosition = new Vector2();
        moveOffset = new Vector2();
        tmp = new Vector2();

    }

    @Override
    public void update(float delta) {
        super.update(delta);
        tmp.set(targetPosition);
        if(tmp.sub(position).len() <= moveOffset.len()) {
            position.set(targetPosition);
            moveOffset.setZero();
        } else {
            position.add(moveOffset);
        }
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setHeightProportion(0.15f);
    }

    @Override
    public boolean touchDown(Vector2 targetPosition, int pointer, int button) {
        this.targetPosition.set(targetPosition);
        moveOffset.set(targetPosition.cpy().sub(position).setLength(V_LENGTH));
        return false;
    }
}

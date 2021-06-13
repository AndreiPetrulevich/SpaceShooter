package game.gb.sprite;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import game.gb.base.Sprite;
import game.gb.math.Rect;

public class SpaceShip extends Sprite {

    private static final float HEIGHT = 0.15f;
    private static final float PADDING = 0.03f;
    private static final int NOT_VALID_POINTER = -1;

    private final Vector2 INITIAL_VELOCITY = new Vector2(0.5f, 0);
    private Vector2 velocity = new Vector2();

    private boolean isPressedLeft;
    private boolean isPressedRight;

    private int leftPointer = NOT_VALID_POINTER;
    private int rightPointer = NOT_VALID_POINTER;


    private Rect worldBounds;

    public SpaceShip(TextureAtlas atlas) {
        //super(atlas.findRegion("main_ship"), 916, 95, 390, 287, 2);
        super(atlas.findRegion("main_ship"), 1, 2, 2);
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        setHeightProportion(HEIGHT);
        setBottom(worldBounds.getBottom() + PADDING);
    }

    @Override
    public void update(float delta) {
        position.mulAdd(velocity, delta);
        if (getRight() > worldBounds.getRight()) {
            setRight(worldBounds.getRight());
            stop();
        }
        if (getLeft() < worldBounds.getLeft()) {
            setLeft(worldBounds.getLeft());
            stop();
        }
    }

    @Override
    public boolean touchDown(Vector2 targetPosition, int pointer, int button) {
        if (targetPosition.x < worldBounds.position.x) {
            if (leftPointer != NOT_VALID_POINTER) {
                return false;
            }
            leftPointer = pointer;
            moveLeft();
        } else {
            if (rightPointer != NOT_VALID_POINTER) {
                return false;
            }
            rightPointer = pointer;
            moveRight();
        }
        return false;
    }

    @Override
    public boolean touchUp(Vector2 targetPosition, int pointer, int button) {
        if (pointer == leftPointer) {
            leftPointer = NOT_VALID_POINTER;
            if (rightPointer != NOT_VALID_POINTER) {
                moveRight();
            } else {
                stop();
            }
        }

        if (pointer == rightPointer) {
            rightPointer = NOT_VALID_POINTER;
            if (leftPointer != NOT_VALID_POINTER) {
                moveLeft();
            } else {
                stop();
            }
        }
        return false;
    }

    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.A:
            case Input.Keys.LEFT:
                isPressedLeft = true;
                moveLeft();
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                isPressedRight = true;
                moveRight();
                break;
        }
        return false;
    }

    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.A:
            case Input.Keys.LEFT:
                isPressedLeft = false;
                if (isPressedRight) {
                    moveRight();
                } else {
                    stop();
                }
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                isPressedRight = false;
                if (isPressedLeft) {
                    moveLeft();
                } else {
                    stop();
                }
                break;
        }
        return false;
    }

    private void moveRight() {
        velocity.set(INITIAL_VELOCITY);
    }

    private void moveLeft() {
        velocity.set(INITIAL_VELOCITY).rotateDeg(180);
    }

    private void stop() {
        velocity.setZero();
    }
}

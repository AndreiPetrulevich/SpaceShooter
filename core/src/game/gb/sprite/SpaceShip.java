package game.gb.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import game.gb.base.Ship;
import game.gb.base.Sprite;
import game.gb.math.Rect;
import game.gb.pool.BulletPool;

public class SpaceShip extends Ship {

    private static final float HEIGHT = 0.15f;
    private static final float PADDING = 0.03f;
    private static final int NOT_VALID_POINTER = -1;
    private static final float RELOAD_TIME = 0.2f;

    private boolean isPressedLeft;
    private boolean isPressedRight;

    private int leftPointer = NOT_VALID_POINTER;
    private int rightPointer = NOT_VALID_POINTER;

    public SpaceShip(TextureAtlas atlas, BulletPool bulletPool, Sound bulletSound) {
        //super(atlas.findRegion("main_ship"), 916, 95, 390, 287, 2);
        super(atlas.findRegion("main_ship"), 1, 2, 2);
        this.bulletPool = bulletPool;
        this.bulletRegion = atlas.findRegion("bulletMainShip");
        this.bulletVelocity = new Vector2(0, 0.5f);
        this.bulletPosition = new Vector2();
        this.bulletSound = bulletSound;
        initialVelocity = new Vector2(0.5f, 0);
        velocity = new Vector2();
        reloadTime = RELOAD_TIME;
        bulletHeight = 0.01f;
        damage = 1;
        hp = 10;
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        setHeightProportion(HEIGHT);
        setBottom(worldBounds.getBottom() + PADDING);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        bulletPosition.set(position.x, position.y + getHalfHeight());
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
            /*case Input.Keys.UP:
                shoot();
                bulletSound.play(0.1f);
                break;*/
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
        velocity.set(initialVelocity);
    }

    private void moveLeft() {
        velocity.set(initialVelocity).rotateDeg(180);
    }

    private void stop() {
        velocity.setZero();
    }

}

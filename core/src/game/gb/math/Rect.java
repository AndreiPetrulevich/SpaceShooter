package game.gb.math;

import com.badlogic.gdx.math.Vector2;

/**
 * Прямоугольник
 */
public class Rect {

    public final Vector2 position = new Vector2(); // позиция по центру
    protected float halfWidth; // половина ширины
    protected float halfHeight; // половина высоты

    public Rect() {

    }

    public Rect(Rect from) {
        this(from.position.x, from.position.y, from.getHalfWidth(), from.getHalfHeight());
    }

    public Rect(float x, float y, float halfWidth, float halfHeight) {
        position.set(x, y);
        this.halfWidth = halfWidth;
        this.halfHeight = halfHeight;
    }

    public float getLeft() {
        return position.x - halfWidth;
    }

    public float getTop() {
        return position.y + halfHeight;
    }

    public float getRight() {
        return position.x + halfWidth;
    }

    public float getBottom() {
        return position.y - halfHeight;
    }

    public float getHalfWidth() {
        return halfWidth;
    }

    public float getHalfHeight() {
        return halfHeight;
    }

    public float getWidth() {
        return halfWidth * 2f;
    }

    public float getHeight() {
        return halfHeight * 2f;
    }

    public void set(Rect from) {
        position.set(from.position);
        halfWidth = from.halfWidth;
        halfHeight = from.halfHeight;
    }

    public void setLeft(float left) {
        position.x = left + halfWidth;
    }

    public void setTop(float top) {
        position.y = top - halfHeight;
    }

    public void setRight(float right) {
        position.x = right - halfWidth;
    }

    public void setBottom(float bottom) {
        position.y = bottom + halfHeight;
    }

    public void setWidth(float width) {
        this.halfWidth = width / 2f;
    }

    public void setHeight(float height) {
        this.halfHeight = height / 2f;
    }

    public void setSize(float width, float height) {
        this.halfWidth = width / 2f;
        this.halfHeight = height / 2f;
    }

    public boolean isMe(Vector2 targetPosition) {
        return targetPosition.x >= getLeft()
                && targetPosition.x <= getRight()
                && targetPosition.y >= getBottom()
                && targetPosition.y <= getTop();
    }

    public boolean isOutside(Rect other) {
        return getLeft() > other.getRight() || getRight() < other.getLeft() || getBottom() > other.getTop() || getTop() < other.getBottom();
    }

    @Override
    public String toString() {
        return "Rectangle: pos" + position + " size(" + getWidth() + ", " + getHeight() + ")";
    }
}
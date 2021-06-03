package game.gb.base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import game.gb.math.MatrixUtils;
import game.gb.math.Rect;


public class BaseScreen implements Screen, InputProcessor {

    protected SpriteBatch batch;

    private Rect screenBounds;
    private Rect worldBounds;
    private Rect glBounds;
    private Vector2 targetPosition;

    private Matrix4 worldToGl;
    private Matrix3 screenToWorld;

    @Override
    public void show() {
        batch = new SpriteBatch();
        screenBounds = new Rect();
        worldBounds = new Rect();
        glBounds = new Rect(0, 0, 1f,1f);
        worldToGl = new Matrix4();
        screenToWorld = new Matrix3();
        targetPosition = new Vector2();
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {
        screenBounds.setSize(width, height);
        screenBounds.setLeft(0);
        screenBounds.setBottom(0);

        float aspect = width / (float) height;
        worldBounds.setHeight(1f);
        worldBounds.setWidth(1f * aspect);

        MatrixUtils.calcTransitionMatrix(worldToGl, worldBounds, glBounds);
        MatrixUtils.calcTransitionMatrix(screenToWorld, screenBounds, worldBounds);

        batch.setProjectionMatrix(worldToGl);
        resize(worldBounds);
    }

    public void resize(Rect worldBounds) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        targetPosition.set(screenX, screenBounds.getHeight() - screenY).mul(screenToWorld);
        touchDown(targetPosition, pointer, button);
        return false;
    }

    public boolean touchDown(Vector2 targetPosition, int pointer, int button) {
        targetPosition.set(targetPosition.x, screenBounds.getHeight() - targetPosition.y).mul(screenToWorld);
        return false;
    }


    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        targetPosition.set(screenX, screenBounds.getHeight() - screenY).mul(screenToWorld);
        touchUP(targetPosition, pointer, button);
        return false;
    }

    public boolean touchUP(Vector2 targetPosition, int pointer, int button) {
        targetPosition.set(targetPosition.x, screenBounds.getHeight() - targetPosition.y).mul(screenToWorld);
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        targetPosition.set(screenX, screenBounds.getHeight() - screenY).mul(screenToWorld);
        touchDragget(targetPosition, pointer);
        return false;
    }

    public boolean touchDragget(Vector2 targetPosition, int pointer) {
        targetPosition.set(targetPosition.x, screenBounds.getHeight() - targetPosition.y).mul(screenToWorld);
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}

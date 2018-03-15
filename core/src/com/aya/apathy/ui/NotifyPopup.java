package com.aya.apathy.ui;

import com.aya.apathy.Game;
import com.aya.apathy.screens.Backable;
import com.aya.apathy.screens.Renderable;
import com.aya.apathy.util.Assets;
import com.aya.apathy.util.Constants;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;

public class NotifyPopup extends BasicActivatable implements Renderable, Backable, InputProcessor, Disposable {
    public Button ok;
    protected int width, height;
    protected String text;
    private Label label;
    private Color bounds, background;
    private SpriteBatch spriteBatch;

    public NotifyPopup(String text){
        spriteBatch=new SpriteBatch();
        bounds=new Color(205/255f, 205/255f, 205/255f, 1);
        background=new Color(0,0,0,.5f);
        width= Constants.WIDTH; height= Constants.HEIGHT;
        this.text=text;
        label=new Label(text, new Label.LabelStyle(Assets.main20, Color.WHITE));
        label.setBounds(width/3, height/3, width/3, height/3);
        label.setAlignment(Align.center);
        label.setWrap(true);
        ok=new Button(Game.language.bundle.get("ok"), width/2, height/3+height/16, Color.rgb565(0,168/255f,81/255f), Color.rgb565(20/225f,188/255f,101/255f), true);
        setIdleState();
    }

    @Override
    public void render(ShapeRenderer shapeRenderer) {
        if (isActive()) {
            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_ONE, GL20.GL_ONE_MINUS_SRC_ALPHA);
            //shapeRenderer.setProjectionMatrix(camera.combined);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(background);
            shapeRenderer.rect(0,0,width,height);
            shapeRenderer.end();
            Gdx.gl.glDisable(GL20.GL_BLEND);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(bounds);
            shapeRenderer.rect(width/4, height/3, width/2, height/3);
            shapeRenderer.end();
            ok.render(shapeRenderer);
            spriteBatch.begin();
            label.draw(spriteBatch, 1);
            spriteBatch.end();
        }
    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
        ok.dispose();
    }

    @Override
    public boolean onBackPressed() {
        if (isActive()) {
            setIdleState();
            return true;
        }
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return isActive() && ok.keyUp(keycode);
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return isActive() && ok.touchDown(screenX, screenY, pointer, button);
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return isActive() && ok.touchUp(screenX, screenY, pointer, button);
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return isActive() && ok.touchDragged(screenX, screenY, pointer);
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

}

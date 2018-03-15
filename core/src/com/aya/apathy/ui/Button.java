package com.aya.apathy.ui;

import com.aya.apathy.Game;
import com.aya.apathy.audio.Sfx;
import com.aya.apathy.screens.Renderable;
import com.aya.apathy.util.Assets;
import com.aya.apathy.util.Constants;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Disposable;

public class Button extends BasicActivatable implements Renderable, Touchable, InputProcessor, Disposable {

    private enum Type {TEXT, BITMAP}
    private Type type;
    private boolean isPositive, soundless;
    private Rectangle rect;
    private String text;
    private Color regular, pressed, textColor;
    private int left, bottom;
    private TextureRegion textureRegion;
    private static int width= Constants.WIDTH, height=Constants.HEIGHT;
    private SpriteBatch spriteBatch;

    public void setSoundless(){
        soundless=true;
    }

    public void setTextColor(Color color){
        textColor=color;
    }

    public Button (String text, int centerX, int centerY, int regular, int pressed, boolean isPositive){
        spriteBatch=new SpriteBatch();
        type=Type.TEXT;
        GlyphLayout glyphLayout=new GlyphLayout(Assets.main20, text);
        this.text=text;
        left =centerX-(int)glyphLayout.width/2;
        bottom =centerY+(int)glyphLayout.height/2;
        rect=new Rectangle(left-width/40, bottom -3*glyphLayout.height/2,
                glyphLayout.width+width/20, 2*glyphLayout.height);
        this.regular=new Color(0,0,0,0);
        this.pressed=new Color(regular);
        Color.rgb565ToColor(this.regular, regular);
        Color.rgb565ToColor(this.pressed, pressed);
        this.isPositive=isPositive;
        textColor=Assets.main20.getColor();
        setIdleState();
    }

    public Button (TextureRegion textureRegion, int centerX, int centerY, int sideIndent, int frontIndent, boolean isPositive){
        spriteBatch=new SpriteBatch();
        type=Type.BITMAP;
        this.textureRegion=textureRegion;
        left =centerX-textureRegion.getRegionWidth()/2;
        bottom =centerY-textureRegion.getRegionHeight()/2;
        rect=new Rectangle(left-sideIndent, bottom -frontIndent, height/6, height/6);
        this.regular=new Color(1,1,1,1);
        this.pressed=new Color(220/255f,220/255f,220/255f,.5f);
        this.isPositive=isPositive;
        setIdleState();
    }

    @Override
    public void render(ShapeRenderer shapeRenderer) {
        shapeRenderer.begin(isActive()?ShapeRenderer.ShapeType.Filled:ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(isActive()?pressed:regular);
        shapeRenderer.rect(rect.x, rect.y, rect.width, rect.height);
        shapeRenderer.end();
        switch (type){
            case TEXT:
                spriteBatch.begin();
                Assets.main20.draw(spriteBatch, text, left, bottom);
                break;
            case BITMAP:
                spriteBatch.begin();
                spriteBatch.draw(textureRegion, left, bottom, height/6, height/6);
                break;
        }
        spriteBatch.end();
    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
    }

    @Override
    public boolean touch(int x, int y) {
        return rect.contains(x,y);
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
        boolean tmp=touch(screenX, screenY);
        if (tmp) setActiveState();
        return tmp;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        boolean tmp=touch(screenX, screenY);
        if (tmp) {
            setIdleState();
            if (!soundless)
                Game.sfx.play(isPositive? Sfx.SoundType.CLICK_POSITIVE: Sfx.SoundType.CLICK_NEGATIVE);
        }
        return tmp;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        boolean tmp=!touch(screenX, screenY);
        if (tmp) setIdleState(); else setActiveState();
        return tmp;
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


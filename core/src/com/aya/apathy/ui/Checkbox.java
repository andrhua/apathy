package com.aya.apathy.ui;

import com.aya.apathy.Game;
import com.aya.apathy.audio.Sfx;
import com.aya.apathy.screens.Renderable;
import com.aya.apathy.util.Assets;
import com.aya.apathy.util.Constants;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

public class Checkbox extends InputAdapter implements Renderable, Disposable{
    private boolean isChecked;
    private int centerX, centerY;
    private String text;
    private Rectangle checkboxBounds;
    private SpriteBatch spriteBatch;
    private Vector2 center;
    private GlyphLayout glyphLayout;
    private TextureRegion textureRegion;
    private Sprite sprite;

    public Checkbox(boolean isChecked, String text, int centerX, int centerY){
        this.isChecked=isChecked;
        this.text=text;
        this.centerX=centerX;
        this.centerY=centerY;
        glyphLayout=new GlyphLayout(Assets.main20, text);
        checkboxBounds=new Rectangle(centerX+glyphLayout.width/2+Constants.WIDTH/20-Constants.WIDTH/50, centerY-Constants.WIDTH/50,
                2*Constants.WIDTH/50, 2*Constants.WIDTH/50);
        center=new Vector2();
        center=checkboxBounds.getCenter(center);
        spriteBatch=new SpriteBatch();
        textureRegion=Assets.other.findRegion(Assets.OTHER_CHECKED);
        sprite=Assets.other.createSprite(Assets.OTHER_CHECKED);
    }

    public void setChecked(boolean isChecked){
        this.isChecked=isChecked;
    }

    public boolean isChecked(){
        return isChecked;
    }

    @Override
    public void render(ShapeRenderer shapeRenderer) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.rect(checkboxBounds.x, checkboxBounds.y, checkboxBounds.width, checkboxBounds.height);
        shapeRenderer.end();
        spriteBatch.begin();
        Assets.main20.draw(spriteBatch, text, centerX-glyphLayout.width/2-Constants.WIDTH/20, checkboxBounds.y+checkboxBounds.height/2+Constants.HEIGHT/40);
        if (isChecked)
            spriteBatch.draw(sprite, center.x-Constants.WIDTH/100, center.y-Constants.WIDTH/100, Constants.WIDTH/50, Constants.WIDTH/50);
        spriteBatch.end();
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        boolean tmp=checkboxBounds.contains(screenX, screenY);
        if (tmp) {
            Game.sfx.play(Sfx.SoundType.CLICK_POSITIVE);
            isChecked=!isChecked;
        }
        return tmp;
    }


    @Override
    public void dispose() {
        spriteBatch.dispose();
    }
}

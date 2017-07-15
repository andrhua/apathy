package com.aya.apathy.model;

import com.aya.apathy.Game;
import com.aya.apathy.screens.Renderable;
import com.aya.apathy.ui.BasicActivatable;
import com.aya.apathy.ui.Touchable;
import com.aya.apathy.util.Assets;
import com.aya.apathy.util.Constants;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public class LevelPreview extends BasicActivatable implements Renderable, Touchable {
    private int width, height;
    private String name;
    private Rectangle preview;
    private int number;
    private SpriteBatch spriteBatch;
    private TextureRegion locked, image;
    private GlyphLayout glyphLayout, deathsLayout;
    private Color lockedColor, levelColor, textColor;
    private String deaths;

    public LevelPreview(int number) {
        this.number=number;
        if (Game.playerData.progress.aromas-1>=number) setActiveState(); else setIdleState();
        width= Constants.WIDTH; height=Constants.HEIGHT;
        image=Assets.backgrounds_preview.findRegion(String.valueOf(number));
        locked=Assets.other.findRegion(Assets.OTHER_LOCKED);
        lockedColor=new Color(0,0,0,.5f);
        levelColor=new Color();
        name= Game.language.bundle.get("level".concat(String.valueOf(number)));
        levelColor=Constants.COLOR[number];
        textColor= number>2?Color.WHITE:Color.BLACK;
        int i=2-number/3, j=number%3+1;
        preview=new Rectangle(j*width/4-width/10, i*height/3-height/10, width/5, height/5);
        spriteBatch=new SpriteBatch();
        glyphLayout=new GlyphLayout(Assets.main15, name);
        deaths=Game.language.bundle.get("deaths").concat(" ").concat(String.valueOf(Game.playerData.progress.deaths[number]));
        deathsLayout=new GlyphLayout(Assets.main20, deaths);
    }

    @Override
    public void render(ShapeRenderer shapeRenderer) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(levelColor);
        shapeRenderer.rect(preview.x, preview.y, preview.width, preview.height);
        shapeRenderer.end();
        spriteBatch.begin();
        spriteBatch.draw(image, preview.x+2, preview.y+2, width/5-4, height/5-4);
        Assets.main15.setColor(textColor);
        Assets.main15.draw(spriteBatch, name, preview.x+width/10-glyphLayout.width/2, preview.y+height/8);
        Assets.main15.setColor(Color.BLACK);
        spriteBatch.end();
        if (!isActive()) {
            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_ONE, GL20.GL_ONE_MINUS_SRC_ALPHA);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(lockedColor);
            shapeRenderer.rect(preview.x, preview.y, preview.width, preview.height);
            shapeRenderer.end();
            Gdx.gl.glDisable(GL20.GL_BLEND);
            spriteBatch.begin();
            spriteBatch.draw(locked, preview.x + width / 10 - height / 16, preview.y + height / 10 - height / 16, height/8, height/8);
            spriteBatch.end();
        } else {
            spriteBatch.begin();
            Assets.main20.draw(spriteBatch, deaths, preview.x+width/10-deathsLayout.width/2, preview.y-height/20+deathsLayout.height/2);
            spriteBatch.end();
        }
    }

    @Override
    public boolean touch(int x, int y) {
        return preview.contains(x,y);
    }
}

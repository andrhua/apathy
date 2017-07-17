package com.aya.apathy.game.object;

import com.aya.apathy.Game;
import com.aya.apathy.game.tiled.core.MapObject;
import com.aya.apathy.util.Assets;
import com.aya.apathy.util.Constants;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;

public class Hint extends StaticGameObject {
    private Label label;

    public Hint(int id, MapObject mapObject, int zOrder) {
        super(id, mapObject, zOrder);
        String text= Game.language.bundle.get("knowledge").split(",")[Integer.valueOf(mapObject.getProperties().getProperty("knowledge"))];
        GlyphLayout glyphLayout=new GlyphLayout(Assets.main20, text);
        label=new Label(text, new Label.LabelStyle(Assets.main20, Color.BLACK));
        label.setBounds(left*Constants.PPM, bottom*Constants.PPM, width*Constants.PPM, height*Constants.PPM);
        label.setWrap(true);
        label.setAlignment(Align.center);
    }

    @Override
    public void draw(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.rect(label.getX(), label.getY(), label.getWidth(), label.getHeight());
        shapeRenderer.end();
        spriteBatch.begin();
        label.draw(spriteBatch, 1);
        spriteBatch.end();
    }


}

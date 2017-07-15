package com.aya.apathy.game.object;

import com.aya.apathy.game.tiled.core.MapObject;
import com.aya.apathy.util.Assets;
import com.aya.apathy.util.Constants;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Background extends StaticGameObject {
    public Background(int id, MapObject mapObject, int zOrder) {
        super(id, mapObject, zOrder);
    }

    @Override
    public void draw(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Assets.Game.background);
        shapeRenderer.rect(left*Constants.PPM,bottom*Constants.PPM,width* Constants.PPM, height*Constants.PPM);
        shapeRenderer.end();
    }

    public static class Overlap extends Background {

        public Overlap(int id, MapObject mapObject, int zOrder) {
            super(id, mapObject, zOrder);
        }
    }
}

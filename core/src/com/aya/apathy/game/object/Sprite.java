package com.aya.apathy.game.object;

import com.aya.apathy.game.tiled.core.MapObject;
import com.aya.apathy.util.Assets;
import com.aya.apathy.util.Constants;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Sprite extends StaticGameObject {
    private Assets.Game.Object object;

    public Sprite(int id, MapObject mapObject, int zOrder) {
        super(id, mapObject, zOrder);
        object = Assets.Game.Object.valueOf(mapObject.getProperties().getProperty("type"));
        textureRegion=Assets.Game.game_objects.findRegion(String.valueOf(object.ordinal()));
        switch (object){
            case TANGELO:case MACHAMP:case LAIR:textureSize=6*Constants.PPM; break;
        }
    }

    @Override
    public void draw(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer) {
        spriteBatch.begin();
        spriteBatch.draw(textureRegion,
                left* Constants.PPM ,
                bottom*Constants.PPM ,
                textureSize,
                textureSize);
        spriteBatch.end();
    }
}

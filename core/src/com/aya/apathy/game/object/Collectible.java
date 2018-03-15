package com.aya.apathy.game.object;

import com.aya.apathy.game.FixtureData;
import com.aya.apathy.game.tiled.core.MapObject;
import com.aya.apathy.util.Assets;
import com.aya.apathy.util.Constants;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.World;

public class Collectible extends Prequark {
    private Assets.Game.Object object;

    public Collectible(int id, MapObject mapObject, World world, int zOrder) {
        super(id, mapObject, world, zOrder);
        object = Assets.Game.Object.valueOf(mapObject.getProperties().getProperty("type"));
        fixture.setUserData(new FixtureData(FixtureData.Type.COLLECTIBLE, id));
        type = FixtureData.Type.COLLECTIBLE;
        textureRegion=Assets.Game.game_objects.findRegion(String.valueOf(object.ordinal()));
        textureSize=Constants.PPM;
    }


    @Override
    public void draw(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer) {
        spriteBatch.begin();
        spriteBatch.draw(textureRegion,
                left*Constants.PPM,
                bottom*Constants.PPM,
                textureSize,
                textureSize);
        spriteBatch.end();
    }

    @Override
    public void reset() {
        if (!isActive()) {
            body.setActive(false);
            world.destroyBody(body);
        }
        body = world.createBody(bodyDef);
        body.createFixture(fixtureDef).setUserData(new FixtureData(FixtureData.Type.COLLECTIBLE, id));
        setActiveState();
    }


}

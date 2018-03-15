package com.aya.apathy.game.object;

import com.aya.apathy.game.FixtureData;
import com.aya.apathy.game.tiled.core.MapObject;
import com.aya.apathy.util.Assets;
import com.aya.apathy.util.Constants;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;


public class GravitySwitcher extends Indicator implements Queryable {
    private World world;
    private Vector2 gravity;

    public GravitySwitcher(int id, MapObject mapObject, World world) {
        super(id, mapObject, world);
        this.world = world;
        boolean isUp = Boolean.parseBoolean(mapObject.getProperties().getProperty("i", "false"));
        fixture.setUserData(new FixtureData(FixtureData.Type.GRAVITY_SWITCHER, id));
        type = FixtureData.Type.GRAVITY_SWITCHER;
        gravity = new Vector2(0, (isUp ? 1 : -1) * 15);
        textureRegion=Assets.Game.game_objects.findRegion(String.valueOf(Assets.Game.Object.AMPHIBIAN.ordinal()));
        textureSize=2*Constants.PPM;
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

    @Override
    public void queryForMagic() {
        world.setGravity(gravity);
    }


}

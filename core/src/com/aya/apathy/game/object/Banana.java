package com.aya.apathy.game.object;

import com.aya.apathy.game.FixtureData;
import com.aya.apathy.game.contactslistener.ContactsListener;
import com.aya.apathy.game.tiled.core.MapObject;
import com.aya.apathy.util.Assets;
import com.aya.apathy.util.Constants;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class Banana extends DynamicGameObject implements Queryable {
    public int state;
    private Vector2[] states;
    private boolean needToTransform;

    public Banana(int id, MapObject mapObject, World world, int zOrder) {
        super(id, mapObject, zOrder);
        state = 0;
        states = new Vector2[10];
        states[0] = new Vector2(524, 56);
        states[1] = new Vector2(520, 43);
        states[2] = new Vector2(511, 56);
        states[3] = new Vector2(515, 43);
        states[4] = new Vector2(517.5f, 48);
        states[5] = states[2];
        states[6] = states[3];
        states[7] = states[0];
        states[8] = new Vector2(518, 43);
        states[9] = new Vector2(518, 0);
        bodyDef.position.set(states[state]);
        body = world.createBody(bodyDef);
        polygonShape.setAsBox(.30f, .30f);
        fixtureDef.shape = polygonShape;
        fixtureDef.isSensor = true;
        fixtureDef.filter.categoryBits = ContactsListener.CATEGORY_SCENERY;
        fixtureDef.filter.maskBits = ContactsListener.MASK_SCENERY;
        body.createFixture(fixtureDef).setUserData(new FixtureData(FixtureData.Type.BANANA, id));
        type = FixtureData.Type.BANANA;
        textureRegion=Assets.Game.game_objects.findRegion(String.valueOf(Assets.Game.Object.BANANA.ordinal()));
        textureSize=Constants.PPM;
    }

    @Override
    public void draw(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer) {
        spriteBatch.begin();
        spriteBatch.draw(textureRegion,
                (body.getPosition().x - .3f)*Constants.PPM,
                (body.getPosition().y - .3f)*Constants.PPM,
                textureSize,
                textureSize);
        spriteBatch.end();
    }

    @Override
    public void reset() {
        state = 0;
        needToTransform = false;
        body.setTransform(states[0], 0);
    }

    @Override
    public void queryForMagic() {
        needToTransform = true;
    }

    @Override
    public void update(float elapsedTime) {
        if (needToTransform) {
            body.setTransform(states[++state], 0);
            needToTransform = false;
        }
    }

}

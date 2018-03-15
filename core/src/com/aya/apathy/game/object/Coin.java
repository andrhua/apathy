package com.aya.apathy.game.object;

import com.aya.apathy.game.FixtureData;
import com.aya.apathy.game.contactslistener.ContactsListener;
import com.aya.apathy.game.tiled.core.MapObject;
import com.aya.apathy.util.Assets;
import com.aya.apathy.util.Constants;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.World;


public class Coin extends StaticGameObject implements Resetable {
    private World world;

    public Coin(int id, MapObject mapObject, World world, int zOrder) {
        super(id, mapObject, zOrder);
        this.world = world;
        bodyDef.position.set(centerX, centerY);
        body = world.createBody(bodyDef);
        polygonShape.setAsBox(.30f, .30f);
        fixtureDef.shape = polygonShape;
        fixtureDef.isSensor = true;
        fixtureDef.filter.categoryBits = ContactsListener.CATEGORY_COIN;
        fixtureDef.filter.maskBits = ContactsListener.MASK_COIN;
        fixture = body.createFixture(fixtureDef);
        fixture.setUserData(new FixtureData(FixtureData.Type.COIN, id));
        type = FixtureData.Type.COIN;
        textureRegion=Assets.Game.game_objects.findRegion(String.valueOf(Assets.Game.Object.COIN.ordinal()));
        textureSize=.6f* Constants.PPM;
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
        body.setActive(false);
        world.destroyBody(body);
        body = world.createBody(bodyDef);
        body.createFixture(fixtureDef).setUserData(new FixtureData(FixtureData.Type.COIN, id));
        setActiveState();
    }
}

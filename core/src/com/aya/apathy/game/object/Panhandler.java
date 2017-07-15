package com.aya.apathy.game.object;

import com.aya.apathy.game.FixtureData;
import com.aya.apathy.game.contactslistener.ContactsListener;
import com.aya.apathy.game.tiled.core.MapObject;
import com.aya.apathy.util.Assets;
import com.aya.apathy.util.Constants;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

public class Panhandler extends StaticGameObject implements Resetable {
    public enum State {SATISFIED, NOT_SATISFIED}
    public boolean isTouching;
    private State state;

    public void setState(State state) {
        this.state = state;
    }

    public Panhandler(int id, MapObject mapObject, World world, int zOrder) {
        super(id, mapObject, zOrder);
        polygonShape.setAsBox(width / 2, height / 2);
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(centerX, centerY);
        body = world.createBody(bodyDef);
        fixtureDef.shape = polygonShape;
        fixtureDef.filter.categoryBits = ContactsListener.CATEGORY_SCENERY;
        fixtureDef.filter.maskBits = ContactsListener.MASK_SCENERY;
        fixtureDef.isSensor = true;
        fixture = body.createFixture(fixtureDef);
        fixture.setUserData(new FixtureData(FixtureData.Type.PANHANDLER, id));
        type = FixtureData.Type.PANHANDLER;
        state = State.NOT_SATISFIED;
        textureRegion=Assets.Game.game_objects.findRegion(String.valueOf(Assets.Game.Object.PANHANDLER.ordinal()));
        textureSize=1.5f* Constants.PPM;
    }

    @Override
    public void draw(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer) {
        spriteBatch.begin();
        spriteBatch.draw(textureRegion,
                left *Constants.PPM,
                bottom *Constants.PPM,
                textureSize,
                textureSize);
        spriteBatch.end();
    }

    public State getState() {
        return state;
    }

    @Override
    public void reset() {
        state = State.NOT_SATISFIED;
        isTouching = false;
    }
}

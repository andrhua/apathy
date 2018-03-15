package com.aya.apathy.game.object;

import com.aya.apathy.game.FixtureData;
import com.aya.apathy.game.contactslistener.ContactsListener;
import com.aya.apathy.game.tiled.core.MapObject;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

public class Indicator extends StaticGameObject {

    public Indicator(int id, MapObject mapObject, World world) {
        super(id, mapObject, 0);
        polygonShape.setAsBox(width / 2, height / 2);
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(centerX, centerY);
        body = world.createBody(bodyDef);
        fixtureDef.shape = polygonShape;
        fixtureDef.isSensor = true;
        fixtureDef.filter.categoryBits = ContactsListener.CATEGORY_TRAP;
        fixtureDef.filter.maskBits = ContactsListener.MASK_SCENERY;
        fixture = body.createFixture(fixtureDef);
        fixture.setUserData(new FixtureData(FixtureData.Type.INDICATOR, id));
        type = FixtureData.Type.INDICATOR;
        setIdleState();
    }

    @Override
    public void draw(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer) {

    }

    public static class Death extends Indicator {

        public Death(int id, MapObject mapObject, World world) {
            super(id, mapObject, world);
            fixture.setUserData(new FixtureData(FixtureData.Type.DEATH_INDICATOR, id));
            type = FixtureData.Type.DEATH_INDICATOR;
        }
    }
}

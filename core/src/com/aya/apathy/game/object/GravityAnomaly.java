package com.aya.apathy.game.object;

import com.aya.apathy.Game;
import com.aya.apathy.game.FixtureData;
import com.aya.apathy.game.contactslistener.ContactsListener;
import com.aya.apathy.game.tiled.core.MapObject;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

public class GravityAnomaly extends DynamicGameObject {
    private enum Kind {NONE, GEYSER}
    private Kind kind = Kind.NONE;
    private Vector2 anomaly;
    public boolean needToAnomaly;
    private Joe joe;

    public GravityAnomaly(int id, MapObject mapObject, World world) {
        super(id, mapObject, 0);
        polygonShape.setAsBox(width / 2, height / 2);
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(centerX, centerY);
        body = world.createBody(bodyDef);
        fixtureDef.shape = polygonShape;
        fixtureDef.filter.categoryBits = ContactsListener.CATEGORY_SCENERY;
        fixtureDef.filter.maskBits = ContactsListener.MASK_SCENERY;
        fixtureDef.isSensor = true;
        fixture = body.createFixture(fixtureDef);
        fixture.setUserData(new FixtureData(FixtureData.Type.GRAVITY_ANOMALY, id));
        type = FixtureData.Type.GRAVITY_ANOMALY;
        joe = Game.joe;
        anomaly = new Vector2(Float.valueOf(mapObject.getProperties().getProperty("anomalyX")),
                -Float.valueOf(mapObject.getProperties().getProperty("anomalyY")));
        setIdleState();
    }

    @Override
    public void draw(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer) {

    }

    @Override
    public void update(float elapsedTime) {
        if (needToAnomaly && isActive()) {
            joe.body.applyLinearImpulse(anomaly, joe.getPosition(), true);
        }
    }

    @Override
    public void reset() {
        setIdleState();
    }

}

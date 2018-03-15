package com.aya.apathy.game.object;

import com.aya.apathy.game.FixtureData;
import com.aya.apathy.game.contactslistener.ContactsListener;
import com.aya.apathy.game.tiled.core.MapObject;
import com.aya.apathy.util.Assets;
import com.aya.apathy.util.Constants;
import com.aya.apathy.util.MissingEssences;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

public class Woomba extends DynamicGameObject {
    private float left, right;
    private boolean isGoingLeft;
    private Vector2 velocity;
    private World world;

    public Woomba(int id, MapObject mapObject, World world, int zOrder) {
        super(id, mapObject, zOrder);
        this.world = world;
        left = Float.valueOf(mapObject.getProperties().getProperty("left"));
        right = Float.valueOf(mapObject.getProperties().getProperty("right"));
        bodyDef.gravityScale = Integer.valueOf(mapObject.getProperties().getProperty("gravity", "1"));
        polygonShape.setAsBox(.4f, .4f);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(centerX, centerY);
        body = world.createBody(bodyDef);
        fixtureDef.shape = polygonShape;
        fixtureDef.filter.categoryBits = ContactsListener.CATEGORY_ENEMY;
        fixtureDef.filter.maskBits = ContactsListener.MASK_ENEMY;
        body.createFixture(fixtureDef).setUserData(new FixtureData(FixtureData.Type.WOOMBA, id));
        polygonShape.setAsBox(.2f, .2f, new Vector2(0, .4f), 0);
        fixtureDef.isSensor = true;
        body.createFixture(fixtureDef).setUserData(new FixtureData(FixtureData.Type.WOOMBA_SENSOR, this.id));
        type = FixtureData.Type.WOOMBA;
        isGoingLeft = true;
        velocity = new Vector2(MathUtils.random(-9f, -8f), 0);
        textureRegion=Assets.Game.game_objects.findRegion(String.valueOf(Assets.Game.Object.WOOMBA.ordinal()));
        textureSize= Constants.PPM;
    }

    @Override
    public void draw(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer) {
        spriteBatch.begin();
        spriteBatch.draw(textureRegion,
                (body.getPosition().x-.5f)*Constants.PPM,
                (body.getPosition().y-.5f)*Constants.PPM,
                textureSize,
                textureSize);
        spriteBatch.end();
    }

    @Override
    public void update(float elapsedTime) {
        if (body.getPosition().x <= this.centerX - left) isGoingLeft = false;
        if (body.getPosition().x >= this.centerX + right) isGoingLeft = true;
        body.applyLinearImpulse(isGoingLeft ? velocity : MissingEssences.abs(velocity), body.getPosition(), true);
        if (isGoingLeft) {
            if (body.getLinearVelocity().x < velocity.x)
                body.setLinearVelocity(new Vector2(velocity.x, body.getLinearVelocity().y));
        } else if (body.getLinearVelocity().x > MissingEssences.abs(velocity).x)
            body.setLinearVelocity(new Vector2(MissingEssences.abs(velocity).x, body.getLinearVelocity().y));
    }

    @Override
    public void reset() {
        body.setActive(false);
        world.destroyBody(body);
        polygonShape.setAsBox(.4f, .4f);
        body = world.createBody(bodyDef);
        fixtureDef.isSensor = false;
        body.createFixture(fixtureDef).setUserData(new FixtureData(FixtureData.Type.WOOMBA, id));
        polygonShape.setAsBox(.2f, .2f, new Vector2(0, .4f), 0);
        fixtureDef.isSensor = true;
        body.createFixture(fixtureDef).setUserData(new FixtureData(FixtureData.Type.WOOMBA_SENSOR, this.id));
        setActiveState();
    }
}

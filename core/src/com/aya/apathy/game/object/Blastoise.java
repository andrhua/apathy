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

public class Blastoise extends DynamicGameObject {
    private float left, right;
    private boolean isGoingLeft;
    private Vector2 velocity;
    private World world;

    public Blastoise(int id, MapObject mapObject, World world, int zOrder) {
        super(id, mapObject, zOrder);
        this.world = world;
        left = Float.valueOf(mapObject.getProperties().getProperty("left"));
        right = Float.valueOf(mapObject.getProperties().getProperty("right"));
        polygonShape.setAsBox(1.5f, 1.5f);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(centerX, centerY);
        body = world.createBody(bodyDef);
        fixtureDef.shape = polygonShape;
        fixtureDef.filter.categoryBits = ContactsListener.CATEGORY_ENEMY;
        fixtureDef.filter.maskBits = ContactsListener.MASK_ENEMY;
        body.createFixture(fixtureDef).setUserData(new FixtureData(FixtureData.Type.BLASTOISE, id));
        type = FixtureData.Type.WOOMBA;
        isGoingLeft = true;
        velocity = new Vector2(MathUtils.random(-3f, -4f), 0);
        textureRegion=Assets.Game.game_objects.findRegion(String.valueOf(Assets.Game.Object.BLASTOISE.ordinal()));
        textureSize=3*Constants.PPM;
    }

    @Override
    public void draw(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer) {
        spriteBatch.begin();
        spriteBatch.draw(textureRegion,
                (body.getPosition().x - 1.5f)* Constants.PPM,
                (body.getPosition().y - 1.5f)*Constants.PPM,
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
        body = world.createBody(bodyDef);
        body.createFixture(fixtureDef).setUserData(new FixtureData(FixtureData.Type.WOOMBA, id));
    }

}

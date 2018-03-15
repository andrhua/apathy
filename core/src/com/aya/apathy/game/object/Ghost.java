package com.aya.apathy.game.object;

import com.aya.apathy.Game;
import com.aya.apathy.game.FixtureData;
import com.aya.apathy.game.contactslistener.ContactsListener;
import com.aya.apathy.game.tiled.core.MapObject;
import com.aya.apathy.util.Assets;
import com.aya.apathy.util.Constants;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;


public class Ghost extends HauntingEnemy {
    public Ghost(int id, MapObject mapObject, World world, int zOrder, Indicator indicator) {
        super(id, mapObject, world, zOrder, indicator);
        polygonShape.setAsBox(width / 2, height / 2);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.gravityScale = 0;
        bodyDef.position.set(centerX, centerY);
        body = world.createBody(bodyDef);
        fixtureDef.shape = polygonShape;
        fixtureDef.filter.categoryBits = ContactsListener.CATEGORY_FLYING_ENEMY;
        fixtureDef.filter.maskBits = ContactsListener.MASK_FLYING_ENEMY;
        fixtureDef.isSensor = true;
        fixture = body.createFixture(fixtureDef);
        fixture.setUserData(new FixtureData(FixtureData.Type.GHOST, id));
        type = FixtureData.Type.GHOST;
        textureRegion=Assets.Game.game_objects.findRegion(String.valueOf(Assets.Game.Object.GHOST.ordinal()));
        textureSize=4* Constants.PPM;
    }

    @Override
    public void reset() {
        super.reset();
        fixture.setUserData(new FixtureData(FixtureData.Type.GHOST, id));
    }

    @Override
    public void update(float elapsedTime) {
        super.update(elapsedTime);
        switch (behaviour) {
            case HAUNTING:
                Vector2 direction = Game.joe.getPosition().sub(body.getPosition());
                Vector2 velocity = new Vector2(direction.x / direction.len(), direction.y / direction.len());
                velocity=velocity.mulAdd(velocity, .4f);
                body.setLinearVelocity(velocity);
        }
    }

    @Override
    public void draw(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer) {
        spriteBatch.begin();
        spriteBatch.draw(textureRegion,
                (body.getPosition().x - 1.5f)*Constants.PPM ,
                (body.getPosition().y - 1.5f)*Constants.PPM ,
                textureSize,
                textureSize);
        spriteBatch.end();
    }

    public void die() {
        body.setActive(false);
        world.destroyBody(body);
        this.setIdleState();
    }

}

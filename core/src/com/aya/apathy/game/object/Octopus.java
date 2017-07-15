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

public class Octopus extends HauntingEnemy {

    public Octopus(int id, MapObject mapObject, World world, int zOrder, Indicator indicator) {
        super(id, mapObject, world, zOrder, indicator);
        this.world = world;
        polygonShape.setAsBox(1, 1);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(centerX, centerY);
        body = world.createBody(bodyDef);
        fixtureDef.shape = polygonShape;
        fixtureDef.filter.categoryBits = ContactsListener.CATEGORY_ENEMY;
        fixtureDef.filter.maskBits = ContactsListener.MASK_ENEMY;
        fixtureDef.density = 1;
        fixture = body.createFixture(fixtureDef);
        fixture.setUserData(new FixtureData(FixtureData.Type.OCTOPUS, id));
        type = FixtureData.Type.OCTOPUS;
        textureRegion=Assets.Game.game_objects.findRegion(String.valueOf(Assets.Game.Object.OCTOPUS.ordinal()));
        textureSize=width*Constants.PPM;
    }

    @Override
    public void draw(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer) {
        spriteBatch.begin();
        spriteBatch.draw(textureRegion,
                (body.getPosition().x  -  width/2)* Constants.PPM,
                (body.getPosition().y  -  height/2)*Constants.PPM,
                textureSize,
                textureSize);
        spriteBatch.end();
    }

    @Override
    public void update(float elapsedTime) {
        super.update(elapsedTime);
        if (behaviour == Behaviour.HAUNTING) {
            Vector2 direction = Game.joe.getPosition().sub(body.getPosition());
            Vector2 velocity = new Vector2(direction.x / direction.len(), (direction.y - 1) / direction.len());
            velocity=velocity.mulAdd(velocity, 5.5f);
            body.setLinearVelocity(velocity);
        }
    }

    @Override
    public void reset() {
        super.reset();
        fixture.setUserData(new FixtureData(FixtureData.Type.OCTOPUS, id));
    }

}

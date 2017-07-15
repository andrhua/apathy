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

public class Cloud extends HauntingEnemy {

    public Cloud(int id, MapObject mapObject, World world, int zOrder, Indicator indicator) {
        super(id, mapObject, world, zOrder, indicator);
        this.world = world;
        this.indicator = indicator;
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
        fixture.setUserData(new FixtureData(FixtureData.Type.CLOUD, id));
        type = FixtureData.Type.CLOUD;
        textureRegion=Assets.Game.game_objects.findRegion(String.valueOf(Assets.Game.Object.CLOUD.ordinal()));
        textureSize=5* Constants.PPM;
    }

    @Override
    public void draw(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer) {
        spriteBatch.begin();
        spriteBatch.draw(textureRegion,
                (body.getPosition().x - 2.5f)*Constants.PPM,
                (body.getPosition().y - 1.5f)*Constants.PPM,
                textureRegion.getRegionX(),
                textureRegion.getRegionY(),
                textureSize,
                textureSize,
                scaleX,
                1,
                0);
        spriteBatch.end();
    }

    @Override
    public void update(float elapsedTime) {
        super.update(elapsedTime);
        switch (behaviour) {
            case HAUNTING:
                Vector2 direction = Game.joe.getPosition().sub(body.getPosition());
                scaleX = direction.x < 0 ? -1 : 1;
                Vector2 velocity = new Vector2(direction.x / direction.len(), direction.y / direction.len());
                velocity=velocity.mulAdd(velocity, 5);
                body.setLinearVelocity(velocity);
        }
    }

    @Override
    public void reset() {
        super.reset();
        fixture.setUserData(new FixtureData(FixtureData.Type.CLOUD, id));
    }

}

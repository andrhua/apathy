package com.aya.apathy.game.object;

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

public class Muk extends StaticGameObject implements Queryable, Resetable {
    private Vector2 velocity;
    private World world;

    public Muk(int id, MapObject mapObject, World world, int zOrder) {
        super(id, mapObject, zOrder);
        this.world = world;
        polygonShape.setAsBox(width / 2, height / 2);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.gravityScale = 0;
        bodyDef.position.set(centerX, centerY);
        body = world.createBody(bodyDef);
        fixtureDef.shape = polygonShape;
        fixtureDef.filter.categoryBits = ContactsListener.CATEGORY_ENEMY;
        fixtureDef.filter.maskBits = ContactsListener.MASK_ENEMY;
        fixtureDef.isSensor = true;
        fixture = body.createFixture(fixtureDef);
        fixture.setUserData(new FixtureData(FixtureData.Type.MUK, id));
        type = FixtureData.Type.MUK;
        velocity = new Vector2(0, -10);
        textureRegion= Assets.Game.game_objects.findRegion(String.valueOf(Assets.Game.Object.MUK.ordinal()));
        textureSize=7*Constants.PPM;
    }

    @Override
    public void draw(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer) {
        left=body.getPosition().x-width/2;
        bottom=body.getPosition().y-height/2;
        spriteBatch.begin();
        spriteBatch.draw(textureRegion,
                left* Constants.PPM ,
                bottom*Constants.PPM ,
                textureSize,
                textureSize);
        spriteBatch.end();
    }

    @Override
    public void queryForMagic() {
        body.setLinearVelocity(velocity);
    }

    @Override
    public void reset() {
        body.setActive(false);
        world.destroyBody(body);
        body = world.createBody(bodyDef);
        body.createFixture(fixtureDef).setUserData(new FixtureData(FixtureData.Type.MUK, id));
        body.setLinearVelocity(new Vector2());

    }


}

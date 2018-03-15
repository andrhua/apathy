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

public class Prequark extends StaticGameObject implements Resetable {
    protected World world;

    public Prequark(int id, MapObject mapObject, World world, int zOrder) {
        super(id, mapObject, zOrder);
        this.world = world;
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.gravityScale = 0;
        bodyDef.position.set(centerX, centerY);
        body = world.createBody(bodyDef);
        polygonShape.setAsBox(.45f, .45f);
        fixtureDef.shape = polygonShape;
        fixtureDef.isSensor = true;
        fixtureDef.filter.categoryBits = ContactsListener.CATEGORY_SCENERY;
        fixtureDef.filter.maskBits = ContactsListener.MASK_SCENERY;
        fixture = body.createFixture(fixtureDef);
        fixture.setUserData(new FixtureData(FixtureData.Type.PREQUARK, id));
        type = FixtureData.Type.PREQUARK;
        textureRegion=Assets.Game.game_objects.findRegion(String.valueOf(Assets.Game.Object.PREQUARK.ordinal()));
        textureSize=.6f* Constants.PPM;
    }

    @Override
    public void draw(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer) {
        spriteBatch.begin();
        spriteBatch.draw(textureRegion,
                left*Constants.PPM ,
                bottom *Constants.PPM,
                textureSize,
                textureSize);
        spriteBatch.end();
    }

    @Override
    public void reset() {
        body.setActive(false);
        world.destroyBody(body);
        body = world.createBody(bodyDef);
        body.createFixture(fixtureDef).setUserData(new FixtureData(FixtureData.Type.PREQUARK, id));
    }


}

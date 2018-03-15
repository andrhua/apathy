package com.aya.apathy.game.object;

import com.aya.apathy.Game;
import com.aya.apathy.game.FixtureData;
import com.aya.apathy.game.contactslistener.ContactsListener;
import com.aya.apathy.game.level.LevelManager;
import com.aya.apathy.game.tiled.core.MapObject;
import com.aya.apathy.util.Assets;
import com.aya.apathy.util.Constants;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;


public class Dragon extends DynamicGameObject implements Queryable, Resetable {
    private boolean needToKill;
    private World world;

    public Dragon(int id, MapObject mapObject, World world, int zOrder) {
        super(id, mapObject, zOrder);
        this.world = world;
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
        fixture.setUserData(new FixtureData(FixtureData.Type.DRAGON, id));
        type = FixtureData.Type.DRAGON;
        needToKill = false;
        textureRegion=Assets.Game.game_objects.findRegion(String.valueOf(Assets.Game.Object.DRAGON.ordinal()));
        textureSize=26* Constants.PPM;
    }

    @Override
    public void draw(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer) {
        spriteBatch.begin();
        spriteBatch.draw(textureRegion,
                (body.getPosition().x - width/2)*Constants.PPM ,
                (body.getPosition().y - height/2)*Constants.PPM ,
                textureSize,
                textureSize);
        spriteBatch.end();
    }

    @Override
    public void update(float elapsedTime) {
        if (needToKill) {
            body.setTransform(397, 107, 0);
            needToKill = false;
        }
    }

    @Override
    public void queryForMagic() {
        needToKill = true;
    }

    @Override
    public void reset() {
        body.setActive(false);
        world.destroyBody(body);
        body = world.createBody(bodyDef);
        fixture = body.createFixture(fixtureDef);
        fixture.setUserData(new FixtureData(FixtureData.Type.DRAGON, id));
    }

}

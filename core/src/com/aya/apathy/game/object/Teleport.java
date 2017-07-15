package com.aya.apathy.game.object;

import com.aya.apathy.Game;
import com.aya.apathy.audio.Sfx;
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

public class Teleport extends DynamicGameObject implements Queryable {
    private float angle;
    private float destinateX, destinateY;
    private boolean needToTeleport;
    public boolean rodnyan, scened;

    public Teleport(int id, MapObject mapObject, World world, int zOrder) {
        super(id, mapObject, zOrder);
        destinateX = Float.valueOf(mapObject.getProperties().getProperty("destinateX"));
        destinateY = Float.valueOf(mapObject.getProperties().getProperty("destinateY"));
        rodnyan = Boolean.valueOf(mapObject.getProperties().getProperty("rodnyan", "false"));
        polygonShape.setAsBox(1, 1);
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(centerX, centerY);
        body = world.createBody(bodyDef);
        fixtureDef.shape = polygonShape;
        fixtureDef.isSensor = true;
        fixtureDef.filter.categoryBits = ContactsListener.CATEGORY_SCENERY;
        fixtureDef.filter.maskBits = ContactsListener.MASK_SCENERY;
        fixture = body.createFixture(fixtureDef);
        fixture.setUserData(new FixtureData(FixtureData.Type.TELEPORT, id));
        type = FixtureData.Type.TELEPORT;
        angle = 0;
        needToTeleport = scened = false;
        textureRegion= Assets.Game.game_objects.findRegion(String.valueOf(Assets.Game.Object.TELEPORT.ordinal()));
        textureSize=2* Constants.PPM;
    }

    @Override
    public void draw(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer) {
        spriteBatch.begin();
        spriteBatch.draw(textureRegion,
                (centerX-width/2)*Constants.PPM ,
                (centerY-height/2)*Constants.PPM ,
                width/2*Constants.PPM,
                height/2*Constants.PPM,
                textureSize,
                textureSize,
                1,
                1,
                angle);
        spriteBatch.end();
    }

    @Override
    public void update(float elapsedTime) {
        if (needToTeleport) {
            Game.sfx.play(Sfx.SoundType.TELEPORT);
            Game.joe.body.setTransform(new Vector2(destinateX, destinateY), 0);
            needToTeleport = false;
        }
        angle += (elapsedTime * 90)%360;
    }

    @Override
    public void queryForMagic() {
        if (destinateX != -1 && destinateY != -1) needToTeleport = true;
    }

    @Override
    public void reset() {
        scened = false;
    }

}

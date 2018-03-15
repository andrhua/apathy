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

public class PacmanGhost extends DynamicGameObject {
    private Vector2 impulse;
    private boolean needToImpulse;
    private World world;
    private Indicator indicator;

    public PacmanGhost(int id, MapObject mapObject, World world, int zOrder, Indicator indicator) {
        super(id, mapObject, zOrder);
        this.world = world;
        this.indicator = indicator;
        polygonShape.setAsBox(width / 2, height / 2);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(centerX, centerY);
        body = world.createBody(bodyDef);
        fixtureDef.shape = polygonShape;
        fixtureDef.filter.categoryBits = ContactsListener.CATEGORY_ENEMY;
        fixtureDef.filter.maskBits = ContactsListener.MASK_ENEMY;
        fixtureDef.density = 1;
        fixture = body.createFixture(fixtureDef);
        fixture.setUserData(new FixtureData(FixtureData.Type.PACMAN_GHOST, id));
        type = FixtureData.Type.PACMAN_GHOST;
        impulse = new Vector2(0, -40);
        textureRegion=Assets.Game.game_objects.findRegion(String.valueOf(Assets.Game.Object.PACMAN_GHOST.ordinal()));
        textureSize=6* Constants.PPM;
    }

    @Override
    public void draw(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer) {
        spriteBatch.begin();
        spriteBatch.draw(textureRegion,
                (body.getPosition().x - width / 2)*Constants.PPM ,
                (body.getPosition().y - height / 2)*Constants.PPM ,
                textureSize,
                textureSize);
        spriteBatch.end();
    }

    @Override
    public void update(float elapsedTime) {
        if (indicator.isActive()) needToImpulse = true;
        if (needToImpulse) {
            body.applyLinearImpulse(impulse, body.getPosition(), true);
            needToImpulse = false;
        }
    }

    @Override
    public void reset() {
        body.setActive(false);
        world.destroyBody(body);
        body = world.createBody(bodyDef);
        body.createFixture(fixtureDef).setUserData(new FixtureData(FixtureData.Type.PACMAN_GHOST, id));
    }

}

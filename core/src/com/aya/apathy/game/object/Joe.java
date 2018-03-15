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

import static com.aya.apathy.game.object.Joe.xState.STAY;


public class Joe extends DynamicGameObject {
    private Vector2 jumpImpulse, xVelocity;
    public boolean needToJump, jumpOverEnemy, inWater;
    public enum xState {LEFT, RIGHT, STAY}
    private xState xState;
    private int scaleX;
    public int footContacts, bodyContacts;

    public void setXState(xState xState) {
        switch (xState){
            case LEFT:
                xVelocity.set(-7, 0);
                scaleX = -1;
                break;
            case RIGHT:
                xVelocity.set(7, 0);
                scaleX = 1;
                break;
            case STAY:
                xVelocity.set(0, xVelocity.y);
                break;
        }
        this.xState = xState;
    }

    public Joe(int id, MapObject mapObject, World world, int zOrder) {
        super(id, mapObject, zOrder);
        bodyDef.position.set(centerX, centerY);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bodyDef);
        body.setFixedRotation(true);
        polygonShape.setAsBox(.4f, 1.4f);
        fixtureDef.density = 1;
        fixtureDef.filter.categoryBits = ContactsListener.CATEGORY_JOE;
        fixtureDef.filter.maskBits = ContactsListener.MASK_JOE;
        fixtureDef.shape = polygonShape;
        body.createFixture(fixtureDef).setUserData(new FixtureData(FixtureData.Type.JOE, this.id));
        polygonShape.setAsBox(.39f, .2f, new Vector2(0, -1.4f), 0);
        fixtureDef.isSensor = true;
        body.createFixture(fixtureDef).setUserData(new FixtureData(FixtureData.Type.LAND_SENSOR, id));
        needToJump = jumpOverEnemy = false;
        jumpImpulse = new Vector2(0, 37);
        xVelocity = new Vector2(-7, 0);
        setXState(STAY);
        textureRegion= Assets.Game.joe;
        textureSize=3*Constants.PPM;
        scaleX = 1;
        type = FixtureData.Type.JOE;
        inWater = false;
        footContacts = bodyContacts = 0;
    }

    @Override
    public void draw(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer) {
        spriteBatch.begin();
        spriteBatch.draw(textureRegion,
                (body.getPosition().x -scaleX*.5f)*Constants.PPM,
                (body.getPosition().y - 1.4f)*Constants.PPM,
                scaleX*Constants.PPM,
                textureSize);
        spriteBatch.end();
    }

    @Override
    public void update(float elapsedTime) {
        body.setLinearDamping(inWater ? 2 : 1);
        if (jumpOverEnemy) {
            Game.sfx.play(Sfx.SoundType.JUMP_OVER_ENEMY);
            jumpImpulse.set(0, body.getLinearVelocity().y +72);
            body.applyLinearImpulse(jumpImpulse, body.getPosition(), true);
            jumpImpulse.set(0, 37);
            jumpOverEnemy = false;
        }
        if (needToJump && !jumpOverEnemy) {
            if (footContacts == 1) {
                Game.sfx.play(Sfx.SoundType.JUMP);
                body.applyLinearImpulse(jumpImpulse, body.getPosition(), true);
            }
            needToJump = false;
        } else jumpOverEnemy = false;
        body.applyLinearImpulse(xVelocity, body.getPosition(), true);
        switch (xState) {
            case LEFT:
                if (body.getLinearVelocity().x < -7)
                    body.setLinearVelocity(-7, body.getLinearVelocity().y);
                break;
            case RIGHT:
                if (body.getLinearVelocity().x > 7)
                    body.setLinearVelocity(7, body.getLinearVelocity().y);
        }
    }

    @Override
    public void reset() {
        body.setTransform(this.left, this.bottom, 0);
        xVelocity.setZero();
        body.setLinearVelocity(0, 0);
        setXState(STAY);
    }

    public Vector2 getPosition() {
        return body.getPosition();
    }


}

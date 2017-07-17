package com.aya.apathy.game.object;

import com.aya.apathy.Game;
import com.aya.apathy.audio.Sfx;
import com.aya.apathy.game.FixtureData;
import com.aya.apathy.game.contactslistener.ContactsListener;
import com.aya.apathy.game.tiled.core.MapObject;
import com.aya.apathy.util.Assets;
import com.aya.apathy.util.Constants;
import com.aya.apathy.util.MissingEssences;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

public class EarRapeSpider extends DynamicGameObject {
    private Indicator indicator;
    private boolean isScreamed, needToKill;
    private enum State {WAITING, ONE, TWO}
    private State state;
    private Vector2 velocity, initial;

    public EarRapeSpider(int id, MapObject mapObject, World world, int zOrder, Indicator indicator) {
        super(id, mapObject, zOrder);
        this.indicator = indicator;
        velocity = new Vector2(0, -15);
        initial = new Vector2(centerX, centerY);
        state = State.WAITING;
        polygonShape.setAsBox(width / 2, height / 2);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.gravityScale = 0;
        bodyDef.position.set(centerX, centerY);
        body = world.createBody(bodyDef);
        fixtureDef.shape = polygonShape;
        fixtureDef.filter.categoryBits = ContactsListener.CATEGORY_FLYING_ENEMY;
        fixtureDef.filter.maskBits = ContactsListener.MASK_FLYING_ENEMY;
        fixtureDef.isSensor = true;
        body.createFixture(fixtureDef).setUserData(new FixtureData(FixtureData.Type.SPIDER, id));
        type = FixtureData.Type.SPIDER;
        textureRegion=Assets.Game.game_objects.findRegion(String.valueOf(Assets.Game.Object.SPIDER.ordinal()));
        textureSize=14*Constants.PPM;
    }

    @Override
    public void draw(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer) {
        if (isScreamed){
            spriteBatch.begin();
            spriteBatch.draw(textureRegion,
                    (body.getPosition().x - width / 2)* Constants.PPM ,
                    (body.getPosition().y - height / 2)*Constants.PPM ,
                    textureSize,
                    textureSize);
            spriteBatch.end();
        }
    }

    @Override
    public void update(float elapsedTime) {
        if (indicator.isActive() && !isScreamed) {
            Game.sfx.play(Sfx.SoundType.JOHN_CENA);
            isScreamed = true;
            needToKill = true;
        }
        if (needToKill)
            switch (state) {
                case WAITING:
                    state = State.ONE;
                    body.setLinearVelocity(velocity);
                    break;
                case ONE:
                    if (body.getPosition().y <= centerY - 38) {
                        state = State.TWO;
                        body.setLinearVelocity(MissingEssences.negate(velocity));
                    }
                    break;
                case TWO:
                    if (body.getPosition().y >= centerY) {
                        needToKill = false;
                        body.setLinearVelocity(new Vector2());
                        body.setTransform(initial, 0);
                    }
            }
    }

    @Override
    public void reset() {
        state = State.WAITING;
        needToKill = false;
        isScreamed = false;
        body.setLinearVelocity(new Vector2());
        body.setTransform(initial, 0);
    }
}

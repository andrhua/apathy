package com.aya.apathy.game.object;

import com.aya.apathy.game.FixtureData;
import com.aya.apathy.game.contactslistener.ContactsListener;
import com.aya.apathy.game.tiled.core.MapObject;
import com.aya.apathy.util.Assets;
import com.aya.apathy.util.Constants;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

public class Spike extends StaticGameObject {
    private enum Direction {UP, DOWN, LEFT, RIGHT}
    private Direction direction;
    protected static Vector2[] vertices;
    private int angle;

    public Spike(int id, MapObject mapObject, World world, int zOrder) {
        super(id, mapObject, zOrder);
        textureRegion= Assets.Game.game_objects.findRegion("23");
        direction = Direction.valueOf(mapObject.getProperties().getProperty("direction", "UP"));
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(centerX, centerY);
        body = world.createBody(bodyDef);
        vertices = new Vector2[3];
        switch (direction) {
            case UP:
                vertices[0] = new Vector2(0, .5f);
                vertices[1] = new Vector2(.5f, -.5f);
                vertices[2] = new Vector2(-.5f, -.5f);
                break;
            case RIGHT:
                vertices[0] = new Vector2(-.5f, .5f);
                vertices[1] = new Vector2(-.5f, -.5f);
                vertices[2] = new Vector2(.5f, 0);
                angle=-90;
                bottom++;
                break;
            case LEFT:
                vertices[0] = new Vector2(.5f, .5f);
                vertices[1] = new Vector2(.5f, -.5f);
                vertices[2] = new Vector2(-.5f, 0);
                angle=90;
                left++;
                break;
            case DOWN:
                vertices[0] = new Vector2(0, -.5f);
                vertices[1] = new Vector2(.5f, .5f);
                vertices[2] = new Vector2(-.5f, .5f);
                angle=180;
                bottom++;
                break;
        }
        polygonShape.set(vertices);
        fixtureDef.shape = polygonShape;
        fixtureDef.density = 1;
        fixtureDef.filter.categoryBits = ContactsListener.CATEGORY_TRAP;
        fixtureDef.filter.maskBits = ContactsListener.MASK_SCENERY;
        fixture = body.createFixture(fixtureDef);
        fixture.setUserData(new FixtureData(FixtureData.Type.SPIKE, id));
        type = FixtureData.Type.SPIKE;
        textureSize=Constants.PPM;
    }

    @Override
    public void draw(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer) {
        spriteBatch.begin();
        spriteBatch.draw(textureRegion,
                left*Constants.PPM, bottom*Constants.PPM,
                0,
                0,
                textureSize, textureSize,
                1, 1,
                angle);
        spriteBatch.end();
    }

    public static class Timing extends DynamicGameObject {
        private Direction direction;
        private int state;
        private float time, delay;
        private Vector2 destinate, initial;

        public Timing(int id, MapObject mapObject, World world, int zOrder) {
            super(id, mapObject, zOrder);
            textureRegion= Assets.Game.game_objects.findRegion("23");
            direction = Direction.valueOf(mapObject.getProperties().getProperty("direction", "UP"));
            state = Integer.valueOf(mapObject.getProperties().getProperty("state", "1"));
            if (state == 1) destinate = new Vector2(centerX, centerY);
            else initial = new Vector2(centerX, centerY);
            delay = MathUtils.random(1.5f, 1.6f);
            bodyDef.type = BodyDef.BodyType.KinematicBody;
            bodyDef.position.set(centerX, centerY);
            body = world.createBody(bodyDef);
            vertices = new Vector2[3];
            switch (direction) {
                case UP:
                    vertices[0] = new Vector2(0, -.5f);
                    vertices[1] = new Vector2(.5f, .5f);
                    vertices[2] = new Vector2(-.5f, .5f);
                    if (state == 1) initial = new Vector2(centerX, centerY - 1);
                    else destinate = new Vector2(centerX, centerY + 1);
                    break;
                case RIGHT:
                    angle=-90;
                    vertices[0] = new Vector2(-.5f, .5f);
                    vertices[1] = new Vector2(-.5f, -.5f);
                    vertices[2] = new Vector2(.5f, 0);
                    bottom++;
                    break;
                case LEFT:
                    angle=90;
                    vertices[0] = new Vector2(.5f, .5f);
                    vertices[1] = new Vector2(.5f, -.5f);
                    vertices[2] = new Vector2(-.5f, 0);
                    bottom++;
                    break;
                case DOWN:
                    angle=180;
                    vertices[0] = new Vector2(0, .5f);
                    vertices[1] = new Vector2(.5f, -.5f);
                    vertices[2] = new Vector2(-.5f, -.5f);
                    if (state == 1) initial = new Vector2(centerX, centerY + 1);
                    else destinate = new Vector2(centerX, centerY - 1);
                    break;
            }
            polygonShape.set(vertices);
            fixtureDef.shape = polygonShape;
            fixtureDef.density = 1;
            fixtureDef.filter.categoryBits = ContactsListener.CATEGORY_TRAP;
            fixtureDef.filter.maskBits = ContactsListener.MASK_SCENERY;
            fixture = body.createFixture(fixtureDef);
            fixture.setUserData(new FixtureData(FixtureData.Type.TIMING_SPIKE, id));
            type = FixtureData.Type.TIMING_SPIKE;
            textureSize=Constants.PPM;
        }

        @Override
        public void draw(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer) {
            spriteBatch.begin();
            spriteBatch.draw(textureRegion,
                    (body.getPosition().x-width/2)*Constants.PPM, (body.getPosition().y-height/2)*Constants.PPM,
                    0,
                    0,
                    textureSize, textureSize,
                    1, 1,
                    angle);
            spriteBatch.end();
        }

        @Override
        public void update(float elapsedTime) {
            time += elapsedTime;
            if (time >= delay) {
                body.setTransform(state == 1 ? initial : destinate, 0);
                state = 3 - state;
                time = 0;
            }
        }

        @Override
        public void reset() {
            time = 0;
        }

    }
}

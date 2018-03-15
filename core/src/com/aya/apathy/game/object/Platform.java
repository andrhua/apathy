package com.aya.apathy.game.object;

import com.aya.apathy.game.FixtureData;
import com.aya.apathy.game.contactslistener.ContactsListener;
import com.aya.apathy.game.tiled.core.MapObject;
import com.aya.apathy.util.Assets;
import com.aya.apathy.util.Constants;
import com.aya.apathy.util.MissingEssences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

public class Platform extends StaticGameObject {
    private boolean isTriangle;
    private Vector2[] vertices;

    public Platform(int id, MapObject mapObject, World world, int zOrder) {
        super(id, mapObject, zOrder);
        isTriangle = Boolean.parseBoolean(mapObject.getProperties().getProperty("isTriangle", "false"));
        String direction = mapObject.getProperties().getProperty("direction", "right");
        if (isTriangle) {
            vertices = new Vector2[3];
            int coeff;
            coeff = direction.equals("right") ? 1 : -1;
            vertices[0] = new Vector2(coeff * width / 2, 3 * height / 2);
            vertices[1] = new Vector2(coeff * width / 2, height / 2);
            vertices[2] = new Vector2(-coeff * width / 2, height / 2);
            polygonShape.set(vertices);
            for (int i=0; i<3; i++){
                vertices[i].x+=centerX;
                vertices[i].y+=centerY;
                vertices[i].x*= Constants.PPM;
                vertices[i].y*=Constants.PPM;
            }
            fixtureDef.friction = 1;
        } else polygonShape.setAsBox(width / 2, height / 2);
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(centerX, centerY);
        body = world.createBody(bodyDef);
        fixtureDef.shape = polygonShape;
        fixtureDef.filter.categoryBits = ContactsListener.CATEGORY_SCENERY;
        fixtureDef.filter.maskBits = ContactsListener.MASK_SCENERY;
        fixtureDef.friction = 0;
        fixtureDef.density = 1;
        fixture = body.createFixture(fixtureDef);
        fixture.setUserData(new FixtureData(FixtureData.Type.PLATFORM, id));
        type = FixtureData.Type.PLATFORM;
    }

    @Override
    public void draw(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Assets.Game.wall);
        if (isTriangle)
            shapeRenderer.triangle(vertices[0].x, vertices[0].y, vertices[1].x, vertices[1].y, vertices[2].x, vertices[2].y);
        else shapeRenderer.rect(left*Constants.PPM, bottom*Constants.PPM, width*Constants.PPM,  height*Constants.PPM);
        shapeRenderer.end();
    }

    public static class Moving extends DynamicGameObject {
        private enum Direction {VERTICAL, HORIZONTAL}
        private Direction direction;
        private float upD, downD, leftD, rightD;
        private Vector2 velocity;
        private boolean isGoingDown, isGoingRight;

        public Moving(int id, MapObject mapObject, World world, int zOrder) {
            super(id, mapObject, zOrder);
            direction = Direction.valueOf(mapObject.getProperties().getProperty("direction"));
            if (direction == Direction.VERTICAL) {
                upD = Float.valueOf(mapObject.getProperties().getProperty("up"));
                downD = Float.valueOf(mapObject.getProperties().getProperty("down"));
                velocity = new Vector2(0, Float.valueOf(mapObject.getProperties().getProperty("velocity")));
                isGoingDown = true;
            } else {
                rightD = Float.valueOf(mapObject.getProperties().getProperty("right"));
                leftD = Float.valueOf(mapObject.getProperties().getProperty("left"));
                velocity = new Vector2(Float.valueOf(mapObject.getProperties().getProperty("velocity")), 0);
                isGoingRight = true;
            }
            velocity.set(velocity.x * MathUtils.cosDeg(angle) - velocity.y * MathUtils.sinDeg(angle),
                    velocity.x * MathUtils.sinDeg(angle) + velocity.y * MathUtils.cosDeg(angle));
            polygonShape.setAsBox(width / 2, height / 2);
            bodyDef.type = BodyDef.BodyType.KinematicBody;
            bodyDef.angle = angle;
            bodyDef.fixedRotation = true;
            bodyDef.position.set(centerX, centerY);
            body = world.createBody(bodyDef);
            fixtureDef.shape = polygonShape;
            fixtureDef.friction = 0;
            fixtureDef.filter.categoryBits = ContactsListener.CATEGORY_SCENERY;
            fixtureDef.filter.maskBits = ContactsListener.MASK_SCENERY;
            fixture = body.createFixture(fixtureDef);
            fixture.setUserData(new FixtureData(FixtureData.Type.PLATFORM, id));
            type = FixtureData.Type.PLATFORM;
        }

        @Override
        public void draw(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer) {
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(Assets.Game.wall);
            shapeRenderer.rect((body.getPosition().x-width/2)*Constants.PPM ,
                    (body.getPosition().y-height/2)*Constants.PPM ,
                    width/2*Constants.PPM,
                    height/2*Constants.PPM,
                    width*Constants.PPM, height*Constants.PPM,
                    1, 1, angle);
            shapeRenderer.end();
        }

        @Override
        public void update(float elapsedTime) {
            if (direction == Direction.VERTICAL) {
                if (body.getPosition().y <= this.centerY - downD) isGoingDown = false;
                else if (body.getPosition().y >= this.centerY + upD) isGoingDown = true;
            } else {
                if (body.getPosition().x >= this.centerX + rightD) isGoingRight = false;
                else if (body.getPosition().x <= this.centerX - leftD) isGoingRight = true;
            }
            if (isGoingDown || isGoingRight) body.setLinearVelocity(velocity);
            else body.setLinearVelocity(MissingEssences.negate(velocity));
            left = body.getPosition().x - width/2;
            bottom = body.getPosition().y - height/2;
        }

        @Override
        public void reset() {
            if (direction == Direction.VERTICAL) {
                velocity = new Vector2(0, Float.valueOf(mapObject.getProperties().getProperty("velocity")));
                isGoingDown = true;
            } else {
                velocity = new Vector2(Float.valueOf(mapObject.getProperties().getProperty("velocity")), 0);
                isGoingRight = true;
            }
            body.setTransform(new Vector2(centerX, centerY), 0);
        }

    }

    public static class DisposableFlashing extends DynamicGameObject implements Queryable {
        private float time = 0, timeToDestroy;
        private boolean needToStartDestroy;
        public enum State {APPEAR, DISAPPEAR}
        public State state;
        private World world;
        private boolean isDestroyed;

        public DisposableFlashing(int id, MapObject mapObject, World world, int zOrder) {
            super(id, mapObject, zOrder);
            this.world = world;
            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.position.set(centerX, centerY);
            body = world.createBody(bodyDef);
            polygonShape.setAsBox(width / 2, height / 2);
            fixtureDef.shape = polygonShape;
            fixtureDef.filter.maskBits = ContactsListener.MASK_SCENERY;
            fixtureDef.filter.categoryBits = ContactsListener.CATEGORY_SCENERY;
            fixtureDef.density = 1;
            fixtureDef.friction = 0;
            fixture = body.createFixture(fixtureDef);
            fixture.setUserData(new FixtureData(FixtureData.Type.DISPOSABLE_FLASHING_PLATFORM, id));
            type = FixtureData.Type.DISPOSABLE_FLASHING_PLATFORM;
            state = State.APPEAR;
            needToStartDestroy = false;
        }

        @Override
        public void draw(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer) {
            if (state==State.APPEAR) {
                shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
                shapeRenderer.setColor(Color.RED);
                shapeRenderer.rect(left*Constants.PPM , bottom *Constants.PPM, width*Constants.PPM , height*Constants.PPM );
                shapeRenderer.end();
            }
        }

        @Override
        public void update(float elapsedTime) {
            if (needToStartDestroy) {
                timeToDestroy += elapsedTime*1000;
                if (timeToDestroy > 2000) setIdleState();
            }

            float appearingTime = 1000;
            float disappearingTime = 2000;
            time += elapsedTime*1000;
            switch (state) {
                case APPEAR:
                    if (time > appearingTime) {
                        time = 0;
                        state = State.DISAPPEAR;
                    }
                    break;
                case DISAPPEAR:
                    if (time > disappearingTime) {
                        time = 0;
                        state = State.APPEAR;
                    }
            }
        }

        @Override
        public void setIdleState() {
            super.setIdleState();
            body.setActive(false);
            world.destroyBody(body);
            isDestroyed =true;
        }

        @Override
        public void queryForMagic() {
            needToStartDestroy = true;
        }

        @Override
        public void reset() {
            if (!isDestroyed) {
                body.setActive(false);
                world.destroyBody(body);
            }
            isDestroyed=false;
            body = world.createBody(bodyDef);
            fixture = body.createFixture(fixtureDef);
            fixture.setUserData(new FixtureData(FixtureData.Type.DISPOSABLE_FLASHING_PLATFORM, id));
            setActiveState();
            needToStartDestroy = false;
            time = 0;
            timeToDestroy = 0;
        }

    }

    public static class Disappearing extends DynamicGameObject implements Queryable {
        private boolean needToDestroy;
        private World world;

        public Disappearing(int id, MapObject mapObject, World world, int zOrder) {
            super(id, mapObject, zOrder);
            this.world = world;
            polygonShape.setAsBox(width / 2, height / 2);
            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.position.set(centerX, centerY);
            body = world.createBody(bodyDef);
            fixtureDef.shape = polygonShape;
            fixtureDef.filter.categoryBits = ContactsListener.CATEGORY_SCENERY;
            fixtureDef.filter.maskBits = ContactsListener.MASK_SCENERY;
            fixtureDef.density = 1;
            fixtureDef.friction = 0;
            fixture = body.createFixture(fixtureDef);
            fixture.setUserData(new FixtureData(FixtureData.Type.DISAPPEARING_PLATFORM, id));
            type = FixtureData.Type.DISAPPEARING_PLATFORM;
            needToDestroy = false;
        }

        @Override
        public void update(float elapsedTime) {
            if (needToDestroy) {
                setIdleState();
                world.destroyBody(body);
                needToDestroy = false;
            }
        }

        @Override
        public void draw(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer) {
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(Assets.Game.wall);
            shapeRenderer.rect(left*Constants.PPM, bottom *Constants.PPM, width *Constants.PPM, height*Constants.PPM );
            shapeRenderer.end();
        }

        @Override
        public void queryForMagic() {
            needToDestroy = true;
        }

        @Override
        public void reset() {
            body.setActive(false);
            world.destroyBody(body);
            body = world.createBody(bodyDef);
            fixture = body.createFixture(fixtureDef);
            fixture.setUserData(new FixtureData(FixtureData.Type.DISAPPEARING_PLATFORM, id));
            setActiveState();
        }
    }

    public static class Fake extends StaticGameObject {

        public Fake(int id, MapObject mapObject, int zOrder) {
            super(id, mapObject, zOrder);
        }

        @Override
        public void draw(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer) {
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(Assets.Game.wall);
            shapeRenderer.rect(left*Constants.PPM , bottom *Constants.PPM, width*Constants.PPM , height*Constants.PPM );
            shapeRenderer.end();
        }
    }

    public static class Shuriken extends Moving {

        public Shuriken(int id, MapObject mapObject, World world, int zOrder) {
            super(id, mapObject, world, zOrder);
            fixture.setUserData(new FixtureData(FixtureData.Type.SHURIKEN, id));
            type = FixtureData.Type.SHURIKEN;
            textureSize=Constants.PPM;
            textureRegion=Assets.Game.game_objects.findRegion(String.valueOf(Assets.Game.Object.SHURIKEN.ordinal()));
        }

        @Override
        public void draw(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer) {
            spriteBatch.begin();
            spriteBatch.draw(textureRegion,
                    (body.getPosition().x-width/2)*Constants.PPM ,
                    (body.getPosition().y-height/2)*Constants.PPM ,
                    textureSize,
                    textureSize);
            spriteBatch.end();
        }

        @Override
        public void update(float elapsedTime) {
            super.update(elapsedTime);
        }
    }

    public static class Transformable extends Platform implements Queryable, Resetable {
        private float transformY;

        public Transformable(int id, MapObject mapObject, World world, int zOrder) {
            super(id, mapObject, world, zOrder);
            transformY = Float.valueOf(mapObject.getProperties().getProperty("transformY", "0"));
            fixture.setFriction(0);
            fixture.setUserData(new FixtureData(FixtureData.Type.TRANSFORMABLE_PLATFORM, id));
            type = FixtureData.Type.TRANSFORMABLE_PLATFORM;
        }

        @Override
        public void draw(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer) {
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(Assets.Game.wall);
            shapeRenderer.rect((body.getPosition().x - width / 2)*Constants.PPM , (body.getPosition().y - height / 2)*Constants.PPM ,
                    width *Constants.PPM, height*Constants.PPM);
            shapeRenderer.end();
        }

        @Override
        public void queryForMagic() {
            body.setTransform(new Vector2(centerX, centerY - transformY), 0);
        }

        @Override
        public void reset() {
            body.setTransform(new Vector2(centerX, centerY), 0);
        }
    }

    public static class Smash extends DynamicGameObject {
        private float time, delay;
        private Vector2 velocity, initial;
        private boolean discharged;

        public Smash(int id, MapObject mapObject, World world, int zOrder) {
            super(id, mapObject, zOrder);
            delay = MathUtils.random(.6f, 1.9f);
            polygonShape.setAsBox(width / 2, height / 2);
            bodyDef.type = BodyDef.BodyType.DynamicBody;
            bodyDef.position.set(centerX, centerY);
            body = world.createBody(bodyDef);
            body.setGravityScale(0);
            fixtureDef.shape = polygonShape;
            fixtureDef.filter.categoryBits = ContactsListener.CATEGORY_ENEMY;
            fixtureDef.filter.maskBits = ContactsListener.MASK_ENEMY;
            fixture = body.createFixture(fixtureDef);
            fixture.setUserData(new FixtureData(FixtureData.Type.SMASH_PLATFORM, id));
            type = FixtureData.Type.SMASH_PLATFORM;
            velocity = new Vector2(0, -50);
            initial = new Vector2(centerX, centerY);
        }

        @Override
        public void draw(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer) {
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(Assets.Game.wall);
            shapeRenderer.rect((body.getPosition().x - width / 2) *Constants.PPM, (body.getPosition().y - height / 2)*Constants.PPM ,
                    width*Constants.PPM , height*Constants.PPM );
            shapeRenderer.end();
        }

        @Override
        public void update(float elapsedTime) {
            time += elapsedTime;
            if (time >= delay) {
                if (!discharged) {
                    body.applyLinearImpulse(velocity, body.getPosition(), true);
                    discharged = true;
                } else if (time >= delay + 1.5f) reset();
            }
        }

        @Override
        public void reset() {
            time = 0;
            discharged = false;
            body.setLinearVelocity(new Vector2());
            body.setTransform(initial, 0);
        }
    }

    public static class Beauteous extends Platform {
        private Assets.Game.Object object;

        public Beauteous(int id, MapObject mapObject, World world, int zOrder) {
            super(id, mapObject, world, zOrder);
            object = Assets.Game.Object.valueOf(mapObject.getProperties().getProperty("type"));
            textureRegion=Assets.Game.game_objects.findRegion(String.valueOf(object.ordinal()));
            textureSize=0;
        }

        @Override
        public void draw(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer) {

        }
        /*
        @Override
        public void render(ShapeRenderer shapeRenderer) {
            canvas.save();
            canvas.translate(left , top );
            canvas.drawBitmap(Assets.Game.objects[object.ordinal()], 0, 0, null);
            canvas.restore();
        }
        */
    }


}

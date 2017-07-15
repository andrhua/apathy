package com.aya.apathy.game.object;

import com.aya.apathy.game.FixtureData;
import com.aya.apathy.game.tiled.core.MapObject;
import com.aya.apathy.util.Constants;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.World;

public class Blade extends Indicator.Death {

    public Blade(int id, MapObject mapObject, World world, int zOrder) {
        super(id, mapObject, world);
        this.zOrder = zOrder;
        fixture.setUserData(new FixtureData(FixtureData.Type.BLADE, id));
        type = FixtureData.Type.BLADE;
    }

    @Override
    public void draw(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.GRAY);
        shapeRenderer.rect(left* Constants.PPM, bottom*Constants.PPM, width*Constants.PPM, height*Constants.PPM);
        shapeRenderer.end();
    }

    public static class Fake extends Blade implements Queryable, Resetable {
        private float fakeX;

        public Fake(int id, MapObject mapObject, World world, int zOrder) {
            super(id, mapObject, world, zOrder);
            fakeX = Float.valueOf(mapObject.getProperties().getProperty("fakeX"));
            left = fakeX;
            type = FixtureData.Type.FAKE_BLADE;
        }

        @Override
        public void queryForMagic() {
            left = centerX - width / 2;
        }

        @Override
        public void reset() {
            left = fakeX;
        }
    }

    public static class Invisible extends Blade implements Queryable, Resetable {

        public Invisible(int id, MapObject mapObject, World world, int zOrder) {
            super(id, mapObject, world, zOrder);
            fixture.setUserData(new FixtureData(FixtureData.Type.INVISIBLE_BLADE, id));
            type = FixtureData.Type.INVISIBLE_BLADE;
            setIdleState();
        }

        @Override
        public void queryForMagic() {
            setActiveState();
        }

        @Override
        public void reset() {
            setIdleState();
        }
    }
}

package com.aya.apathy.game.object;

import com.aya.apathy.game.FixtureData;
import com.aya.apathy.game.level.LevelManager;
import com.aya.apathy.game.tiled.core.MapObject;
import com.aya.apathy.ui.BasicActivatable;
import com.aya.apathy.util.Constants;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

abstract public class StaticGameObject extends BasicActivatable implements Drawable {
    public Body body;
    PolygonShape polygonShape;
    BodyDef bodyDef;
    FixtureDef fixtureDef;
    Fixture fixture;
    public MapObject mapObject;
    public int id, zOrder;
    public float left, bottom, centerX, centerY, width, height, angle, textureSize;
    public FixtureData.Type type;
    protected TextureRegion textureRegion;


    public StaticGameObject(int id, MapObject mapObject, int zOrder) {
        this.zOrder = zOrder;
        this.id = id;
        this.mapObject = mapObject;
        Rectangle rectangle = mapObject.getBounds();
        width = rectangle.width / Constants.TPX;
        height = rectangle.height / Constants.TPX;
        left = mapObject.getX() / Constants.TPX;
        bottom = (LevelManager.mapHeight-mapObject.getY()) / Constants.TPX;
        centerX = left + rectangle.width / 2 / Constants.TPX;
        centerY = bottom + rectangle.height / 2 / Constants.TPX;
        angle = -mapObject.getAngle();
        bodyDef = new BodyDef();
        polygonShape = new PolygonShape();
        fixtureDef = new FixtureDef();
        setActiveState();
        type = FixtureData.Type.NONE;
    }

    public static class Comparator implements java.util.Comparator<StaticGameObject> {

        @Override
        public int compare(StaticGameObject o1, StaticGameObject o2) {
            return o1.zOrder - o2.zOrder;
        }
    }

}

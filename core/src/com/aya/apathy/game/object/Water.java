package com.aya.apathy.game.object;


import com.aya.apathy.game.FixtureData;
import com.aya.apathy.game.contactslistener.ContactsListener;
import com.aya.apathy.game.tiled.core.MapObject;
import com.aya.apathy.util.Constants;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;


public class Water extends StaticGameObject {
    private Color color;

    public Water(int id, MapObject mapObject, World world, int zOrder) {
        super(id, mapObject, zOrder);
        bodyDef.position.set(centerX, centerY);
        bodyDef.type = BodyDef.BodyType.StaticBody;
        body = world.createBody(bodyDef);
        body.setFixedRotation(true);
        polygonShape.setAsBox(width / 2, height / 2);
        fixtureDef.isSensor = true;
        fixtureDef.filter.categoryBits = ContactsListener.CATEGORY_SCENERY;
        fixtureDef.filter.maskBits = ContactsListener.MASK_SCENERY;
        fixtureDef.shape = polygonShape;
        body.createFixture(fixtureDef).setUserData(new FixtureData(FixtureData.Type.WATER, id));
        type = FixtureData.Type.WATER;
        color=Color.valueOf("#0a4761");
        color.a=.15f;
    }

    @Override
    public void draw(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer) {
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_ONE, GL20.GL_ONE_MINUS_SRC_ALPHA);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(color);
        shapeRenderer.rect(left* Constants.PPM, bottom*Constants.PPM, width*Constants.PPM, height*Constants.PPM);
        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }
}

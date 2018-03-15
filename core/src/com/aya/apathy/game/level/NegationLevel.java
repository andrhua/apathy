package com.aya.apathy.game.level;

import com.aya.apathy.game.FixtureData;
import com.aya.apathy.game.contactslistener.DownContactsListener;
import com.aya.apathy.game.object.DynamicGameObject;
import com.aya.apathy.game.object.StaticGameObject;
import com.aya.apathy.game.scripts.BasicScript;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;

public class NegationLevel extends BasicLevel {
    public enum Script {TRAFFIC_LIGHT}

    NegationLevel(World world,
                  LevelManager.Stage stage,
                  ArrayList<StaticGameObject> statics,
                  ArrayList<DynamicGameObject> dynamics,
                  ArrayList<BasicScript> scripts) {
        super(world, stage, statics, dynamics, scripts);
        DownContactsListener dcl = new DownContactsListener(world, this);
        world.setContactListener(dcl);
        scene = 1;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        for (DynamicGameObject object : dynamics)
            if (object.isActive()&& isLocatedInCamera(object.left, object.bottom, object.width, object.height)
                    ||
                    (object.type== FixtureData.Type.JOE)) object.update(delta);
        executeScripts(delta);
    }

    @Override
    public void render(ShapeRenderer shapeRenderer) {
        super.render(shapeRenderer);
    }
}



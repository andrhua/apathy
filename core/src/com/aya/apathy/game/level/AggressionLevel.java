package com.aya.apathy.game.level;

import com.aya.apathy.game.FixtureData;
import com.aya.apathy.game.contactslistener.TopContactsListener;
import com.aya.apathy.game.object.DynamicGameObject;
import com.aya.apathy.game.object.StaticGameObject;
import com.aya.apathy.game.scripts.BasicScript;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;

public class AggressionLevel extends BasicLevel {
    public enum Script {PRISON, BANANA}

    AggressionLevel(World world,
                    LevelManager.Stage stage,
                    ArrayList<StaticGameObject> statics,
                    ArrayList<DynamicGameObject> dynamics,
                    ArrayList<BasicScript> scripts) {
        super(world, stage, statics, dynamics, scripts);
        TopContactsListener tcl = new TopContactsListener(world, this);
        world.setContactListener(tcl);
        scene = 1;
    }

    @Override
    public void update(float elapsedTime) {
        super.update(elapsedTime);
        for (DynamicGameObject object : dynamics)
            if (scene == 1 && object.left < 380 || scene == 2 && object.left > 380 || object.type == FixtureData.Type.JOE)
                if (object.isActive()) object.update(elapsedTime);
        executeScripts(elapsedTime);
    }

}

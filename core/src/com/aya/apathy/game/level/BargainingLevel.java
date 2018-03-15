package com.aya.apathy.game.level;

import com.aya.apathy.game.FixtureData;
import com.aya.apathy.game.contactslistener.StrangeContactsListener;
import com.aya.apathy.game.object.DynamicGameObject;
import com.aya.apathy.game.object.StaticGameObject;
import com.aya.apathy.game.scripts.BasicScript;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;

public class BargainingLevel extends BasicLevel {

    BargainingLevel(World world,
                    LevelManager.Stage stage,
                    ArrayList<StaticGameObject> statics,
                    ArrayList<DynamicGameObject> dynamics,
                    ArrayList<BasicScript> scripts) {
        super(world, stage, statics, dynamics, scripts);
        StrangeContactsListener scl = new StrangeContactsListener(world, this);
        world.setContactListener(scl);
    }

    @Override
    public void update(float elapsedTime) {
        super.update(elapsedTime);
        for (DynamicGameObject object : dynamics)
            if (scene == 1 && object.left < 130 || (scene == 2 && object.left > 130 && object.left < 285) ||
                    (scene == 3 && object.left > 285 && object.left < 564) || (scene == 4 && object.left > 564 && object.left < 819) ||
                    (scene == 5 && object.left > 819) || object.type == FixtureData.Type.JOE)
                if (object.isActive()) object.update(elapsedTime);
        executeScripts(elapsedTime);
    }
}

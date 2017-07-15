package com.aya.apathy.game.contactslistener;

import com.aya.apathy.game.FixtureData;
import com.aya.apathy.game.level.AggressionLevel;
import com.badlogic.gdx.physics.box2d.World;

public class TopContactsListener extends ContactsListener {

    public TopContactsListener(World world, AggressionLevel level) {
        super(world, level);
    }

    @Override
    protected void collideDisappearingWall(FixtureData fd) {
        level.getScript(AggressionLevel.Script.PRISON.ordinal()).start();
    }
}
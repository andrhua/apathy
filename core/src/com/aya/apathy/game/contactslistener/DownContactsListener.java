package com.aya.apathy.game.contactslistener;

import com.aya.apathy.game.FixtureData;
import com.aya.apathy.game.level.NegationLevel;
import com.badlogic.gdx.physics.box2d.World;

public class DownContactsListener extends ContactsListener {
    public DownContactsListener(World world, NegationLevel level) {
        super(world, level);
    }

    @Override
    protected void collideDisappearingWall(FixtureData fd) {
        level.getScript(NegationLevel.Script.TRAFFIC_LIGHT.ordinal()).start();
    }
}

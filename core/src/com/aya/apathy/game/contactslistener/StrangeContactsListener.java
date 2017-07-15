package com.aya.apathy.game.contactslistener;

import com.aya.apathy.game.FixtureData;
import com.aya.apathy.game.level.BargainingLevel;
import com.badlogic.gdx.physics.box2d.World;

public class StrangeContactsListener extends ContactsListener {

    public StrangeContactsListener(World world, BargainingLevel level) {
        super(world, level);
    }

    @Override
    protected void collideDisappearingWall(FixtureData fd) {

    }
}

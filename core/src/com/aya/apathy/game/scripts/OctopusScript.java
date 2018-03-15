package com.aya.apathy.game.scripts;

import com.aya.apathy.game.object.Collectible;
import com.aya.apathy.game.object.HauntingEnemy;
import com.aya.apathy.game.object.Octopus;
import com.aya.apathy.game.object.Platform;

public class OctopusScript extends BasicScript {
    private Octopus octopus;
    private Collectible key;
    private Platform.Disappearing door;

    public OctopusScript(Octopus octopus, Collectible key, Platform.Disappearing door) {
        this.octopus = octopus;
        this.key = key;
        this.door = door;
        setActiveState();
    }

    @Override
    public void execute() {
        door.queryForMagic();
        octopus.behaviour = HauntingEnemy.Behaviour.HAUNTING;
        setIdleState();
    }

    @Override
    protected boolean checkCondition(float elapsedTime) {
        return !key.isActive();
    }

    @Override
    public void reset() {
        setActiveState();
    }
}

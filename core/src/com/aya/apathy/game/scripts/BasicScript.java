package com.aya.apathy.game.scripts;

import com.aya.apathy.core.Updatable;
import com.aya.apathy.game.object.Resetable;
import com.aya.apathy.ui.BasicActivatable;

public abstract class BasicScript extends BasicActivatable implements Updatable, Resetable {

    public abstract void execute();

    protected abstract boolean checkCondition(float elapsedTime);

    public void start() {
        setActiveState();
    }

    @Override
    public void update(float elapsedTime) {
        if (isActive())
            if (checkCondition(elapsedTime)) execute();
    }
}

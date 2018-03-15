package com.aya.apathy.game.scripts;

import com.aya.apathy.game.object.Indicator;
import com.aya.apathy.game.object.Platform;

public class BackdoorScript extends BasicScript {
    private Indicator indicator;
    private Platform.Transformable platform;
    private boolean isLocked;

    public BackdoorScript(Indicator indicator, Platform.Transformable platform) {
        this.indicator = indicator;
        this.platform = platform;
        setActiveState();
        isLocked = true;
    }

    @Override
    public void execute() {
        if (isLocked) {
            platform.queryForMagic();
            isLocked = false;
        }
    }

    @Override
    protected boolean checkCondition(float elapsedTime) {
        return indicator.isActive();
    }

    @Override
    public void reset() {
        isLocked = true;
    }
}

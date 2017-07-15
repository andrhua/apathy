package com.aya.apathy.game.scripts;

import com.aya.apathy.Game;
import com.aya.apathy.game.object.Cloud;
import com.aya.apathy.game.object.Dragon;
import com.aya.apathy.game.object.Platform;
import com.aya.apathy.game.object.TrafficLight;
import com.badlogic.gdx.Gdx;

public class TrafficLightScript extends BasicScript {
    private TrafficLight trafficLight;
    private Platform.Disappearing wallDisappearing;
    private Dragon dragon;
    private Cloud cloud;
    private boolean isPositiveResult;

    public TrafficLightScript(TrafficLight trafficLight, Platform.Disappearing wallDisappearing, Dragon dragon, Cloud cloud) {
        this.trafficLight = trafficLight;
        this.wallDisappearing = wallDisappearing;
        this.dragon = dragon;
        this.cloud = cloud;
        setIdleState();
    }

    @Override
    public void execute() {
        if (isPositiveResult) {
            wallDisappearing.queryForMagic();
            cloud.queryForMagic();
        } else {
            dragon.queryForMagic();
        }
    }

    @Override
    protected boolean checkCondition(float elapsedTime) {
        if (trafficLight.getState() == TrafficLight.State.GREEN && Game.joe.footContacts == 0) {
            isPositiveResult = true;
            return true;
        } else {
            if (trafficLight.getState() != TrafficLight.State.GREEN && Game.joe.footContacts == 0) {
                isPositiveResult = false;
                return true;
            }
        }
        return false;
    }

    @Override
    public void start() {
        super.start();
        trafficLight.queryForMagic();
    }

    @Override
    public void reset() {
        setIdleState();
    }
}

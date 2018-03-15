package com.aya.apathy.core;

import com.aya.apathy.Game;
import com.aya.apathy.game.object.Joe;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.math.Vector3;

public class XboxControllerListener implements ControllerListener {
    public static final int BUTTON_A = 0;
    public static final int BUTTON_B = 1;
    public static final int BUTTON_X = 2;
    public static final int BUTTON_Y = 3;
    public static final int BUTTON_LB = 4;
    public static final int BUTTON_RB = 5;
    public static final int BUTTON_BACK = 6;
    public static final int BUTTON_START = 7;
    public static final int BUTTON_LS = 8; //Left Stick pressed down
    public static final int BUTTON_RS = 9; //Right Stick pressed down

    public static final int POV = 0;

    public static final int AXIS_LY = 0; //-1 is up | +1 is down
    public static final int AXIS_LX = 1; //-1 is left | +1 is right
    public static final int AXIS_RY = 2; //-1 is up | +1 is down
    public static final int AXIS_RX = 3; //-1 is left | +1 is right
    public static final int AXIS_TRIGGER = 4; //LT and RT are on the same Axis! LT > 0 | RT < 0
    private Joe joe;

    public XboxControllerListener(){
        joe= Game.joe;
    }

    @Override
    public void connected(Controller controller) {
        Gdx.app.log(controller.getName(), "connected");
    }

    @Override
    public void disconnected(Controller controller) {
        Gdx.app.log(controller.getName(), "disconnected");
    }

    @Override
    public boolean buttonDown(Controller controller, int buttonCode) {
        switch (buttonCode){
            case BUTTON_A: joe.needToJump=true; break;
            case BUTTON_BACK: break;
        }
        return true;
    }

    @Override
    public boolean buttonUp(Controller controller, int buttonCode) {
        return false;
    }

    @Override
    public boolean axisMoved(Controller controller, int axisCode, float value) {
        switch (axisCode){
            case AXIS_LX: if (value<-.15f) joe.setXState(Joe.xState.LEFT);else
                if (value>.15f) joe.setXState(Joe.xState.RIGHT); else
                    joe.setXState(Joe.xState.STAY);
        }
        return false;
    }

    @Override
    public boolean povMoved(Controller controller, int povCode, PovDirection value) {
        return false;
    }

    @Override
    public boolean xSliderMoved(Controller controller, int sliderCode, boolean value) {
        return false;
    }

    @Override
    public boolean ySliderMoved(Controller controller, int sliderCode, boolean value) {
        return false;
    }

    @Override
    public boolean accelerometerMoved(Controller controller, int accelerometerCode, Vector3 value) {
        return false;
    }
}

package com.aya.apathy.util;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.scaleTo;

public class TouchListener extends ClickListener {
    private Button myButton;

    public TouchListener(Button button){
        this.myButton =button;
        myButton.setOrigin(myButton.getWidth() / 2, myButton.getHeight() / 2);
        myButton.setTransform(true);
    }

    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        myButton.addAction(scaleTo(Constants.BUTTON_SCALE, Constants.BUTTON_SCALE, Constants.SCALE_DURATION));
        return true;
    }

    @Override
    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
        myButton.addAction(scaleTo(Constants.BUTTON_SCALE, Constants.BUTTON_SCALE, Constants.SCALE_DURATION));
    }

    @Override
    public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
        myButton.addAction(scaleTo(1, 1, Constants.SCALE_DURATION));
    }

}

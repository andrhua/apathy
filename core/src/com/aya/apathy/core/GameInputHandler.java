package com.aya.apathy.core;

import com.aya.apathy.Game;
import com.aya.apathy.game.object.Joe;
import com.aya.apathy.game.object.Resetable;
import com.aya.apathy.util.TouchListener;
import com.aya.apathy.util.Assets;
import com.aya.apathy.util.Constants;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;

import sun.rmi.runtime.Log;

public class GameInputHandler implements Resetable {
    public ImageButton leftb, rightb;
    private Joe joe;
    private int width, height;
    private Stage stage;
    private boolean isXboxConnected;


    public GameInputHandler(){
        isXboxConnected=false;
        findContollers();
        width= Constants.WIDTH;
        height=Constants.HEIGHT;
        leftb=new ImageButton(new TextureRegionDrawable(Assets.other.findRegion(Assets.OTHER_ARROW_LEFT)));
        leftb.setPosition(5*width/64, height/8);
        leftb.setSize(height/6, height/6);
        rightb=new ImageButton(new TextureRegionDrawable(Assets.other.findRegion(Assets.OTHER_ARROW_RIGHT)));
        rightb.setPosition(13*width/64, height/8);
        rightb.setSize(height/6, height/6);
        stage=new Stage(Game.viewport);
        stage.addActor(leftb);
        stage.addActor(rightb);
        stage.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                joe.needToJump = x>width/2;
                return true;
            }
        });

        leftb.addListener(new TouchListener(leftb){

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                super.enter(event, x, y, pointer, fromActor);
                joe.setXState(Joe.xState.LEFT);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                super.exit(event, x, y, pointer, toActor);
                joe.setXState(Joe.xState.STAY);
            }
        });

        rightb.addListener(new TouchListener(rightb){
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                super.enter(event, x, y, pointer, fromActor);
                joe.setXState(Joe.xState.RIGHT);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                super.exit(event, x, y, pointer, toActor);
                joe.setXState(Joe.xState.STAY);
            }
        });
        joe= Game.joe;
    }

    private void findContollers(){
        Array<Controller> controllers = Controllers.getControllers();
        if(controllers.size==0){
            //there are no controllers...
        } else {
            for (Controller c : controllers) {
                Gdx.app.log("controler", c.getName());
                if (c.getName().contains("360")) {
                    Controllers.addListener(new XboxControllerListener());
                    isXboxConnected=true;
                }
            }
        }

    }

    public Stage getStage() {
        return stage;
    }

    @Override
    public void reset() {
        Game.joe.setXState(Joe.xState.STAY);
    }

    public boolean isXboxConnected() {
        return isXboxConnected;
    }


}

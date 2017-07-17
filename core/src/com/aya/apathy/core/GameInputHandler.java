package com.aya.apathy.core;

import com.aya.apathy.Game;
import com.aya.apathy.game.object.Joe;
import com.aya.apathy.game.object.Resetable;
import com.aya.apathy.util.TouchListener;
import com.aya.apathy.util.Assets;
import com.aya.apathy.util.Constants;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class GameInputHandler /*extends InputAdapter*/ implements Resetable {
    //public Button left, right;
    public ImageButton leftb, rightb;
    private Joe joe;
    private int width, height;
    private Stage stage;


    public GameInputHandler(){
        width= Constants.WIDTH;
        height=Constants.HEIGHT;
        /*left=new Button(Assets.other.findRegion(Assets.OTHER_ARROW_LEFT), 3*width/16, height/3, 0, 0, true);
        right=new Button(Assets.other.findRegion(Assets.OTHER_ARROW_RIGHT), 5*width/16, height/3, 0, 0, true);
        */
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
    /*
    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (left.isActive()&&!left.touch(screenX,height-screenY)) {
            joe.setXState(Joe.xState.STAY);
            left.setIdleState();
            return true;
        } else
        if (left.touch(screenX,height-screenY)) {
            left.setActiveState();
            joe.setXState(Joe.xState.LEFT);
            return true;
        }
        if (right.isActive()&&!right.touch(screenX,height-screenY)){
            joe.setXState(Joe.xState.STAY);
            right.setIdleState();
            return true;
        } else
        if (right.touch(screenX,height-screenY)){
            right.setActiveState();
            joe.setXState(Joe.xState.RIGHT);
            return true;
        } else
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (left.isActive()&&left.touch(screenX, height-screenY)) {
            joe.setXState(Joe.xState.STAY);
            left.setIdleState();
            return true;
        }
        if (right.isActive()&&right.touch(screenX,height- screenY)) {
            joe.setXState(Joe.xState.STAY);
            right.setIdleState();
            return true;
        }
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (screenX>width/2) {
            joe.needToJump =true;
            return true;
        }
        if (left.touch(screenX, height-screenY)) {
            left.touchDown(screenX, height-screenY, pointer, button);
            joe.setXState(Joe.xState.LEFT);
            return true;
        }
        if (right.touch(screenX, height-screenY)) {
            right.touchDown(screenX, height-screenY, pointer, button);
            joe.setXState(Joe.xState.RIGHT);
            return true;
        }
        return false;
    }

    @Override
    public void reset() {
        left.setIdleState();
        right.setIdleState();
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode){
            case Input.Keys.LEFT:
                joe.setXState(Joe.xState.LEFT);
                break;
            case Input.Keys.RIGHT:
                joe.setXState(Joe.xState.RIGHT);
                break;
            case Input.Keys.SPACE:
                joe.needToJump=true;
                break;
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode){
            case Input.Keys.LEFT:
            case Input.Keys.RIGHT:
                joe.setXState(Joe.xState.STAY);
                break;
        }
        return true;
    }
    */

    public Stage getStage() {
        return stage;
    }

    @Override
    public void reset() {
        Game.joe.setXState(Joe.xState.STAY);
    }
}

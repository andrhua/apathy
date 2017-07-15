package com.aya.apathy.ui;

import com.aya.apathy.Game;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class ActionPopup extends NotifyPopup  {
    public Button no;

    public ActionPopup(String text){
        super(text);
        ok=new Button(Game.language.bundle.get("ok"), 9*width/20, height/3+height/16, Color.rgb565(0,168/255f,81/255f), Color.rgb565(20/255f,188/255f,101/255f), true);
        no=new Button(Game.language.bundle.get("no"), 11*width/20, height/3+height/16, Color.rgb565(244/255f,67/255f,54/255f), Color.rgb565(250/255f, 87/255f, 74/255f), false);
        setIdleState();
    }

    @Override
    public void render(ShapeRenderer shapeRenderer) {
        super.render(shapeRenderer);
        if (isActive()) no.render(shapeRenderer);
    }

    @Override
    public void dispose() {
        super.dispose();
        no.dispose();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        super.touchDown(screenX, screenY, pointer, button);
        return isActive() && no.touchDown(screenX, screenY, pointer, button);
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        super.touchDragged(screenX, screenY, pointer);
        return isActive() && no.touchDragged(screenX, screenY, pointer);
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        super.touchUp(screenX, screenY, pointer, button);
        return isActive() && no.touchUp(screenX, screenY, pointer, button);
    }
}

package com.aya.apathy.screens;

import com.aya.apathy.Game;
import com.aya.apathy.ui.ActionPopup;
import com.aya.apathy.ui.Button;
import com.aya.apathy.ui.Checkbox;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class SettingsScreen extends BasicScreen {
    private Button english, russian, back, reset;
    private ActionPopup resetPopup;
    private Checkbox isSfxEnabled;

    public SettingsScreen(Game game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
        english=new Button(Game.language.bundle.get("english"), 13*width/32, 3*height/20, Color.argb8888(0,0,0,0), Color.rgb565(220/255f, 220/255f, 220/255f), true);
        russian=new Button(Game.language.bundle.get("russian"), 19*width/32, 3*height/20, Color.argb8888(0,0,0,0), Color.rgb565(220/255f, 220/255f, 220/255f), true);
        back=new Button(Game.language.bundle.get("back"), width/15, height/20, Color.argb8888(0,0,0,0), Color.rgb565(220/255f, 220/255f, 220/255f), false);
        reset=new Button(Game.language.bundle.get("reset_data"), width/2, height/2, Color.argb8888(0,0,0,0), Color.rgb565(220/255f, 220/255f, 220/255f), true);
        resetPopup=new ActionPopup(Game.language.bundle.get("reset_confirmation"));
        isSfxEnabled=new Checkbox(Game.sfx.getSFX(), Game.language.bundle.get("sfx"), width/2, 7*height/9);
    }

    @Override
    public void render(ShapeRenderer shapeRenderer) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.line(width/3, height/3, 2*width/3, height/3);
        shapeRenderer.line(width/3, 2*height/3, 2*width/3, 2*height/3);
        shapeRenderer.end();
        english.render(shapeRenderer);
        russian.render(shapeRenderer);
        isSfxEnabled.render(shapeRenderer);
        reset.render(shapeRenderer);
        back.render(shapeRenderer);
        resetPopup.render(shapeRenderer);
    }

    @Override
    public boolean onBackPressed() {
        if (resetPopup.isActive())resetPopup.setIdleState(); else game.setScreen(new MainScreen(game));
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (resetPopup.isActive())
            resetPopup.touchDown(screenX, height-screenY, pointer, button);
        else {
            english.touchDown(screenX, height-screenY, pointer, button);
            russian.touchDown(screenX, height-screenY, pointer, button);
            reset.touchDown(screenX, height-screenY, pointer, button);
            back.touchDown(screenX, height-screenY, pointer, button);
            isSfxEnabled.touchDown(screenX, height-screenY, pointer, button);
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (resetPopup.isActive()){
            if (resetPopup.ok.touchUp(screenX, height-screenY, pointer, button)) {
                Game.playerData.clear();
                game.setScreen(new MainScreen(game));
            }
            if (resetPopup.no.touchUp(screenX, height-screenY, pointer, button)) resetPopup.setIdleState();
        }
        else {
            if (english.touchUp(screenX, height-screenY, pointer, button)&&!Game.language.locale.equals("en")) {
                Game.language.setLanguage("en");
                game.setScreen(new MainScreen(game));
            }
            if (russian.touchUp(screenX, height-screenY, pointer, button)&&!Game.language.locale.equals("ru")) {
                Game.language.setLanguage("ru");
                game.setScreen(new MainScreen(game));
            }
            if (reset.touchUp(screenX, height-screenY, pointer, button)) resetPopup.setActiveState();
            if (back.touchUp(screenX, height-screenY, pointer, button)) game.setScreen(new MainScreen(game));
            if (isSfxEnabled.touchUp(screenX, height-screenY, pointer, button)) Game.sfx.setEnabled(isSfxEnabled.isChecked());
        }
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (resetPopup.isActive())
            resetPopup.touchDragged(screenX, height-screenY, pointer);
        else {
            english.touchDragged(screenX, height-screenY, pointer);
            russian.touchDragged(screenX, height-screenY, pointer);
            reset.touchDragged(screenX, height-screenY, pointer);
            back.touchDragged(screenX, height-screenY, pointer);
            isSfxEnabled.touchDragged(screenX, height-screenY, pointer);
        }
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}

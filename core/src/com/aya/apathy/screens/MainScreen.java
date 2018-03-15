package com.aya.apathy.screens;

import com.aya.apathy.Game;
import com.aya.apathy.ui.ActionPopup;
import com.aya.apathy.ui.Button;
import com.aya.apathy.util.Assets;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class MainScreen extends BasicScreen {
    private Button start, settings, exit;
    private ActionPopup exitPopup;

    public MainScreen(Game game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
        glyphLayout=new GlyphLayout(Assets.main4, Game.language.bundle.get("apathy"));
        start=new Button(Game.language.bundle.get(Game.playerData.progress.isNewGame?"new_game":"continue"), width/2, 3*height/8, Color.argb8888(0,0,0,0), Color.rgb565(220/255f, 220/255f, 220/255f), true);
        settings=new Button(Game.language.bundle.get("settings"), width/2, height/4, Color.argb8888(0,0,0,0), Color.rgb565(220/255f, 220/255f, 220/255f), true);
        exit=new Button(Game.language.bundle.get("exit"), width/2, height/8, Color.argb8888(0,0,0,0), Color.rgb565(220/255f, 220/255f, 220/255f), true);
        exitPopup=new ActionPopup(Game.language.bundle.get("exit_confirmation"));
        widgets.addAll(start, settings, start);
    }


    @Override
    public void render(ShapeRenderer shapeRenderer) {
        batch.begin();
        Assets.main4.draw(batch, Game.language.bundle.get("apathy"), width/2-glyphLayout.width/2, 7*height/10);
        batch.end();
        start.render(shapeRenderer);
        settings.render(shapeRenderer);
        exit.render(shapeRenderer);
        exitPopup.render(shapeRenderer);
    }

    @Override
    public void dispose() {
        super.dispose();
        start.dispose();
        settings.dispose();
        exit.dispose();
        exitPopup.dispose();
    }

    @Override
    public boolean onBackPressed() {
        if (exitPopup.isActive()) exitPopup.setIdleState(); else exitPopup.setActiveState();
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
        if (exitPopup.isActive())
            exitPopup.touchDown(screenX, height-screenY, pointer, button);
        else {
            start.touchDown(screenX, height - screenY, pointer, button);
            settings.touchDown(screenX, height - screenY, pointer, button);
            exit.touchDown(screenX, height - screenY, pointer, button);
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (exitPopup.isActive()) {
            if (exitPopup.ok.touchUp(screenX, height-screenY, pointer, button)) Gdx.app.exit();
            if (exitPopup.no.touchUp(screenX, height-screenY, pointer, button)) exitPopup.setIdleState();
        }
        else {
            if (start.touchUp(screenX, height - screenY, pointer, button))
                game.setScreen(new LevelScreen(game));
            if (settings.touchUp(screenX, height - screenY, pointer, button))
                game.setScreen(new SettingsScreen(game));
            if (exit.touchUp(screenX, height - screenY, pointer, button))
                exitPopup.setActiveState();
        }
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (exitPopup.isActive())
            exitPopup.touchDragged(screenX, height-screenY, pointer);
        else {
            start.touchDragged(screenX, height - screenY, pointer);
            settings.touchDragged(screenX, height - screenY, pointer);
            exit.touchDragged(screenX, height - screenY, pointer);
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

package com.aya.apathy.screens;

import com.aya.apathy.Game;
import com.aya.apathy.audio.Sfx;
import com.aya.apathy.game.level.LevelManager;
import com.aya.apathy.model.LevelPreview;
import com.aya.apathy.ui.Button;
import com.aya.apathy.ui.NotifyPopup;
import com.aya.apathy.util.Assets;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

//import com.kekonyan.remastered.aromatique.game.level.BasicLevel;
//import com.kekonyan.remastered.aromatique.game.level.LevelManager;

public class LevelScreen extends BasicScreen{
    private LevelPreview levels[];
    private Button back;
    private NotifyPopup lockedPopup, nextLifePopup;
    private enum State{CHOOSING, PREPARATION, LOADING}
    private State state;
    private int selected;

    @Override
    public void show() {
        super.show();
        levels=new LevelPreview[6];
        for (int i=0; i<6; i++) levels[i]=new LevelPreview(i);
        state=State.CHOOSING;
        back=new Button(Game.language.bundle.get("back"), width/15, height/20, Color.argb8888(0,0,0,0), Color.rgb565(220/255f, 220/255f, 220/255f), false);
        lockedPopup=new NotifyPopup(Game.language.bundle.get("level_locked"));
        nextLifePopup=new NotifyPopup(Game.language.bundle.get("level_will_never_be_made"));
    }

    public LevelScreen(Game game) {
        super(game);
    }

    @Override
    public void render(ShapeRenderer shapeRenderer) {
        switch (state){
            case CHOOSING:{
                for (int i=0; i<6; i++) levels[i].render(shapeRenderer);
                back.render(shapeRenderer);
                nextLifePopup.render(shapeRenderer);
                lockedPopup.render(shapeRenderer);
            } break;
            case PREPARATION:
                batch.begin();
                Assets.main10.draw(batch, Game.language.bundle.get("loading"), width/2-glyphLayout.width/2, height/2+glyphLayout.height/2);
                batch.end();
                state=State.LOADING;
                break;
            case LOADING:
                LevelManager levelManager = new LevelManager(LevelManager.Stage.values()[selected]);
                game.setScreen(new GameScreen(game, levelManager.loadLevel()));
                break;
        }
    }

    @Override
    public boolean onBackPressed() {
        if (!nextLifePopup.onBackPressed()&&!lockedPopup.onBackPressed()) game.setScreen(new MainScreen(game));
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
        nextLifePopup.touchDown(screenX, height-screenY, pointer, button);
        lockedPopup.touchDown(screenX, height-screenY, pointer, button);
        back.touchDown(screenX, height-screenY, pointer, button);
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (nextLifePopup.isActive()&&nextLifePopup.ok.touchUp(screenX, height-screenY, pointer, button)) {
            nextLifePopup.setIdleState();
            return true;
        }
        if (lockedPopup.isActive()&&lockedPopup.ok.touchUp(screenX, height-screenY, pointer, button)){
            lockedPopup.setIdleState();
            return true;
        }
        if (!nextLifePopup.isActive()&&!lockedPopup.isActive()) {
            if (back.touchUp(screenX, height - screenY, pointer, button))
                game.setScreen(new MainScreen(game));
            for (int i = 0; i < 6; i++) {
                if (levels[i].touch(screenX, height - screenY) && levels[i].isActive()) {
                    selected = i;
                    Game.sfx.play(Sfx.SoundType.CLICK_POSITIVE);
                    state = State.PREPARATION;
                    glyphLayout=new GlyphLayout(Assets.main10, Game.language.bundle.get("loading"));
                } else if (levels[i].touch(screenX, height - screenY)) {
                    Game.sfx.play(Sfx.SoundType.CLICK_POSITIVE);
                    if (i > 2) nextLifePopup.setActiveState();
                    else lockedPopup.setActiveState();
                }
            }
        }
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        nextLifePopup.touchDragged(screenX, height-screenY, pointer);
        lockedPopup.touchDragged(screenX, height-screenY, pointer);
        back.touchDragged(screenX, height-screenY, pointer);
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

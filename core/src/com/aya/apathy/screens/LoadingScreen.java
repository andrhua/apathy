package com.aya.apathy.screens;

import com.aya.apathy.Game;
import com.aya.apathy.util.Assets;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class LoadingScreen extends BasicScreen {
    private enum State {WAITING, LOADING, FINISHED}
    private State state;
    private GlyphLayout layout1, layout2;
    private float time;

    public LoadingScreen(Game game) {
        super(game);
        state= State.WAITING;
    }

    @Override
    public void show() {
        super.show();
        FreeTypeFontGenerator generator=new FreeTypeFontGenerator(Gdx.files.internal("font/main.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter=new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size=height/15;
        parameter.characters="абвгдежзийклмнопрстуфхцчшщъыьэюяabcdefghijklmnopqrstuvwxyzАБВГДЕЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789][_!$%#@|\\/?-+=()*&.;:,{}\"´`'<>";
        parameter.color= Color.BLACK;
        Assets.main15=generator.generateFont(parameter);
        parameter.size= height/20;
        Assets.main20=generator.generateFont(parameter);
        generator.dispose();
        glyphLayout=new GlyphLayout(Assets.main15, Game.language.bundle.get("in_memory"));
        layout1=new GlyphLayout(Assets.main20, "2016 - 2016");
        layout2=new GlyphLayout(Assets.main20, Game.language.bundle.get("loading"));
        time=0;
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        time+=delta;
        batch.begin();
        Assets.main15.draw(batch, Game.language.bundle.get("in_memory"), width/2-glyphLayout.width/2, height/2+glyphLayout.height/2);
        Assets.main20.draw(batch, "2016 - 2016", width/2-layout1.width/2, 2*height/5+layout1.height/2);
        Assets.main20.draw(batch, Game.language.bundle.get("loading"), width/2-layout2.width/2, height/15+layout2.height/2);
        batch.end();
        switch (state){
            case WAITING: state=State.LOADING; break;
            case LOADING: new Assets(); state=State.FINISHED; break;
            case FINISHED:/*if (time>=5)*/ game.setScreen(new MainScreen(game)); break;
        }

    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
    public void render(ShapeRenderer shapeRenderer) {

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
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
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

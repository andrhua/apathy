package com.aya.apathy.screens;

import com.aya.apathy.Game;
import com.aya.apathy.util.Constants;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public abstract class BasicScreen extends ScreenAdapter implements Renderable, Backable, InputProcessor{

    protected SpriteBatch batch;
    protected ShapeRenderer shapeRenderer;
    protected GlyphLayout glyphLayout;
    protected int width, height;
    protected Game game;

    public BasicScreen(Game game){
        this.game=game;
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void show() {
        width= Constants.WIDTH;
        height=Constants.HEIGHT;
        batch=new SpriteBatch();
        shapeRenderer=new ShapeRenderer();
    }

    @Override
    public void dispose() {
        batch.dispose();
        shapeRenderer.dispose();
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        render(shapeRenderer);
    }
}

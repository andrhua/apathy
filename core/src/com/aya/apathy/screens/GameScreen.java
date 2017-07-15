package com.aya.apathy.screens;

import com.aya.apathy.Game;
import com.aya.apathy.core.GameInputHandler;
import com.aya.apathy.game.level.BasicLevel;
import com.aya.apathy.ui.Button;
import com.aya.apathy.util.Assets;
import com.aya.apathy.util.Constants;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;

public class GameScreen extends BasicScreen {
    private BasicLevel level;
    private GameInputHandler gameInputHandler;
    private Button resume, exit, tryAgain;
    public enum State{GAME, PAUSE, LOSE, WIN}
    private State state;
    private GlyphLayout winLayout, loseLayout;
    private Camera camera;

    public GameScreen(Game game, BasicLevel level) {
        super(game);
        this.level=level;
    }

    public void setState(State state){
        this.state=state;
        if (state==State.LOSE) Game.playerData.progress.deaths[level.stage.ordinal()]++;
        Gdx.input.setInputProcessor(state==State.GAME?gameInputHandler.getStage():this);
    }

    public void updateCamera(float delta){
        Vector3 target = new Vector3(Game.joe.body.getPosition().x*Constants.PPM,
                Game.joe.body.getPosition().y*Constants.PPM,
                0);
        final float speed=delta * 2,ispeed=1.0f-speed;
        Vector3 cameraPosition = camera.position;
        cameraPosition.scl(ispeed);
        target.scl(speed);
        cameraPosition.add(target);
        camera.position.set(cameraPosition);
        camera.update();
    }

    @Override
    public void dispose() {
        super.dispose();
        level.dispose();
    }

    @Override
    public void show() {
        super.show();
        level.setGameScreenContext(this);
        gameInputHandler=new GameInputHandler();
        setState(State.GAME);
        resume=new Button(Game.language.bundle.get("resume"), 14*width/15, height/20, Color.argb8888(0,0,0,0), Color.rgb565(220/255f, 220/255f, 220/255f), true);
        tryAgain=new Button(Game.language.bundle.get("try_again"), 26*width/30, height/20, Color.argb8888(0,0,0,0), Color.rgb565(220/255f, 220/255f, 220/255f), true);
        exit=new Button(Game.language.bundle.get("exit"), width/15, height/20, Color.argb8888(0,0,0,0), Color.rgb565(220/255f, 220/255f, 220/255f), true);
        winLayout=new GlyphLayout(Assets.main15, Game.language.bundle.get("win"));
        loseLayout=new GlyphLayout(Assets.main15, Game.language.bundle.get("lose"));
        level.setSpriteBatch(batch);
        camera=new OrthographicCamera(Constants.CAMERA_WIDTH, Constants.CAMERA_HEIGHT);
        camera.translate(camera.viewportWidth/2, camera.viewportHeight/2, 0);
        camera.update();
        level.setCamera(camera);
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (state==State.GAME) {

            level.update(delta);

            updateCamera(delta);
        }
        gameInputHandler.getStage().act(delta);
        render(shapeRenderer);
    }

    @Override
    public void render(ShapeRenderer shapeRenderer) {
        drawLevel();
        drawGUI();
    }

    private void drawGUI() {
        batch.setProjectionMatrix(game.camera.combined);
        shapeRenderer.setProjectionMatrix(game.camera.combined);
        gameInputHandler.getStage().draw();
        if (state ==State.PAUSE||state==State.LOSE||state==State.WIN) {
            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_ONE, GL20.GL_ONE_MINUS_SRC_ALPHA);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(0,0,0,.25f);
            shapeRenderer.rect(0,0,width,height);
            shapeRenderer.end();
            Gdx.gl.glDisable(GL20.GL_BLEND);
        }
        switch (state){
            case PAUSE:
                resume.render(shapeRenderer);
                exit.render(shapeRenderer);
                break;
            case LOSE: tryAgain.render(shapeRenderer);
            case WIN:
                batch.begin();
                Assets.main15.draw(batch,
                        Game.language.bundle.get(state==State.WIN?"win":"lose"),
                        width/2-(state==State.WIN?winLayout.width:loseLayout.width)/2, 3*height/5-winLayout.height/2);
                batch.end();
                exit.render(shapeRenderer);
                break;
        }
    }

    private void drawLevel() {
        batch.setProjectionMatrix(game.camera.combined);
        batch.begin();
        batch.draw(level.background, 0, 0, width, height);
        batch.end();
        batch.setProjectionMatrix(camera.combined);
        shapeRenderer.setProjectionMatrix(camera.combined);
        level.render(shapeRenderer);
    }

    @Override
    public boolean onBackPressed() {
        switch (state){
            case GAME: setState(State.PAUSE); break;
            case PAUSE: setState(State.GAME); break;
            case LOSE:
            case WIN: game.setScreen(new LevelScreen(game)); break;
        }
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode){
            case Input.Keys.ESCAPE:
                onBackPressed();
                break;
        }
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        switch (state){
            case PAUSE:
                if (resume.touchDown(screenX, height-screenY, pointer, button)) return true;
                if (exit.touchDown(screenX, height-screenY, pointer, button)) return true;
                break;
            case LOSE:
                if (tryAgain.touchDown(screenX, height-screenY, pointer, button)) return true;
            case WIN:
                if (exit.touchDown(screenX, height-screenY, pointer, button)) return true;
                break;
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        switch (state){
            case PAUSE:
                if (resume.touchUp(screenX, height-screenY, pointer, button)) {
                    setState(State.GAME);
                    return true;
                }
                if (exit.touchUp(screenX, height-screenY, pointer, button)){
                    game.setScreen(new LevelScreen(game));
                    return true;
                }
                break;
            case LOSE:
                if (tryAgain.touchUp(screenX, height-screenY, pointer, button)){
                    level.restart();
                    gameInputHandler.reset();
                    setState(State.GAME);
                    return true;
                }
            case WIN:
                if (exit.touchUp(screenX, height-screenY, pointer, button)){
                    game.setScreen(new LevelScreen(game));
                    return true;
                }
                break;
        }
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        switch (state){
            case PAUSE:
                if (resume.touchDragged(screenX, height-screenY, pointer)) return true;
                if (exit.touchDragged(screenX, height-screenY, pointer)) return true;
                break;
            case LOSE:
                if (tryAgain.touchDragged(screenX, height-screenY, pointer)) return true;
            case WIN:
                if (exit.touchDragged(screenX, height-screenY, pointer)) return true;
                break;
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

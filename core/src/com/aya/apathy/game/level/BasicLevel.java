package com.aya.apathy.game.level;

import com.aya.apathy.Game;
import com.aya.apathy.core.Updatable;
import com.aya.apathy.game.FixtureData;
import com.aya.apathy.game.object.Blade;
import com.aya.apathy.game.object.Coin;
import com.aya.apathy.game.object.Collectible;
import com.aya.apathy.game.object.Dialog;
import com.aya.apathy.game.object.DynamicGameObject;
import com.aya.apathy.game.object.Muk;
import com.aya.apathy.game.object.Panhandler;
import com.aya.apathy.game.object.Platform;
import com.aya.apathy.game.object.Prequark;
import com.aya.apathy.game.object.StaticGameObject;
import com.aya.apathy.game.scripts.BasicScript;
import com.aya.apathy.screens.GameScreen;
import com.aya.apathy.screens.Renderable;
import com.aya.apathy.util.Assets;
import com.aya.apathy.util.Constants;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;

import java.util.ArrayList;
import java.util.Collections;

public abstract class BasicLevel implements Renderable, Updatable, Disposable {
    public LevelManager.Stage stage;
    private ArrayList<StaticGameObject> statics;
    protected ArrayList<DynamicGameObject> dynamics;
    protected ArrayList<StaticGameObject> objects;
    private short collectedPrequarks;
    protected World world;
    private ArrayList<Query> queueForRemove;
    private ArrayList<BasicScript> scripts;
    public int scene;
    protected SpriteBatch spriteBatch;
    public TextureRegion background;
    protected Camera camera;
    protected GameScreen gameScreen;

    public BasicLevel(World world,
                      LevelManager.Stage stage,
                      ArrayList<StaticGameObject> statics,
                      ArrayList<DynamicGameObject> dynamics,
                      ArrayList<BasicScript> scripts) {
        this.world = world;
        this.stage = stage;
        this.statics = statics;
        this.dynamics = dynamics;
        this.scripts = scripts;
        collectedPrequarks = 0;
        queueForRemove = new ArrayList<>();
        objects = new ArrayList<>(statics.size() + dynamics.size());
        objects.addAll(statics);
        objects.addAll(dynamics);
        objects.trimToSize();
        Collections.sort(objects, new StaticGameObject.Comparator());
        scene = 1;
        background=Assets.Game.backgrounds.findRegion(String.valueOf(stage.ordinal()));
        Game.playerData.progress.isNewGame=false;
    }

    public void setGameScreenContext(GameScreen gameScreen){
        this.gameScreen=gameScreen;
    }

    public void setState(GameScreen.State state){
        gameScreen.setState(state);
    }

    public void setSpriteBatch(SpriteBatch spriteBatch){
        this.spriteBatch=spriteBatch;
    }

    public void setCamera(Camera camera){
        this.camera=camera;
        camera.position.set(Game.joe.getPosition().x*Constants.PPM, Game.joe.getPosition().y*Constants.PPM, 0);
    }

    protected boolean isLocatedInCamera(float x, float y, float width, float height){
        return (x+width)*Constants.PPM>camera.position.x-Constants.CAMERA_WIDTH
                && x*Constants.PPM<camera.position.x+Constants.CAMERA_WIDTH
                &&
                (y+height)*Constants.PPM>camera.position.y-Constants.CAMERA_HEIGHT
                && y*Constants.PPM<camera.position.y+Constants.CAMERA_HEIGHT;
    }

    @Override
    public void update(float delta) {
        Gdx.app.log("begin", "!");
        world.step(1 / 60f, 8, 3);
        Gdx.app.log("end", "!");
        removeObjects();
    }

    @Override
    public void render(ShapeRenderer shapeRenderer) {
        for (StaticGameObject object : objects) {
            if (object.isActive() &&
                    isLocatedInCamera(
                            object.left,
                            object.bottom,
                            object.width,
                            object.height)
                    ||
                    (object.type == FixtureData.Type.JOE))
                object.draw(spriteBatch, shapeRenderer);
        }
    }

    public void incrementPrequarks(){
        collectedPrequarks++;
        if (collectedPrequarks == 3) {
            gameScreen.setState(GameScreen.State.WIN);
            Game.playerData.progress.aromas = Math.min(stage.ordinal() + 2, 3);
        }
    }

    void executeScripts(float elapsedTime) {
        for (BasicScript bs : scripts) bs.update(elapsedTime);
    }

    public void queryForRemove(Fixture f) {
        queueForRemove.add(new Query(f));
    }

    private void removeObjects() {
        if (!queueForRemove.isEmpty()) {
            for (Query q : queueForRemove) {
                if (q.isDynamic) {
                    q.fixture.getBody().setActive(false);
                    dynamics.get(q.id).setIdleState();
                } else {
                    q.fixture.getBody().setActive(false);
                    statics.get(q.id).setIdleState();
                }
            }
            queueForRemove.clear();
        }
    }

    public void restart() {
        scene = 1;
        world.clearForces();
        collectedPrequarks = 0;
        Game.joe.reset();
        for (StaticGameObject object : statics) {
            switch (object.type) {
                case INDICATOR:
                    object.setIdleState();
                    break;
                case COLLECTIBLE:
                    ((Collectible) object).reset();
                    break;
                case PREQUARK:
                    ((Prequark) object).reset();
                    break;
                case DIALOG:
                    ((Dialog) object).reset();
                    break;
                case TRANSFORMABLE_PLATFORM:
                    ((Platform.Transformable) object).reset();
                    break;
                case PANHANDLER:
                    ((Panhandler) object).reset();
                    break;
                case FAKE_BLADE:
                    ((Blade.Fake) object).reset();
                    break;
                case MUK:
                    ((Muk) object).reset();
                    break;
                case COIN:
                    ((Coin) object).reset();
                    break;
            }
        }
        for (DynamicGameObject object : dynamics) object.reset();
        for (BasicScript script : scripts) script.reset();

    }

    public BasicScript getScript(int id) {
        return scripts.get(id);
    }

    public StaticGameObject getStatic(int id) {
        return statics.get(id);
    }

    public DynamicGameObject getDynamic(int id) {
        return dynamics.get(id);
    }

    private class Query {
        private Fixture fixture;
        private int id;
        private boolean isDynamic;

        public Query(Fixture fixture){
            this.fixture=fixture;
            FixtureData fd = (FixtureData) fixture.getUserData();
            id = fd.id;
            isDynamic = fd.isDynamic;
        }
    }

    @Override
    public void dispose(){
        world.dispose();
        Game.sfx.disposeGameSfx();
        queueForRemove=null;
        scripts=null;
        dynamics=null;
        statics=null;
        objects=null;
    }
}

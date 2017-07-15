package com.aya.apathy.game.object;

import com.aya.apathy.game.FixtureData;
import com.aya.apathy.game.tiled.core.MapObject;
import com.aya.apathy.util.Constants;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class TrafficLight extends DynamicGameObject implements Queryable, Resetable {
    public enum State {IDLE, RED, YELLOW, FAKE_GREEN, GREEN}
    private State state;
    private float time;
    private Color neither;

    public State getState() {
        return state;
    }

    public TrafficLight(int id, MapObject mapObject, int zOrder) {
        super(id, mapObject, zOrder);
        state = State.IDLE;
        time = 0;
        type = FixtureData.Type.TRAFFIC_LIGHT;
        neither=new Color();
        Color.rgb888ToColor(neither, Color.rgb888(220/255f, 220/255f, 220/255f));
    }

    @Override
    public void draw(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.rect(left* Constants.PPM, bottom*Constants.PPM, width*Constants.PPM , height *Constants.PPM);
        shapeRenderer.setColor(neither);
        shapeRenderer.circle((left +width / 2)*Constants.PPM ,
                (bottom +height / 6)*Constants.PPM ,
                (height / 8)*Constants.PPM );
        shapeRenderer.circle((left +width / 2)*Constants.PPM ,
                (bottom +height / 2)*Constants.PPM ,
                (height / 8)*Constants.PPM );
        shapeRenderer.circle((left +width / 2)*Constants.PPM ,
                (bottom +5 * height / 6)*Constants.PPM ,
                (height / 8)*Constants.PPM );
        switch (state){
            case RED:
                shapeRenderer.setColor(Color.RED);
                shapeRenderer.circle((left +width / 2)*Constants.PPM ,
                        (bottom +height / 6)*Constants.PPM ,
                        (height / 8)*Constants.PPM );
                break;
            case YELLOW:
                shapeRenderer.setColor(Color.YELLOW);
                shapeRenderer.circle((left +width / 2)*Constants.PPM ,
                        (bottom +height / 2)*Constants.PPM ,
                        (height / 8)*Constants.PPM );
                break;
            case GREEN:
                shapeRenderer.setColor(Color.GREEN);
                shapeRenderer.circle((left +width / 2)*Constants.PPM ,
                        (bottom +5 * height / 6)*Constants.PPM ,
                        height / 8*Constants.PPM);
                break;
        }
        shapeRenderer.end();
    }


    @Override
    public void update(float elapsedTime) {
        if (state != State.IDLE) time += elapsedTime;
        if (time >= 4.1f) state = State.RED;
        else if (time >= 3.4f) state = State.GREEN;
        else if (time >= 2) state = State.FAKE_GREEN;
        else if (time >= 1) state = State.YELLOW;
    }

    @Override
    public void queryForMagic() {
        state = State.RED;
    }

    @Override
    public void reset() {
        state = State.IDLE;
        time = 0;
    }
}

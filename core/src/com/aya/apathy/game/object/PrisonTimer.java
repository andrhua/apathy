package com.aya.apathy.game.object;

import com.aya.apathy.game.tiled.core.MapObject;
import com.aya.apathy.util.Assets;
import com.aya.apathy.util.Constants;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class PrisonTimer extends DynamicGameObject implements Queryable {
    private long time;
    private boolean needToCountdown;

    public PrisonTimer(int id, MapObject mapObject, int zOrder) {
        super(id, mapObject, zOrder);
        time = TimeUnit.MINUTES.toMillis(2);
    }

    @Override
    public void draw(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.rect(left* Constants.PPM , bottom *Constants.PPM, width*Constants.PPM , height*Constants.PPM );
        shapeRenderer.end();
        spriteBatch.begin();
        Assets.Game.main.draw(spriteBatch, String.format(Locale.getDefault(), "%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(time),
                TimeUnit.MILLISECONDS.toSeconds(time) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(time))),
                (left +width / 2)*Constants.PPM ,
                (bottom +3 * height / 5)*Constants.PPM );
        spriteBatch.end();
    }

    @Override
    public void update(float elapsedTime) {
        if (needToCountdown) time -= elapsedTime;
        if (time <= 0) {
            needToCountdown = false;
            time = 0;
        }
    }

    @Override
    public void reset() {
        needToCountdown = false;
        time = TimeUnit.MINUTES.toMillis(2);
    }

    @Override
    public void queryForMagic() {
        needToCountdown = true;
    }

}

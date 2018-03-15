package com.aya.apathy;

import com.aya.apathy.audio.Sfx;
import com.aya.apathy.core.Language;
import com.aya.apathy.core.PlayerData;
import com.aya.apathy.game.object.Joe;
import com.aya.apathy.screens.Backable;
import com.aya.apathy.screens.BasicScreen;
import com.aya.apathy.screens.LoadingScreen;
import com.aya.apathy.util.Constants;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.io.IOException;

public class Game extends com.badlogic.gdx.Game implements Backable {

	public static PlayerData playerData;
	public static Sfx sfx;
	public static Language language;
	public Camera camera;
	public static Viewport viewport;
	public static Joe joe;

	@Override
	public void pause() {
		try {
			playerData.write();
		} catch (IOException e) {
			e.printStackTrace();
		}
		super.pause();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		viewport.update(width, height);
		camera.position.set(camera.viewportWidth/2, camera.viewportHeight/2, 0);
	}

	@Override
	public void render() {
		camera.update();
		super.render();
		if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.BACK)) onBackPressed();
	}


	@Override
	public void setScreen(Screen screen) {
		if (getScreen()!=null) getScreen().dispose();
		super.setScreen(screen);
	}

	@Override
	public void create() {
		Gdx.input.setCatchBackKey(true);
		new Constants();
		camera=new OrthographicCamera();
		viewport=new FitViewport(Constants.WIDTH, Constants.HEIGHT, camera);
		viewport.apply();
		camera.translate(viewport.getWorldWidth()/2, viewport.getWorldHeight()/2, 0);
		camera.update();
		playerData=new PlayerData();
		sfx=new Sfx();
		language=new Language();
		setScreen(new LoadingScreen(this));
	}

	@Override
	public BasicScreen getScreen() {
		return ((BasicScreen)this.screen);
	}

	@Override
	public boolean onBackPressed() {
		sfx.play(Sfx.SoundType.CLICK_NEGATIVE);
		return getScreen().onBackPressed();
	}


}

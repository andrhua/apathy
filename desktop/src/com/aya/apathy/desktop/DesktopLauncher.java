package com.aya.apathy.desktop;

import com.aya.apathy.Game;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title="Meet dawn with frenchman";
		config.width=960;
		config.height=540;
		new LwjglApplication(new Game(), config);
	}
}

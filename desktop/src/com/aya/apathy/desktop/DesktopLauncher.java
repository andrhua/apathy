package com.aya.apathy.desktop;

import com.aya.apathy.Game;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		System.setProperty("user.name","\\xD0\\xA2\\xD0\\xB0\\xD1\\x82\\xD1\\x8C\\xD1\\x8F\\xD0\\xBD\\xD0\\xB0");

		config.title="Meet dawn with frenchman";
		config.width=960;
		config.height=540;
		new LwjglApplication(new Game(), config);
	}
}

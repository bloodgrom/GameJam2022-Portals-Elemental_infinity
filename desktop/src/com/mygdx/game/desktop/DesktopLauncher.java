package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.MyGdxGame;

public class DesktopLauncher {
	public static int WIDTH;
	public static int HEIGHT;
	public static void main (String[] arg) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "Elemental Infinity";
		cfg.vSyncEnabled = true;

		cfg.width = LwjglApplicationConfiguration.getDesktopDisplayMode().width;
    	cfg.height = LwjglApplicationConfiguration.getDesktopDisplayMode().height;
		cfg.fullscreen = true;

		WIDTH = LwjglApplicationConfiguration.getDesktopDisplayMode().width;
		HEIGHT = LwjglApplicationConfiguration.getDesktopDisplayMode().height;

		new LwjglApplication(new MyGdxGame(), cfg);
	}
}

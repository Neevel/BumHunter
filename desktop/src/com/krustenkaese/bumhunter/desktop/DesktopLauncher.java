package com.krustenkaese.bumhunter.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.krustenkaese.bumhunter.BumHunter;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new BumHunter(), config);
		config.width = 998;
		config.height = 615;
	}
}

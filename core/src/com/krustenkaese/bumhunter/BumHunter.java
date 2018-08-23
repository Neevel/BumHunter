package com.krustenkaese.bumhunter;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.krustenkaese.bumhunter.Screens.PlayScreen;

public class BumHunter extends Game{

	public static final int V_WIDTH = 500;
	public static final int V_HEIGHT = 308;
	public static final float PPM = 100;

	public static final short DEFAULT_BIT = 1;
	public static final  short HUNTER_BIT = 2;
	public static final short COIN_BIT = 8;
	public static final short DESTROYED_BIT =16;


	private SpriteBatch batch;

	public static AssetManager manager;

	
	@Override
	public void create () {
		batch = new SpriteBatch();
		manager = new AssetManager();

		manager.load("audio/sounds/jump.wav", Sound.class);

		//manager.load("audio/music/Suspense Loop.ogg", Music.class);
		manager.finishLoading();

		setScreen(new PlayScreen(this));
	}

	@Override
	public void render () {
		super.render();
		manager.update();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		manager.dispose();


	}
	public SpriteBatch getBatch() {
		return batch;
	}
}

package com.casino.gui.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.casino.gui.MyGame;

public class MainMenuScreen implements Screen {
	
	MyGame game;
	SpriteBatch batch;
	BitmapFont font;
	
	public MainMenuScreen(MyGame game) {
		this.game = game;
		this.batch = new SpriteBatch();
		this.font = new BitmapFont();
		this.font.setColor(Color.RED);
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
		font.draw(batch, "Main Menu Screen", 100, 100);
		batch.end();
	}

	@Override
	public void dispose() {
		game.dispose();
		batch.dispose();
		font.dispose();
	}
	
	@Override
	public void resize(int width, int height) {}

	@Override
	public void pause() {}

	@Override
	public void resume() {}

	@Override
	public void show() {}
	
	@Override
	public void hide() {}

}

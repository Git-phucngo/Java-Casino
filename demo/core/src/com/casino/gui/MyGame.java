package com.casino.gui;

import com.badlogic.gdx.Game;
import com.casino.gui.screens.GameScreen;
import com.casino.gui.screens.MainMenuScreen;

public class MyGame extends Game {
	
	public MainMenuScreen mainMenuScreen;
	public GameScreen gameScreen;
	
	@Override
	public void create() {
		mainMenuScreen = new MainMenuScreen(this);
		gameScreen = new GameScreen(this);
		setScreen(gameScreen);
	}	
}

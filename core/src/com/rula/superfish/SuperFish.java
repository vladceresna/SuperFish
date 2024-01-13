package com.rula.superfish;

import com.badlogic.gdx.Game;
import com.rula.superfish.screens.GameScreen;

public class SuperFish extends Game {
	
	@Override
	public void create () {
		setScreen(new GameScreen());

	}
}

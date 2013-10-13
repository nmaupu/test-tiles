package org.maupu.tiledtest.movingCharacter;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class TiledTest extends StateBasedGame {
	public static final String GAME_NAME="Tiled Test";
	
	public TiledTest(String name) {
		super(name);
	}

	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		addState(new Game());
	}
	
	public static void main(String[] args) {
		try {
			AppGameContainer container = new AppGameContainer(new TiledTest(GAME_NAME));
			container.setDisplayMode(ConfigValues.RESOLUTION_WIDTH, ConfigValues.RESOLUTION_HEIGHT, false);
			container.setVSync(true);
			container.setShowFPS(false);
			container.setTargetFrameRate(60);
			container.setTitle(GAME_NAME);
			container.start();
		} catch (SlickException se) {
			se.printStackTrace();
		}
	}
}

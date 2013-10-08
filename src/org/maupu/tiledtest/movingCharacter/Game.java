package org.maupu.tiledtest.movingCharacter;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

public class Game extends BasicGameState {
	private TiledMap map;
	private Character character;

	public Game() {
	}

	@Override
	public void init(GameContainer container, StateBasedGame state) throws SlickException {
		map = new TiledMap("tiles/test-tiles.tmx");
		character = new Character();
	}

	@Override
	public void render(GameContainer container, StateBasedGame state, Graphics g) throws SlickException {
		map.render(0, 0);
		character.render(g);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		character.update(container, game, delta);
	}

	@Override
	public int getID() {
		return 0;
	}
}

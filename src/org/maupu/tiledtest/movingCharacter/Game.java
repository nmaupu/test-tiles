package org.maupu.tiledtest.movingCharacter;

import java.util.Iterator;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class Game extends BasicGameState {
	private GameMap gameMap;
	private Character character;
	private boolean isHitboxDisplayed = false;

	@Override
	public void init(GameContainer container, StateBasedGame state) throws SlickException {
		gameMap = new GameMap(Constant.MAP_FILE);
		character = new Character();
	}

	@Override
	public void render(GameContainer container, StateBasedGame state, Graphics g) throws SlickException {
		gameMap.render(0, 0, gameMap.getLayerIndex(Constant.MAP_LAYER_BACKGROUND));
		gameMap.render(0, 0, gameMap.getLayerIndex(Constant.MAP_LAYER_FOREGROUND));
		character.render(g);
		gameMap.render(0, 0, gameMap.getLayerIndex(Constant.MAP_LAYER_TOP));
		
		// Draw hitbox
		if(isHitboxDisplayed) {
			Iterator<Shape> it = gameMap.getCollisionShapes().iterator();
			while(it.hasNext()) {
				g.draw(it.next());
			}
		}
		
		g.drawString("Press h to toggle hitboxes", 10, 10);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		Input input = container.getInput();
		
		if(input.isKeyDown(Keyboard.KEY_UP)) {
			Shape s = character.getNextMoveHitbox(Character.DIRECTION_UP);
			character.moveCharacter(Character.DIRECTION_UP, delta, !gameMap.isColliding(s));
		} else if(input.isKeyDown(Keyboard.KEY_DOWN)) {
			Shape s = character.getNextMoveHitbox(Character.DIRECTION_DOWN);
			character.moveCharacter(Character.DIRECTION_DOWN, delta, !gameMap.isColliding(s));
		} else if(input.isKeyDown(Keyboard.KEY_LEFT)) {
			Shape s = character.getNextMoveHitbox(Character.DIRECTION_LEFT);
			character.moveCharacter(Character.DIRECTION_LEFT, delta, !gameMap.isColliding(s));
		} else if(input.isKeyDown(Keyboard.KEY_RIGHT)) {
			Shape s = character.getNextMoveHitbox(Character.DIRECTION_RIGHT);
			character.moveCharacter(Character.DIRECTION_RIGHT, delta, !gameMap.isColliding(s));
		} else if(input.isKeyPressed(Keyboard.KEY_H)) {
			character.toggleDrawingHitbox();
			this.isHitboxDisplayed = !isHitboxDisplayed;
		}
	}

	@Override
	public int getID() {
		return Constant.ID_GAME_STATE;
	}
}

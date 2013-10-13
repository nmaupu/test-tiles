package org.maupu.tiledtest.movingCharacter;

import java.util.Iterator;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class Game extends BasicGameState {
	private GameMap gameMap;
	private Character character;
	private boolean isHitboxDisplayed = false;
	// Viewport is the "window" displayed on screen
	private static float viewportX = 0;
	private static float viewportY = 0;
	private static int viewportW = ConfigValues.RESOLUTION_WIDTH;
	private static int viewportH = ConfigValues.RESOLUTION_HEIGHT;
	private static int viewportWTile = viewportW/ConfigValues.MAP_TILE_SIZE;
	private static int viewportHTile = viewportH/ConfigValues.MAP_TILE_SIZE;
	private static int limitRight, limitLeft, limitTop, limitBottom;

	@Override
	public void init(GameContainer container, StateBasedGame state) throws SlickException {
		gameMap = new GameMap(Constant.MAP_FILE);
		character = new Character();
		
		limitRight = gameMap.getWidth()*ConfigValues.MAP_TILE_SIZE-ConfigValues.RESOLUTION_WIDTH;
		limitLeft = 0;
		limitTop = 0;
		limitBottom = gameMap.getHeight()*ConfigValues.MAP_TILE_SIZE-ConfigValues.RESOLUTION_HEIGHT; 
	}

	@Override
	public void render(GameContainer container, StateBasedGame state, Graphics g) throws SlickException {
		// offset x and y to display are calculated from a given tile
		int x = (int)(viewportX % ConfigValues.MAP_TILE_SIZE);
		int y = (int)(viewportY % ConfigValues.MAP_TILE_SIZE);

		gameMap.render(-x, -y,
				(int)(viewportX/ConfigValues.MAP_TILE_SIZE), (int)(viewportY/ConfigValues.MAP_TILE_SIZE),
				// We have to display one more tile on the left and on the bottom because of offset x and y
				viewportWTile + ConfigValues.MAP_TILE_SIZE, viewportHTile + ConfigValues.MAP_TILE_SIZE, 
				gameMap.getLayerIndex(Constant.MAP_LAYER_BACKGROUND), false);
		gameMap.render(-x, -y,
				(int)(viewportX/ConfigValues.MAP_TILE_SIZE), (int)(viewportY/ConfigValues.MAP_TILE_SIZE),
				viewportWTile + ConfigValues.MAP_TILE_SIZE, viewportHTile + ConfigValues.MAP_TILE_SIZE,
				gameMap.getLayerIndex(Constant.MAP_LAYER_FOREGROUND), false);
		character.render(g);
		gameMap.render(-x, -y,
				(int)(viewportX/ConfigValues.MAP_TILE_SIZE), (int)(viewportY/ConfigValues.MAP_TILE_SIZE),
				viewportWTile + ConfigValues.MAP_TILE_SIZE, viewportHTile + ConfigValues.MAP_TILE_SIZE,
				gameMap.getLayerIndex(Constant.MAP_LAYER_TOP), false);

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
		float viewportXOffset=0, viewportYOffset=0;
		float characterVelocityX=0, characterVelocityY=0;
		int direction = -1;
		
		if(input.isKeyDown(Keyboard.KEY_RIGHT)) {
			direction = Character.DIRECTION_RIGHT;
			if(character.isCenteredX()) {
				viewportXOffset = character.getSpeed();
				
				if(viewportX + viewportXOffset > limitRight) {
					viewportXOffset = 0;
					characterVelocityX = character.getSpeed();
				}
			} else {
				characterVelocityX = character.getSpeed();
			}
		} else if(input.isKeyDown(Keyboard.KEY_LEFT)) {
			direction = Character.DIRECTION_LEFT;
			if(character.isCenteredX()) {
				viewportXOffset = -character.getSpeed();
		
				if(viewportX + viewportXOffset < limitLeft) {
					viewportXOffset = 0;
					characterVelocityX = -character.getSpeed();
				}
			} else {
				characterVelocityX = -character.getSpeed();
			}
		} else if(input.isKeyDown(Keyboard.KEY_UP)) {
			direction = Character.DIRECTION_UP;
			if(character.isCenteredY()) {
				viewportYOffset = -character.getSpeed();
				
				if(viewportY + viewportYOffset < limitTop) {
					viewportYOffset = 0;
					characterVelocityY = -character.getSpeed();
				}
			} else {
				characterVelocityY = -character.getSpeed();
			}
		} else if(input.isKeyDown(Keyboard.KEY_DOWN)) {
			direction = Character.DIRECTION_DOWN;
			if(character.isCenteredY()) {
				viewportYOffset = character.getSpeed();
				
				if(viewportY + viewportYOffset > limitBottom) {
					viewportYOffset = 0;
					characterVelocityY = character.getSpeed();
				}
			} else {
				characterVelocityY = character.getSpeed();
			}
		} else if(input.isKeyPressed(Keyboard.KEY_H)) {
			character.toggleDrawingHitbox();
			this.isHitboxDisplayed = !isHitboxDisplayed;
		}
		
		if(direction != -1) {
			// Character is colliding ?
			boolean collide = false;
			Iterator<Shape> it = gameMap.getCollisionShapes().iterator();
			while(it.hasNext()) {
				Shape s = it.next();
				Shape nextS = new Rectangle(s.getX()-viewportXOffset, s.getY()-viewportYOffset, s.getWidth(), s.getHeight());
				collide = nextS.intersects(character.getNextMoveHitbox(direction, characterVelocityX, characterVelocityY));
				if(collide)
					break;
			}
			
			// Updating character move if no collision
			if(!collide) {
				// Updating viewport
				viewportX += viewportXOffset;
				viewportY += viewportYOffset;
				
				character.newMoveCharacter(direction, delta, characterVelocityX, characterVelocityY);
				
				// Updating hitbox coordinates depending on viewport
				it = gameMap.getCollisionShapes().iterator();
				while(it.hasNext()) {
					Shape s = it.next();
					s.setX(s.getX()-viewportXOffset);
					s.setY(s.getY()-viewportYOffset);
				}
			} else {
				character.newMoveCharacter(direction, delta, 0, 0);
			}
		}	
	}

	@Override
	public int getID() {
		return Constant.ID_GAME_STATE;
	}
}

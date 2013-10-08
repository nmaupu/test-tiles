package org.maupu.tiledtest.movingCharacter;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame;

public class Character {
	private SpriteSheet spriteSheet;
	private Rectangle bounds = new Rectangle(0, 0, 32, 32);
	private Animation characterRight;
	private Animation characterLeft;
	private Animation characterUp;
	private Animation characterDown;
	private static final int DIR_U = Keyboard.KEY_UP;
	private static final int DIR_D = Keyboard.KEY_DOWN;
	private static final int DIR_L = Keyboard.KEY_LEFT;
	private static final int DIR_R = Keyboard.KEY_RIGHT;
	private int direction = DIR_R;
	private float x, y;
	private float speed = 2.5f;
	private static final int FRAME_DURATION = 150;
	private long lastTick = 0;
	private int currentFrame = 0;

	public Character() {
		try {
			spriteSheet = new SpriteSheet("sprites/characters.png", 32, 32);
			characterRight = new Animation();
			characterLeft = new Animation();
			characterUp = new Animation();
			characterDown = new Animation();
			for(int i=0; i<3; i++) {
				characterUp.addFrame(spriteSheet.getSprite(i, 3), 150);
				characterDown.addFrame(spriteSheet.getSprite(i, 0), 150);
				characterLeft.addFrame(spriteSheet.getSprite(i, 1), 150);
				characterRight.addFrame(spriteSheet.getSprite(i, 2), 150);
				characterUp.setAutoUpdate(false);
				characterDown.setAutoUpdate(false);
				characterLeft.setAutoUpdate(false);
				characterRight.setAutoUpdate(false);
			}

			x = 150f;
			y = 150f;

			bounds.setLocation(x, y);

		} catch(SlickException se) {
			se.printStackTrace();
		}
	}

	public void render(Graphics g) {
		switch(direction) {
		case DIR_R:
			characterRight.draw(x, y);
			break;
		case DIR_L:
			characterLeft.draw(x, y);
			break;
		case DIR_U:
			characterUp.draw(x, y);
			break;
		case DIR_D:
			characterDown.draw(x, y);
			break;
		}
		
		bounds.setLocation(x, y);
	}

	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		Input input = container.getInput();
		
		if(lastTick == 0)
			lastTick = System.currentTimeMillis();
		
		boolean isMoving = false;
		
		if(input.isKeyDown(Keyboard.KEY_RIGHT)) {
			isMoving = true;
			if(direction != DIR_R) {
				currentFrame = 0;
				direction = DIR_R;
			}
			
			x += this.speed;
		} else if(input.isKeyDown(Keyboard.KEY_LEFT)) {
			isMoving = true;
			if(direction != DIR_L) {
				currentFrame = 0;
				direction = DIR_L;
			}
			
			x -= this.speed;
		} else if(input.isKeyDown(Keyboard.KEY_UP)) {
			isMoving = true;
			if(direction != DIR_U) {
				currentFrame = 0;
				direction = DIR_U;
			}
			
			y -= this.speed;
		} else if(input.isKeyDown(Keyboard.KEY_DOWN)) {
			isMoving = true;
			if(direction != DIR_D) {
				currentFrame = 0;
				direction = DIR_D;
			}
			
			y += this.speed;
		}
		
		if(isMoving && System.currentTimeMillis()-lastTick > FRAME_DURATION) {
			lastTick = 0;
			currentFrame = (currentFrame+1)%3;
			characterUp.setCurrentFrame(currentFrame);
			characterDown.setCurrentFrame(currentFrame);
			characterLeft.setCurrentFrame(currentFrame);
			characterRight.setCurrentFrame(currentFrame);	
		}
	}
}

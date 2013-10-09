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
	private Animation currentAnimation;
	private float x, y;
	private float speed = 2.5f;
	private static final int CHARACTER_ANIMATION_FRAME_DURATION = 150;

	public Character() {
		try {
			spriteSheet = new SpriteSheet("sprites/characters.png", 32, 32);
			characterRight = new Animation();
			characterLeft = new Animation();
			characterUp = new Animation();
			characterDown = new Animation();
			for(int i=0; i<3; i++) {
				characterUp.addFrame(spriteSheet.getSprite(i, 3), CHARACTER_ANIMATION_FRAME_DURATION);
				characterDown.addFrame(spriteSheet.getSprite(i, 0), CHARACTER_ANIMATION_FRAME_DURATION);
				characterLeft.addFrame(spriteSheet.getSprite(i, 1), CHARACTER_ANIMATION_FRAME_DURATION);
				characterRight.addFrame(spriteSheet.getSprite(i, 2), CHARACTER_ANIMATION_FRAME_DURATION);
				characterUp.setAutoUpdate(false);
				characterDown.setAutoUpdate(false);
				characterLeft.setAutoUpdate(false);
				characterRight.setAutoUpdate(false);
			}

			x = 150f;
			y = 150f;

			currentAnimation = characterRight;
			bounds.setLocation(x, y);

		} catch(SlickException se) {
			se.printStackTrace();
		}
	}

	public void render(Graphics g) {
		currentAnimation.draw(x, y);
		bounds.setLocation(x, y);
	}

	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		Input input = container.getInput();
		boolean isMoving = false;
		
		if(input.isKeyDown(Keyboard.KEY_RIGHT)) {
			currentAnimation = characterRight;
			isMoving = true;
			x += this.speed;
		} else if(input.isKeyDown(Keyboard.KEY_LEFT)) {
			currentAnimation = characterLeft;
			isMoving = true;
			x -= this.speed;
		} else if(input.isKeyDown(Keyboard.KEY_UP)) {
			currentAnimation = characterUp;
			isMoving = true;
			y -= this.speed;
		} else if(input.isKeyDown(Keyboard.KEY_DOWN)) {
			currentAnimation = characterDown;
			isMoving = true;
			y += this.speed;
		}
		
		if(isMoving) {
			// Delta is the number of ms since the last update
			currentAnimation.update(delta);
		}
	}
}

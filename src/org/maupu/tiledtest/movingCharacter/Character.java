package org.maupu.tiledtest.movingCharacter;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

public class Character {
	public final static int DIRECTION_UP=0;
	public final static int DIRECTION_DOWN=1;
	public final static int DIRECTION_LEFT=2;
	public final static int DIRECTION_RIGHT=3;
	private SpriteSheet spriteSheet;
	private int offsetHitboxX=4;
	private int offsetHitboxY=20;
	private Shape hitbox = new Rectangle(offsetHitboxX, offsetHitboxY, 24, 12);
	private Animation characterRight;
	private Animation characterLeft;
	private Animation characterUp;
	private Animation characterDown;
	private Animation currentAnimation;
	private float x, y;
	private float speed = 2.5f;
	private static final int[] CHARACTER_ANIM_DURATION = new int[] {150,150,150,150};
	private boolean isHitboxDisplayed=false;

	public Character() {
		try {
			spriteSheet = new SpriteSheet("sprites/characters.png", 32, 32);
			characterRight = new Animation(false);
			characterLeft = new Animation(false);
			characterUp = new Animation(false);
			characterDown = new Animation(false);
			for(int i=0; i<3; i++) {
				characterUp.addFrame(spriteSheet.getSprite(i, 3), CHARACTER_ANIM_DURATION[i]);
				characterDown.addFrame(spriteSheet.getSprite(i, 0), CHARACTER_ANIM_DURATION[i]);
				characterLeft.addFrame(spriteSheet.getSprite(i, 1), CHARACTER_ANIM_DURATION[i]);
				characterRight.addFrame(spriteSheet.getSprite(i, 2), CHARACTER_ANIM_DURATION[i]);
			}

			currentAnimation = characterRight;
			
			x = ConfigValues.RESOLUTION_WIDTH / 2 - currentAnimation.getWidth() / 2;
			y = ConfigValues.RESOLUTION_HEIGHT / 2 - currentAnimation.getHeight() / 2;
		} catch(SlickException se) {
			se.printStackTrace();
		}
	}

	public void render(Graphics g) {
		currentAnimation.draw(x, y);
		hitbox.setLocation(this.x+offsetHitboxX, this.y+offsetHitboxY);
		
		if(isHitboxDisplayed)
			g.draw(hitbox);
	}
	
	public void moveCharacter(int direction, int delta, boolean effectiveMove) {
		if(effectiveMove) {
			//Shape s = getNextMoveHitbox(direction);
			Point p = getNextMove(direction, x, y);

			this.x = p.getX();
			this.y = p.getY();
			
			// update character animation frame if needed
			currentAnimation.update(delta);	
		}
		
		setCurrentAnimation(direction);
	}
	
	public void newMoveCharacter(int direction, int delta, float velocityX, float velocityY) {
		this.x += velocityX;
		this.y += velocityY;
		
		currentAnimation.update(delta);
		setCurrentAnimation(direction);
	}
	
	private void setCurrentAnimation(int direction) {
		switch(direction) {
		case DIRECTION_UP:
			currentAnimation=characterUp;
			break;
		case DIRECTION_DOWN:
			currentAnimation=characterDown;
			break;
		case DIRECTION_LEFT:
			currentAnimation=characterLeft;
			break;
		case DIRECTION_RIGHT:
			currentAnimation=characterRight;
			break;
		}
	}
	
	public Shape getNextMoveHitbox(int direction) {
		Point p = getNextMove(direction, hitbox.getX(), hitbox.getY());
		return new Rectangle(p.getX(), p.getY(), hitbox.getWidth(), hitbox.getHeight());
	}
	
	public Shape getNextMoveHitbox(int direction, float velocityX, float velocityY) {
		Point p = getNextMove(direction, hitbox.getX()+velocityX, hitbox.getY()+velocityY);
		return new Rectangle(p.getX()+velocityX, p.getY()+velocityY, hitbox.getWidth(), hitbox.getHeight());
	}
	
	private Point getNextMove(int direction, float cx, float cy) {
		Point p = new Point(cx, cy);
		
		switch(direction) {
		case DIRECTION_UP:
			p.setY(cy-this.speed);
			break;
		case DIRECTION_DOWN:
			p.setY(cy+this.speed);
			break;
		case DIRECTION_LEFT:
			p.setX(cx-this.speed);
			break;
		case DIRECTION_RIGHT:
			p.setX(cx+this.speed);
			break;
		}
		
		return p;
	}
	
	public Shape getHitbox() {
		return hitbox;
	}
	
	public void toggleDrawingHitbox() {
		isHitboxDisplayed = !isHitboxDisplayed;
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public float getSpeed() {
		return speed;
	}
	
	public boolean isCenteredX() {
		return 	x == ConfigValues.RESOLUTION_WIDTH / 2 - currentAnimation.getWidth() / 2; 
	}
	
	public boolean isCenteredY() {
		return y == ConfigValues.RESOLUTION_HEIGHT / 2 - currentAnimation.getHeight() / 2;
	}
}

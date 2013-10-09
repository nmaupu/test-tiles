package org.maupu.tiledtest.movingCharacter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.tiled.TiledMap;

public class GameMap extends TiledMap {
	private final List<Shape> collisionShapes = new ArrayList<Shape>();

	public GameMap(String mapFile) throws SlickException {
		super(mapFile);

		final int objectGroup=0;
		for(int i=0; i<super.getObjectCount(objectGroup); i++) {
			Rectangle rect = new Rectangle(
					super.getObjectX(objectGroup, i),
					super.getObjectY(objectGroup, i),
					super.getObjectWidth(objectGroup, i),
					super.getObjectHeight(objectGroup, i));
			collisionShapes.add(rect);
		}
	}
	
	public List<Shape> getCollisionShapes() {
		return collisionShapes;
	}
	
	public boolean isColliding(Shape s) {
		Iterator<Shape> it = collisionShapes.iterator();
		while(it.hasNext()) {
			if(it.next().intersects(s))
				return true;
		}
		
		return false;
	}
}

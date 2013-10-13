package org.maupu.tiledtest.movingCharacter;

public abstract class ConfigValues {
	public static final int MAP_TILE_SIZE=32; 
	public static final String MAP_LAYER_BACKGROUND="background";
	public static final String MAP_LAYER_FOREGROUND="foreground";
	public static final String MAP_LAYER_TOP="top";
	// Useless without TiledMapPlus class
	public static final String MAP_LAYER_COLLISION="collision";
	
	public static final int ID_NO_STATE=-1;
	public static final int ID_GAME_STATE=0;
	
	public static final String MAP_FILE="tiles/test-tiles.tmx";
	
	public static final int viewportx = 0;
	public static final int viewporty = 0;
	
	// Resolution must be a multiplier of MAP_TILE_SIZE
	public static final int RESOLUTION_WIDTH = 416;
	public static final int RESOLUTION_HEIGHT = 320;
}

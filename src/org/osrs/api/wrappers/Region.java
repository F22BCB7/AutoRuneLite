package org.osrs.api.wrappers;

public interface Region{
	public org.osrs.api.wrappers.Tile[][][] tiles();
	public int entityCount();
	public int[][][] tileHeights();
	public int minPlane();
	public int maxPlane();
	public int maxY();
	public int maxX();
	public InteractableObject[] objects();
	public int[][][] tileCycles();
	public int[][] tileRotations();
	public int[][] tileMasks();
	public org.osrs.api.wrappers.Tile getHoveringTile();
	public int invoke_getObjectFlags(int plane, int localX, int localY, long hash);
}
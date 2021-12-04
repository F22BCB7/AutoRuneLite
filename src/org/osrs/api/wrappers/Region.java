package org.osrs.api.wrappers;

public interface Region{
	public int maxPlane();
	public org.osrs.api.wrappers.Tile[][][] tiles();
	public int entityCount();
	public int[][][] tileHeights();
	public int maxY();
	public int minPlane();
	public InteractableObject[] objects();
	public int[][][] tileCycles();
	public int[][] tileRotations();
	public int maxX();
	public int[][] tileMasks();
	public org.osrs.api.wrappers.Tile getHoveringTile();
	public void setHoveringTile(org.osrs.api.wrappers.Tile tile);
}
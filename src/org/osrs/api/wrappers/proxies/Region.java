package org.osrs.api.wrappers.proxies;

import org.osrs.injection.bytescript.BClass;
import org.osrs.injection.bytescript.BField;
import org.osrs.injection.bytescript.BGetter;

@BClass(name="Region")
public class Region implements org.osrs.api.wrappers.Region{
	@BField
	public int maxPlane;
	@BGetter
	@Override
	public int maxPlane(){return maxPlane;}
	@BField
	public org.osrs.api.wrappers.Tile[][][] tiles;
	@BGetter
	@Override
	public org.osrs.api.wrappers.Tile[][][] tiles(){return tiles;}
	@BField
	public int entityCount;
	@BGetter
	@Override
	public int entityCount(){return entityCount;}
	@BField
	public int[][][] tileHeights;
	@BGetter
	@Override
	public int[][][] tileHeights(){return tileHeights;}
	@BField
	public int maxY;
	@BGetter
	@Override
	public int maxY(){return maxY;}
	@BField
	public int minPlane;
	@BGetter
	@Override
	public int minPlane(){return minPlane;}
	@BField
	public org.osrs.api.wrappers.InteractableObject[] objects;
	@BGetter
	@Override
	public org.osrs.api.wrappers.InteractableObject[] objects(){return objects;}
	@BField
	public int[][][] tileCycles;
	@BGetter
	@Override
	public int[][][] tileCycles(){return tileCycles;}
	@BField
	public int[][] tileRotations;
	@BGetter
	@Override
	public int[][] tileRotations(){return tileRotations;}
	@BField
	public int maxX;
	@BGetter
	@Override
	public int maxX(){return maxX;}
	@BField
	public int[][] tileMasks;
	@BGetter
	@Override
	public int[][] tileMasks(){return tileMasks;}
}
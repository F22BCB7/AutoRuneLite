package org.osrs.api.wrappers.proxies;

import org.osrs.injection.bytescript.BClass;
import org.osrs.injection.bytescript.BDetour;
import org.osrs.injection.bytescript.BField;
import org.osrs.injection.bytescript.BFunction;
import org.osrs.injection.bytescript.BGetter;
import org.osrs.injection.bytescript.BGetterDetour;
import org.osrs.injection.bytescript.BMethod;
import org.osrs.injection.bytescript.BSetterDetour;
import org.osrs.injection.bytescript.BVar;

@BClass(name="Region")
public class Region implements org.osrs.api.wrappers.Region{
	@BVar
	public Tile hoveringTile;
	@BFunction
	@Override
	public org.osrs.api.wrappers.Tile getHoveringTile(){
		return hoveringTile;
	}
	@BFunction
	public void setHoveringTile(Tile tile){
		hoveringTile = tile;
	}
	@BMethod(name="drawRegion")
	public void _drawRegion(int a, int b, int c, int d, int e, int f){}
	@BDetour
	public void drawRegion(int camX, int camZ, int camY, int camPitch, int camYaw, int plane){
		_drawRegion(camX, camZ, camY, camPitch, camYaw, plane);
	}
	@BMethod(name="addTile")
	public void _addTile(int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9, int var10, int var11, int var12, int var13, int var14, int var15, int var16, int var17, int var18, int var19, int var20){}
	@BDetour
	public void addTile(int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9, int var10, int var11, int var12, int var13, int var14, int var15, int var16, int var17, int var18, int var19, int var20){
		_addTile(var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11, var12, var13, var14, var15, var16, var17, var18, var19, var20);
	}
	@BMethod(name="drawTile")
	public void _drawTile(Tile var1, boolean var2){}
	@BDetour
	public void drawTile(Tile var1, boolean var2){
		_drawTile(var1, var2);
	}
	
	@BField
	public int maxPlane;
	@BGetter
	@Override
	public int maxPlane(){return maxPlane;}
	@BField
	public Tile[][][] tiles;
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
	public InteractableObject[] objects;
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
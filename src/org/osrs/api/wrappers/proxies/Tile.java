package org.osrs.api.wrappers.proxies;

import org.osrs.injection.bytescript.BClass;
import org.osrs.injection.bytescript.BDetour;
import org.osrs.injection.bytescript.BField;
import org.osrs.injection.bytescript.BMethod;
import org.osrs.injection.bytescript.BGetter;
import org.osrs.injection.bytescript.BVar;
import java.util.HashMap;
import java.util.Map;

@BClass(name="Tile")
public class Tile extends Node implements org.osrs.api.wrappers.Tile{

	@BField
	public int physicalPlane;
	@BGetter
	@Override
	public int physicalPlane(){return physicalPlane;}
	@BField
	public int x;
	@BGetter
	@Override
	public int x(){return x;}
	@BField
	public int y;
	@BGetter
	@Override
	public int y(){return y;}
	@BField
	public int renderPlane;
	@BGetter
	@Override
	public int renderPlane(){return renderPlane;}
	@BField
	public org.osrs.api.wrappers.TilePaint paint;
	@BGetter
	@Override
	public org.osrs.api.wrappers.TilePaint paint(){return paint;}
	@BField
	public org.osrs.api.wrappers.ItemLayer itemLayer;
	@BGetter
	@Override
	public org.osrs.api.wrappers.ItemLayer itemLayer(){return itemLayer;}
	@BField
	public org.osrs.api.wrappers.BoundaryObject boundary;
	@BGetter
	@Override
	public org.osrs.api.wrappers.BoundaryObject boundary(){return boundary;}
	@BField
	public org.osrs.api.wrappers.WallDecoration wall;
	@BGetter
	@Override
	public org.osrs.api.wrappers.WallDecoration wall(){return wall;}
	@BField
	public boolean visible;
	@BGetter
	@Override
	public boolean visible(){return visible;}
	@BField
	public org.osrs.api.wrappers.TileModel overlay;
	@BGetter
	@Override
	public org.osrs.api.wrappers.TileModel overlay(){return overlay;}
	@BField
	public int entityCount;
	@BGetter
	@Override
	public int entityCount(){return entityCount;}
	@BField
	public org.osrs.api.wrappers.InteractableObject[] objects;
	@BGetter
	@Override
	public org.osrs.api.wrappers.InteractableObject[] objects(){return objects;}
	@BField
	public int[] entityFlags;
	@BGetter
	@Override
	public int[] entityFlags(){return entityFlags;}
	@BField
	public int flags;
	@BGetter
	@Override
	public int flags(){return flags;}
	@BField
	public boolean draw;
	@BGetter
	@Override
	public boolean draw(){return draw;}
	@BField
	public boolean drawEntities;
	@BGetter
	@Override
	public boolean drawEntities(){return drawEntities;}
	@BField
	public int wallCullDirection;
	@BGetter
	@Override
	public int wallCullDirection(){return wallCullDirection;}
	@BField
	public int plane;
	@BGetter
	@Override
	public int plane(){return plane;}
	@BField
	public int wallDrawFlag;
	@BGetter
	@Override
	public int wallDrawFlag(){return wallDrawFlag;}
	@BField
	public int wallUncullDirection;
	@BGetter
	@Override
	public int wallUncullDirection(){return wallUncullDirection;}
	@BField
	public int wallCullOppositeDirection;
	@BGetter
	@Override
	public int wallCullOppositeDirection(){return wallCullOppositeDirection;}
	@BField
	public org.osrs.api.wrappers.FloorDecoration floor;
	@BGetter
	@Override
	public org.osrs.api.wrappers.FloorDecoration floor(){return floor;}
	@BField
	public org.osrs.api.wrappers.Tile bridge;
	@BGetter
	@Override
	public org.osrs.api.wrappers.Tile bridge(){return bridge;}
}
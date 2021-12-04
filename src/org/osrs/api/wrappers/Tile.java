package org.osrs.api.wrappers;

import java.awt.Point;
import java.awt.Polygon;

import org.osrs.api.objects.RSModel;
import org.osrs.api.objects.RSTile;

public interface Tile extends Node{
	public int physicalPlane();
	public int x();
	public int y();
	public int renderPlane();
	public TilePaint paint();
	public ItemLayer itemLayer();
	public BoundaryObject boundary();
	public WallDecoration wall();
	public boolean visible();
	public TileModel overlay();
	public int entityCount();
	public InteractableObject[] objects();
	public int[] entityFlags();
	public int flags();
	public boolean draw();
	public boolean drawEntities();
	public int wallCullDirection();
	public int plane();
	public int wallDrawFlag();
	public int wallUncullDirection();
	public int wallCullOppositeDirection();
	public FloorDecoration floor();
	public Tile bridge();
	
	//Custom model st00f
	public void createBoundsHeights();
	public Point getCenterPoint();
	public Polygon getBounds();
	public Point[] projectVertices();
	public Polygon[] getWireframe();
	public boolean isHovering();
}
package org.osrs.api.wrappers;

public interface WallDecoration{
	public long hash();
	public int renderInfo();
	public int x();
	public int y();
	public int height();
	public RenderableNode model();
	public RenderableNode secondaryModel();
	public int orientation();
	public int secondaryOrientation();
	public int insetX();
	public int insetY();
}
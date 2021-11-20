package org.osrs.api.wrappers;

public interface BoundaryObject{
	public long id();
	public int renderInfo();
	public int x();
	public int y();
	public int height();
	public RenderableNode model();
	public RenderableNode secondaryModel();
	public int orientation();
	public int secondaryOrientation();
	
}
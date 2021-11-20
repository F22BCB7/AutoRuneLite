package org.osrs.api.wrappers;

public interface FloorDecoration{
	public RenderableNode model();
	public int x();
	public int y();
	public int height();
	public long hash();
	public int renderInfo();
}
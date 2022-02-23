package org.osrs.api.wrappers;

import org.osrs.api.objects.RSModel;

public interface ItemLayer{
	public RenderableNode bottom();
	public int x();
	public int y();
	public int tileHeight();
	public long hash();
	public RenderableNode middle();
	public RenderableNode top();
	public int height();
	
	public RSModel getModel();
}
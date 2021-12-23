package org.osrs.api.wrappers;

import org.osrs.api.objects.RSModel;

public interface RenderableNode extends EntityNode{
	public int height();

	public RSModel getCachedModel();
}
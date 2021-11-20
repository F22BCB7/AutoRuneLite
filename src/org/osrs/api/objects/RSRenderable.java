package org.osrs.api.objects;

import org.osrs.api.wrappers.Client;
import org.osrs.util.Data;

public abstract class RSRenderable extends Interactable{
	public abstract org.osrs.api.wrappers.RenderableNode getAccessor();
	public RSRenderable(){
		methods = ((Client)Data.clientInstance).getMethodContext();
	}
	public int getHeight(){
		return getAccessor().height();
	}
	@Override
	public boolean equals(Object o){
		if(o!=null && o instanceof RSRenderable){
			if(this.getAccessor().entityUID()==((RSRenderable)o).getAccessor().entityUID())
				return true;
		}
		return false;
	}
}

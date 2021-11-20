package org.osrs.api.wrappers.proxies;

import org.osrs.injection.bytescript.BClass;
import org.osrs.injection.bytescript.BDetour;
import org.osrs.injection.bytescript.BField;
import org.osrs.injection.bytescript.BFunction;
import org.osrs.injection.bytescript.BMethod;
import org.osrs.injection.bytescript.BGetter;
import org.osrs.injection.bytescript.BVar;
import java.util.HashMap;
import java.util.Map;

@BClass(name="InteractableObject")
public class InteractableObject implements org.osrs.api.wrappers.InteractableObject{

	@BField
	public int cycle;
	@BGetter
	@Override
	public int cycle(){return cycle;}
	@BField
	public int priority;
	@BGetter
	@Override
	public int priority(){return priority;}
	@BField
	public long hash;
	@BGetter
	@Override
	public long hash(){return hash;}
	@BField
	public int renderInfo;
	@BGetter
	@Override
	public int renderInfo(){return renderInfo;}
	@BField
	public int plane;
	@BGetter
	@Override
	public int plane(){return plane;}
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
	public int height;
	@BGetter
	@Override
	public int height(){return height;}
	@BField
	public org.osrs.api.wrappers.RenderableNode model;
	@BGetter
	@Override
	public org.osrs.api.wrappers.RenderableNode model(){return model;}
	@BField
	public int orientation;
	@BGetter
	@Override
	public int orientation(){return orientation;}
	@BField
	public int relativeX;
	@BGetter
	@Override
	public int relativeX(){return relativeX;}
	@BField
	public int relativeY;
	@BGetter
	@Override
	public int relativeY(){return relativeY;}
	@BField
	public int offsetX;
	@BGetter
	@Override
	public int offsetX(){return offsetX;}
	@BField
	public int offsetY;
	@BGetter
	@Override
	public int offsetY(){return offsetY;}
	
}
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

@BClass(name="WallDecoration")
public class WallDecoration implements org.osrs.api.wrappers.WallDecoration{

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
	public RenderableNode model;
	@BGetter
	@Override
	public org.osrs.api.wrappers.RenderableNode model(){return model;}
	@BField
	public RenderableNode secondaryModel;
	@BGetter
	@Override
	public org.osrs.api.wrappers.RenderableNode secondaryModel(){return secondaryModel;}
	@BField
	public int orientation;
	@BGetter
	@Override
	public int orientation(){return orientation;}
	@BField
	public int secondaryOrientation;
	@BGetter
	@Override
	public int secondaryOrientation(){return secondaryOrientation;}
	@BField
	public int insetX;
	@BGetter
	@Override
	public int insetX(){return insetX;}
	@BField
	public int insetY;
	@BGetter
	@Override
	public int insetY(){return insetY;}
	
}
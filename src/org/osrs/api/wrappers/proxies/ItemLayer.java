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

@BClass(name="ItemLayer")
public class ItemLayer implements org.osrs.api.wrappers.ItemLayer{

	@BField
	public org.osrs.api.wrappers.RenderableNode bottom;
	@BGetter
	@Override
	public org.osrs.api.wrappers.RenderableNode bottom(){return bottom;}
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
	public int tileHeight;
	@BGetter
	@Override
	public int tileHeight(){return tileHeight;}
	@BField
	public long hash;
	@BGetter
	@Override
	public long hash(){return hash;}
	@BField
	public org.osrs.api.wrappers.RenderableNode middle;
	@BGetter
	@Override
	public org.osrs.api.wrappers.RenderableNode middle(){return middle;}
	@BField
	public org.osrs.api.wrappers.RenderableNode top;
	@BGetter
	@Override
	public org.osrs.api.wrappers.RenderableNode top(){return top;}
	@BField
	public int height;
	@BGetter
	@Override
	public int height(){return height;}
	
}
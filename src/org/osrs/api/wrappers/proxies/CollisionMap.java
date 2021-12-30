package org.osrs.api.wrappers.proxies;

import org.osrs.injection.bytescript.BClass;
import org.osrs.injection.bytescript.BField;
import org.osrs.injection.bytescript.BGetter;

@BClass(name="CollisionMap")
public class CollisionMap implements org.osrs.api.wrappers.CollisionMap{
	@BField
	public int[][] flags;
	@BGetter
	@Override
	public int[][] flags(){return flags;}
	@BField
	public int widthOffset;
	@BGetter
	@Override
	public int widthOffset(){return widthOffset;}
	@BField
	public int heightOffset;
	@BGetter
	@Override
	public int heightOffset(){return heightOffset;}
	@BField
	public int width;
	@BGetter
	@Override
	public int width(){return width;}
	@BField
	public int height;
	@BGetter
	@Override
	public int height(){return height;}
}
package org.osrs.api.wrappers.proxies;

import org.osrs.injection.bytescript.BClass;
import org.osrs.injection.bytescript.BDetour;
import org.osrs.injection.bytescript.BField;
import org.osrs.injection.bytescript.BMethod;
import org.osrs.injection.bytescript.BGetter;
import org.osrs.injection.bytescript.BVar;
import java.util.HashMap;
import java.util.Map;

@BClass(name="TilePaint")
public class TilePaint implements org.osrs.api.wrappers.TilePaint{

	@BField
	public int swColor;
	@BGetter
	@Override
	public int swColor(){return swColor;}
	@BField
	public int seColor;
	@BGetter
	@Override
	public int seColor(){return seColor;}
	@BField
	public int neColor;
	@BGetter
	@Override
	public int neColor(){return neColor;}
	@BField
	public int nwColor;
	@BGetter
	@Override
	public int nwColor(){return nwColor;}
	@BField
	public int textureID;
	@BGetter
	@Override
	public int textureID(){return textureID;}
	@BField
	public int rgb;
	@BGetter
	@Override
	public int rgb(){return rgb;}
	@BField
	public boolean flat;
	@BGetter
	@Override
	public boolean flat(){return flat;}
}
package org.osrs.api.wrappers.proxies;

import org.osrs.injection.bytescript.BClass;
import org.osrs.injection.bytescript.BDetour;
import org.osrs.injection.bytescript.BField;
import org.osrs.injection.bytescript.BMethod;
import org.osrs.injection.bytescript.BGetter;
import org.osrs.injection.bytescript.BVar;
import java.util.HashMap;
import java.util.Map;

@BClass(name="ItemStorage")
public class ItemStorage extends Node implements org.osrs.api.wrappers.ItemStorage{

	@BField
	public int[] stackSizes;
	@BGetter
	@Override
	public int[] stackSizes(){return stackSizes;}
	@BField
	public int[] ids;
	@BGetter
	@Override
	public int[] ids(){return ids;}
}
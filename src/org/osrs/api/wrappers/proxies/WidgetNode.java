package org.osrs.api.wrappers.proxies;

import org.osrs.injection.bytescript.BClass;
import org.osrs.injection.bytescript.BDetour;
import org.osrs.injection.bytescript.BField;
import org.osrs.injection.bytescript.BMethod;
import org.osrs.injection.bytescript.BGetter;
import org.osrs.injection.bytescript.BVar;
import java.util.HashMap;
import java.util.Map;

@BClass(name="WidgetNode")
public class WidgetNode extends Node implements org.osrs.api.wrappers.WidgetNode{

	@BField
	public int ownerID;
	@BGetter
	@Override
	public int ownerID(){return ownerID;}
	@BField
	public boolean root;
	@BGetter
	@Override
	public boolean root(){return root;}
	@BField
	public int id;
	@BGetter
	@Override
	public int id(){return id;}
}
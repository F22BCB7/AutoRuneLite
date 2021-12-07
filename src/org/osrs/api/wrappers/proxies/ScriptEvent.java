package org.osrs.api.wrappers.proxies;

import org.osrs.injection.bytescript.BClass;
import org.osrs.injection.bytescript.BDetour;
import org.osrs.injection.bytescript.BField;
import org.osrs.injection.bytescript.BMethod;
import org.osrs.injection.bytescript.BGetter;
import org.osrs.injection.bytescript.BVar;
import java.util.HashMap;
import java.util.Map;

@BClass(name="ScriptEvent")
public class ScriptEvent extends Node implements org.osrs.api.wrappers.ScriptEvent{

	@BField
	public int eventIndex;
	@BGetter
	@Override
	public int eventIndex(){return eventIndex;}
	@BField
	public int mouseY;
	@BGetter
	@Override
	public int mouseY(){return mouseY;}
	@BField
	public int typedKeyChar;
	@BGetter
	@Override
	public int typedKeyChar(){return typedKeyChar;}
	@BField
	public int type;
	@BGetter
	@Override
	public int type(){return type;}
	@BField
	public int op;
	@BGetter
	@Override
	public int op(){return op;}
	@BField
	public org.osrs.api.wrappers.Widget target;
	@BGetter
	@Override
	public org.osrs.api.wrappers.Widget target(){return target;}
	@BField
	public int mouseX;
	@BGetter
	@Override
	public int mouseX(){return mouseX;}
	@BField
	public java.lang.Object[] args;
	@BGetter
	@Override
	public java.lang.Object[] args(){return args;}
	@BField
	public String opbase;
	@BGetter
	@Override
	public String opbase(){return opbase;}
	@BField
	public org.osrs.api.wrappers.Widget source;
	@BGetter
	@Override
	public org.osrs.api.wrappers.Widget source(){return source;}
	@BField
	public int typedKeyCode;
	@BGetter
	@Override
	public int typedKeyCode(){return typedKeyCode;}
	@BField
	public boolean consumable;
	@BGetter
	@Override
	public boolean consumable(){return consumable;}
}
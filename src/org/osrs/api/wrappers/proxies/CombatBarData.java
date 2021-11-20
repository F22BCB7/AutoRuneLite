package org.osrs.api.wrappers.proxies;

import org.osrs.injection.bytescript.BClass;
import org.osrs.injection.bytescript.BDetour;
import org.osrs.injection.bytescript.BField;
import org.osrs.injection.bytescript.BMethod;
import org.osrs.injection.bytescript.BGetter;
import org.osrs.injection.bytescript.BVar;
import java.util.HashMap;
import java.util.Map;

@BClass(name="CombatBarData")
public class CombatBarData extends Node implements org.osrs.api.wrappers.CombatBarData{

	@BField
	public int cycle;
	@BGetter
	@Override
	public int cycle(){return cycle;}
	@BField
	public int healthRatio;
	@BGetter
	@Override
	public int healthRatio(){return healthRatio;}
	@BField
	public int health;
	@BGetter
	@Override
	public int health(){return health;}
	@BField
	public int cycleLength;
	@BGetter
	@Override
	public int cycleLength(){return cycleLength;}
}
package org.osrs.api.wrappers.proxies;

import org.osrs.injection.bytescript.BClass;
import org.osrs.injection.bytescript.BDetour;
import org.osrs.injection.bytescript.BField;
import org.osrs.injection.bytescript.BMethod;
import org.osrs.injection.bytescript.BGetter;
import org.osrs.injection.bytescript.BVar;
import java.util.HashMap;
import java.util.Map;

@BClass(name="CombatBarDefinition")
public class CombatBarDefinition extends EntityNode implements org.osrs.api.wrappers.CombatBarDefinition{

	@BField
	public int overlaySpriteID;
	@BGetter
	@Override
	public int overlaySpriteID(){return overlaySpriteID;}
	@BField
	public int underlaySpriteID;
	@BGetter
	@Override
	public int underlaySpriteID(){return underlaySpriteID;}
	@BField
	public int maxHealth;
	@BGetter
	@Override
	public int maxHealth(){return maxHealth;}
}
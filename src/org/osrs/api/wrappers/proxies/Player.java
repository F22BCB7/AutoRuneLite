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

@BClass(name="Player")
public class Player extends Actor implements org.osrs.api.wrappers.Player{

	@BField
	public int minX;
	@BGetter
	@Override
	public int minX(){return minX;}
	@BField
	public org.osrs.api.wrappers.PlayerDefinition definition;
	@BGetter
	@Override
	public org.osrs.api.wrappers.PlayerDefinition definition(){return definition;}
	@BField
	public int skullIcon;
	@BGetter
	@Override
	public int skullIcon(){return skullIcon;}
	@BField
	public int overheadIcon;
	@BGetter
	@Override
	public int overheadIcon(){return overheadIcon;}
	@BField
	public org.osrs.api.wrappers.NameComposite nameComposite;
	@BGetter
	@Override
	public org.osrs.api.wrappers.NameComposite nameComposite(){return nameComposite;}
	@BField
	public int team;
	@BGetter
	@Override
	public int team(){return team;}
	@BField
	public int totalLevel;
	@BGetter
	@Override
	public int totalLevel(){return totalLevel;}
	@BField
	public int maxY;
	@BGetter
	@Override
	public int maxY(){return maxY;}
	@BField
	public int animationCycleEnd;
	@BGetter
	@Override
	public int animationCycleEnd(){return animationCycleEnd;}
	@BField
	public boolean updateMovement;
	@BGetter
	@Override
	public boolean updateMovement(){return updateMovement;}
	@BField
	public int regionZ;
	@BGetter
	@Override
	public int regionZ(){return regionZ;}
	@BField
	public String[] actions;
	@BGetter
	@Override
	public String[] actions(){return actions;}
	@BField
	public org.osrs.api.wrappers.Model model;
	@BGetter
	@Override
	public org.osrs.api.wrappers.Model model(){return model;}
	@BField
	public int animationCycleStart;
	@BGetter
	@Override
	public int animationCycleStart(){return animationCycleStart;}
	@BField
	public int minY;
	@BGetter
	@Override
	public int minY(){return minY;}
	@BField
	public int maxX;
	@BGetter
	@Override
	public int maxX(){return maxX;}
	@BField
	public int regionY;
	@BGetter
	@Override
	public int regionY(){return regionY;}
	@BField
	public int tileHeight;
	@BGetter
	@Override
	public int tileHeight(){return tileHeight;}
	@BField
	public int tileX;
	@BGetter
	@Override
	public int tileX(){return tileX;}
	@BField
	public boolean hidden;
	@BGetter
	@Override
	public boolean hidden(){return hidden;}
	@BField
	public int plane;
	@BGetter
	@Override
	public int plane(){return plane;}
	@BField
	public int playerID;
	@BGetter
	@Override
	public int playerID(){return playerID;}
	@BField
	public int regionX;
	@BGetter
	@Override
	public int regionX(){return regionX;}
	@BField
	public int combatLevel;
	@BGetter
	@Override
	public int combatLevel(){return combatLevel;}
	@BField
	public boolean lowDetail;
	@BGetter
	@Override
	public boolean lowDetail(){return lowDetail;}
	@BField
	public int tileY;
	@BGetter
	@Override
	public int tileY(){return tileY;}
}
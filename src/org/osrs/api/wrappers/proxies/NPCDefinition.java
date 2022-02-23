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

@BClass(name="NPCDefinition")
public class NPCDefinition extends EntityNode implements org.osrs.api.wrappers.NPCDefinition{

	@BField
	public int widthScale;
	@BGetter
	@Override
	public int widthScale(){return widthScale;}
	@BField
	public String name;
	@BGetter
	@Override
	public String name(){return name;}
	@BField
	public int heightScale;
	@BGetter
	@Override
	public int heightScale(){return heightScale;}
	@BField
	public boolean isVisible;
	@BGetter
	@Override
	public boolean isVisible(){return isVisible;}
	@BField
	public int ambient;
	@BGetter
	@Override
	public int ambient(){return ambient;}
	@BField
	public int contrast;
	@BGetter
	@Override
	public int contrast(){return contrast;}
	@BField
	public int headIcon;
	@BGetter
	@Override
	public int headIcon(){return headIcon;}
	@BField
	public int rotation;
	@BGetter
	@Override
	public int rotation(){return rotation;}
	@BField
	public short[] recolorToFind;
	@BGetter
	@Override
	public short[] recolorToFind(){return recolorToFind;}
	@BField
	public short[] recolorToReplace;
	@BGetter
	@Override
	public short[] recolorToReplace(){return recolorToReplace;}
	@BField
	public short[] textureToFind;
	@BGetter
	@Override
	public short[] textureToFind(){return textureToFind;}
	@BField
	public short[] textureToReplace;
	@BGetter
	@Override
	public short[] textureToReplace(){return textureToReplace;}
	@BField
	public boolean isClickable;
	@BGetter
	@Override
	public boolean isClickable(){return isClickable;}
	@BField
	public int size;
	@BGetter
	@Override
	public int size(){return size;}
	@BField
	public int standingAnimation;
	@BGetter
	@Override
	public int standingAnimation(){return standingAnimation;}
	@BField
	public boolean isMoving;
	@BGetter
	@Override
	public boolean isMoving(){return isMoving;}
	@BField
	public int standingTurnedAnimation;
	@BGetter
	@Override
	public int standingTurnedAnimation(){return standingTurnedAnimation;}
	@BField
	public boolean boundaryDimension;
	@BGetter
	@Override
	public boolean boundaryDimension(){return boundaryDimension;}
	@BField
	public int runAnimation;
	@BGetter
	@Override
	public int runAnimation(){return runAnimation;}
	@BField
	public int walkingAnimation;
	@BGetter
	@Override
	public int walkingAnimation(){return walkingAnimation;}
	@BField
	public int rotate180Animation;
	@BGetter
	@Override
	public int rotate180Animation(){return rotate180Animation;}
	@BField
	public int rotate90RightAnimation;
	@BGetter
	@Override
	public int rotate90RightAnimation(){return rotate90RightAnimation;}
	@BField
	public int rotate90LeftAnimation;
	@BGetter
	@Override
	public int rotate90LeftAnimation(){return rotate90LeftAnimation;}
	@BField
	public int varpID;
	@BGetter
	@Override
	public int varpID(){return varpID;}
	@BField
	public int configID;
	@BGetter
	@Override
	public int configID(){return configID;}
	@BField
	public FixedSizeDeque params;
	@BGetter
	@Override
	public org.osrs.api.wrappers.FixedSizeDeque params(){return params;}
	@BField
	public boolean isMinimapVisible;
	@BGetter
	@Override
	public boolean isMinimapVisible(){return isMinimapVisible;}
	@BField
	public int combatLevel;
	@BGetter
	@Override
	public int combatLevel(){return combatLevel;}
	@BField
	public int[] childrenIDs;
	@BGetter
	@Override
	public int[] childrenIDs(){return childrenIDs;}
	@BField
	public int[] npcModels;
	@BGetter
	@Override
	public int[] npcModels(){return npcModels;}
	@BField
	public int id;
	@BGetter
	@Override
	public int id(){return id;}
	@BField
	public String[] actions;
	@BGetter
	@Override
	public String[] actions(){return actions;}
	@BField
	public int[] additionalModels;
	@BGetter
	@Override
	public int[] additionalModels(){return additionalModels;}
	
	@BMethod(name="getChildDefinition")
	public NPCDefinition _getChildDefinition(int a){return null;}
	@BMethod(name="getChildDefinition")
	public NPCDefinition _getChildDefinition(byte a){return null;}
	@BMethod(name="getChildDefinition")
	public NPCDefinition _getChildDefinition(short a){return null;}
	@BFunction
	@Override
	public org.osrs.api.wrappers.NPCDefinition invoke_getChildDefinition(){
		org.osrs.api.wrappers.NPCDefinition def = null;
		Object predicate = Client.clientInstance.getMethodPredicate("NPCDefinition", "getChildDefinition", "(?)L*;", false);
		if(predicate instanceof Integer)
			def = _getChildDefinition((int)predicate);
		if(predicate instanceof Byte)
			def = _getChildDefinition((byte)predicate);
		if(predicate instanceof Short)
			def = _getChildDefinition((short)predicate);
		return def;
	}
}
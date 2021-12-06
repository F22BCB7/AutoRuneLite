package org.osrs.api.wrappers.proxies;

import org.osrs.api.objects.RSActor;
import org.osrs.api.objects.RSNpc;
import org.osrs.api.objects.RSPlayer;
import org.osrs.injection.bytescript.BClass;
import org.osrs.injection.bytescript.BDetour;
import org.osrs.injection.bytescript.BField;
import org.osrs.injection.bytescript.BFunction;
import org.osrs.injection.bytescript.BMethod;
import org.osrs.injection.bytescript.BGetter;
import org.osrs.injection.bytescript.BVar;
import org.osrs.util.Data;

import java.util.HashMap;
import java.util.Map;

@BClass(name="Actor")
public class Actor extends RenderableNode implements org.osrs.api.wrappers.Actor{

	@BField
	public boolean animationStretching;
	@BGetter
	@Override
	public boolean animationStretching(){return animationStretching;}
	@BField
	public int size;
	@BGetter
	@Override
	public int size(){return size;}
	@BField
	public int standAnimationID;
	@BGetter
	@Override
	public int standAnimationID(){return standAnimationID;}
	@BField
	public int standTurnedAnimationID;
	@BGetter
	@Override
	public int standTurnedAnimationID(){return standTurnedAnimationID;}
	@BField
	public int runAnimation;
	@BGetter
	@Override
	public int runAnimation(){return runAnimation;}
	@BField
	public int walkingAnimationID;
	@BGetter
	@Override
	public int walkingAnimationID(){return walkingAnimationID;}
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
	public int runAnimationID;
	@BGetter
	@Override
	public int runAnimationID(){return runAnimationID;}
	@BField
	public String overheadMessage;
	@BGetter
	@Override
	public String overheadMessage(){return overheadMessage;}
	@BField
	public boolean validRemotePlayer;
	@BGetter
	@Override
	public boolean validRemotePlayer(){return validRemotePlayer;}
	@BField
	public int overheadTextCyclesRemaining;
	@BGetter
	@Override
	public int overheadTextCyclesRemaining(){return overheadTextCyclesRemaining;}
	@BField
	public int regionBaseX;
	@BGetter
	@Override
	public int regionBaseX(){return regionBaseX;}
	@BField
	public int regionBaseY;
	@BGetter
	@Override
	public int regionBaseY(){return regionBaseY;}
	@BField
	public byte hitsplatCount;
	@BGetter
	@Override
	public byte hitsplatCount(){return hitsplatCount;}
	@BField
	public int[] hitsplatTypes;
	@BGetter
	@Override
	public int[] hitsplatTypes(){return hitsplatTypes;}
	@BField
	public int[] hitsplatDamages;
	@BGetter
	@Override
	public int[] hitsplatDamages(){return hitsplatDamages;}
	@BField
	public int[] hitsplatCycles;
	@BGetter
	@Override
	public int[] hitsplatCycles(){return hitsplatCycles;}
	@BField
	public int[] hitsplatTypes2;
	@BGetter
	@Override
	public int[] hitsplatTypes2(){return hitsplatTypes2;}
	@BField
	public int[] hitsplatDamages2;
	@BGetter
	@Override
	public int[] hitsplatDamages2(){return hitsplatDamages2;}
	@BField
	public org.osrs.api.wrappers.NodeList combatInfoList;
	@BGetter
	@Override
	public org.osrs.api.wrappers.NodeList combatInfoList(){return combatInfoList;}
	@BField
	public int interactingID;
	@BGetter
	@Override
	public int interactingID(){return interactingID;}
	@BField
	public boolean interacting;
	@BGetter
	@Override
	public boolean interacting(){return interacting;}
	@BField
	public int angleTurnTo;
	@BGetter
	@Override
	public int angleTurnTo(){return angleTurnTo;}
	@BField
	public int poseAnimation;
	@BGetter
	@Override
	public int poseAnimation(){return poseAnimation;}
	@BField
	public int poseFrame;
	@BGetter
	@Override
	public int poseFrame(){return poseFrame;}
	@BField
	public int poseFrameCycle;
	@BGetter
	@Override
	public int poseFrameCycle(){return poseFrameCycle;}
	@BField
	public int animationID;
	@BGetter
	@Override
	public int animationID(){return animationID;}
	@BField
	public int animationFrameCycle;
	@BGetter
	@Override
	public int animationFrameCycle(){return animationFrameCycle;}
	@BField
	public int animationFrame;
	@BGetter
	@Override
	public int animationFrame(){return animationFrame;}
	@BField
	public int animationDelay;
	@BGetter
	@Override
	public int animationDelay(){return animationDelay;}
	@BField
	public int animationLoops;
	@BGetter
	@Override
	public int animationLoops(){return animationLoops;}
	@BField
	public int graphicID;
	@BGetter
	@Override
	public int graphicID(){return graphicID;}
	@BField
	public int spotAnimFrame;
	@BGetter
	@Override
	public int spotAnimFrame(){return spotAnimFrame;}
	@BField
	public int spotAnimFrameCycle;
	@BGetter
	@Override
	public int spotAnimFrameCycle(){return spotAnimFrameCycle;}
	@BField
	public int npcCycle;
	@BGetter
	@Override
	public int npcCycle(){return npcCycle;}
	@BField
	public int logicalHeight;
	@BGetter
	@Override
	public int logicalHeight(){return logicalHeight;}
	@BField
	public int rotationOffset;
	@BGetter
	@Override
	public int rotationOffset(){return rotationOffset;}
	@BField
	public int rotation;
	@BGetter
	@Override
	public int rotation(){return rotation;}
	@BField
	public int movementSpeed;
	@BGetter
	@Override
	public int movementSpeed(){return movementSpeed;}
	@BField
	public int[] pathX;
	@BGetter
	@Override
	public int[] pathX(){return pathX;}
	@BField
	public int[] pathY;
	@BGetter
	@Override
	public int[] pathY(){return pathY;}
	@BField
	public byte[] pathTraversed;
	@BGetter
	@Override
	public byte[] pathTraversed(){return pathTraversed;}
	@BField
	public int currPathIndex;
	@BGetter
	@Override
	public int currPathIndex(){return currPathIndex;}
	@BField
	public int currPathLength;
	@BGetter
	@Override
	public int currPathLength(){return currPathLength;}
	@BField
	public int worldX;
	@BGetter
	@Override
	public int worldX(){return worldX;}
	@BField
	public int worldY;
	@BGetter
	@Override
	public int worldY(){return worldY;}
	@BField
	public int worldX2;
	@BGetter
	@Override
	public int worldX2(){return worldX2;}
	@BField
	public int worldY2;
	@BGetter
	@Override
	public int worldY2(){return worldY2;}
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
	public int orientation;
	@BGetter
	@Override
	public int orientation(){return orientation;}
	@BField
	public int angle;
	@BGetter
	@Override
	public int angle(){return angle;}
	@BField
	public int orientation2;
	@BGetter
	@Override
	public int orientation2(){return orientation2;}
	@BField
	public int endCycle;
	@BGetter
	@Override
	public int endCycle(){return endCycle;}
	@BField
	public int startCycle;
	@BGetter
	@Override
	public int startCycle(){return startCycle;}
	@BField
	public int graphicsDelay;
	@BGetter
	@Override
	public int graphicsDelay(){return graphicsDelay;}
	@BField
	public int cycle;
	@BGetter
	@Override
	public int cycle(){return cycle;}
	@BField
	public boolean rights;
	@BGetter
	@Override
	public boolean rights(){return rights;}
	@BField
	public int heightOffset;
	@BGetter
	@Override
	public int heightOffset(){return heightOffset;}
	@BField
	public int overridingCombatLevel;
	@BGetter
	@Override
	public int overridingCombatLevel(){return overridingCombatLevel;}
	
	@BVar
	public org.osrs.api.objects.RSModel cachedModel;
	@BFunction
	@Override
	public org.osrs.api.objects.RSModel getCachedModel(){
		return cachedModel;
	}
	@BFunction
	@Override
	public void setCachedModel(org.osrs.api.objects.RSModel model){
		cachedModel = model;
	}
	
}
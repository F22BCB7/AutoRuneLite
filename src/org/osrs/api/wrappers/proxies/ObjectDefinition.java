package org.osrs.api.wrappers.proxies;

import org.osrs.injection.bytescript.BClass;
import org.osrs.injection.bytescript.BDetour;
import org.osrs.injection.bytescript.BField;
import org.osrs.injection.bytescript.BMethod;
import org.osrs.injection.bytescript.BGetter;
import org.osrs.injection.bytescript.BVar;
import java.util.HashMap;
import java.util.Map;

@BClass(name="ObjectDefinition")
public class ObjectDefinition extends EntityNode implements org.osrs.api.wrappers.ObjectDefinition{

	@BField
	public int[] objectModels;
	@BGetter
	@Override
	public int[] objectModels(){return objectModels;}
	@BField
	public int[] objectTypes;
	@BGetter
	@Override
	public int[] objectTypes(){return objectTypes;}
	@BField
	public boolean clipped;
	@BGetter
	@Override
	public boolean clipped(){return clipped;}
	@BField
	public int modelSizeX;
	@BGetter
	@Override
	public int modelSizeX(){return modelSizeX;}
	@BField
	public String name;
	@BGetter
	@Override
	public String name(){return name;}
	@BField
	public int modelHeight;
	@BGetter
	@Override
	public int modelHeight(){return modelHeight;}
	@BField
	public int modelSizeY;
	@BGetter
	@Override
	public int modelSizeY(){return modelSizeY;}
	@BField
	public int mapSceneID;
	@BGetter
	@Override
	public int mapSceneID(){return mapSceneID;}
	@BField
	public int offsetX;
	@BGetter
	@Override
	public int offsetX(){return offsetX;}
	@BField
	public int offsetZ;
	@BGetter
	@Override
	public int offsetZ(){return offsetZ;}
	@BField
	public int offsetY;
	@BGetter
	@Override
	public int offsetY(){return offsetY;}
	@BField
	public boolean obstructsGround;
	@BGetter
	@Override
	public boolean obstructsGround(){return obstructsGround;}
	@BField
	public boolean isHollow;
	@BGetter
	@Override
	public boolean isHollow(){return isHollow;}
	@BField
	public int supportItems;
	@BGetter
	@Override
	public int supportItems(){return supportItems;}
	@BField
	public int width;
	@BGetter
	@Override
	public int width(){return width;}
	@BField
	public int ambientSoundID;
	@BGetter
	@Override
	public int ambientSoundID(){return ambientSoundID;}
	@BField
	public int length;
	@BGetter
	@Override
	public int length(){return length;}
	@BField
	public int[] soundEffectIDs;
	@BGetter
	@Override
	public int[] soundEffectIDs(){return soundEffectIDs;}
	@BField
	public int clipType;
	@BGetter
	@Override
	public int clipType(){return clipType;}
	@BField
	public boolean blocksProjectile;
	@BGetter
	@Override
	public boolean blocksProjectile(){return blocksProjectile;}
	@BField
	public int mapIconID;
	@BGetter
	@Override
	public int mapIconID(){return mapIconID;}
	@BField
	public int mapDoorFlag;
	@BGetter
	@Override
	public int mapDoorFlag(){return mapDoorFlag;}
	@BField
	public int contouredGround;
	@BGetter
	@Override
	public int contouredGround(){return contouredGround;}
	@BField
	public boolean nonFlatShading;
	@BGetter
	@Override
	public boolean nonFlatShading(){return nonFlatShading;}
	@BField
	public boolean modelClipped;
	@BGetter
	@Override
	public boolean modelClipped(){return modelClipped;}
	@BField
	public int animationID;
	@BGetter
	@Override
	public int animationID(){return animationID;}
	@BField
	public int decorDisplacement;
	@BGetter
	@Override
	public int decorDisplacement(){return decorDisplacement;}
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
	public int[] childrenIDs;
	@BGetter
	@Override
	public int[] childrenIDs(){return childrenIDs;}
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
	public FixedSizeDeque params;
	@BGetter
	@Override
	public org.osrs.api.wrappers.FixedSizeDeque params(){return params;}
	@BField
	public boolean isRotated;
	@BGetter
	@Override
	public boolean isRotated(){return isRotated;}
	@BField
	public short[] recolorToReplace;
	@BGetter
	@Override
	public short[] recolorToReplace(){return recolorToReplace;}
	@BField
	public short[] recolorToFind;
	@BGetter
	@Override
	public short[] recolorToFind(){return recolorToFind;}
	@BField
	public String[] actions;
	@BGetter
	@Override
	public String[] actions(){return actions;}
	@BField
	public int id;
	@BGetter
	@Override
	public int id(){return id;}
}
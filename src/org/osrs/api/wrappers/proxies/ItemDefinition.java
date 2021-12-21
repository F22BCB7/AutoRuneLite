package org.osrs.api.wrappers.proxies;

import org.osrs.injection.bytescript.BClass;
import org.osrs.injection.bytescript.BDetour;
import org.osrs.injection.bytescript.BField;
import org.osrs.injection.bytescript.BMethod;
import org.osrs.injection.bytescript.BGetter;
import org.osrs.injection.bytescript.BVar;
import java.util.HashMap;
import java.util.Map;

@BClass(name="ItemDefinition")
public class ItemDefinition extends EntityNode implements org.osrs.api.wrappers.ItemDefinition{

	@BField
	public int inventoryModelID;
	@BGetter
	@Override
	public int inventoryModelID(){return inventoryModelID;}
	@BField
	public boolean tradable;
	@BGetter
	@Override
	public boolean tradable(){return tradable;}
	@BField
	public int spriteScale;
	@BGetter
	@Override
	public int spriteScale(){return spriteScale;}
	@BField
	public int spritePitch;
	@BGetter
	@Override
	public int spritePitch(){return spritePitch;}
	@BField
	public int spriteRoll;
	@BGetter
	@Override
	public int spriteRoll(){return spriteRoll;}
	@BField
	public int spriteOffsetX;
	@BGetter
	@Override
	public int spriteOffsetX(){return spriteOffsetX;}
	@BField
	public int spriteOffsetY;
	@BGetter
	@Override
	public int spriteOffsetY(){return spriteOffsetY;}
	@BField
	public int isStackable;
	@BGetter
	@Override
	public int isStackable(){return isStackable;}
	@BField
	public int unnotedID;
	@BGetter
	@Override
	public int unnotedID(){return unnotedID;}
	@BField
	public int price;
	@BGetter
	@Override
	public int price(){return price;}
	@BField
	public int notedID;
	@BGetter
	@Override
	public int notedID(){return notedID;}
	@BField
	public int maleModel2;
	@BGetter
	@Override
	public int maleModel2(){return maleModel2;}
	@BField
	public int femaleModel2;
	@BGetter
	@Override
	public int femaleModel2(){return femaleModel2;}
	@BField
	public boolean isMembers;
	@BGetter
	@Override
	public boolean isMembers(){return isMembers;}
	@BField
	public int placeholderID;
	@BGetter
	@Override
	public int placeholderID(){return placeholderID;}
	@BField
	public int placeholderTemplateID;
	@BGetter
	@Override
	public int placeholderTemplateID(){return placeholderTemplateID;}
	@BField
	public int maleModel;
	@BGetter
	@Override
	public int maleModel(){return maleModel;}
	@BField
	public int maleOffset;
	@BGetter
	@Override
	public int maleOffset(){return maleOffset;}
	@BField
	public int maleModel1;
	@BGetter
	@Override
	public int maleModel1(){return maleModel1;}
	@BField
	public int femaleModel;
	@BGetter
	@Override
	public int femaleModel(){return femaleModel;}
	@BField
	public int femaleOffset;
	@BGetter
	@Override
	public int femaleOffset(){return femaleOffset;}
	@BField
	public int femaleModel1;
	@BGetter
	@Override
	public int femaleModel1(){return femaleModel1;}
	@BField
	public int maleHeadModel;
	@BGetter
	@Override
	public int maleHeadModel(){return maleHeadModel;}
	@BField
	public int femaleHeadModel;
	@BGetter
	@Override
	public int femaleHeadModel(){return femaleHeadModel;}
	@BField
	public int maleHeadModel2;
	@BGetter
	@Override
	public int maleHeadModel2(){return maleHeadModel2;}
	@BField
	public int femaleHeadModel2;
	@BGetter
	@Override
	public int femaleHeadModel2(){return femaleHeadModel2;}
	@BField
	public int spriteYaw;
	@BGetter
	@Override
	public int spriteYaw(){return spriteYaw;}
	@BField
	public int note;
	@BGetter
	@Override
	public int note(){return note;}
	@BField
	public int notedTemplate;
	@BGetter
	@Override
	public int notedTemplate(){return notedTemplate;}
	@BField
	public int shiftClickIndex;
	@BGetter
	@Override
	public int shiftClickIndex(){return shiftClickIndex;}
	@BField
	public int resizeX;
	@BGetter
	@Override
	public int resizeX(){return resizeX;}
	@BField
	public int resizeY;
	@BGetter
	@Override
	public int resizeY(){return resizeY;}
	@BField
	public int resizeZ;
	@BGetter
	@Override
	public int resizeZ(){return resizeZ;}
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
	public int team;
	@BGetter
	@Override
	public int team(){return team;}
	@BField
	public int[] stackAmounts;
	@BGetter
	@Override
	public int[] stackAmounts(){return stackAmounts;}
	@BField
	public int[] stackIDs;
	@BGetter
	@Override
	public int[] stackIDs(){return stackIDs;}
	@BField
	public short[] colourToReplace;
	@BGetter
	@Override
	public short[] colourToReplace(){return colourToReplace;}
	@BField
	public short[] colourToReplaceWith;
	@BGetter
	@Override
	public short[] colourToReplaceWith(){return colourToReplaceWith;}
	@BField
	public short[] textureToReplace;
	@BGetter
	@Override
	public short[] textureToReplace(){return textureToReplace;}
	@BField
	public short[] textureToReplaceWith;
	@BGetter
	@Override
	public short[] textureToReplaceWith(){return textureToReplaceWith;}
	@BField
	public String[] inventoryActions;
	@BGetter
	@Override
	public String[] inventoryActions(){return inventoryActions;}
	@BField
	public String[] groundActions;
	@BGetter
	@Override
	public String[] groundActions(){return groundActions;}
	@BField
	public String name;
	@BGetter
	@Override
	public String name(){return name;}
	@BField
	public int id;
	@BGetter
	@Override
	public int id(){return id;}
	@BField
	public FixedSizeDeque params;
	@BGetter
	@Override
	public org.osrs.api.wrappers.FixedSizeDeque params(){return params;}
}
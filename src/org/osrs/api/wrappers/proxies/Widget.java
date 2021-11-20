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

@BClass(name="Widget")
public class Widget extends Node implements org.osrs.api.wrappers.Widget{
	@BVar
	public boolean isVisible;
	@BFunction
	@Override
	public boolean isVisible(){return isVisible;}
	@BFunction
	@Override
	public void setVisible(boolean flag){isVisible=flag;}
	
	@BField
	public boolean hasScript;
	@BGetter
	@Override
	public boolean hasScript(){return hasScript;}
	@BField
	public int id;
	@BGetter
	@Override
	public int id(){return id;}
	@BField
	public int index;
	@BGetter
	@Override
	public int index(){return index;}
	@BField
	public int menuType;
	@BGetter
	@Override
	public int menuType(){return menuType;}
	@BField
	public int contentType;
	@BGetter
	@Override
	public int contentType(){return contentType;}
	@BField
	public int dynamicX;
	@BGetter
	@Override
	public int dynamicX(){return dynamicX;}
	@BField
	public int dynamicY;
	@BGetter
	@Override
	public int dynamicY(){return dynamicY;}
	@BField
	public int dynamicWidth;
	@BGetter
	@Override
	public int dynamicWidth(){return dynamicWidth;}
	@BField
	public int dynamicHeight;
	@BGetter
	@Override
	public int dynamicHeight(){return dynamicHeight;}
	@BField
	public int originalX;
	@BGetter
	@Override
	public int originalX(){return originalX;}
	@BField
	public int originalY;
	@BGetter
	@Override
	public int originalY(){return originalY;}
	@BField
	public int originalWidth;
	@BGetter
	@Override
	public int originalWidth(){return originalWidth;}
	@BField
	public int originalHeight;
	@BGetter
	@Override
	public int originalHeight(){return originalHeight;}
	@BField
	public int relativeX;
	@BGetter
	@Override
	public int relativeX(){return relativeX;}
	@BField
	public int relativeY;
	@BGetter
	@Override
	public int relativeY(){return relativeY;}
	@BField
	public int width;
	@BGetter
	@Override
	public int width(){return width;}
	@BField
	public int height;
	@BGetter
	@Override
	public int height(){return height;}
	@BField
	public int oldWidth;
	@BGetter
	@Override
	public int oldWidth(){return oldWidth;}
	@BField
	public int oldHeight;
	@BGetter
	@Override
	public int oldHeight(){return oldHeight;}
	@BField
	public int parentID;
	@BGetter
	@Override
	public int parentID(){return parentID;}
	@BField
	public boolean isHidden;
	@BGetter
	@Override
	public boolean isHidden(){return isHidden;}
	@BField
	public int scrollX;
	@BGetter
	@Override
	public int scrollX(){return scrollX;}
	@BField
	public int scrollY;
	@BGetter
	@Override
	public int scrollY(){return scrollY;}
	@BField
	public int scrollWidth;
	@BGetter
	@Override
	public int scrollWidth(){return scrollWidth;}
	@BField
	public int scrollHeight;
	@BGetter
	@Override
	public int scrollHeight(){return scrollHeight;}
	@BField
	public int disabledColor;
	@BGetter
	@Override
	public int disabledColor(){return disabledColor;}
	@BField
	public int enabledColor;
	@BGetter
	@Override
	public int enabledColor(){return enabledColor;}
	@BField
	public int disabledHoverColor;
	@BGetter
	@Override
	public int disabledHoverColor(){return disabledHoverColor;}
	@BField
	public int enabledHoverColor;
	@BGetter
	@Override
	public int enabledHoverColor(){return enabledHoverColor;}
	@BField
	public boolean filled;
	@BGetter
	@Override
	public boolean filled(){return filled;}
	@BField
	public int alpha;
	@BGetter
	@Override
	public int alpha(){return alpha;}
	@BField
	public int lineWidth;
	@BGetter
	@Override
	public int lineWidth(){return lineWidth;}
	@BField
	public int spriteID;
	@BGetter
	@Override
	public int spriteID(){return spriteID;}
	@BField
	public int enabledSpriteID;
	@BGetter
	@Override
	public int enabledSpriteID(){return enabledSpriteID;}
	@BField
	public int textureID;
	@BGetter
	@Override
	public int textureID(){return textureID;}
	@BField
	public boolean spriteTiling;
	@BGetter
	@Override
	public boolean spriteTiling(){return spriteTiling;}
	@BField
	public int borderThickness;
	@BGetter
	@Override
	public int borderThickness(){return borderThickness;}
	@BField
	public int shadowColor;
	@BGetter
	@Override
	public int shadowColor(){return shadowColor;}
	@BField
	public int disabledMediaType;
	@BGetter
	@Override
	public int disabledMediaType(){return disabledMediaType;}
	@BField
	public int disabledMediaID;
	@BGetter
	@Override
	public int disabledMediaID(){return disabledMediaID;}
	@BField
	public int enabledMediaType;
	@BGetter
	@Override
	public int enabledMediaType(){return enabledMediaType;}
	@BField
	public int enabledMediaID;
	@BGetter
	@Override
	public int enabledMediaID(){return enabledMediaID;}
	@BField
	public int disabledAnimationID;
	@BGetter
	@Override
	public int disabledAnimationID(){return disabledAnimationID;}
	@BField
	public int enabledAnimationID;
	@BGetter
	@Override
	public int enabledAnimationID(){return enabledAnimationID;}
	@BField
	public int modelOffsetX;
	@BGetter
	@Override
	public int modelOffsetX(){return modelOffsetX;}
	@BField
	public int modelOffsetY;
	@BGetter
	@Override
	public int modelOffsetY(){return modelOffsetY;}
	@BField
	public int rotationX;
	@BGetter
	@Override
	public int rotationX(){return rotationX;}
	@BField
	public int rotationZ;
	@BGetter
	@Override
	public int rotationZ(){return rotationZ;}
	@BField
	public int rotationY;
	@BGetter
	@Override
	public int rotationY(){return rotationY;}
	@BField
	public int modelZoom;
	@BGetter
	@Override
	public int modelZoom(){return modelZoom;}
	@BField
	public boolean renderAtPoint;
	@BGetter
	@Override
	public boolean renderAtPoint(){return renderAtPoint;}
	@BField
	public int fontID;
	@BGetter
	@Override
	public int fontID(){return fontID;}
	@BField
	public String disabledText;
	@BGetter
	@Override
	public String disabledText(){return disabledText;}
	@BField
	public String enabledText;
	@BGetter
	@Override
	public String enabledText(){return enabledText;}
	@BField
	public int horizontalMargin;
	@BGetter
	@Override
	public int horizontalMargin(){return horizontalMargin;}
	@BField
	public int verticalMargin;
	@BGetter
	@Override
	public int verticalMargin(){return verticalMargin;}
	@BField
	public boolean textShadowed;
	@BGetter
	@Override
	public boolean textShadowed(){return textShadowed;}
	@BField
	public int paddingX;
	@BGetter
	@Override
	public int paddingX(){return paddingX;}
	@BField
	public int paddingY;
	@BGetter
	@Override
	public int paddingY(){return paddingY;}
	@BField
	public int clickMask;
	@BGetter
	@Override
	public int clickMask(){return clickMask;}
	@BField
	public String opbase;
	@BGetter
	@Override
	public String opbase(){return opbase;}
	@BField
	public org.osrs.api.wrappers.Widget dragParent;
	@BGetter
	@Override
	public org.osrs.api.wrappers.Widget dragParent(){return dragParent;}
	@BField
	public int dragDeadZone;
	@BGetter
	@Override
	public int dragDeadZone(){return dragDeadZone;}
	@BField
	public int dragDeadTime;
	@BGetter
	@Override
	public int dragDeadTime(){return dragDeadTime;}
	@BField
	public boolean dragRenderBehavior;
	@BGetter
	@Override
	public boolean dragRenderBehavior(){return dragRenderBehavior;}
	@BField
	public String targetVerb;
	@BGetter
	@Override
	public String targetVerb(){return targetVerb;}
	@BField
	public boolean hasListener;
	@BGetter
	@Override
	public boolean hasListener(){return hasListener;}
	@BField
	public String spellName;
	@BGetter
	@Override
	public String spellName(){return spellName;}
	@BField
	public String tooltip;
	@BGetter
	@Override
	public String tooltip(){return tooltip;}
	@BField
	public int itemID;
	@BGetter
	@Override
	public int itemID(){return itemID;}
	@BField
	public int itemQuantity;
	@BGetter
	@Override
	public int itemQuantity(){return itemQuantity;}
	@BField
	public int currentFrameIndex;
	@BGetter
	@Override
	public int currentFrameIndex(){return currentFrameIndex;}
	@BField
	public int currentFrameLength;
	@BGetter
	@Override
	public int currentFrameLength(){return currentFrameLength;}
	@BField
	public boolean hovering;
	@BGetter
	@Override
	public boolean hovering(){return hovering;}
	@BField
	public boolean isClickDown;
	@BGetter
	@Override
	public boolean isClickDown(){return isClickDown;}
	@BField
	public int visibleCycle;
	@BGetter
	@Override
	public int visibleCycle(){return visibleCycle;}
	@BField
	public int pendingVarbitCount;
	@BGetter
	@Override
	public int pendingVarbitCount(){return pendingVarbitCount;}
	@BField
	public int pendingItemTriggerCount;
	@BGetter
	@Override
	public int pendingItemTriggerCount(){return pendingItemTriggerCount;}
	@BField
	public int changedSkillsCount;
	@BGetter
	@Override
	public int changedSkillsCount(){return changedSkillsCount;}
	@BField
	public int boundsIndex;
	@BGetter
	@Override
	public int boundsIndex(){return boundsIndex;}
	@BField
	public int displayCycle;
	@BGetter
	@Override
	public int displayCycle(){return displayCycle;}
	@BField
	public boolean noClickThrough;
	@BGetter
	@Override
	public boolean noClickThrough(){return noClickThrough;}
	@BField
	public boolean noScrollThrough;
	@BGetter
	@Override
	public boolean noScrollThrough(){return noScrollThrough;}
	@BField
	public java.lang.Object[] onMouseRepeatListener;
	@BGetter
	@Override
	public java.lang.Object[] onMouseRepeatListener(){return onMouseRepeatListener;}
	@BField
	public java.lang.Object[] onSubChangeListener;
	@BGetter
	@Override
	public java.lang.Object[] onSubChangeListener(){return onSubChangeListener;}
	@BField
	public java.lang.Object[] onReleaseListener;
	@BGetter
	@Override
	public java.lang.Object[] onReleaseListener(){return onReleaseListener;}
	@BField
	public java.lang.Object[] onTimerListener;
	@BGetter
	@Override
	public java.lang.Object[] onTimerListener(){return onTimerListener;}
	@BField
	public boolean flippedVertically;
	@BGetter
	@Override
	public boolean flippedVertically(){return flippedVertically;}
	@BField
	public boolean flippedHorizontally;
	@BGetter
	@Override
	public boolean flippedHorizontally(){return flippedHorizontally;}
	@BField
	public String[] actions;
	@BGetter
	@Override
	public String[] actions(){return actions;}
	@BField
	public java.lang.Object[] onInvTransmitListener;
	@BGetter
	@Override
	public java.lang.Object[] onInvTransmitListener(){return onInvTransmitListener;}
	@BField
	public int[] invTransmitTriggers;
	@BGetter
	@Override
	public int[] invTransmitTriggers(){return invTransmitTriggers;}
	@BField
	public int[] itemIDs;
	@BGetter
	@Override
	public int[] itemIDs(){return itemIDs;}
	@BField
	public int[] itemQuantities;
	@BGetter
	@Override
	public int[] itemQuantities(){return itemQuantities;}
	@BField
	public int childModelRotationHash;
	@BGetter
	@Override
	public int childModelRotationHash(){return childModelRotationHash;}
	@BField
	public int itemStackType;
	@BGetter
	@Override
	public int itemStackType(){return itemStackType;}
	@BField
	public int defaultMargin;
	@BGetter
	@Override
	public int defaultMargin(){return defaultMargin;}
	@BField
	public java.lang.Object[] onClickListener;
	@BGetter
	@Override
	public java.lang.Object[] onClickListener(){return onClickListener;}
	@BField
	public int[] spriteIDs;
	@BGetter
	@Override
	public int[] spriteIDs(){return spriteIDs;}
	@BField
	public int[] ySprites;
	@BGetter
	@Override
	public int[] ySprites(){return ySprites;}
	@BField
	public int[] xSprites;
	@BGetter
	@Override
	public int[] xSprites(){return xSprites;}
	@BField
	public java.lang.Object[] onStockTransmitListener;
	@BGetter
	@Override
	public java.lang.Object[] onStockTransmitListener(){return onStockTransmitListener;}
	@BField
	public int type;
	@BGetter
	@Override
	public int type(){return type;}
	@BField
	public java.lang.Object[] onLoadListener;
	@BGetter
	@Override
	public java.lang.Object[] onLoadListener(){return onLoadListener;}
	@BField
	public java.lang.Object[] onClickRepeatListener;
	@BGetter
	@Override
	public java.lang.Object[] onClickRepeatListener(){return onClickRepeatListener;}
	@BField
	public java.lang.Object[] onFriendTransmitListener;
	@BGetter
	@Override
	public java.lang.Object[] onFriendTransmitListener(){return onFriendTransmitListener;}
	@BField
	public java.lang.Object[] onHoldListener;
	@BGetter
	@Override
	public java.lang.Object[] onHoldListener(){return onHoldListener;}
	@BField
	public java.lang.Object[] onMouseOverListener;
	@BGetter
	@Override
	public java.lang.Object[] onMouseOverListener(){return onMouseOverListener;}
	@BField
	public java.lang.Object[] onMouseLeaveListener;
	@BGetter
	@Override
	public java.lang.Object[] onMouseLeaveListener(){return onMouseLeaveListener;}
	@BField
	public int[] widgetVarps;
	@BGetter
	@Override
	public int[] widgetVarps(){return widgetVarps;}
	@BField
	public java.lang.Object[] onTargetEnterListener;
	@BGetter
	@Override
	public java.lang.Object[] onTargetEnterListener(){return onTargetEnterListener;}
	@BField
	public java.lang.Object[] onTargetLeaveListener;
	@BGetter
	@Override
	public java.lang.Object[] onTargetLeaveListener(){return onTargetLeaveListener;}
	@BField
	public java.lang.Object[] onVarTransmitListener;
	@BGetter
	@Override
	public java.lang.Object[] onVarTransmitListener(){return onVarTransmitListener;}
	@BField
	public int[] varTransmitTriggers;
	@BGetter
	@Override
	public int[] varTransmitTriggers(){return varTransmitTriggers;}
	@BField
	public java.lang.Object[] onMiscTransmitListener;
	@BGetter
	@Override
	public java.lang.Object[] onMiscTransmitListener(){return onMiscTransmitListener;}
	@BField
	public java.lang.Object[] onOpListener;
	@BGetter
	@Override
	public java.lang.Object[] onOpListener(){return onOpListener;}
	@BField
	public java.lang.Object[] onScrollWheelListener;
	@BGetter
	@Override
	public java.lang.Object[] onScrollWheelListener(){return onScrollWheelListener;}
	@BField
	public java.lang.Object[] onChatTransmitListener;
	@BGetter
	@Override
	public java.lang.Object[] onChatTransmitListener(){return onChatTransmitListener;}
	@BField
	public java.lang.Object[] onKeyListener;
	@BGetter
	@Override
	public java.lang.Object[] onKeyListener(){return onKeyListener;}
	@BField
	public java.lang.Object[] onDialogAbortListener;
	@BGetter
	@Override
	public java.lang.Object[] onDialogAbortListener(){return onDialogAbortListener;}
	@BField
	public java.lang.Object[] onClanTransmitListener;
	@BGetter
	@Override
	public java.lang.Object[] onClanTransmitListener(){return onClanTransmitListener;}
	@BField
	public String[] configActions;
	@BGetter
	@Override
	public String[] configActions(){return configActions;}
	@BField
	public java.lang.Object[] onStatTransmitListener;
	@BGetter
	@Override
	public java.lang.Object[] onStatTransmitListener(){return onStatTransmitListener;}
	@BField
	public int[] statTransmitTriggers;
	@BGetter
	@Override
	public int[] statTransmitTriggers(){return statTransmitTriggers;}
	@BField
	public java.lang.Object[] onResizeListener;
	@BGetter
	@Override
	public java.lang.Object[] onResizeListener(){return onResizeListener;}
	@BField
	public java.lang.Object[] onCamFinishedListener;
	@BGetter
	@Override
	public java.lang.Object[] onCamFinishedListener(){return onCamFinishedListener;}
	@BField
	public int[][] scriptOpcodes;
	@BGetter
	@Override
	public int[][] scriptOpcodes(){return scriptOpcodes;}
	@BField
	public int[] tableActions;
	@BGetter
	@Override
	public int[] tableActions(){return tableActions;}
	@BField
	public org.osrs.api.wrappers.Widget[] children;
	@BGetter
	@Override
	public org.osrs.api.wrappers.Widget[] children(){return children;}
	@BField
	public java.lang.Object[] onDragListener;
	@BGetter
	@Override
	public java.lang.Object[] onDragListener(){return onDragListener;}
	@BField
	public java.lang.Object[] onDragCompleteListener;
	@BGetter
	@Override
	public java.lang.Object[] onDragCompleteListener(){return onDragCompleteListener;}
}
package org.osrs.api.objects;

import java.awt.Component;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

import org.osrs.api.wrappers.Client;
import org.osrs.api.wrappers.HashTable;
import org.osrs.api.wrappers.Node;
import org.osrs.api.wrappers.Widget;
import org.osrs.api.wrappers.WidgetNode;
import org.osrs.util.Data;

public class RSWidget extends Interactable{
	private int index;
	private RSInterface parentInterface;
	private RSWidget parentWidget;
	private RSWidget[] cachedChildren = new RSWidget[]{};
	private final HashMap<Integer, RSWidget> sparseMap = new HashMap<Integer, RSWidget>();
	public RSWidget(RSInterface parent, int index){
		methods = ((Client)Data.clientInstance).getMethodContext();
		this.index=index;
		this.parentInterface=parent;
		this.parentWidget=null;
	}
	public RSWidget(RSInterface parent, RSWidget parent2, int index){
		methods = ((Client)Data.clientInstance).getMethodContext();
		this.index=index;
		this.parentInterface=parent;
		this.parentWidget=parent2;
	}
	public boolean isDisplayed(){
		Widget w = getInternal();
		if(w!=null){
			return w.isDisplayed();
		}
		return false;
	}
	public boolean isVisible(){
		Widget w = getInternal();
		if(w!=null){
			return w.isVisible();
		}
		return false;
	}
	public RSWidget getChild(int ind){
		Widget iface = getInternal();
		if(iface==null)
			return null;
		Widget[] childs = iface.children();
		if(childs==null || childs.length<=ind)
			return null;
		RSWidget temp=null;
		int cacheLength = cachedChildren.length;
		if(ind<cacheLength){
			temp = cachedChildren[ind];
			if(temp==null){
				temp = new RSWidget(parentInterface, this, ind);
				cachedChildren[ind] = temp;
			}
		}
		else{
			temp = sparseMap.get(ind);
			if(temp==null){
				enlargeCache();
				if(ind<cacheLength){
					temp = cachedChildren[ind];
					if(temp==null){
						temp = new RSWidget(parentInterface, this, ind);
						cachedChildren[ind]=temp;
					}
				}
				else{
					temp = new RSWidget(parentInterface, this, ind);
					sparseMap.put(ind, temp);
				}
			}
		}
		return temp;
	}
	public synchronized int getChildCount(){
		enlargeCache();
		return cachedChildren.length;
	}
	public RSWidget[] getChildren(){
		enlargeCache();
		Widget[] children = getChildrenInternal();
		if(children==null || children.length==0)
			return new RSWidget[]{};
		ArrayList<RSWidget> out = new ArrayList<RSWidget>();
		for(int i=0;i<children.length;++i){
			RSWidget tmp = getChild(i);
			if(tmp!=null)
				out.add(tmp);
		}
		return out.toArray(new RSWidget[]{});
	}
	public Widget[] getChildrenInternal(){
		Widget internal = getInternal();
		if(internal!=null)
			return internal.children();
		return null;
	}
	public int getIndex(){
		return index;
	}
	public int getContainerIndex(){
		int id = id();
		if(id!=-1){
			return id >> 16;
		}
		return -1;
	}
	public int getWidgetIndex(){
		int id = id();
		if(id!=-1){
			return id & 0xFFFF;
		}
		return -1;
	}
	public Widget getInternal(){
		if(parentWidget!=null){
			Widget p = parentWidget.getInternal();
			if(p!=null){
				Widget[] children = p.children();
				if(children!=null && index>=0 && index<children.length)
					return children[index];
			}
		}
		else{
			Widget[] children = parentInterface.getChildrenInternal();
			if(children!=null && index>=0 && index<children.length)
				return children[index];
		}
		return null;
	}
	public RSInterface getParentInterface(){
		return parentInterface;
	}
	public RSWidget getParentWidget(){
		return parentWidget;
	}
	public int getParentID(){
		int id = parentID();
		if(id!=-1)
			return id;
		HashTable table = ((Client)Data.clientInstance).componentTable();
		if(table!=null){
			for(Node node : table.buckets()){
				if(node!=null){
					if(node instanceof WidgetNode){
						WidgetNode wNode = (WidgetNode)node;
						if(wNode.id()==getContainerIndex())
							return (int)wNode.uid();
					}
					for(Node next=node.next();next!=null && !next.equals(node);next=next.next()){
						if(next instanceof WidgetNode){
							WidgetNode wNode = (WidgetNode)next;
							if(wNode.id()==getContainerIndex())
								return (int)wNode.uid();
						}
					}
				}
			}
		}
		return -1;
	}
	public int getAbsoluteX(){
		int x = 0;
		int parentID = getParentID();
		if (parentID != -1) {
			int containerIndex = getParentID() >> 16;
			int widgetIndex = getParentID() & 0xFFFF;
			RSWidget parent = methods.widgets.getChild(containerIndex, widgetIndex);
			x = parent.getAbsoluteX() - scrollX();
		} else {
			int posX[] = ((Client)Data.clientInstance).widgetPositionsX();
			int bi = boundsIndex();
			if (bi != -1 && bi < posX.length) {
				return posX[bi];
			}
		}
		x += relativeX();
		return x;
	}
	public int getAbsoluteY(){
		int y = 0;
		int parentID = getParentID();
		if (parentID != -1) {
			int index1 = getParentID() >> 16;
			int index2 = getParentID() & 0xFFFF;
			RSWidget parent = methods.widgets.getChild(index1, index2);
			y = parent.getAbsoluteY() - scrollY();
		} else {
			int posY[] = ((Client)Data.clientInstance).widgetPositionsY();
			int bi = boundsIndex();
			if (bi != -1 && bi < posY.length) {
				return posY[bi];
			}
		}
		y += relativeY();
		return y;
	}
	public Point getAbsolutePosition(){
		return new Point(getAbsoluteX(), getAbsoluteY());
	}
	public Rectangle getBounds(){
		return new Rectangle(getAbsoluteX(), getAbsoluteY(), width(), height());
	}
	@Override
	public Point getCenterPoint(){
		return new Point(this.getAbsoluteX()+(this.width()/2), this.getAbsoluteY()+(this.height()/2));
	}
	@Override
	public Point getRandomPoint() {
		Rectangle bounds = getBounds();
		return new Point(bounds.x+methods.calculations.random(bounds.width), bounds.y+methods.calculations.random(bounds.height));
	}
	public boolean isHovering(){
		return getBounds().contains(methods.mouse.getLocation());
	}
	private synchronized void enlargeCache() {
		Widget[] childs = getChildrenInternal();
		if(childs!=null && cachedChildren.length<childs.length){
			cachedChildren = Arrays.copyOf(cachedChildren, childs.length);
			for(int i=0;i<cachedChildren.length;++i){
				RSWidget tmp = sparseMap.get(i);
				if(tmp!=null){
					sparseMap.remove(i);
					cachedChildren[i]=tmp;
				}
			}
		}
	}
	
	
	public boolean hasScript(){
		Widget w = getInternal();
		if(w!=null){
			return w.hasScript();
		}
		return false;
	}
	public int id(){
		Widget w = getInternal();
		if(w!=null){
			return w.id();
		}
		return -1;
	}
	public int index(){
		Widget w = getInternal();
		if(w!=null){
			return w.index();
		}
		return -1;
	}
	public int menuType(){
		Widget w = getInternal();
		if(w!=null){
			return w.menuType();
		}
		return -1;
	}
	public int contentType(){
		Widget w = getInternal();
		if(w!=null){
			return w.contentType();
		}
		return -1;
	}
	public int dynamicX(){
		Widget w = getInternal();
		if(w!=null){
			return w.dynamicX();
		}
		return -1;
	}
	public int dynamicY(){
		Widget w = getInternal();
		if(w!=null){
			return w.dynamicY();
		}
		return -1;
	}
	public int dynamicWidth(){
		Widget w = getInternal();
		if(w!=null){
			return w.dynamicWidth();
		}
		return -1;
	}
	public int dynamicHeight(){
		Widget w = getInternal();
		if(w!=null){
			return w.dynamicHeight();
		}
		return -1;
	}
	public int originalX(){
		Widget w = getInternal();
		if(w!=null){
			return w.originalX();
		}
		return -1;
	}
	public int originalY(){
		Widget w = getInternal();
		if(w!=null){
			return w.originalY();
		}
		return -1;
	}
	public int originalWidth(){
		Widget w = getInternal();
		if(w!=null){
			return w.originalWidth();
		}
		return -1;
	}
	public int originalHeight(){
		Widget w = getInternal();
		if(w!=null){
			return w.originalHeight();
		}
		return -1;
	}
	public int relativeX(){
		Widget w = getInternal();
		if(w!=null){
			return w.relativeX();
		}
		return -1;
	}
	public int relativeY(){
		Widget w = getInternal();
		if(w!=null){
			return w.relativeY();
		}
		return -1;
	}
	public int width(){
		Widget w = getInternal();
		if(w!=null){
			return w.width();
		}
		return -1;
	}
	public int height(){
		Widget w = getInternal();
		if(w!=null){
			return w.height();
		}
		return -1;
	}
	public int oldWidth(){
		Widget w = getInternal();
		if(w!=null){
			return w.oldWidth();
		}
		return -1;
	}
	public int oldHeight(){
		Widget w = getInternal();
		if(w!=null){
			return w.oldHeight();
		}
		return -1;
	}
	public int parentID(){
		Widget w = getInternal();
		if(w!=null){
			return w.parentID();
		}
		return -1;
	}
	public boolean isHidden(){
		Widget w = getInternal();
		if(w!=null){
			return w.isHidden();
		}
		return false;
	}
	public int scrollX(){
		Widget w = getInternal();
		if(w!=null){
			return w.scrollX();
		}
		return -1;
	}
	public int scrollY(){
		Widget w = getInternal();
		if(w!=null){
			return w.scrollY();
		}
		return -1;
	}
	public int scrollWidth(){
		Widget w = getInternal();
		if(w!=null){
			return w.scrollWidth();
		}
		return -1;
	}
	public int scrollHeight(){
		Widget w = getInternal();
		if(w!=null){
			return w.scrollHeight();
		}
		return -1;
	}
	public int disabledColor(){
		Widget w = getInternal();
		if(w!=null){
			return w.disabledColor();
		}
		return -1;
	}
	public int enabledColor(){
		Widget w = getInternal();
		if(w!=null){
			return w.enabledColor();
		}
		return -1;
	}
	public int disabledHoverColor(){
		Widget w = getInternal();
		if(w!=null){
			return w.disabledHoverColor();
		}
		return -1;
	}
	public int enabledHoverColor(){
		Widget w = getInternal();
		if(w!=null){
			return w.enabledHoverColor();
		}
		return -1;
	}
	public boolean filled(){
		Widget w = getInternal();
		if(w!=null){
			return w.filled();
		}
		return false;
	}
	public int alpha(){
		Widget w = getInternal();
		if(w!=null){
			return w.alpha();
		}
		return -1;
	}
	public int lineWidth(){
		Widget w = getInternal();
		if(w!=null){
			return w.lineWidth();
		}
		return -1;
	}
	public int spriteID(){
		Widget w = getInternal();
		if(w!=null){
			return w.spriteID();
		}
		return -1;
	}
	public int enabledSpriteID(){
		Widget w = getInternal();
		if(w!=null){
			return w.enabledSpriteID();
		}
		return -1;
	}
	public int textureID(){
		Widget w = getInternal();
		if(w!=null){
			return w.textureID();
		}
		return -1;
	}
	public boolean spriteTiling(){
		Widget w = getInternal();
		if(w!=null){
			return w.spriteTiling();
		}
		return false;
	}
	public int borderThickness(){
		Widget w = getInternal();
		if(w!=null){
			return w.borderThickness();
		}
		return -1;
	}
	public int shadowColor(){
		Widget w = getInternal();
		if(w!=null){
			return w.shadowColor();
		}
		return -1;
	}
	public int disabledMediaType(){
		Widget w = getInternal();
		if(w!=null){
			return w.disabledMediaType();
		}
		return -1;
	}
	public int disabledMediaID(){
		Widget w = getInternal();
		if(w!=null){
			return w.disabledMediaID();
		}
		return -1;
	}
	public int enabledMediaType(){
		Widget w = getInternal();
		if(w!=null){
			return w.enabledMediaType();
		}
		return -1;
	}
	public int enabledMediaID(){
		Widget w = getInternal();
		if(w!=null){
			return w.enabledMediaID();
		}
		return -1;
	}
	public int disabledAnimationID(){
		Widget w = getInternal();
		if(w!=null){
			return w.disabledAnimationID();
		}
		return -1;
	}
	public int enabledAnimationID(){
		Widget w = getInternal();
		if(w!=null){
			return w.enabledAnimationID();
		}
		return -1;
	}
	public int modelOffsetX(){
		Widget w = getInternal();
		if(w!=null){
			return w.modelOffsetX();
		}
		return -1;
	}
	public int modelOffsetY(){
		Widget w = getInternal();
		if(w!=null){
			return w.modelOffsetY();
		}
		return -1;
	}
	public int rotationX(){
		Widget w = getInternal();
		if(w!=null){
			return w.rotationX();
		}
		return -1;
	}
	public int rotationZ(){
		Widget w = getInternal();
		if(w!=null){
			return w.rotationZ();
		}
		return -1;
	}
	public int rotationY(){
		Widget w = getInternal();
		if(w!=null){
			return w.rotationY();
		}
		return -1;
	}
	public int modelZoom(){
		Widget w = getInternal();
		if(w!=null){
			return w.modelZoom();
		}
		return -1;
	}
	public boolean renderAtPoint(){
		Widget w = getInternal();
		if(w!=null){
			return w.renderAtPoint();
		}
		return false;
	}
	public int fontID(){
		Widget w = getInternal();
		if(w!=null){
			return w.fontID();
		}
		return -1;
	}
	public String disabledText(){
		Widget w = getInternal();
		if(w!=null){
			return w.disabledText();
		}
		return null;
	}
	public String enabledText(){
		Widget w = getInternal();
		if(w!=null){
			return w.enabledText();
		}
		return null;
	}
	public int horizontalMargin(){
		Widget w = getInternal();
		if(w!=null){
			return w.horizontalMargin();
		}
		return -1;
	}
	public int verticalMargin(){
		Widget w = getInternal();
		if(w!=null){
			return w.verticalMargin();
		}
		return -1;
	}
	public boolean textShadowed(){
		Widget w = getInternal();
		if(w!=null){
			return w.textShadowed();
		}
		return false;
	}
	public int paddingX(){
		Widget w = getInternal();
		if(w!=null){
			return w.paddingX();
		}
		return -1;
	}
	public int paddingY(){
		Widget w = getInternal();
		if(w!=null){
			return w.paddingY();
		}
		return -1;
	}
	public int clickMask(){
		Widget w = getInternal();
		if(w!=null){
			return w.clickMask();
		}
		return -1;
	}
	public String opbase(){
		Widget w = getInternal();
		if(w!=null){
			return w.opbase();
		}
		return null;
	}
	public Widget dragParent(){
		Widget w = getInternal();
		if(w!=null){
			return w.dragParent();
		}
		return null;
	}
	public int dragDeadZone(){
		Widget w = getInternal();
		if(w!=null){
			return w.dragDeadZone();
		}
		return -1;
	}
	public int dragDeadTime(){
		Widget w = getInternal();
		if(w!=null){
			return w.dragDeadTime();
		}
		return -1;
	}
	public boolean dragRenderBehavior(){
		Widget w = getInternal();
		if(w!=null){
			return w.dragRenderBehavior();
		}
		return false;
	}
	public String targetVerb(){
		Widget w = getInternal();
		if(w!=null){
			return w.targetVerb();
		}
		return null;
	}
	public boolean hasListener(){
		Widget w = getInternal();
		if(w!=null){
			return w.hasListener();
		}
		return false;
	}
	public String spellName(){
		Widget w = getInternal();
		if(w!=null){
			return w.spellName();
		}
		return null;
	}
	public String tooltip(){
		Widget w = getInternal();
		if(w!=null){
			return w.tooltip();
		}
		return null;
	}
	public int itemID(){
		Widget w = getInternal();
		if(w!=null){
			return w.itemID();
		}
		return -1;
	}
	public int itemQuantity(){
		Widget w = getInternal();
		if(w!=null){
			return w.itemQuantity();
		}
		return -1;
	}
	public int currentFrameIndex(){
		Widget w = getInternal();
		if(w!=null){
			return w.currentFrameIndex();
		}
		return -1;
	}
	public int currentFrameLength(){
		Widget w = getInternal();
		if(w!=null){
			return w.currentFrameLength();
		}
		return -1;
	}
	public boolean hovering(){
		Widget w = getInternal();
		if(w!=null){
			return w.hovering();
		}
		return false;
	}
	public boolean isClickDown(){
		Widget w = getInternal();
		if(w!=null){
			return w.isClickDown();
		}
		return false;
	}
	public int visibleCycle(){
		Widget w = getInternal();
		if(w!=null){
			return w.visibleCycle();
		}
		return -1;
	}
	public int pendingVarbitCount(){
		Widget w = getInternal();
		if(w!=null){
			return w.pendingVarbitCount();
		}
		return -1;
	}
	public int changedSkillsCount(){
		Widget w = getInternal();
		if(w!=null){
			return w.changedSkillsCount();
		}
		return -1;
	}
	public int boundsIndex(){
		Widget w = getInternal();
		if(w!=null){
			return w.boundsIndex();
		}
		return -1;
	}
	public int displayCycle(){
		Widget w = getInternal();
		if(w!=null){
			return w.displayCycle();
		}
		return -1;
	}
	public boolean noClickThrough(){
		Widget w = getInternal();
		if(w!=null){
			return w.noClickThrough();
		}
		return false;
	}
	public boolean noScrollThrough(){
		Widget w = getInternal();
		if(w!=null){
			return w.noScrollThrough();
		}
		return false;
	}
	public boolean flippedVertically(){
		Widget w = getInternal();
		if(w!=null){
			return w.flippedVertically();
		}
		return false;
	}
	public boolean flippedHorizontally(){
		Widget w = getInternal();
		if(w!=null){
			return w.flippedHorizontally();
		}
		return false;
	}
	public String[] actions(){
		Widget w = getInternal();
		if(w!=null){
			return w.actions();
		}
		return null;
	}
	public boolean containsAction(String action){
		String[] actions = actions();
		for(int i=0;actions!=null && i<actions.length;++i){
			if(actions[i].equals(action))
				return true;
		}
		return false;
	}
	public int[] itemIDs(){
		Widget w = getInternal();
		if(w!=null){
			return w.itemIDs();
		}
		return null;
	}
	public boolean containsItemID(int id){
		int[] ids = itemIDs();
		for(int i=0;ids!=null && i<ids.length;++i){
			if(ids[i]==id)
				return true;
		}
		return false;
	}
	public int[] itemQuantities(){
		Widget w = getInternal();
		if(w!=null){
			return w.itemQuantities();
		}
		return null;
	}
	public boolean containsItemQuantity(int quantity){
		int[] quantities = itemQuantities();
		for(int i=0;quantities!=null && i<quantities.length;++i){
			if(quantities[i]==quantity)
				return true;
		}
		return false;
	}
	public int childModelRotationHash(){
		Widget w = getInternal();
		if(w!=null){
			return w.childModelRotationHash();
		}
		return -1;
	}
	public int itemStackType(){
		Widget w = getInternal();
		if(w!=null){
			return w.itemStackType();
		}
		return -1;
	}
	public int defaultMargin(){
		Widget w = getInternal();
		if(w!=null){
			return w.defaultMargin();
		}
		return -1;
	}
	public int[] spriteIDs(){
		Widget w = getInternal();
		if(w!=null){
			return w.spriteIDs();
		}
		return null;
	}
	public boolean containsSpriteID(int id){
		int[] ids = spriteIDs();
		for(int i=0;ids!=null && i<ids.length;++i){
			if(ids[i]==id)
				return true;
		}
		return false;
	}
	public int[] ySprites(){
		Widget w = getInternal();
		if(w!=null){
			return w.ySprites();
		}
		return null;
	}
	public int[] xSprites(){
		Widget w = getInternal();
		if(w!=null){
			return w.xSprites();
		}
		return null;
	}
	public int type(){
		Widget w = getInternal();
		if(w!=null){
			return w.type();
		}
		return -1;
	}
	public int[] widgetVarps(){
		Widget w = getInternal();
		if(w!=null){
			return w.widgetVarps();
		}
		return null;
	}
	public boolean containsWidgetVarp(int id){
		int[] ids = widgetVarps();
		for(int i=0;ids!=null && i<ids.length;++i){
			if(ids[i]==id)
				return true;
		}
		return false;
	}
	public String[] configActions(){
		Widget w = getInternal();
		if(w!=null){
			return w.configActions();
		}
		return null;
	}
	public boolean containsConfigAction(String action){
		String[] actions = configActions();
		for(int i=0;actions!=null && i<actions.length;++i){
			if(actions[i].equals(action))
				return true;
		}
		return false;
	}
	public int[][] scriptOpcodes(){
		Widget w = getInternal();
		if(w!=null){
			return w.scriptOpcodes();
		}
		return null;
	}
	public int[] tableActions(){
		Widget w = getInternal();
		if(w!=null){
			return w.tableActions();
		}
		return null;
	}
}

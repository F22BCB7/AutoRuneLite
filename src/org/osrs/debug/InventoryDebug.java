package org.osrs.debug;

import java.awt.Graphics;
import java.awt.Rectangle;

import org.osrs.api.methods.MethodContext;
import org.osrs.api.objects.InventoryItem;
import org.osrs.util.Data;

public class InventoryDebug {
	private MethodContext methods = null;
	public InventoryDebug(MethodContext methods){
		this.methods=methods;
	}
	public Graphics paint(Graphics g){
		if(!Data.clientFrame.inventoryDebugOption.getState() ||
				!methods.inventory.isDisplayed())
			return g;
		
		for(InventoryItem item : methods.inventory.getItems()){
			if(item==null)
				continue;
			Rectangle r = item.getBounds();
			g.drawRect(r.x, r.y, r.width, r.height);
			g.drawString((methods.game.itemSelectionState()==1?(methods.game.lastSelectedItemIndex()==item.getIndex()?"*":""):"")+item.getID(), r.x, r.y+14);
		}
		return g;
	}
}

package org.osrs.debug;

import java.awt.Graphics;
import java.awt.Rectangle;

import org.osrs.api.methods.MethodContext;
import org.osrs.api.objects.EquipmentItem;
import org.osrs.util.Data;

public class EquipmentDebug {
	private MethodContext methods = null;
	public EquipmentDebug(MethodContext methods){
		this.methods=methods;
	}
	public Graphics paint(Graphics g){
		if(!Data.clientFrame.equipmentDebugOption.getState() ||
				!methods.equipment.isDisplayed())
			return g;
		
		for(EquipmentItem item : methods.equipment.getItems()){
			if(item==null)
				continue;
			Rectangle r = item.getBounds();
			g.drawRect(r.x, r.y, r.width, r.height);
			g.drawString(""+item.getID(), r.x, r.y+20);
		}
		return g;
	}
}

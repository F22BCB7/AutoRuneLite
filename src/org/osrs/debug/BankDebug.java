package org.osrs.debug;

import java.awt.Graphics;
import java.awt.Rectangle;

import org.osrs.api.methods.MethodContext;
import org.osrs.api.objects.BankItem;
import org.osrs.api.objects.BankTab;
import org.osrs.util.Data;

public class BankDebug {
	private MethodContext methods = null;
	public BankDebug(MethodContext methods){
		this.methods=methods;
	}
	public Graphics paint(Graphics g){
		if(!Data.clientFrame.bankDebugOption.getState() ||
				!methods.bank.isOpen())
			return g;
		
		for(BankItem item : methods.bank.getItems()){
			if(item==null)
				continue;
			Rectangle r = item.getBounds();
			g.drawRect(r.x, r.y, r.width, r.height);
			g.drawString(""+item.getID(), r.x, r.y+28);
		}
		for(BankTab tab : methods.bank.getTabs()){
			if(tab==null)
				continue;
			Rectangle r = tab.getBounds();
			g.drawRect(r.x, r.y, r.width, r.height);
			if(tab.isMainTab())
				g.drawString((tab.isSelected()?"*":"")+"Main", r.x, r.y+28);
			else if(tab.isAddTab())
				g.drawString((tab.isSelected()?"*":"")+"Add", r.x, r.y+28);
			else
				g.drawString((tab.isSelected()?"*":"")+"Tab "+(tab.getIndex()+1), r.x, r.y+28);
		}
		return g;
	}
}

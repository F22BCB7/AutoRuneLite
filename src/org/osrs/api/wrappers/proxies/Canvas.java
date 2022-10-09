package org.osrs.api.wrappers.proxies;

import org.osrs.debug.BankDebug;
import org.osrs.debug.BoundaryObjDebug;
import org.osrs.debug.CameraDebug;
import org.osrs.debug.EquipmentDebug;
import org.osrs.debug.FloorDecDebug;
import org.osrs.debug.GroundItemDebug;
import org.osrs.debug.InteractableObjDebug;
import org.osrs.debug.InventoryDebug;
import org.osrs.debug.LocationDebug;
import org.osrs.debug.MenuDebug;
import org.osrs.debug.MouseDebug;
import org.osrs.debug.NPCDebug;
import org.osrs.debug.PathDebug;
import org.osrs.debug.PlayerDebug;
import org.osrs.debug.TileDebug;
import org.osrs.debug.WallDecDebug;
import org.osrs.debug.WidgetDebug;
import org.osrs.injection.bytescript.BClass;
import org.osrs.injection.bytescript.BField;
import org.osrs.injection.bytescript.BFunction;
import org.osrs.injection.bytescript.BGetter;
import org.osrs.injection.bytescript.BVar;
import org.osrs.util.Data;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

@BClass(name="Canvas")
public class Canvas extends java.awt.Canvas implements org.osrs.api.wrappers.Canvas{
	@BField
	public java.awt.Component component;
	@BGetter
	@Override
	public java.awt.Component component(){return component;}
	
	@BVar
	public BufferedImage gameImage = new BufferedImage(1000, 700, BufferedImage.TYPE_INT_RGB);
	
	@BFunction
	@Override
	public void applySize(int w, int h){
		this.setSize(w, h);
		gameImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
	}
	@BFunction
	@Override
	public Graphics getGraphics(){
		if(gameImage==null || gameImage.getWidth()!=getWidth() || gameImage.getHeight()!=getHeight())
			gameImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
		
		Graphics g = gameImage.getGraphics();
		try {
			g.setColor(Color.ORANGE);
			g.drawString("AutoRuneLite v0.2", 15, 15);
	
			int gameCycle = Client.clientInstance.gameCycle();
			Widget[][] allWidgets = Client.widgets;
			if(allWidgets!=null){
				for(Widget[] widgets : allWidgets){
					if(widgets!=null){
						for(Widget widget : widgets){
							if(widget!=null){
								widget.setDisplayed(widget.displayCycle()>=gameCycle);
								Widget[] children = widget.children;
								if(children!=null){
									for(Widget child : children){
										if(child!=null){
											child.setDisplayed(child.displayCycle()>=gameCycle);
										}
									}
								}
							}
						}
					}
				}
			}
			if(Client.clientInstance.getMethodContext()!=null){
				WidgetDebug widgets = Client.clientInstance.getWidgetDebug();
				if(widgets!=null){
					if(widgets.debugger.isVisible())
						widgets.paint(g);
				}
	
				g.setColor(Color.ORANGE);
				InventoryDebug inventory = Client.clientInstance.getInventoryDebug();
				if(inventory!=null){
					inventory.paint(g);
				}
	
				PathDebug paths = Client.clientInstance.getPathDebug();
				if(paths!=null){
					paths.paint(g);
				}
	
				TileDebug tile = Client.clientInstance.getTileDebug();
				if(tile!=null){
					tile.paint(g);
				}
				
				CameraDebug camera = Client.clientInstance.getCameraDebug();
				if(camera!=null){
					camera.paint(g);
				}
				
				LocationDebug location = Client.clientInstance.getLocationDebug();
				if(location!=null){
					location.paint(g);
				}
	
				MouseDebug mouse = Client.clientInstance.getMouseDebug();
				if(mouse!=null){
					mouse.paint(g);
				}
				
				MenuDebug menu = Client.clientInstance.getMenuDebug();
				if(menu!=null){
					menu.paint(g);
				}
				
				NPCDebug npcs = Client.clientInstance.getNPCDebug();
				if(npcs!=null){
					npcs.paint(g);
				}
	
				PlayerDebug players = Client.clientInstance.getPlayerDebug();
				if(players!=null){
					players.paint(g);
				}
				
				BoundaryObjDebug boundary = Client.clientInstance.getBoundaryObjDebug();
				if(boundary!=null){
					boundary.paint(g);
				}
				
				FloorDecDebug floor = Client.clientInstance.getFloorDecDebug();
				if(floor!=null){
					floor.paint(g);
				}
				
				InteractableObjDebug interactable = Client.clientInstance.getInteractableObjDebug();
				if(interactable!=null){
					interactable.paint(g);
				}
				
				WallDecDebug wall = Client.clientInstance.getWallDecDebug();
				if(wall!=null){
					wall.paint(g);
				}
	
				GroundItemDebug groundItems = Client.clientInstance.getGroundItemDebug();
				if(groundItems!=null){
					groundItems.paint(g);
				}
	
				BankDebug bank = Client.clientInstance.getBankDebug();
				if(bank!=null){
					bank.paint(g);
				}
	
				EquipmentDebug equip = Client.clientInstance.getEquipmentDebug();
				if(equip!=null){
					equip.paint(g);
				}
			}
			if(Data.currentScript!=null){
				if(Data.currentScript instanceof org.osrs.script.listeners.PaintListener){
					((org.osrs.script.listeners.PaintListener)Data.currentScript).paint(g);
				}
			}
			
			Client.mouseListener.paintMouse(g);
			
			super.getGraphics().drawImage(gameImage, 0, 0, null);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return g;
	}
}
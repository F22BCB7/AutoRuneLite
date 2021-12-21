package org.osrs.debug;

import java.awt.Graphics;

import org.osrs.api.methods.MethodContext;
import org.osrs.api.objects.RSPlayer;
import org.osrs.api.objects.RSTile;
import org.osrs.util.Data;

public class LocationDebug {
	private MethodContext methods = null;
	public LocationDebug(MethodContext methods){
		this.methods=methods;
	}
	public Graphics paint(Graphics g){
		if(Data.clientFrame.locationDebugOption.getState()){
			RSPlayer localPlayer = methods.players.getLocalPlayer();
			if(localPlayer!=null){
				RSTile loc = localPlayer.getLocation();
				g.drawString("Location : "+loc.getX()+", "+loc.getY(), 30, 60);
				g.drawString("Local : "+loc.getLocalX()+", "+loc.getLocalY(), 30, 75);
				g.drawString("Plane : "+loc.getPlane(), 30, 90);
				g.drawString("Map Base : "+methods.game.mapBaseX()+", "+methods.game.mapBaseY(), 30, 105);	
				g.drawString("Region ID : "+((((loc.getX()>>3)/8) << 8) + ((loc.getY()>>3)/8)), 30, 120);	
				g.drawString("Destination : "+methods.walking.getDestination(), 30, 135);	
			}
		}
		return g;
	}
}

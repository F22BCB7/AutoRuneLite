package org.osrs.api.methods;

import java.awt.Point;
import java.util.Random;

import org.osrs.api.objects.RSTile;
import org.osrs.api.wrappers.Client;
import org.osrs.api.wrappers.Tile;

public class Region extends MethodDefinition{
	public Region(MethodContext context){
		super(context);
	}
	public int[][][] getTileHeights(){
		return ((Client)methods.botInstance).tileHeights();
	}
	public RSTile getHoveringTile(){
		org.osrs.api.wrappers.Region region = methods.game.region();
		if(region!=null){
			Tile tile = region.getHoveringTile();
			if(tile!=null)
				return new RSTile(tile.x(), tile.y(), tile.renderPlane());
		}
		return null;
	}
	public void clickMap(RSTile tile){
		if(methods.calculations.onMap(tile)){
			Point pt = methods.calculations.locationToMinimap(tile);
			if(!pt.equals(new Point(-1, -1))){
				methods.mouse.move(pt);
				try{Thread.sleep(new Random().nextInt(40)+10);}catch(@SuppressWarnings("unused") Exception e){}
				methods.mouse.click();
			}
		}
	}
	public int getObjectFlags(int plane, int localX, int localY, long hash){
		return methods.game.getObjectFlags(plane, localX, localY, hash);
	}
}

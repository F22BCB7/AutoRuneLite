package org.osrs.api.methods;

import java.awt.Point;
import java.util.Random;

import org.osrs.api.objects.RSTile;
import org.osrs.api.wrappers.Client;

public class Region extends MethodDefinition{
	public Region(MethodContext context){
		super(context);
	}
	public int[][][] getTileHeights(){
		return ((Client)methods.botInstance).tileHeights();
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
}

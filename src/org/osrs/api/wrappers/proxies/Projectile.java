package org.osrs.api.wrappers.proxies;

import org.osrs.injection.bytescript.BClass;
import org.osrs.injection.bytescript.BField;
import org.osrs.injection.bytescript.BGetter;

@BClass(name="Projectile")
public class Projectile extends RenderableNode implements org.osrs.api.wrappers.Projectile{

	@BField
	public int currentFrameIndex;
	@BGetter
	@Override
	public int currentFrameIndex(){return currentFrameIndex;}
	@BField
	public int frameProgress;
	@BGetter
	@Override
	public int frameProgress(){return frameProgress;}
	@BField
	public int id;
	@BGetter
	@Override
	public int id(){return id;}
	@BField
	public int plane;
	@BGetter
	@Override
	public int plane(){return plane;}
	@BField
	public int startRegionX;
	@BGetter
	@Override
	public int startRegionX(){return startRegionX;}
	@BField
	public int startRegionY;
	@BGetter
	@Override
	public int startRegionY(){return startRegionY;}
	@BField
	public int height;
	@BGetter
	@Override
	public int height(){return height;}
	@BField
	public int startCycle;
	@BGetter
	@Override
	public int startCycle(){return startCycle;}
	@BField
	public int endCycle;
	@BGetter
	@Override
	public int endCycle(){return endCycle;}
	@BField
	public int slope;
	@BGetter
	@Override
	public int slope(){return slope;}
	@BField
	public int startHeight;
	@BGetter
	@Override
	public int startHeight(){return startHeight;}
	@BField
	public int interactingID;
	@BGetter
	@Override
	public int interactingID(){return interactingID;}
	@BField
	public int endHeight;
	@BGetter
	@Override
	public int endHeight(){return endHeight;}
	@BField
	public boolean isLaunched;
	@BGetter
	@Override
	public boolean isLaunched(){return isLaunched;}
	@BField
	public double x;
	@BGetter
	@Override
	public double x(){return x;}
	@BField
	public double velocityX;
	@BGetter
	@Override
	public double velocityX(){return velocityX;}
	@BField
	public double y;
	@BGetter
	@Override
	public double y(){return y;}
	@BField
	public double velocityY;
	@BGetter
	@Override
	public double velocityY(){return velocityY;}
	@BField
	public double z;
	@BGetter
	@Override
	public double z(){return z;}
	@BField
	public double velocityZ;
	@BGetter
	@Override
	public double velocityZ(){return velocityZ;}
	@BField
	public double heightOffset;
	@BGetter
	@Override
	public double heightOffset(){return heightOffset;}
	@BField
	public double scalar;
	@BGetter
	@Override
	public double scalar(){return scalar;}
	@BField
	public int rotationX;
	@BGetter
	@Override
	public int rotationX(){return rotationX;}
	@BField
	public int rotationY;
	@BGetter
	@Override
	public int rotationY(){return rotationY;}
}
package org.osrs.api.wrappers;

public interface Projectile extends RenderableNode{
	public int currentFrameIndex();
	public int frameProgress();
	public int id();
	public int plane();
	public int startRegionX();
	public int startRegionY();
	public int height();
	public int startCycle();
	public int endCycle();
	public int slope();
	public int startHeight();
	public int interactingID();
	public int endHeight();
	public boolean isLaunched();
	public double x();
	public double velocityX();
	public double y();
	public double velocityY();
	public double z();
	public double velocityZ();
	public double heightOffset();
	public double scalar();
	public int rotationX();
	public int rotationY();
}

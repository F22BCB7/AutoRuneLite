package org.osrs.api.wrappers;

public interface AnimableObject {
	public int x();
	public int y();
	public int plane();
	public int height();
	public int id();
	public int startCycle();
	public int currentFrameIndex();
	public int currentFrameLength();
	public boolean isFinished();
}

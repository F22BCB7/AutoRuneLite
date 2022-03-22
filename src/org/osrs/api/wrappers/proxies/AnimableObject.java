package org.osrs.api.wrappers.proxies;

import org.osrs.injection.bytescript.BClass;
import org.osrs.injection.bytescript.BField;
import org.osrs.injection.bytescript.BGetter;
@BClass
public class AnimableObject extends RenderableNode implements org.osrs.api.wrappers.AnimableObject{
	@BField
	public int x;
	@BGetter
	@Override
	public int x() {
		return x;
	}

	@BField
	public int y;
	@BGetter
	@Override
	public int y() {
		return y;
	}

	@BField
	public int plane;
	@BGetter
	@Override
	public int plane() {
		return plane;
	}

	@BField
	public int id;
	@BGetter
	@Override
	public int id() {
		return id;
	}

	@BField
	public int startCycle;
	@BGetter
	@Override
	public int startCycle() {
		return startCycle;
	}

	@BField
	public int currentFrameIndex;
	@BGetter
	@Override
	public int currentFrameIndex() {
		return currentFrameIndex;
	}

	@BField
	public int currentFrameLength;
	@BGetter
	@Override
	public int currentFrameLength() {
		return currentFrameLength;
	}

	@BField
	public boolean isFinished;
	@BGetter
	@Override
	public boolean isFinished() {
		return isFinished;
	}

}

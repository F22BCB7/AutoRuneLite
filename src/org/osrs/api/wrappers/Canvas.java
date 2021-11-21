package org.osrs.api.wrappers;

import java.awt.Component;

import org.osrs.debug.WidgetDebug;

public interface Canvas{
	public void applySize(int w, int h);

	public Component component();

	public WidgetDebug getWidgetDebugFrame();
}
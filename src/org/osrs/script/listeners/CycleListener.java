package org.osrs.script.listeners;

import java.util.EventListener;

public interface CycleListener extends EventListener{
	abstract void gameCycle();
}
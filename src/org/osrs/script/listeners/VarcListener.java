package org.osrs.script.listeners;

import java.util.EventListener;

import org.osrs.script.events.VarcEvent;

public interface VarcListener extends EventListener{
	abstract void varcEvent(VarcEvent rse);
}
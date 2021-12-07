package org.osrs.script.listeners;

import java.util.EventListener;

import org.osrs.api.wrappers.ScriptEvent;

public interface ScriptEventListener extends EventListener {
	abstract void scriptEventStarted(ScriptEvent e);
}
package org.osrs.script.listeners;

import java.util.EventListener;

import org.osrs.script.events.RuneScriptEvent;

public interface RuneScriptVMListener extends EventListener{
	abstract void rsvmEvent(RuneScriptEvent rse);
}
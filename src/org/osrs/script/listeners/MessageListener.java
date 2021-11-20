package org.osrs.script.listeners;

import java.util.EventListener;

import org.osrs.script.events.MessageEvent;

public interface MessageListener extends EventListener {
	abstract void messageReceived(MessageEvent e);
}
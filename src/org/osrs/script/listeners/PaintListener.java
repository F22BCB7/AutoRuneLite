package org.osrs.script.listeners;

import java.awt.Graphics;
import java.util.EventListener;

public interface PaintListener extends EventListener{
	abstract void paint(Graphics g);
}
package org.osrs.api.wrappers;

import java.awt.Graphics;
import java.awt.event.MouseEvent;

public interface MouseListener{
	public int getRealX();
	public int getRealY();
	public int getRealPressX();
	public int getRealPressY();
	public long getRealPressTime();
	public boolean isRealPressed();
	public boolean isRealPresent();
	public int getX();
	public int getY();
	public int getPressX();
	public int getPressY();
	public long getPressTime();
	public boolean isPressed();
	public boolean isPresent();
	public void mouseClicked(MouseEvent e);
	public void mouseDragged(MouseEvent e);
	public void mouseEntered(MouseEvent e);
	public void mouseExited(MouseEvent e);
	public void mouseMoved(MouseEvent e);
	public void mousePressed(MouseEvent e);
	public void mouseReleased(MouseEvent e);
	public void sendEvent(MouseEvent e);
	public void paint(Graphics g);
}
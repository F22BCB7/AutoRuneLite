package org.osrs.api.wrappers;

import java.awt.event.KeyEvent;

public interface KeyboardListener{
	public KeyInputData[] keyInputData();
	public boolean[] keysPressed();
	
	public void keyPressed(KeyEvent e);
	public void keyReleased(KeyEvent e);
	public void keyTyped(KeyEvent e);

}
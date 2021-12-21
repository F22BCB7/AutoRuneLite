package org.osrs.api.wrappers.proxies;

import org.osrs.injection.bytescript.BClass;
import org.osrs.injection.bytescript.BFunction;
import org.osrs.injection.bytescript.BMethod;
import org.osrs.injection.bytescript.BVar;

import java.awt.event.KeyEvent;

@BClass(name="KeyboardListener")
public class KeyboardListener implements org.osrs.api.wrappers.KeyboardListener{
	@BVar
	public boolean isShiftPressed;

	@BMethod(name="keyPressed0")
	public void keyPressed0(KeyEvent e){}
	@BFunction
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.isShiftDown() || e.getKeyCode()==KeyEvent.VK_SHIFT)
			isShiftPressed=true;
		keyPressed0(e);
	}

	@BMethod(name="keyReleased0")
	public void keyReleased0(KeyEvent e){}
	@BFunction
	@Override
	public void keyReleased(KeyEvent e) {
		if(!e.isShiftDown() || e.getKeyCode()==KeyEvent.VK_SHIFT)
			isShiftPressed=false;
		keyReleased0(e);
	}

	@BMethod(name="keyTyped0")
	public void keyTyped0(KeyEvent e){}
	@BFunction
	@Override
	public void keyTyped(KeyEvent e) {
		keyTyped0(e);
	}
}
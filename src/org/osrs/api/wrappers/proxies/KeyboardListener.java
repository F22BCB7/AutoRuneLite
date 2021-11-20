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

	@BMethod(name="keyPressed")
	public void _keyPressed(KeyEvent e){}
	@BFunction
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.isShiftDown() || e.getKeyCode()==KeyEvent.VK_SHIFT)
			isShiftPressed=true;
		_keyPressed(e);
	}

	@BMethod(name="keyReleased")
	public void _keyReleased(KeyEvent e){}
	@BFunction
	@Override
	public void keyReleased(KeyEvent e) {
		if(!e.isShiftDown() || e.getKeyCode()==KeyEvent.VK_SHIFT)
			isShiftPressed=false;
		_keyReleased(e);
	}

	@BMethod(name="keyTyped")
	public void _keyTyped(KeyEvent e){}
	@BFunction
	@Override
	public void keyTyped(KeyEvent e) {
		_keyTyped(e);
	}
}
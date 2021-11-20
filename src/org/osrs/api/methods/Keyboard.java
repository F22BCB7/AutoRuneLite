package org.osrs.api.methods;

import java.awt.Component;
import java.awt.event.KeyEvent;
import java.util.Random;

import org.osrs.util.Data;

public class Keyboard extends MethodDefinition{
	public Keyboard(MethodContext context){
		super(context);
	}
	public int getLocation(final char ch) {
		if (ch >= KeyEvent.VK_SHIFT && ch <= KeyEvent.VK_ALT) {
			return new Random().nextInt((KeyEvent.KEY_LOCATION_RIGHT + 1)-KeyEvent.KEY_LOCATION_LEFT)+KeyEvent.KEY_LOCATION_LEFT;
		}
		return KeyEvent.KEY_LOCATION_STANDARD;
	}
	public void pressKey(char s){
		int code = s;
		if (s >= 'a' && s <= 'z') {
			code -= 32;
		}
		Component keyboardTarget = getTarget();
		KeyEvent event = new KeyEvent(keyboardTarget, KeyEvent.KEY_PRESSED, 0, 0, code, s, getLocation(s));
		Data.inputManager.getKeyboardListener().keyPressed(event);
		event = new KeyEvent(keyboardTarget, KeyEvent.KEY_TYPED, 0, 0, KeyEvent.VK_UNDEFINED, s, 0);
		Data.inputManager.getKeyboardListener().keyTyped(event);
	}
	public void releaseKey(char s){
		int code = s;
		if (s >= 'a' && s <= 'z') {
			code -= 32;
		}
		Component keyboardTarget = getTarget();
		KeyEvent event = new KeyEvent(keyboardTarget, KeyEvent.KEY_RELEASED, 0, 0, code, s, getLocation(s));
		Data.inputManager.getKeyboardListener().keyReleased(event);
		
	}
	public void sendKey(char s){
		int code = s;
		if (s >= 'a' && s <= 'z') {
			code -= 32;
		}
		Component keyboardTarget = getTarget();
		KeyEvent event = new KeyEvent(keyboardTarget, KeyEvent.KEY_PRESSED, 0, 0, code, s, getLocation(s));
		Data.inputManager.getKeyboardListener().keyPressed(event);
		
		event = new KeyEvent(keyboardTarget, KeyEvent.KEY_TYPED, 0, 0, KeyEvent.VK_UNDEFINED, s, 0);
		Data.inputManager.getKeyboardListener().keyTyped(event);
		
		event = new KeyEvent(keyboardTarget, KeyEvent.KEY_RELEASED, 0, 0, code, s, getLocation(s));
		Data.inputManager.getKeyboardListener().keyReleased(event);
		
	}
	public void sendKeys(String str){
		for(int i=0;i<str.length();++i){
			sendKey(str.charAt(i));
			try{
				Thread.sleep(new Random().nextInt(100));
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	private Component getTarget(){
		return (Component)Data.clientInstance;
	}
}


package org.osrs.input;

import org.osrs.api.wrappers.Client;
import org.osrs.api.wrappers.KeyboardListener;
import org.osrs.api.wrappers.MouseListener;

public class InputManager {
	private Client clientInstance = null;
	public InputManager(Client instance){
		clientInstance = instance;
	}
	public MouseListener getMouseListener(){
		if(clientInstance!=null){
			return clientInstance.mouseListener();
		}
		return null;
	}
	public KeyboardListener getKeyboardListener(){
		if(clientInstance!=null){
			return clientInstance.keyboardListener();
		}
		return null;
	}
}

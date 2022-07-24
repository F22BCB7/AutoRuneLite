package org.osrs.api.methods;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import org.osrs.api.wrappers.Client;
import org.osrs.api.wrappers.MouseWheelListener;
import org.osrs.util.Data;

public class MouseWheel extends MethodDefinition{
	public MouseWheel(MethodContext context) {
		super(context);
	}
	public void rotate(int rotation){
		if(Data.clientInstance!=null){
			MouseWheelListener mouse = ((Client)Data.clientInstance).mouseWheelListener();
			if(mouse!=null){
				Component target = getTarget();
				Point mouseLoc = methods.mouse.getLocation();
				MouseWheelEvent me = new MouseWheelEvent(target, MouseEvent.MOUSE_WHEEL, System.currentTimeMillis(), 0, mouseLoc.x, mouseLoc.y, 0, false, MouseWheelEvent.WHEEL_UNIT_SCROLL, 3, rotation);
				mouse.mouseWheelMoved(me);
			}
			else{
				System.out.println("Null MouseWheelListener");
			}
		}
	}
	private Component getTarget(){
		return (Component)Data.clientInstance;
	}
}

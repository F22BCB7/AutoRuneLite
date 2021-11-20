package org.osrs.api.wrappers.proxies;

import org.osrs.injection.bytescript.BClass;
import org.osrs.injection.bytescript.BField;
import org.osrs.injection.bytescript.BFunction;
import org.osrs.injection.bytescript.BMethod;
import org.osrs.injection.bytescript.BGetter;
import java.awt.event.MouseWheelEvent;

@BClass(name="MouseWheelListener")
public class MouseWheelListener implements org.osrs.api.wrappers.MouseWheelListener{

	@BField
	public int rotation;
	@BGetter
	@Override
	public int rotation(){return rotation;}
	
	
	@BMethod(name="_mouseWheelMoved")
	public void _mouseWheelMoved(MouseWheelEvent var1) {}
	@BFunction
	@Override
	public void mouseWheelMoved(MouseWheelEvent var1) {
		//System.out.println(e.toString());
		_mouseWheelMoved(var1);
	}
}
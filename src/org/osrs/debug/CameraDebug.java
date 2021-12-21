package org.osrs.debug;

import java.awt.Graphics;

import org.osrs.api.methods.Camera;
import org.osrs.api.methods.MethodContext;
import org.osrs.util.Data;

public class CameraDebug {
	private MethodContext methods = null;
	public CameraDebug(MethodContext methods){
		this.methods=methods;
	}
	public Graphics paint(Graphics g){
		if(Data.clientFrame.cameraDebugOption.getState()){
			Camera cam = methods.camera;
			g.drawString("Camera X/Y/Z : "+cam.getX()+", "+cam.getY()+", "+cam.getZ(), 30, 60);
			g.drawString("Pitch/Yaw/Angle : "+cam.getPitch()+" : "+cam.getYaw()+" : "+cam.getAngle(), 30, 75);
			g.drawString("Viewport Width/Height/Scale : "+methods.game.viewportWidth()+" : "+methods.game.viewportHeight()+" : "+methods.game.viewportScale(), 30, 90);
		}
		return g;
	}
}

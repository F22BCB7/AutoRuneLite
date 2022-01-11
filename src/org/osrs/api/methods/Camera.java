package org.osrs.api.methods;

import java.applet.Applet;
import java.awt.Component;
import java.awt.event.KeyEvent;

import org.osrs.util.Data;
import org.osrs.api.wrappers.Client;

public class Camera extends MethodDefinition{
	public Camera(MethodContext context){
		super(context);
	}
	public int getX(){
		return ((Client)Data.clientInstance).cameraX();
	}
	public int getY(){
		return ((Client)Data.clientInstance).cameraY();
	}
	public int getZ(){
		return ((Client)Data.clientInstance).cameraZ();
	}
	public int getPitch(){
		return ((Client)Data.clientInstance).cameraPitch();
	}
	public int getYaw(){
		return ((Client)Data.clientInstance).cameraYaw();
	}
	public int getAngle(){
		return (int)(getYaw()/(256.0/45.0));
	}
	public boolean isCameraYawNorth(){
		int yaw = getAngle();
		return yaw > 350 || yaw < 10;
	}
	public boolean isCameraYawWest()	{
		int yaw = getAngle();
		return yaw > 80 && yaw < 100;
	}
	public boolean isCameraYawEast(){
		int yaw = getAngle();
		return yaw > 260 && yaw < 280;
	}
	public boolean isCameraYawSouth(){
		int yaw = getAngle();
		return yaw > 170 && yaw < 190;
	}
	public boolean setPitch(int pitch){
		long start = System.currentTimeMillis();
		if(pitch>383)
			pitch=380;
		if(pitch<149)
			pitch=150;
		int curr = getPitch();
		boolean up = pitch>curr;
		Component keyboardTarget = ((Applet)methods.botInstance).getComponent(0);
		KeyEvent event = new KeyEvent(keyboardTarget, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, (up?KeyEvent.VK_UP:KeyEvent.VK_DOWN), (char)KeyEvent.VK_UNDEFINED, KeyEvent.KEY_LOCATION_STANDARD);
		keyboardTarget.dispatchEvent(event); 
		int last=-1;
		for(int i=0;i<20;){
			curr=getPitch();
			if(last==-1)
				last=curr;
			if(last==curr){
				i++;
			}
			if(up){
				if(curr>=pitch)
					break;
			}
			else{
				if(curr<=pitch)
					break;
			}
			if(System.currentTimeMillis()-start>2000)
				break;
			sleep(100, 200);
		}
		event = new KeyEvent(keyboardTarget, KeyEvent.KEY_RELEASED, System.currentTimeMillis(), 0, (up?KeyEvent.VK_UP:KeyEvent.VK_DOWN), (char)KeyEvent.VK_UNDEFINED, KeyEvent.KEY_LOCATION_STANDARD);
		keyboardTarget.dispatchEvent(event);
		return up?(curr>=pitch):(curr<=pitch);
	}	
	public int getAngleTo(int degrees) {
		int ca = getAngle();
		if (ca < degrees) {
			ca += 360;
		}
		int da = ca - degrees;
		if (da > 180) {
			da -= 360;
		}
		return da;
	}
	public boolean setAngle(int degrees) {
		long start = System.currentTimeMillis();
		Component keyboardTarget = ((Applet)methods.botInstance).getComponent(0);
		if (getAngleTo(degrees) > 5) {
			KeyEvent event = new KeyEvent(keyboardTarget, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_LEFT, (char)KeyEvent.VK_UNDEFINED, KeyEvent.KEY_LOCATION_STANDARD);
			Data.inputManager.getKeyboardListener().keyPressed(event);
			int last=-1;
			for(int i=0;i<20;){
				int curr = getAngleTo(degrees);
				if(last!=-1){
					if(last==curr){
						i++;
					}
				}
				else
					last=curr;
				if(curr<=5)
					break;
				if(System.currentTimeMillis()-start>2000)
					break;
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
				}
			}
			event = new KeyEvent(keyboardTarget, KeyEvent.KEY_RELEASED, System.currentTimeMillis(), 0, KeyEvent.VK_LEFT, (char)KeyEvent.VK_UNDEFINED, KeyEvent.KEY_LOCATION_STANDARD);
			Data.inputManager.getKeyboardListener().keyReleased(event);
		} else if (getAngleTo(degrees) < -5) {
			KeyEvent event = new KeyEvent(keyboardTarget, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_RIGHT, (char)KeyEvent.VK_UNDEFINED, KeyEvent.KEY_LOCATION_STANDARD);
			Data.inputManager.getKeyboardListener().keyPressed(event);
			int last=-1;
			for(int i=0;i<20;){
				int curr = getAngleTo(degrees);
				if(last!=-1){
					if(last==curr){
						i++;
					}
				}
				else
					last=curr;
				if(curr<-5)
					break;
				if(System.currentTimeMillis()-start>2000)
					break;
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
				}
			}
			event = new KeyEvent(keyboardTarget, KeyEvent.KEY_RELEASED, System.currentTimeMillis(), 0, KeyEvent.VK_RIGHT, (char)KeyEvent.VK_UNDEFINED, KeyEvent.KEY_LOCATION_STANDARD);
			Data.inputManager.getKeyboardListener().keyReleased(event);
		}
		return Math.abs(getAngleTo(degrees))<5;
	}
	public void setCompass(final char direction) {
		switch (direction) {
		case 'n':
			setAngle(359);
			break;
		case 'w':
			setAngle(89);
			break;
		case 's':
			setAngle(179);
			break;
		case 'e':
			setAngle(269);
			break;
		default:
			setAngle(359);
			break;
		}
	}
	public void setNorth(){
		setCompass('n');
	}
	public boolean isUp(){
		return getPitch()>=300;
	}
	public boolean isDown(){
		return getPitch()<230;
	}
}

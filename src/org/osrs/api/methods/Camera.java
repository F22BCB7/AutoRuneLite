package org.osrs.api.methods;

import java.applet.Applet;
import java.awt.Component;
import java.awt.event.KeyEvent;

import org.osrs.util.Data;
import org.osrs.api.objects.GameObject;
import org.osrs.api.objects.GroundItem;
import org.osrs.api.objects.RSActor;
import org.osrs.api.objects.RSTile;
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
		methods.game.client().pressedKeys()[up?98:99]=true;
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
		methods.game.client().pressedKeys()[up?98:99]=false;
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
		boolean ccw = getAngleTo(degrees)<0;
		methods.game.client().pressedKeys()[ccw?97:96]=true;
		int last=-1;
		for(int i=0;i<20;){
			int curr = Math.abs(getAngleTo(degrees));
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
		methods.game.client().pressedKeys()[ccw?97:96]=false;
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
	public boolean isFacing(RSActor actor){
		return isFacing(actor.getLocation());
	}
	public boolean isFacing(GameObject object){
		return isFacing(object.getLocation());
	}
	public boolean isFacing(GroundItem item){
		return isFacing(item.getLocation());
	}
	public boolean isFacing(RSTile t){
		int desiredAngle = (((methods.calculations.angleToTile(t)-90)+360)%360);
		int angle = getAngle();
		return angle-10<desiredAngle && angle+10>desiredAngle;
	}
	public boolean turnTo(RSActor actor){
		return turnTo(actor.getLocation());
	}
	public boolean turnTo(GameObject object){
		return turnTo(object.getLocation());
	}
	public boolean turnTo(GroundItem item){
		return turnTo(item.getLocation());
	}
	public boolean turnTo(RSTile t){
		int desiredAngle = (((methods.calculations.angleToTile(t)-90)+360)%360);
		return setAngle(desiredAngle);
	}
}

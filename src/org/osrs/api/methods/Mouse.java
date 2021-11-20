package org.osrs.api.methods;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.util.Random;

import org.osrs.util.Data;

import com.github.joonasvali.naturalmouse.api.MouseMotionFactory;
import com.github.joonasvali.naturalmouse.util.FactoryTemplates;

public class Mouse extends MethodDefinition{
	public Mouse(MethodContext context) {
		super(context);
		mouseHandler = FactoryTemplates.createFastGamerMotionFactory();
	}
	private MouseMotionFactory mouseHandler;
	public static final int LEFT_BUTTON = MouseEvent.BUTTON1;
	public static final int MIDDLE_BUTTON = MouseEvent.BUTTON2;
	public static final int RIGHT_BUTTON = MouseEvent.BUTTON3;
	private static int getButtonModifiers(int button) throws IllegalArgumentException {
		switch (button) {
		case LEFT_BUTTON:
			return InputEvent.BUTTON1_MASK;
		case MIDDLE_BUTTON:
			return InputEvent.BUTTON2_MASK;
		case RIGHT_BUTTON:
			return InputEvent.BUTTON3_MASK;
		default:
			throw new IllegalArgumentException("Not a valid button choice.");
		}
	}    
	public void click(){
		press(LEFT_BUTTON);
		sleep(new Random().nextInt(100)+50);
		release(LEFT_BUTTON);
	}
	public Point getLocation(){
		return new Point(Data.inputManager.getMouseListener().getX(), Data.inputManager.getMouseListener().getY());
	}
	public Point getRealLocation(){
		return new Point(Data.inputManager.getMouseListener().getRealX(), Data.inputManager.getMouseListener().getRealY());
	}
	public boolean isPressed(){
		return Data.inputManager.getMouseListener().isPressed();
	}
	public void move(Point p1) {
		try {
			mouseHandler.move(p1.x, p1.y);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void move(int x, int y){
		try {
			mouseHandler.move(x, y);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void press(int button){
		Point last = getLocation();
		press(last.x, last.y, button);
	}
	public void press(int x, int y, int button){
		int buttonModifiers = getButtonModifiers(button);
		Component target = getTarget();
		Data.inputManager.getMouseListener().sendEvent(new MouseEvent(target, MouseEvent.MOUSE_PRESSED, System.currentTimeMillis(), buttonModifiers, x, y, 1, false, button));
	}
	public void release(int button){
		Point last = getLocation();
		release(last.x, last.y, button);
	}
	public void release(int x, int y, int button){
		int buttonModifiers = getButtonModifiers(button);
		Component target = getTarget();
		Data.inputManager.getMouseListener().sendEvent(new MouseEvent(target, MouseEvent.MOUSE_RELEASED, System.currentTimeMillis(), buttonModifiers, x, y, 1, false, button));
		Data.inputManager.getMouseListener().sendEvent(new MouseEvent(target, MouseEvent.MOUSE_CLICKED, System.currentTimeMillis(), buttonModifiers, x, y, 1, false, button));
	}
	public void rightClick(){
		press(RIGHT_BUTTON);
		sleep(new Random().nextInt(100)+50);
		release(RIGHT_BUTTON);
	}
	private Component getTarget(){
		return (Component)Data.clientInstance;
	}
}

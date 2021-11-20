package org.osrs.script;

import java.awt.Graphics;
import org.osrs.api.methods.MethodContext;

public abstract class ScriptDef extends Thread{
	public abstract void run();
	public boolean isPaused=false;
	public MethodContext methods = null;
	public void setContext(MethodContext context){
		methods=context;
	}
	/**
	 * Returns a random value between 0
	 * and given max value.
	 * @param max
	 * @return random
	 */
	public int random(int max){
		return random(0, max);
	}
	/**
	 * Returns a random value between 
	 * given min and max bounds.
	 * @param min
	 * @param max
	 * @return random
	 */
	public int random(int min, int max){
		return methods.calculations.random(min, max);
	}
	/**
	 * Stops this current instance script
	 */
	@SuppressWarnings("deprecation")
	public void stopScript(){
		isPaused=false;
		this.stop();
	}
	/**
	 * Pauses this current instance script
	 */
	@SuppressWarnings("deprecation")
	public void pause(){
		isPaused=true;
		this.suspend();
	}
	/**
	 * Unpauses this current instance script
	 */
	@SuppressWarnings("deprecation")
	public void unpause(){
		isPaused=false;
		this.resume();
	}
	/**
	 * Current instance script will 
	 * have its thread sleep for given millisecond 
	 * timeout.
	 * @param timeout
	 */
	public void sleep(int timeout){
		try{
			super.sleep(timeout);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}

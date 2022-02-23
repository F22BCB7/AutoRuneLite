package org.osrs.util;

import java.security.SecureRandom;

public class ConditionalSleep {
	//TODO add reaction+lag time
	private SecureRandom random; 
	private int ticks=-1;
	private int minTickSleep=-1;
	private int maxTickSleep=-1;
	public ConditionalSleep(){
		//Default upto 4sec/4000ms; 20*random(min, max);
		random = new SecureRandom();
		ticks = 20;
		minTickSleep = 100;
		maxTickSleep = 200;
	}
	public ConditionalSleep(int count, int min, int max){
		random = new SecureRandom();
		ticks = count;
		minTickSleep = min;
		maxTickSleep = max;
	}
	/**
	 * Override and implement condition for resetting the sleep.
	 * e.g. still mining waiting for ore to get obtained/rock despawns
	 */
	public boolean resetSleep(){
		return false;
	}
	/**
	 * Override and implement condition for ending sleep early.
	 * e.g. rock despawned while mining and didnt recieve ore.
	 */
	public boolean exitSleep(){
		return false;
	}
	public void sleep(){
		for(int sleepCount=0;sleepCount<ticks;sleepCount++){
			try{
				int a = maxTickSleep-minTickSleep;
				int val = random.nextInt(Math.abs(a))+minTickSleep;
				Thread.sleep(val);
				if(resetSleep())
					sleepCount = 0;
				if(exitSleep())
					break;
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}
}

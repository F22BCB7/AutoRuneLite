package org.osrs.api.methods;

import java.security.SecureRandom;

public class MethodDefinition {
	private final SecureRandom random = new SecureRandom();
	protected MethodContext methods=null;
	public MethodDefinition(MethodContext context){
		methods=context;
	}
	public void sleep(int min, int max){
		sleep(random.nextInt(max-min)+min);
	}
	public void sleep(long timeout){
		try{
			Thread.sleep(timeout);
		}
		catch(Exception e){
			e.printStackTrace();
		}
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
		try{
			int a = max-min;
			//random.setSeed(Data.randomSeed);
			return random.nextInt(Math.abs(a))+min;
		}
		catch(Exception e){}
		return (min+max)/2;
	}
	public long random(long min, long max){
		try{
			//random.setSeed(Data.randomSeed);
			return min + (max - min) * random.nextLong();
		}
		catch(Exception e){}
		return (min+max)/2;
	}
	public double random(double min, double max){
		try{
			//random.setSeed(Data.randomSeed);
			return min + (max - min) * random.nextDouble();
		}
		catch(Exception e){}
		return (min+max)/2;
	}
}

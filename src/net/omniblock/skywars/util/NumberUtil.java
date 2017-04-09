package net.omniblock.skywars.util;

import java.util.Random;

public class NumberUtil {
	
	public static int getRandomInt(int min, int max) {
	    Random rand = new Random();
	    int randomNum = rand.nextInt((max - min) + 1) + min;

	    return randomNum;
	}
	
	public static double getRandomDouble(double min, double max) {
	    Random rand = new Random();
	    return rand.nextDouble() * (max - min) + min;
	}
	
}

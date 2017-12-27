/*
 *  Omniblock Developers Team - Copyright (C) 2016
 *
 *  This program is not a free software; you cannot redistribute it and/or modify it.
 *
 *  Only this enabled the editing and writing by the members of the team. 
 *  No third party is allowed to modification of the code.
 *
 */

package net.omniblock.skywars.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ArrayUtils {

	public static String[] getBestAssert(String[] def, String[]... anothers) {

		Map<String[], Integer> asserts = new HashMap<String[], Integer>();

		String[] cachearray = new String[] { "none" };
		int cacheinteger = 0;

		for (String[] k : anothers) {
			int found = assertArgs(def, k);
			asserts.put(k, found);
		}

		for (Map.Entry<String[], Integer> k : asserts.entrySet()) {
			if (cacheinteger < k.getValue()) {
				cachearray = k.getKey();
				cacheinteger = k.getValue();
			}
		}

		return cachearray;
	}

	public static int assertArgs(String[] def, String[] another) {

		int asserts = 0;

		for (String k : def) {
			for (String p : another) {
				if (k.equalsIgnoreCase(p))
					asserts++;
			}
		}

		return asserts;
	}

	public static double getAverage(Double[] averages) {
		
		double sum = 0;
		
		for(Double row : averages)
			sum = sum + row;
		
		return sum / averages.length;
		
	}
	
	public static <T> T[] append(T[] arr, T element) {
	    final int N = arr.length;
	    arr = Arrays.copyOf(arr, N + 1);
	    arr[N] = element;
	    return arr;
	}
	
}

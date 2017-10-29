package net.omniblock.skywars.util;

import java.util.HashMap;
import java.util.Map;

public class MapUtils {

	public static <K, V> Map<K, V> clone(Map<K, V> map) {

		Map<K, V> clone = new HashMap<K, V>();
		clone.putAll(map);

		return clone;

	}

}

package org.mobangjack.common.util;

import java.util.Map;

public class MapUtil {
	
	/**
	 * is null or empty
	 * @param map
	 * @return
	 */
	public static <K,V> boolean isNOE(Map<K,V> map) {
		return map==null||map.isEmpty();
	}

}

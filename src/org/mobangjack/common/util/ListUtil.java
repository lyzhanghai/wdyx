package org.mobangjack.common.util;

import java.util.List;

public class ListUtil {

	/**
	 * is null or empty
	 * @param list
	 * @return
	 */
	public static <T> boolean isNOE(List<T> list) {
		return list==null||list.isEmpty();
	}
}

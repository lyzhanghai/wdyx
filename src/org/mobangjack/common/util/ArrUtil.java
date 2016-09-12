package org.mobangjack.common.util;

public class ArrUtil {

	/**
	 * is null or empty
	 * @param arr
	 * @return
	 */
	public static <T> boolean isNOE(T[] arr) {
		if(arr==null)return true;
		for(T t:arr){
			if(t!=null)
				return false;
		}
		return false;
	}

}

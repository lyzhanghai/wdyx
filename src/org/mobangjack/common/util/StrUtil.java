package org.mobangjack.common.util;

public class StrUtil {

	/**
	 * is null or empty
	 * @return
	 */
	public static boolean isNOE(String str) {
		return str==null||str.equals("");
	}
	
	/**
	 * is null or blank
	 * @return
	 */
	public static boolean isNOB(String str) {
		return isNOE(str)||str.trim().equals("");
	}

	public static Integer toInt(String str) {
		return Integer.parseInt(str);
	}
	
	public static Double toDouble(String str) {
		return Double.parseDouble(str);
	}
	
	public static Long toLong(String str) {
		return Long.parseLong(str);
	}
	
	public static Float toFloat(String str) {
		return Float.parseFloat(str);
	}
}

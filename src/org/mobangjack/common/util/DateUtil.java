package org.mobangjack.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateUtil {

	public static final String FORMAT_DATETIME = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMAT_DATE = "yyyy-MM-dd";
	
	public static String format(Date date,String format) {
		if(date == null) return null;
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        return sdf.format(date);
	}
	
	
    /**
     * 字符串转时间
     * @param dateString
     * @param style
     * @return
     */
    public static Date str2Date(String dateStr, String style) {
        if (dateStr==null||dateStr.trim().equals("")) return null;
        Date date = new Date();
        SimpleDateFormat strToDate = new SimpleDateFormat(style);
        try {
			date = strToDate.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
        return date;
    }
    
    /**
     * 字符串转时间
     * @param dateString
     * @param style
     * @return
     */
    public static Date str2Date(String dateStr) {
        if (dateStr==null||dateStr.trim().equals("")) return null;
        Date date = new Date();
        SimpleDateFormat strToDate = new SimpleDateFormat(FORMAT_DATE);
        try {
			date = strToDate.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
        return date;
    }

    /**
     * 判断传入的时间是否在当前时间之后，返回boolean值
     * true: 过期
     * false: 还没过期
     * @param date
     * @return
     */
    public static boolean isExpire(Date date) {
        if(date.before(new Date())) return true;
        return false;
    }

    public static void main(String[] args) {
        System.out.println(isExpire(DateUtil.str2Date("2015-05-09 20:57:20", FORMAT_DATETIME)));
    }

    public static Date getHourDif(Date date, int dif) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.roll(11, dif);
        return calendar.getTime();
    }

    public static Date getDateDif(Date date, int dif) {
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        now.set(5, now.get(5) + dif);
        return now.getTime();
    }

    public static Date getMinuteDif(Date date, int dif) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, dif);
        return calendar.getTime();
    }

}

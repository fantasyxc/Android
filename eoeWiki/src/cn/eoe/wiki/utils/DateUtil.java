package cn.eoe.wiki.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.text.TextUtils;

public class DateUtil {
	public static final long MINUTE_MILLTS = 60 * 1000;
	public static final long HOUR_MILLTS = MINUTE_MILLTS * 60;
	public static final long DAY_MILLTS = HOUR_MILLTS * 24;
	public static final long WEEK_MILLTS = DAY_MILLTS * 7;
	public static final long MONTH_MILLTS = DAY_MILLTS * 30;
	public static final long HALF_MONTH_MILLTS = MONTH_MILLTS / 2;
	public static final String DATE_DEFAULT_FORMATE = "yyyyMMdd";
	public static final String DATE_FORMATE_ALL = "yyyy-MM-dd HH:mm:ss";
	public static final String DATE_FORMATE_TRANSACTION = "dd/MM/yyyy, HH:mm";
	public static final String DATE_FORMATE_DAY_HOUR_MINUTE = "MM/dd HH:mm";
	public static final String DATE_FORMATE_HOUR_MINUTE = "HH:mm";
	public static final String DATE_FORMATE_HOUR_MINUTE_SECOND = "HH:mm:ss";
	
	public static SimpleDateFormat dateFormate = new SimpleDateFormat();
	
	public static String toTime(long milliseconds) {
		return toTime(new Date(milliseconds), DATE_FORMATE_ALL);
	}
	
	public static String toTime(long milliseconds, String pattern) {
		return toTime(new Date(milliseconds), pattern);
	}
	
	public static String toTime(Date date, String pattern) {
		if (TextUtils.isEmpty(pattern)) {
			pattern = DATE_DEFAULT_FORMATE;
		}
		dateFormate.applyPattern(pattern);
		if (null == date) {
			date = new Date();
		}
		try {
			return dateFormate.format(date);
		} catch (Exception e) {
			return "";
		}
	}
}

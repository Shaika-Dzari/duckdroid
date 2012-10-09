/*
 * DateUtils.java
 * 
 * This program is free software. It comes without any warranty, to
 * the extent permitted by applicable law. You can redistribute it
 * and/or modify it under the terms of the Do What The Fuck You Want
 * To Public License, Version 2, as published by Sam Hocevar. 
 * See http://sam.zoy.org/wtfpl/COPYING for more details. 
 * 
 * @author shaika-dzari
 * @since 2012-10-08
 */ 
package net.nakama.duckdroid.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

	public static final String STD_FORMAT_DATE = "yyyy-MM-dd HH:mm:ss";
	
	public static String format(Date d) {
		return format(d, STD_FORMAT_DATE);
	}
	
	public static String format(Date d, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(d);
	}
}

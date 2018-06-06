package com.ss.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateConventer {
	
	public static Date formatDate(String pattern,Date date) throws Exception {
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
		Date val = dateFormat.parse(dateFormat.format(date));
		return val;
	}
	
	public static Date parseStringDate(String pattern,String dateStr) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
		Date date;
		try {
			date = dateFormat.parse(dateStr);
		} catch (ParseException e) {
			date = new Date();
		}
		return date;
	}
	
	public static Date getTimeFromStamp(Long timestamp) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(timestamp); 
		Date date = c.getTime();
		return date;
	}

}

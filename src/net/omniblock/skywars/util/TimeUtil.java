package net.omniblock.skywars.util;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {

	public static String format(Date date) {
		Format formatter = new SimpleDateFormat("dd/MM/yyyy");
		String s = formatter.format(date);
		return s;
	}
	
	public static String format(int seconds){
		if(seconds >= 3600){
			return toTimeHourFormat(seconds);
		}
		return toTimeMinutesFormat(seconds);
	}
	
	public static String toTimeMinutesFormat(int seconds){
		  int rem = seconds%3600;
		  int mn = rem/60;
		  int sec = rem%60;
		  String mnStr = (mn<10 ? "0" : "")+mn;
		  String secStr = (sec<10 ? "0" : "")+sec; 
		  return mnStr + ":" + secStr;
	}
	
	public static String toTimeHourFormat(int seconds){
		  int hr = seconds/3600;
		  int rem = seconds%3600;
		  int mn = rem/60;
		  int sec = rem%60;
		  String hrStr = (hr<10 ? "0" : "")+hr;
		  String mnStr = (mn<10 ? "0" : "")+mn;
		  String secStr = (sec<10 ? "0" : "")+sec; 
		  return hrStr + ":" + mnStr + ":" + secStr;
	}
	
}

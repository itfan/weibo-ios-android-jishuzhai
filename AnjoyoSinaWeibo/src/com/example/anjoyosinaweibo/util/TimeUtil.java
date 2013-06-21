package com.example.anjoyosinaweibo.util;

import java.util.Calendar;
import java.util.Date;

public class TimeUtil {


    @SuppressWarnings("unused")
	public static String getShortTime(String time) {
        String shortstring =null;
        long now = Calendar.getInstance().getTimeInMillis();
        @SuppressWarnings("deprecation")
		Date date =new Date(time);
        if(date ==null)return shortstring;
        long deltime = (now - date.getTime())/1000;
        if(deltime > 365*24*60*60) {
            shortstring = (int)(deltime/(365*24*60*60)) +"年前";
        }else if(deltime > 24*60*60) {
            shortstring = (int)(deltime/(24*60*60)) +"天前";
        }else if(deltime > 60*60) {
            shortstring = (int)(deltime/(60*60)) +"小时前";
        }else if(deltime > 60) {
            shortstring = (int)(deltime/(60)) +"分前";
        }else if(deltime > 1) {
            shortstring = deltime +"秒前";
        }else{
            shortstring ="1秒前";
        }
        return shortstring;
    }
	
	
}

package com.example.anjoyosinaweibo.util;

import android.content.Context;
import android.content.SharedPreferences;

public class Myspf {

	public static final String NAME="myspfinfo";
	
	public static void saveLoginFlag(Context context){
		SharedPreferences preferences=context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
		preferences.edit().putBoolean("islogin", true).commit();
	}

	public static boolean  getLoginFlag(Context context){
		SharedPreferences preferences=context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
		return preferences.getBoolean("islogin", false);
	}
	
	/**
	 * 将没有发送的微博保存草稿箱
	 * @param context
	 * @param msg
	 */
	public static void saveMsgBox(Context context,String msg){
		SharedPreferences preferences=context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
		preferences.edit().putString("msg", msg).commit();
	}
	/**
	 * 读取保存的微博
	 * @param context
	 * @return
	 */
	public static String getMsgBox(Context context){
		SharedPreferences preferences=context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
		return preferences.getString("msg", "");
	}
	
	
	
	
}

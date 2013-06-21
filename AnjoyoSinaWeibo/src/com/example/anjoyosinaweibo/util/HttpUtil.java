package com.example.anjoyosinaweibo.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class HttpUtil {

	/**
	 *  jian cha wangluo shi 否成功
	 * @param con
	 * @return
	 */
	public static boolean NetISok(Context con) {
		ConnectivityManager connectivityManager = (ConnectivityManager) con.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
		if (networkInfo==null||!networkInfo.isAvailable()) {
			return false;
		}
		return true;
	}

}

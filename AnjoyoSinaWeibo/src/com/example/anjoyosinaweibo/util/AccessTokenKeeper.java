package com.example.anjoyosinaweibo.util;

import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Handler;

import com.example.anjoyosinaweibo.WeiboAct;
import com.weibo.sdk.android.Oauth2AccessToken;
import com.weibo.sdk.android.WeiboParameters;
import com.weibo.sdk.android.net.HttpManager;

/**
 * 该类用于保存Oauth2AccessToken到sharepreference，并提供读取功能
 * 
 * @author xiaowei6@staff.sina.com.cn
 * 
 */
public class AccessTokenKeeper {
	private static final String PREFERENCES_NAME = "com_weibo_sdk_android";

	/**
	 * 保存accesstoken到SharedPreferences
	 * 
	 * @param context
	 *            Activity 上下文环境
	 * @param token
	 *            Oauth2AccessToken
	 */
	public static void keepAccessToken(Context context, Oauth2AccessToken token) {
		SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_APPEND);
		Editor editor = pref.edit();
		editor.putString("token", token.getToken());
		editor.putLong("expiresTime", token.getExpiresTime());
		editor.commit();
	}

	/**
	 * 清空sharepreference
	 * 
	 * @param context
	 */
	public static void clear(Context context) {
		SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_APPEND);
		Editor editor = pref.edit();
		editor.clear();
		editor.commit();
	}

	public static void saveuid(String uid, Context con) {
		SharedPreferences preferences = con.getSharedPreferences(PREFERENCES_NAME, Context.MODE_APPEND);
		preferences.edit().putString("uid", uid).commit();
	}

	public static String getuid(Context con) {
		SharedPreferences preferences = con.getSharedPreferences(PREFERENCES_NAME, Context.MODE_APPEND);
		return preferences.getString("uid", null);
	}

	/**
	 * 从SharedPreferences读取accessstoken
	 * 
	 * @param context
	 * @return Oauth2AccessToken
	 */
	public static Oauth2AccessToken readAccessToken(Context context) {
		Oauth2AccessToken token = new Oauth2AccessToken();
		SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_APPEND);
		token.setToken(pref.getString("token", ""));
		token.setExpiresTime(pref.getLong("expiresTime", 0));
		return token;
	}

	public static void SavecodeToken(final String code, final Context con, final Handler handler) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				String URL = "https://api.weibo.com/oauth2/access_token";
				WeiboParameters params = new WeiboParameters();
				params.add("client_id", ConstantS.APP_KEY);
				params.add("client_secret", ConstantS.APP_SECRET);
				params.add("grant_type", "authorization_code");
				params.add("redirect_uri", ConstantS.REDIRECT_URL);
				params.add("code", code);
				try {
					String json = HttpManager.openUrl(URL, "POST", params, null);
					JSONObject jsonObject = new JSONObject(json);
					String token = jsonObject.getString("access_token");
					String expires_in = jsonObject.getString("expires_in");
					String uid = jsonObject.getString("uid");
					// 保存UID 用于获取用户信息
					saveuid(uid, con);
					Oauth2AccessToken token2 = new Oauth2AccessToken(token, expires_in);
					//保存token
					keepAccessToken(con, token2);
					handler.sendEmptyMessage(WeiboAct.GET_TOKEN_OK);
				} catch (Exception e) {
					handler.sendEmptyMessage(WeiboAct.GET_TOKEN_ERROR);
					e.printStackTrace();
				}
			}
		}).start();
	}

}

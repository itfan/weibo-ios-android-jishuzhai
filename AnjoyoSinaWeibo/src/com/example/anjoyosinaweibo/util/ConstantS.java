package com.example.anjoyosinaweibo.util;


public interface ConstantS {
	
	//应用的key 请到官方申请正式的appkey替换APP_KEY
	public static final String APP_KEY="3792133613";
	//替换为开发�1�7�REDIRECT_URL
	public static final String REDIRECT_URL = "http://www.anjoyo.com";
	
	public static final String APP_SECRET ="b4bca03ce659e6bf28778b419cc60401";
	
	//新支持scope 支持传入多个scope权限，用逗号分隔
	public static final String SCOPE = "email,direct_messages_read,direct_messages_write," +
			"friendships_groups_read,friendships_groups_write,statuses_to_me_read," +
				"follow_app_official_microblog";
	
	public static final String CLIENT_ID = "client_id";
	
	public static final String RESPONSE_TYPE = "response_type";
	
	public static final String USER_REDIRECT_URL = "redirect_uri";
	
	public static final String DISPLAY = "display";
	
	public static final String USER_SCOPE = "scope";
	
	public static final String PACKAGE_NAME = "packagename";
	
	public static final String KEY_HASH = "key_hash";

	   
}

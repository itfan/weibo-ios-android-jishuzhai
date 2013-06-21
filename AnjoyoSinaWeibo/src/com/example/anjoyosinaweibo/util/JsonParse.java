package com.example.anjoyosinaweibo.util;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.anjoyosinaweibo.entry.Comments;
import com.example.anjoyosinaweibo.entry.Statuses;
import com.example.anjoyosinaweibo.entry.User;

public class JsonParse {
	/**
	 * { "created_at": "Tue May 31 17:46:55 +0800 2011", "id": 11488058246,
	 * "text": "求关注。", "source":
	 * "<a href="http://weibo.com" rel="nofollow">新浪微博</a>", "favorited": false,
	 * "truncated": false, "in_reply_to_status_id": "", "in_reply_to_user_id":
	 * "", "in_reply_to_screen_name": "", "geo": null, "mid":
	 * "5612814510546515491", "reposts_count": 8, "comments_count": 9,
	 * "annotations": [], "user": { "id": 1404376560, "screen_name": "zaku",
	 * "name": "zaku", "province": "11", "city": "5", "location": "北京 朝阳区",
	 * "description": "人生五十年，乃如梦如幻；有生斯有死，壮士复何憾。", "url":
	 * "http://blog.sina.com.cn/zaku", "profile_image_url":
	 * "http://tp1.sinaimg.cn/1404376560/50/0/1", "domain": "zaku", "gender":
	 * "m", "followers_count": 1204, "friends_count": 447, "statuses_count":
	 * 2908, "favourites_count": 0, "created_at":
	 * "Fri Aug 28 00:00:00 +0800 2009", "following": false,
	 * "allow_all_act_msg": false, "remark": "", "geo_enabled": true,
	 * "verified": false, "allow_all_comment": true, "avatar_large":
	 * "http://tp1.sinaimg.cn/1404376560/180/0/1", "verified_reason": "",
	 * "follow_me": false, "online_status": 0, "bi_followers_count": 215 } },
	 * 
	 * @param json
	 * @throws JSONException
	 */
	/**
	 * 
	 * 解析微博列表
	 */
	public static ArrayList<Statuses> ParseStatuses(String json) {
		ArrayList<Statuses> statuses = new ArrayList<Statuses>();
		JSONObject obj = null;
		try {
			obj = new JSONObject(json);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		JSONArray array;
		try {
			array = obj.getJSONArray("statuses");
			int n = array.length();
			for (int i = 0; i < n; i++) {
				JSONObject jsonObject = array.getJSONObject(i);
				statuses.add(pareseWeiBo(jsonObject));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return statuses;
		}
		return statuses;
	}

	/**
	 * 解析单个微博内容
	 * 
	 * @param stuobj
	 * @return
	 */
	public static Statuses pareseWeiBo(JSONObject stuobj) {
		Statuses sta = new Statuses();
		try {
			sta.setText(stuobj.getString("text"));
			sta.setAttitudes_count(stuobj.getInt("attitudes_count"));
			sta.setComments_count(stuobj.getInt("comments_count"));
			sta.setCreated_at(stuobj.getString("created_at"));
			sta.setId(stuobj.getLong("id"));
			if (stuobj.has("thumbnail_pic")) {
				sta.setOriginal_pic(stuobj.getString("original_pic"));
				sta.setBmiddle_pic(stuobj.getString("bmiddle_pic"));
				sta.setThumbnail_pic(stuobj.getString("thumbnail_pic"));
			}
			sta.setSource(stuobj.getString("source"));
			sta.setReposts_count(stuobj.getInt("reposts_count"));
			sta.setUser(parseuser(stuobj.getJSONObject("user")));
			// 如果该微博是转发微博
			if (stuobj.has("retweeted_status")) {
				JSONObject jsonObject = stuobj.getJSONObject("retweeted_status");
				sta.setRetweeted_status(pareseWeiBo(jsonObject));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return sta;
	}

	/**
	 * 解析单个User
	 * 
	 * @param jsonobj
	 * @return
	 */
	public static User parseuser(JSONObject jsonobj) {
		User user = new User();
		try {
			user.setId(jsonobj.getLong("id"));
			user.setScreen_name(jsonobj.getString("screen_name"));
			user.setProfile_image_url(jsonobj.getString("profile_image_url"));
			user.setAvatar_large(jsonobj.getString("avatar_large"));
			user.setDescription(jsonobj.getString("description"));
			user.setFavourites_count(jsonobj.getInt("favourites_count"));
			user.setFollowers_count(jsonobj.getInt("followers_count"));
			user.setFriends_count(jsonobj.getInt("friends_count"));
			user.setGender(jsonobj.getString("gender"));// m f
			user.setStatuses_count(jsonobj.getInt("statuses_count"));
			user.setVerified_reason(jsonobj.getString("verified_reason"));
			user.setVerified(jsonobj.getBoolean("verified"));

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return user;
	}

	/**
	 * 获取用户的粉丝列表
	 * @param txt
	 * @return
	 */
	public static ArrayList<User> parseflowerlist(String txt) {
		ArrayList<User> users = new ArrayList<User>();
		try {
			JSONArray array = new JSONObject(txt).getJSONArray("users");
			int n = array.length();
			JSONObject jsonObject;
			User user;
			for (int i = 0; i < n; i++) {
				user = new User();
				jsonObject = array.getJSONObject(i);
				user.setId(jsonObject.getLong("id"));
				user.setProfile_image_url(jsonObject.getString("profile_image_url"));
				user.setScreen_name(jsonObject.getString("screen_name"));
				users.add(user);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return users;
	}
	
	/**
	 * 获取@ me 的微博
	 * @param json
	 * @return
	 */
	public static ArrayList<Comments> parseatme(String json){
		 ArrayList<Comments> comments=new ArrayList<Comments>();
		try {
			JSONArray array=new JSONObject(json).getJSONArray("comments");
			int n=array.length();
			JSONObject jsonObject;
			Comments com;
			for (int i = 0; i < n; i++) {
				jsonObject=array.getJSONObject(i);
				com=new Comments();
				com.setUser(parseuser(jsonObject.getJSONObject("user")));
				com.setStatus(pareseWeiBo(jsonObject.getJSONObject("status")));
				com.setCreated_at(jsonObject.getString("created_at"));
				com.setId(jsonObject.getLong("id"));
				com.setText(jsonObject.getString("text"));
				com.setSource(jsonObject.getString("source"));
				comments.add(com);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return comments;
	}
	

}

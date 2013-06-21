package com.example.anjoyosinaweibo.entry;

public class User {
//	  "id": 1404376560,
//      "screen_name": "zaku",
//      "name": "zaku",
//      "province": "11",
//      "city": "5",
//      "location": "北京 朝阳区",
//      "description": "人生五十年，乃如梦如幻；有生斯有死，壮士复何憾。",
//      "url": "http://blog.sina.com.cn/zaku",
//      "profile_image_url": "http://tp1.sinaimg.cn/1404376560/50/0/1",
//      "domain": "zaku",
//      "gender": "m",
//      "followers_count": 1204,
//      "friends_count": 447,
//      "statuses_count": 2908,
//      "favourites_count": 0,
//      "created_at": "Fri Aug 28 00:00:00 +0800 2009",
//      "following": false,
//      "allow_all_act_msg": false,
//      "remark": "",
//      "geo_enabled": true,
//      "verified": false,
//      "allow_all_comment": true,
//      "avatar_large": "http://tp1.sinaimg.cn/1404376560/180/0/1",
//      "verified_reason": "",
//      "follow_me": false,
//      "online_status": 0,
//      "bi_followers_count": 215
	
	long id;
	String screen_name;
	String location;
	String profile_image_url;//小头像
	String avatar_large;//大头像
	String description;
	int followers_count;
	int friends_count;
	int statuses_count;
	int favourites_count;
	int bi_followers_count; //互粉数
	String gender;//	性别，m：男、f：女、n：未知
    String verified_reason;//V认证原因
	boolean verified;//是否是V认证
    boolean follow_me;
	
	 
	public boolean isFollow_me() {
		return follow_me;
	}
	public void setFollow_me(boolean follow_me) {
		this.follow_me = follow_me;
	}
	public boolean isVerified() {
		return verified;
	}
	public void setVerified(boolean verified) {
		this.verified = verified;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getScreen_name() {
		return screen_name;
	}
	public void setScreen_name(String screen_name) {
		this.screen_name = screen_name;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getProfile_image_url() {
		return profile_image_url;
	}
	public void setProfile_image_url(String profile_image_url) {
		this.profile_image_url = profile_image_url;
	}
	public String getAvatar_large() {
		return avatar_large;
	}
	public void setAvatar_large(String avatar_large) {
		this.avatar_large = avatar_large;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getFollowers_count() {
		return followers_count;
	}
	public void setFollowers_count(int followers_count) {
		this.followers_count = followers_count;
	}
	public int getFriends_count() {
		return friends_count;
	}
	public void setFriends_count(int friends_count) {
		this.friends_count = friends_count;
	}
	public int getStatuses_count() {
		return statuses_count;
	}
	public void setStatuses_count(int statuses_count) {
		this.statuses_count = statuses_count;
	}
	public int getFavourites_count() {
		return favourites_count;
	}
	public void setFavourites_count(int favourites_count) {
		this.favourites_count = favourites_count;
	}
	public int getBi_followers_count() {
		return bi_followers_count;
	}
	public void setBi_followers_count(int bi_followers_count) {
		this.bi_followers_count = bi_followers_count;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getVerified_reason() {
		return verified_reason;
	}
	public void setVerified_reason(String verified_reason) {
		this.verified_reason = verified_reason;
	}
	 
	 
}

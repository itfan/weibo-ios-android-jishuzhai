package com.example.anjoyosinaweibo.entry;

public class Statuses {

//	  "created_at": "Tue May 31 17:46:55 +0800 2011",
//      "id": 11488058246,
//      "text": "求关注。",
//      "source": "<a href="http://weibo.com" rel="nofollow">新浪微博</a>",
//      "favorited": false,
//      "truncated": false,
//      "in_reply_to_status_id": "",
//      "in_reply_to_user_id": "",
//      "in_reply_to_screen_name": "",
//      "geo": null,
//      "mid": "5612814510546515491",
//      "reposts_count": 8,
//      "comments_count": 9,
//      "annotations": [],
	
	public Statuses getRetweeted_status() {
		return retweeted_status;
	}
	public void setRetweeted_status(Statuses retweeted_status) {
		this.retweeted_status = retweeted_status;
	}
	String created_at;
	long id;
	String text;//内容
	int comments_count;//评论数
	int reposts_count;//转发数
	int attitudes_count;//赞
	String	thumbnail_pic	;//	缩略图片地址，没有时不返回此字段
	String bmiddle_pic;		//中等尺寸图片地址，没有时不返回此字段
	String original_pic;        //原始图片地址，没有时不返回此字段
	User user;
	String source;
	Statuses retweeted_status;//转发微博
	
	
	
	
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getCreated_at() {
		return created_at;
	}
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public int getComments_count() {
		return comments_count;
	}
	public void setComments_count(int comments_count) {
		this.comments_count = comments_count;
	}
	public int getReposts_count() {
		return reposts_count;
	}
	public void setReposts_count(int reposts_count) {
		this.reposts_count = reposts_count;
	}
	public int getAttitudes_count() {
		return attitudes_count;
	}
	public void setAttitudes_count(int attitudes_count) {
		this.attitudes_count = attitudes_count;
	}
	public String getThumbnail_pic() {
		return thumbnail_pic;
	}
	public void setThumbnail_pic(String thumbnail_pic) {
		this.thumbnail_pic = thumbnail_pic;
	}
	public String getBmiddle_pic() {
		return bmiddle_pic;
	}
	public void setBmiddle_pic(String bmiddle_pic) {
		this.bmiddle_pic = bmiddle_pic;
	}
	public String getOriginal_pic() {
		return original_pic;
	}
	public void setOriginal_pic(String original_pic) {
		this.original_pic = original_pic;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	
	
	
	
}

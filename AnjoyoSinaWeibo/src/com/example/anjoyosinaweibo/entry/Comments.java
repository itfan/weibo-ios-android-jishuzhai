package com.example.anjoyosinaweibo.entry;

public class Comments {
//	created_at	string	评论创建时间
//	id	int64	评论的ID
//	text	string	评论的内容
//	source	string	评论的来源
//	user	object	评论作者的用户信息字段 详细
//	mid	string	评论的MID
//	idstr	string	字符串型的评论ID
//	status	object	评论的微博信息字段 详细
//	reply_comment object	评论来源评论，当本评论属于对另一评论的回复时返回此字段
	
	String created_at;
	long id;
	String text;
	String source;
	User user;
	Statuses status;
	
	
	
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
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Statuses getStatus() {
		return status;
	}
	public void setStatus(Statuses status) {
		this.status = status;
	}



}

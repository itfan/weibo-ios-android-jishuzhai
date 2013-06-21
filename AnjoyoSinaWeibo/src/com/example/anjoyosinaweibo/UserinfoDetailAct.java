package com.example.anjoyosinaweibo;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.anjoyosinaweibo.adapter.WeiboAdapter;
import com.example.anjoyosinaweibo.api.StatusesAPI;
import com.example.anjoyosinaweibo.api.UsersAPI;
import com.example.anjoyosinaweibo.api.WeiboAPI;
import com.example.anjoyosinaweibo.data.WeiBoData;
import com.example.anjoyosinaweibo.entry.Statuses;
import com.example.anjoyosinaweibo.entry.User;
import com.example.anjoyosinaweibo.util.JsonParse;
import com.weibo.sdk.android.WeiboException;
import com.weibo.sdk.android.net.RequestListener;

public class UserinfoDetailAct extends WeiboAct {

	ListView listView;
	StatusesAPI api;
	User user;
	WeiboAdapter adapter;
	ArrayList<Statuses> statuses = new ArrayList<Statuses>();
	View head ;
	TextView tvnme, tvdseciption, tvlike, tvguanzhu, tvfensi, tvzan;
	ImageView ivicon;
	
	public void showuserinfo(){
		if(user!=null){
			tvnme.setText(user.getScreen_name());
			tvdseciption.setText(user.getDescription());
			tvguanzhu.setText("关注\n"+user.getFollowers_count());
			tvfensi.setText("关注\n"+user.getFollowers_count());
			tvzan.setText("互粉\n"+user.getBi_followers_count());
			if(user.isFollow_me()){
				tvlike.setText("取消关注");
			}else{
				tvlike.setText("加关注");
			}
			MyApplication.finalbitmap.display(ivicon, user.getAvatar_large());
			LoaduserWeibo();
		}
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.userinfodetail_layout);
		String name = getIntent().getStringExtra("username");
		user = WeiBoData.user;
		InitView();
		//是不是自己
		if(name!=null){
			tvnme.setText(name);
			//不是 的话
			UsersAPI api=new UsersAPI(accessToken);
			api.show(name, new RequestListener() {
				@Override
				public void onIOException(IOException arg0) {
				}
				@Override
				public void onError(WeiboException arg0) {
					System.out.println(arg0.getMessage());
				}
				@Override
				public void onComplete4binary(ByteArrayOutputStream arg0) {
				}
				@Override
				public void onComplete(String arg0) {
					System.out.println(arg0);
				   try {
					user=JsonParse.parseuser(new JSONObject(arg0));
					//解析完毕显示用户信息
					handler.postDelayed(new Runnable() {
						@Override
						public void run() {
							showuserinfo();
						}
					}, 500);
				} catch (JSONException e) {
					e.printStackTrace();
				}
				}
			});
		}else{
			showuserinfo();
		}

	}

	private void LoaduserWeibo() {
		api = new StatusesAPI(accessToken);
		api.userTimeline(user.getScreen_name(), 0, 0, 10, 1, false, WeiboAPI.FEATURE.ALL, false, new RequestListener() {
			@Override
			public void onIOException(IOException arg0) {
			}

			@Override
			public void onError(WeiboException arg0) {
			}

			@Override
			public void onComplete4binary(ByteArrayOutputStream arg0) {
			}

			@Override
			public void onComplete(String arg0) {
				statuses.addAll(JsonParse.ParseStatuses(arg0));
				handler.sendEmptyMessage(LOADDATA_OVER);
			}
		});
	}

	private void InitView() {
		listView = (ListView) findViewById(R.id.userinfolistview);
		head = LayoutInflater.from(this).inflate(R.layout.userinfo_listview_headerview, null);
		tvnme = (TextView) head.findViewById(R.id.tvdetailname);
		tvdseciption = (TextView) head.findViewById(R.id.tvdseciption);
		tvlike = (TextView) head.findViewById(R.id.btlike);
		tvzan = (TextView) head.findViewById(R.id.zan);
		tvguanzhu = (TextView) head.findViewById(R.id.guanzhu);
		tvfensi = (TextView) head.findViewById(R.id.fensi);
		ivicon = (ImageView) head.findViewById(R.id.ivicon);
		listView.addHeaderView(head);
		adapter = new WeiboAdapter(this, statuses, null);
		listView.setAdapter(adapter);
	}
	@Override
	protected void handmessage(Message msg) {
		if (msg.what == LOADDATA_OVER) {
			adapter.notifyDataSetChanged();
		}
		super.handmessage(msg);
	}
}

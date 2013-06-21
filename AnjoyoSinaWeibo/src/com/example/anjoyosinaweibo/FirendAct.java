package com.example.anjoyosinaweibo;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.anjoyosinaweibo.api.FriendshipsAPI;
import com.example.anjoyosinaweibo.data.WeiBoData;
import com.example.anjoyosinaweibo.entry.User;
import com.example.anjoyosinaweibo.util.JsonParse;
import com.weibo.sdk.android.WeiboException;
import com.weibo.sdk.android.net.RequestListener;

public class FirendAct extends WeiboAct {

	ImageView usericon;
	TextView tvname, tvstucount, tvfavcount, tvflowercount;
	ArrayList<User> flowerlist = new ArrayList<User>();
	ListView listView;
	LayoutInflater inflater;
	ArrayAdapter<User> adapter;
    RelativeLayout userinfolay,findfrdmap;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.friend_layout);
		
		listView = (ListView) findViewById(R.id.listView1);
		inflater = LayoutInflater.from(con);
		View head=inflater.inflate(R.layout.friend_listviewheader_lay, null);
		listView.addHeaderView(head);
		
		usericon = (ImageView) head.findViewById(R.id.ivicon);
		tvname = (TextView)head. findViewById(R.id.tvusername);
		tvstucount = (TextView)head. findViewById(R.id.tvstatus);
		tvfavcount = (TextView) head.findViewById(R.id.tvfav);
		tvflowercount = (TextView) head.findViewById(R.id.tvlikenum);
		userinfolay=(RelativeLayout) head.findViewById(R.id.showuserinfo);
		findfrdmap=(RelativeLayout) head.findViewById(R.id.findfrdmap);
		
		if (WeiBoData.user != null) {
			showinfo(WeiBoData.user);
		} else {// 请求user信息
		    
		}
		adapter = new ArrayAdapter<User>(con, R.layout.flowerlist_item, R.id.tvname, flowerlist) {
			
			@Override
			public int getCount() {
				if(flowerlist.size()==0)return 1;
				return flowerlist.size();
			}

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				if(getCount()==1){
					return inflater.inflate(R.layout.loading_layut, null);
				}
				View view = inflater.inflate(R.layout.flowerlist_item, null);
				User user = getItem(position);
				MyApplication.finalbitmap.display((ImageView) view.findViewById(R.id.imageView1), user.getProfile_image_url());
				((TextView) view.findViewById(R.id.tvname)).setText(user.getScreen_name());
				return view;
			}
		};

		listView.setAdapter(adapter);
		userinfolay.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
                      startActivity(new Intent(con, UserinfoDetailAct.class));				
			}
		});
		findfrdmap.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                startActivity(new Intent(con, FindFrdMapAct.class));
            }
        });
		usericon.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			    startActivity(new Intent(con, UserinfoDetailAct.class));				
			}
		});
		loadflowerlist();
	}

	public void showinfo(User user) {
		MyApplication.finalbitmap.display(usericon, user.getProfile_image_url());
		tvname.setText(user.getScreen_name());
		tvstucount.setText(user.getStatuses_count() + "       微博");
		tvfavcount.setText(user.getFavourites_count() + "          收藏");
		tvflowercount.setText(user.getFollowers_count() + "        粉丝");
	}

	@Override
	protected void handmessage(Message msg) {
		if (msg.what == LOADDATA_OVER) {
			adapter.notifyDataSetChanged();
		}
		super.handmessage(msg);
	}

	public void loadflowerlist() {
		FriendshipsAPI api = new FriendshipsAPI(accessToken);
		api.followers(WeiBoData.user.getId(), 20, 0, true, new RequestListener() {
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
				flowerlist.addAll(JsonParse.parseflowerlist(arg0));
				handler.sendEmptyMessage(LOADDATA_OVER);
			}
		});

	}


}

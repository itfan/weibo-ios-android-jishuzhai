package com.example.anjoyosinaweibo;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.anjoyosinaweibo.adapter.WeiboAdapter;
import com.example.anjoyosinaweibo.api.StatusesAPI;
import com.example.anjoyosinaweibo.api.UsersAPI;
import com.example.anjoyosinaweibo.api.WeiboAPI;
import com.example.anjoyosinaweibo.customui.PullToRefreshListView;
import com.example.anjoyosinaweibo.customui.PullToRefreshListView.OnRefreshListener;
import com.example.anjoyosinaweibo.data.WeiBoData;
import com.example.anjoyosinaweibo.db.WeiboHelper;
import com.example.anjoyosinaweibo.entry.Statuses;
import com.example.anjoyosinaweibo.util.AccessTokenKeeper;
import com.example.anjoyosinaweibo.util.JsonParse;
import com.weibo.sdk.android.WeiboException;
import com.weibo.sdk.android.net.RequestListener;

public class HomeTimeLine extends WeiboAct {

	PullToRefreshListView listView;
	ImageView refresh, writeweibo;
	TextView username;
	WeiboAdapter adapter;

	@Override
	protected void handmessage(Message msg) {
		switch (msg.what) {
		case LOADDATA_ERROR:
			ToastInfo("刷新数据失败");
			break;
		case LOADDATA_OVER:
			adapter.notifyDataSetChanged();
			listView.onRefreshComplete("最后一次更新时间");
			break;
		case LOADUSER_ERROR:
			username.setText(WeiBoData.user.getScreen_name());
			break;
		}
		super.handmessage(msg);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hometimeine_lay);
		initviews();
		LoadUserinfo();
		addlistener();
	}

	private void initviews() {
		listView = (PullToRefreshListView) findViewById(R.id.listView1);
		username = (TextView) findViewById(R.id.username);
		accessToken = AccessTokenKeeper.readAccessToken(this);
		adapter = new WeiboAdapter(this, WeiBoData.statuses, listView);
		listView.setAdapter(adapter);
		refresh = (ImageView) findViewById(R.id.toolbar_refresh);
		writeweibo = (ImageView) findViewById(R.id.toolbar_writeweibo);
	}

	private void addlistener() {
		writeweibo.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(HomeTimeLine.this, SendWeiBoAct.class));
			}
		});
		listView.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh() {
				refreshLoad();
			}
		});
		refresh.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				refreshLoad();
				listView.onRefresh();
			}
		});
		
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				  Intent IN=new Intent(con, WeiboDetailAct.class);
				  WeiBoData.currentSaatuses=(Statuses) arg0.getAdapter().getItem(arg2);
				  startActivity(IN);
			}
		});
	}
	private void LoadUserinfo() {
		if (WeiBoData.user != null) {
			username.setText(WeiBoData.user.getScreen_name());
		} else {
			UsersAPI usersAPI = new UsersAPI(accessToken);
			// 请求获取用户信息(首选项中已经保存了UID)
			usersAPI.show(Long.parseLong(AccessTokenKeeper.getuid(HomeTimeLine.this)), new RequestListener() {
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
					System.out.println(arg0 + " user ");
					try {
						WeiBoData.user = JsonParse.parseuser(new JSONObject(arg0));
						System.out.println(WeiBoData.user.getDescription() + "------------------");
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			});
		}
	}

	private void refreshLoad() {
		StatusesAPI api = new StatusesAPI(accessToken);
		api.friendsTimeline(0, 0, 30, 1, false, WeiboAPI.FEATURE.ALL, false, new RequestListener() {
			@Override
			public void onIOException(IOException arg0) {
			}

			@Override
			public void onError(WeiboException arg0) {
				handler.sendEmptyMessage(LOADDATA_ERROR);
				System.err.println(arg0.getMessage());
			}

			@Override
			public void onComplete4binary(ByteArrayOutputStream arg0) {
			}

			@Override
			public void onComplete(String arg0) {
				System.out.println("aaaaa========" + arg0);
				if (arg0 != null) {
					new WeiboHelper(HomeTimeLine.this).insertweibo(arg0);
					WeiBoData.statuses.clear();
					WeiBoData.statuses.addAll(JsonParse.ParseStatuses(arg0));
					handler.sendEmptyMessage(LOADDATA_OVER);
				} else {
					handler.sendEmptyMessage(LOADDATA_ERROR);
				}
			}
		});
	}
}

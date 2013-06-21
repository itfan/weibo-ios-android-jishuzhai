package com.example.anjoyosinaweibo;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.example.anjoyosinaweibo.adapter.MypageAdapter;
import com.example.anjoyosinaweibo.api.StatusesAPI;
import com.example.anjoyosinaweibo.api.UsersAPI;
import com.example.anjoyosinaweibo.api.WeiboAPI;
import com.example.anjoyosinaweibo.data.WeiBoData;
import com.example.anjoyosinaweibo.db.WeiboHelper;
import com.example.anjoyosinaweibo.util.AccessTokenKeeper;
import com.example.anjoyosinaweibo.util.JsonParse;
import com.example.anjoyosinaweibo.util.Myspf;
import com.weibo.sdk.android.WeiboException;
import com.weibo.sdk.android.net.RequestListener;

public class Welcome_Guide extends WeiboAct {

	ViewPager pager;
	WeiboHelper helper;
	private void StartHomeline() {
		startActivity(new Intent(con, MainTab.class));
		overridePendingTransition(R.anim.actin, R.anim.actout);
		finish();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		helper = new WeiboHelper(this);
		
		
	}
	
	@Override
	protected void handmessage(Message msg) {
		switch (msg.what) {
		case OAUTH_VALID:
			if (WeiBoData.statuses.size() > 0) {
				handler.sendEmptyMessage(LOADDATA_OVER);
			} else {
				// 如果有效 加载数据
				loaddate();
			}
			break;
		case GET_TOKEN_ERROR:
			ToastInfo("获取token失败");
			break;
		case OAUTH_ERROE:
			ToastInfo("Oauth认证失败");
			break;
		case GET_TOKEN_OK:
			accessToken=AccessTokenKeeper.readAccessToken(this);
			loaddate();
			break;
		case LOADDATA_OVER:
			findViewById(R.id.progressBar1).setVisibility(View.GONE);
			if(!Myspf.getLoginFlag(con)){
				ShowViewPage();
			}else{
				//延迟3秒 启动新界面
			  handler.postDelayed(new Runnable() {
				@Override
				public void run() {
					StartHomeline();		
				}
			}, 3000);
			}
			break;
		}
		super.handmessage(msg);
	}
	
@Override
protected void onResume() {
	super.onResume();

}

	private void ShowViewPage() {
		pager = (ViewPager) findViewById(R.id.viewpage);
		if (!Myspf.getLoginFlag(con)) {
			pager.setVisibility(View.VISIBLE);
			List<View> views = new ArrayList<View>();
			ImageView iv = new ImageView(this);
			iv.setScaleType(ScaleType.FIT_XY);
			iv.setImageResource(R.drawable.guide_350_01);

			ImageView iv1 = new ImageView(this);
			iv1.setScaleType(ScaleType.FIT_XY);
			iv1.setImageResource(R.drawable.guide_350_02);

			ImageView iv2 = new ImageView(this);
			iv2.setScaleType(ScaleType.FIT_XY);
			iv2.setImageResource(R.drawable.guide_350_03);

			View iv3 = getLayoutInflater().inflate(R.layout.page4, null);
			ImageView btstarweibo = (ImageView) iv3.findViewById(R.id.btstarweibo);

			views.add(iv);
			views.add(iv1);
			views.add(iv2);
			views.add(iv3);
			MypageAdapter adapter = new MypageAdapter(views);
			pager.setAdapter(adapter);

			//开始微博的按钮
			btstarweibo.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					//保存首选项 已经不是第一次登陆了
					Myspf.saveLoginFlag(con);
					StartHomeline();
				}
			});
		}
	}
	
	public void loaddate() {
		UsersAPI usersAPI=new UsersAPI(accessToken);
		usersAPI.show(Long.parseLong(AccessTokenKeeper.getuid(con)), new RequestListener() {
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
				try {
				  WeiBoData.user=JsonParse.parseuser(new JSONObject(arg0));
				  System.out.println( WeiBoData.user.getDescription()+"------------------");
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
		//本地有没有缓存上一次的微博信息
		if (helper.getchcheweibo() != null) {
			WeiBoData.statuses.addAll(JsonParse.ParseStatuses(helper.getchcheweibo()));
			handler.sendEmptyMessage(LOADDATA_OVER);
			return;
		}
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
					helper.insertweibo(arg0);
					WeiBoData.statuses.addAll(JsonParse.ParseStatuses(arg0));
					handler.sendEmptyMessage(LOADDATA_OVER);
				}else{
					handler.sendEmptyMessageDelayed(LOADDATA_ERROR, 1000);
				}
			}
		});
	}

}

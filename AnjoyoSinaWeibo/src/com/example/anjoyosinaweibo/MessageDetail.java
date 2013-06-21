package com.example.anjoyosinaweibo;

import java.util.ArrayList;

import android.os.Bundle;
import android.os.Message;
import android.widget.TextView;

import com.example.anjoyosinaweibo.adapter.CommentsAdapter;
import com.example.anjoyosinaweibo.api.UsersAPI;
import com.example.anjoyosinaweibo.api.WeiboAPI;
import com.example.anjoyosinaweibo.customui.PullToRefreshListView;
import com.example.anjoyosinaweibo.entry.Comments;
import com.example.anjoyosinaweibo.util.JsonParse;

public class MessageDetail extends WeiboAct{

	
	PullToRefreshListView listView;
	ArrayList<Comments> comments=new ArrayList<Comments>();
	CommentsAdapter adapter;
	TextView tvtitle;
	public static final int AT_ME=0;
	public static final int COMMENTS_ME=1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.messagedetail);
	    listView=(PullToRefreshListView) findViewById(R.id.pullToRefreshListView1);
	    tvtitle=(TextView) findViewById(R.id.tvtitle);
       int what = getIntent().getIntExtra("what", -1);
       UsersAPI api=new UsersAPI(accessToken);
       if(what==AT_ME){
    	   tvtitle.setText("@我的信息");
    	   api.getatme(0, 0, 20, 1, WeiboAPI.AUTHOR_FILTER.ALL, 0, this);
       }else{
    	   tvtitle.setText("我收到的评论");
    	   api.getComment(0, 0, 20, 1, WeiboAPI.AUTHOR_FILTER.ALL, 0, this);
       }
	    adapter=new CommentsAdapter(this, comments);
	    listView.setAdapter(adapter);
	    listView.onRefresh();
	}
	
	@Override
	public void onComplete(String arg0) {
		comments.addAll(JsonParse.parseatme(arg0));
		handler.sendEmptyMessage(LOADDATA_OVER);
		super.onComplete(arg0);
	}
	@Override
	protected void handmessage(Message msg) {
        if(msg.what==LOADDATA_OVER){
        	//adapter gengxin
        	adapter.notifyDataSetChanged();
        	listView.onRefreshComplete("最后更新时间");
        }
		super.handmessage(msg);
	}
}


package com.example.anjoyosinaweibo;

import java.util.ArrayList;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.anjoyosinaweibo.adapter.SimpleComentAdapter;
import com.example.anjoyosinaweibo.api.CommentsAPI;
import com.example.anjoyosinaweibo.api.WeiboAPI;
import com.example.anjoyosinaweibo.data.WeiBoData;
import com.example.anjoyosinaweibo.entry.Comments;
import com.example.anjoyosinaweibo.entry.Statuses;
import com.example.anjoyosinaweibo.util.JsonParse;
import com.example.anjoyosinaweibo.util.WeiboAutolinkUtil;

public class WeiboDetailAct extends WeiboAct {

    ListView listView;
    View head;
    Statuses statuses;
    TextView tvname, tvstacon, tvrewcon, tvrewcount, tvcomcount, tvlikecount, tvtime, tvsource;
    ImageView usericon, staimg, starewimg;
    // 一跳微博的评论列表
    ArrayList<Comments> comments = new ArrayList<Comments>();
    SimpleComentAdapter adapter;

    public void loadcommlist() {
        CommentsAPI api = new CommentsAPI(accessToken);
        api.show(statuses.getId(), 0, 0, 20, 1, WeiboAPI.AUTHOR_FILTER.ALL, this);
    }
    @Override
    public void onComplete(String arg0) {
        comments.addAll(JsonParse.parseatme(arg0));
        handler.post(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
            }
        });
        super.onComplete(arg0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weibo_detail_layout);
        initview();
        adapter = new SimpleComentAdapter(comments, this);
        listView.setAdapter(adapter);
        if (WeiBoData.currentSaatuses != null) {
            statuses = WeiBoData.currentSaatuses;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    tvname.setText(statuses.getUser().getScreen_name());
                    tvstacon.setText(WeiboAutolinkUtil.Autolink(statuses.getText(),
                            WeiboDetailAct.this));
                    if (statuses.getRetweeted_status() != null) {
                        Statuses rewStatuses = statuses.getRetweeted_status();
                        MyApplication.finalbitmap.display(usericon, statuses.getUser()
                                .getProfile_image_url());
                        staimg.setVisibility(View.GONE);
                        tvrewcon.setText(rewStatuses.getText());
                        tvcomcount.setText(rewStatuses.getComments_count() + "");
                        tvrewcount.setText(rewStatuses.getReposts_count() + "");
                        tvlikecount.setText(rewStatuses.getAttitudes_count() + "");
                        if (rewStatuses.getBmiddle_pic() != null) {
                            starewimg.setVisibility(View.VISIBLE);
                            MyApplication.finalbitmap.display(starewimg,
                                    rewStatuses.getBmiddle_pic());
                        } else {
                            starewimg.setVisibility(View.GONE);
                        }
                    } else {
                        if (statuses.getBmiddle_pic() != null) {
                            starewimg.setVisibility(View.VISIBLE);
                            MyApplication.finalbitmap.display(staimg, statuses.getBmiddle_pic());
                        } else {
                            starewimg.setVisibility(View.GONE);
                        }
                    }

                }
            }, 500);
        }

    }

    private void initview() {
        listView = (ListView) findViewById(R.id.weibo_detaillistview);
        head = getLayoutInflater().inflate(R.layout.weibodetail_listview_headview, null);
        listView.addHeaderView(head);
        tvname = (TextView) head.findViewById(R.id.user_name);
        tvstacon = (TextView) head.findViewById(R.id.tvstatusecontent);
        tvrewcon = (TextView) head.findViewById(R.id.tvrewstatusecontent);
        tvrewcount = (TextView) head.findViewById(R.id.tvrewrewcount);
        tvcomcount = (TextView) head.findViewById(R.id.tvrewcommentcount);
        tvlikecount = (TextView) head.findViewById(R.id.tvrewlikecount);
        tvtime = (TextView) head.findViewById(R.id.tvtime);
        tvsource = (TextView) head.findViewById(R.id.tvsource);
        usericon = (ImageView) head.findViewById(R.id.user_icon);
        staimg = (ImageView) head.findViewById(R.id.tvstatusesimg);
        starewimg = (ImageView) head.findViewById(R.id.tvrewstatusesimg);
    }
    @Override
    protected void onResume() {
        loadcommlist();
        super.onResume();
    }
}

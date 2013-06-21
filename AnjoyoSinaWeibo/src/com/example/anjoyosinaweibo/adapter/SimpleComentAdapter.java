package com.example.anjoyosinaweibo.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.anjoyosinaweibo.MyApplication;
import com.example.anjoyosinaweibo.R;
import com.example.anjoyosinaweibo.entry.Comments;
import com.example.anjoyosinaweibo.util.WeiboAutolinkUtil;

public class SimpleComentAdapter extends BaseAdapter{

    Context context;
    LayoutInflater inflater;
    ArrayList<Comments> coms;
    public SimpleComentAdapter(ArrayList<Comments> coms,Context con){
        this.context=con;
        inflater=LayoutInflater.from(context);
        this.coms=coms;
    }
    
    @Override
    public int getCount() {
//        if(coms==null||coms.size()==0)return 1;
        return coms.size();
    }

    @Override
    public Object getItem(int position) {
        return coms.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        if(getCount()==1){
//            //如果没有数据 显示等待view
//            return inflater.inflate(R.layout.loading_layut, null);
//        }
        ViewhoderComm holder=null;
        if(convertView==null){
            holder=new ViewhoderComm();
            convertView=inflater.inflate(R.layout.comments_item, null);
            holder.ivicon=(ImageView) convertView.findViewById(R.id.ivcomments_usericon);
            holder.tvname=(TextView) convertView.findViewById(R.id.tvcommnets_info);
            holder.tvcomments=(TextView) convertView.findViewById(R.id.tvname);
            convertView.setTag(holder);
        }else{
            holder=(ViewhoderComm) convertView.getTag();
        }
        Comments comments=(Comments) getItem(position);
        try {
            MyApplication.finalbitmap.display(holder.ivicon, comments.getUser().getProfile_image_url());
            holder.tvname.setText(comments.getUser().getScreen_name());
            holder.tvcomments.setText(WeiboAutolinkUtil.Autolink(comments.getText(), context));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertView;
    }

    class ViewhoderComm
    {
        ImageView ivicon;
        TextView tvname,tvcomments;
    }
    
}


package com.example.anjoyosinaweibo.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.anjoyosinaweibo.R;
import com.example.anjoyosinaweibo.entry.Emotion;

public class EmotaionAdapter extends BaseAdapter {
    Context con;
    ArrayList<Emotion> emos;
    LayoutInflater inflater;

    public EmotaionAdapter(Context con, ArrayList<Emotion> emos) {
        this.con = con;
        inflater = LayoutInflater.from(con);
        this.emos = emos;
    }

    @Override
    public int getCount() {
        return emos.size();
    }

    @Override
    public Object getItem(int position) {
        return emos.get(position).getName();
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        viewholderemotion em = null;
        if (convertView == null) {
            em = new viewholderemotion();
            convertView = inflater.inflate(R.layout.emtion_item_layout, null);
            em.iv = (ImageView) convertView.findViewById(R.id.ivemotion);
            convertView.setTag(em);
        } else {
            em = (viewholderemotion) convertView.getTag();
        }
        em.iv.setImageBitmap(emos.get(position).getBitmap());
        return convertView;
    }

    class viewholderemotion {
        ImageView iv;
    }
}

package com.example.anjoyosinaweibo.adapter;

import java.util.ArrayList;

import net.tsz.afinal.FinalBitmap;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.anjoyosinaweibo.ImageDetailAct;
import com.example.anjoyosinaweibo.MyApplication;
import com.example.anjoyosinaweibo.R;
import com.example.anjoyosinaweibo.UserinfoDetailAct;
import com.example.anjoyosinaweibo.entry.Comments;
import com.example.anjoyosinaweibo.entry.Statuses;
import com.example.anjoyosinaweibo.util.TimeUtil;
import com.example.anjoyosinaweibo.util.WeiboAutolinkUtil;

public class CommentsAdapter extends BaseAdapter {

	Context con;
	ArrayList<Comments> comments;
	LayoutInflater inflater;
	FinalBitmap finalAnsy;
	Bitmap iconbit;
	Bitmap loadingimg;

	public CommentsAdapter(Context con, ArrayList<Comments> comments) {
		this.con = con;
		inflater = LayoutInflater.from(con);
		this.comments = comments;
		finalAnsy = MyApplication.finalbitmap;
		// 两个加载状态是显示的图像
		iconbit = BitmapFactory.decodeResource(con.getResources(), R.drawable.new_regist_poppic_default);
		loadingimg = BitmapFactory.decodeResource(con.getResources(), R.drawable.skin_loading_icon);
	}

	@Override
	public int getCount() {
		return comments.size();
	}

	@Override
	public Object getItem(int position) {
		return comments.get(position);
	}
	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.item_layout, null);
			holder.ivicon = (ImageView) convertView.findViewById(R.id.ivicon);
			holder.ivimg = (ImageView) convertView.findViewById(R.id.statusesimg);
			holder.tvname = (TextView) convertView.findViewById(R.id.username);
			holder.tvtext = (TextView) convertView.findViewById(R.id.tvtext);
			holder.tvcommentcout = (TextView) convertView.findViewById(R.id.commentcount);
			holder.tvredcount = (TextView) convertView.findViewById(R.id.redirectcount);
			holder.tvlikecount = (TextView) convertView.findViewById(R.id.likecount);
			holder.tvretcontent = (TextView) convertView.findViewById(R.id.red_stu_content);
			holder.tvtime = (TextView) convertView.findViewById(R.id.tvtime);
			holder.tvsource = (TextView) convertView.findViewById(R.id.tvsource);
			holder.ivrewimg = (ImageView) convertView.findViewById(R.id.red_stu_img);
			holder.rewlayout = (RelativeLayout) convertView.findViewById(R.id.retw_layout);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		final Comments comm = this.comments.get(position);
		// 异步记载头像
		finalAnsy.display(holder.ivicon, comm.getUser().getProfile_image_url(), iconbit, iconbit);
		holder.ivimg.setVisibility(View.GONE);
		
		holder.tvname.setText(comm.getUser().getScreen_name());
		/**如果spandbleString 无法点击需要添加下面的属性*/
		holder.tvtext.setMovementMethod(LinkMovementMethod.getInstance());
		
		holder.tvtext.setText(WeiboAutolinkUtil.Autolink(comm.getText(), con));
		holder.tvcommentcout.setVisibility(View.GONE);
		holder.tvredcount.setVisibility(View.GONE);
		holder.tvlikecount.setText(" + " + comm.getStatus().getAttitudes_count() + "");
		holder.tvtime.setText(TimeUtil.getShortTime(comm.getCreated_at()));
		holder.tvsource.setText(Html.fromHtml(comm.getSource()));
		if (comm.getStatus() != null) {
			holder.rewlayout.setVisibility(View.VISIBLE);
			holder.ivimg.setVisibility(View.GONE);
			Statuses rewsStatuses = comm.getStatus();
			holder.tvretcontent.setMovementMethod(LinkMovementMethod.getInstance());
			holder.tvretcontent.setText(WeiboAutolinkUtil.Autolink(rewsStatuses.getText(), con));
			String rewimg = rewsStatuses.getThumbnail_pic();
			if (rewimg != null) {
				finalAnsy.display(holder.ivrewimg, rewimg, loadingimg, loadingimg);
			} else {
				holder.ivrewimg.setVisibility(View.GONE);
			}
		} else {
			holder.rewlayout.setVisibility(View.GONE);
		}
		holder.ivrewimg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent in = new Intent(con, ImageDetailAct.class);
				in.putExtra("orurl", comm.getStatus().getRetweeted_status().getOriginal_pic());
				con.startActivity(in);
			}
		});
		holder.ivicon.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
                    Intent in=new Intent(con, UserinfoDetailAct.class);
                    in.putExtra("username", comm.getStatus().getUser().getScreen_name());
                    con.startActivity(in);
			}
		});
		return convertView;
	}

	class ViewHolder {
		TextView tvname, tvtext, tvcommentcout, tvredcount, tvretcontent, tvtime, tvsource, tvlikecount;
		ImageView ivicon, ivimg, ivrewimg;
		RelativeLayout rewlayout;
	}

}

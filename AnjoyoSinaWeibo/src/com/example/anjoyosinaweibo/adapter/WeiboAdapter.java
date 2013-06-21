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
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.anjoyosinaweibo.ImageDetailAct;
import com.example.anjoyosinaweibo.MyApplication;
import com.example.anjoyosinaweibo.R;
import com.example.anjoyosinaweibo.UserinfoDetailAct;
import com.example.anjoyosinaweibo.entry.Statuses;
import com.example.anjoyosinaweibo.util.TimeUtil;
import com.example.anjoyosinaweibo.util.WeiboAutolinkUtil;

public class WeiboAdapter extends BaseAdapter {

	Context con;
	ArrayList<Statuses> statuses;
	LayoutInflater inflater;
	FinalBitmap finalAnsy;
	Bitmap iconbit;
	Bitmap loadingimg;

	public WeiboAdapter(Context con, ArrayList<Statuses> statuses, ListView listView) {
		this.con = con;
		inflater = LayoutInflater.from(con);
		this.statuses = statuses;
		finalAnsy = MyApplication.finalbitmap;
		// 两个加载状态是显示的图像
		iconbit = BitmapFactory.decodeResource(con.getResources(), R.drawable.new_regist_poppic_default);
		loadingimg = BitmapFactory.decodeResource(con.getResources(), R.drawable.skin_loading_icon);
	}

	@Override
	public int getCount() {
		return statuses.size();
	}

	@Override
	public Object getItem(int position) {
		return statuses.get(position);
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

		final Statuses statuses = this.statuses.get(position);
		// 异步记载头像
		finalAnsy.display(holder.ivicon, statuses.getUser().getProfile_image_url(), iconbit, iconbit);

		String img = statuses.getThumbnail_pic();
		holder.ivimg.setVisibility(View.VISIBLE);

		finalAnsy.display(holder.ivimg, img, loadingimg, loadingimg);
		holder.tvname.setText(statuses.getUser().getScreen_name());
		/**如果spandbleString 无法点击需要添加下面的属性*/
		holder.tvtext.setMovementMethod(LinkMovementMethod.getInstance());
		
		holder.tvtext.setText(WeiboAutolinkUtil.Autolink(statuses.getText(), con));
		holder.tvcommentcout.setText(statuses.getComments_count() + "");
		holder.tvredcount.setText(statuses.getReposts_count() + "");
		holder.tvlikecount.setText(" + " + statuses.getAttitudes_count() + "");
		holder.tvtime.setText(TimeUtil.getShortTime(statuses.getCreated_at()));
		holder.tvsource.setText(Html.fromHtml(statuses.getSource()));
		if (statuses.getRetweeted_status() != null) {
			holder.rewlayout.setVisibility(View.VISIBLE);
			holder.ivimg.setVisibility(View.GONE);
			Statuses rewsStatuses = statuses.getRetweeted_status();
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
				in.putExtra("orurl", statuses.getRetweeted_status().getOriginal_pic());
				con.startActivity(in);
			}
		});
		holder.ivimg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent in = new Intent(con, ImageDetailAct.class);
				in.putExtra("orurl", statuses.getOriginal_pic());
				con.startActivity(in);
			}
		});

		holder.ivicon.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
                    Intent in=new Intent(con, UserinfoDetailAct.class);
                    in.putExtra("username", statuses.getUser().getScreen_name());
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

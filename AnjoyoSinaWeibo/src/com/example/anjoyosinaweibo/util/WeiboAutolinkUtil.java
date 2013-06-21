package com.example.anjoyosinaweibo.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.Toast;

import com.example.anjoyosinaweibo.MyApplication;
import com.example.anjoyosinaweibo.UserinfoDetailAct;

public class WeiboAutolinkUtil {

	
	@SuppressWarnings("deprecation")
	public static SpannableString Autolink(String txt,final Context con){
		SpannableString spannableString=new SpannableString(txt);
		Pattern pattern = Pattern.compile("@([a-zA-Z0-9_\\-\\u4e00-\\u9fa5]+)|"
				+ "#([^\\#|.]+)#" + "|http://t\\.cn/\\w|" + "\\[(\\S+?)\\]");
		Matcher matcher=pattern.matcher(txt);
		while(matcher.find()){
		    final	String group=matcher.group();
			if(group.startsWith("@")){
				//设置点击
				spannableString.setSpan(new ClickableSpan() {
					@Override
					public void onClick(View widget) {
						Toast.makeText(con, group, Toast.LENGTH_SHORT).show();
						Intent in=new Intent(con, UserinfoDetailAct.class);
						in.putExtra("username", group.substring(1));
						con.startActivity(in);
					}
				}, matcher.start(), matcher.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
				//设置颜色
				spannableString.setSpan(new ForegroundColorSpan(0xff0077ff), matcher.start(), matcher.end(),
				Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			}else if(group.startsWith("#")){
				spannableString.setSpan(new ForegroundColorSpan(0xff0077ff), matcher.start(), matcher.end(),
						Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
					}else if(group.startsWith("#")){
			}else if(group.startsWith("[")){
				Bitmap b=MyApplication.getInstence().getEmotion(group);
				spannableString.setSpan(new ImageSpan(b), matcher.start(), matcher.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			}else if(group.startsWith("http://")){
//				Bitmap b=BitmapFactory.decodeResource(con.getResources(), R.drawable.d_bizui);
//				spannableString.setSpan(new ImageSpan(b), matcher.start(), matcher.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			}
			
		}
		return spannableString;
	}
	
	
}

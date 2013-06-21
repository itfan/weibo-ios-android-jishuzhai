package com.example.anjoyosinaweibo.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;

public class BitmapUtil {

	public static final String CACHESPATH = "mnt/sdcard/anjoyweibo/cache/";

	/**
	 * 将网络图片 转换成Bitmap
	 * 
	 * @param path
	 * @return
	 */
	public static Bitmap getBit(String path) {
		//从SD卡获取缓存到本地的 图片
		Bitmap bitmap =getbitfromfile(path);
		if (bitmap == null) {
			try {
				URL url = new URL(path);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setDoInput(true);
				conn.connect();
				bitmap = BitmapFactory.decodeStream(conn.getInputStream());
				saveBitmap(path, bitmap);
				conn.disconnect();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return bitmap;
	}
	public static Bitmap getbitfromfile(String url) {
		return BitmapFactory.decodeFile(CACHESPATH + MD5Util.MD5(url) + ".png");
	}

	/**
	 * 保存图片
	 * @param url
	 * @param bitmap
	 */
	public static  void saveBitmap(String url, Bitmap bitmap) {
		File file = new File(CACHESPATH + MD5Util.MD5(url) + ".png");
//		file.getParentFile().length()
		if (!file.exists()) {
			file.getParentFile().mkdirs();
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		FileOutputStream os;
		try {
			os = new FileOutputStream(file);
			bitmap.compress(CompressFormat.PNG, 100, os);
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}

package com.example.anjoyosinaweibo.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class WeiboHelper {

	String DBNAME = "weibo.db";
	String table_name = "weiboinfo";
	SQLiteDatabase db;
	WeiboDB helper;

	public WeiboHelper(Context con) {
		helper = new WeiboDB(con, DBNAME, null, 1);
	}

	public void insertweibo(String weibocon) {
		if (getchcheweibo() != null){
			    delete();
			}
		db = helper.getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put("weibocontent", weibocon);
		db.insert(table_name, null, cv);
		db.close();
	}
	public String getchcheweibo() {
		try {
			db = helper.getReadableDatabase();
			Cursor c = db.query(table_name, null, null, null, null, null, null);
			String info="";
			while (c.moveToNext()) {
				info=c.getString(1);
			}
			c.close();
			db.close();
			return info;
		} catch (Exception e) {
		}
		
		return null;
	}

	public void delete() {
		db = helper.getReadableDatabase();
		db.delete(table_name, null, null);
		db.close();
	}

}

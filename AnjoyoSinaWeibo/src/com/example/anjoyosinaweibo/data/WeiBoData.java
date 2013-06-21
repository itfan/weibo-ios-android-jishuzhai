package com.example.anjoyosinaweibo.data;

import java.util.ArrayList;

import com.example.anjoyosinaweibo.entry.Statuses;
import com.example.anjoyosinaweibo.entry.User;

public class WeiBoData {

	/**存放主界微博面数据的list*/
	public static ArrayList<Statuses> statuses=new ArrayList<Statuses>();
	
	public static User user=new User();
	/**每次跳转到详情界面要赋值的*/
	public static Statuses currentSaatuses=new Statuses();
	
}

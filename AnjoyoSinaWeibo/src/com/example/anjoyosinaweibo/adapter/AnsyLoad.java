package com.example.anjoyosinaweibo.adapter;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;

import com.example.anjoyosinaweibo.util.BitmapUtil;

public class AnsyLoad {

	private Map<String, SoftReference<Bitmap>> caches;
	private List<Task> taskqueen;

	public AnsyLoad() {
		caches = new HashMap<String, SoftReference<Bitmap>>();
		taskqueen = new ArrayList<Task>();
		thread.start();
	}
	
	Handler handler=new Handler(){
		@Override
		public void handleMessage(Message msg) {
			Task t=(Task) msg.obj;
			t.imgCallBack.loadimg(t.url, t.bitmap);
			System.out.println("回调加载");
			super.handleMessage(msg);
		}
		
	};

	Thread thread = new Thread() {
		public void run() {
			while (true) {
				// 当 有任务的时候
				while (taskqueen.size() > 0) {
					Task task = taskqueen.remove(0);
                    task.bitmap=BitmapUtil.getBit(task.url);
					caches.put(task.url, new SoftReference<Bitmap>(task.bitmap));
					System.out.println("下载完放入"+task.url);
					Message m=new Message();
					m.obj=task;
					handler.sendMessage(m);
					// 当执行完任务 线程锁定
					synchronized (this) {
						try {
							this.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
	};


	
	
	
	public Bitmap display(String url,ImgCallBack callBack){
		Bitmap bitmap=null;
		if(caches.containsKey(url)){
			//获取 软引用中的图片
			bitmap=caches.get(url).get();
			if(bitmap!=null){
				return bitmap;
			}else{
			  //如果这个图片 被释放掉 移除
				caches.remove(url);
			}
		}
		if(!caches.containsKey(url)){
			Task task=new Task();
			task.url=url;
			task.imgCallBack=callBack;
			taskqueen.add(task);
			
			//唤醒线程
			synchronized (thread) {
				thread.notify();
			}
		}
		return null;
	}
	
	
	public class Task {
		String url;
		Bitmap bitmap;
		ImgCallBack imgCallBack;
	}

	public interface ImgCallBack {
		void loadimg(String url, Bitmap bitmap);
	}

}


package com.example.anjoyosinaweibo;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import net.tsz.afinal.FinalBitmap;
import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.anjoyosinaweibo.entry.Emotion;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class MyApplication extends Application {

    /**
     * key [花心] value
     * http://img.t.sinajs.cn/t35/style/images/common/face/ext/normal
     * /8c/hsa_org.png
     */
    Map<String, String> emotions = new HashMap<String, String>();
    Map<String, Bitmap> emos = new HashMap<String, Bitmap>();
    public ArrayList<Emotion> emotionlist = new ArrayList<Emotion>();

    public static FinalBitmap finalbitmap;
    public static MyApplication instence;

    @Override
    public void onCreate() {
        finalbitmap = FinalBitmap.create(this, "mnt/sdcard/anjoyoweibo/weibocaches", 0.4f, 30, 3);
        super.onCreate();
        instence = this;
        loademotion();
    }

    public static MyApplication getInstence() {
        return instence;
    }

    public void loademotion(){
		InputStream is=getResources().openRawResource(R.raw.emotions);
		emotions = new Gson().fromJson(new InputStreamReader(is),
				new TypeToken<Map<String, String>>() {
                }.getType());
		
         if(emotions!=null){
        	 Set<String> kes=emotions.keySet();
        	 for (String string : kes) {
				String url=emotions.get(string);
				String pngname=url.substring(url.lastIndexOf("/")+1);
				try {
					Bitmap b=BitmapFactory.decodeStream(getAssets().open(pngname));
					Emotion emotion=new Emotion(getKeyByValue(url), b);
					emotionlist.add(emotion);
					if(b!=null){
						emos.put(string, b);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
         }		
		
	}

    public String getKeyByValue(String value){
	    Set set=emotions.entrySet();
	    Iterator it=set.iterator();
	    while(it.hasNext()) {
	     Map.Entry entry=(Map.Entry)it.next();
	     if(entry.getValue().equals(value)) {
	        return entry.getKey().toString();
	     }
	    }
	   return "";
	   
	}

    public Bitmap getEmotion(String key) {
        return emos.get(key);
    }

}

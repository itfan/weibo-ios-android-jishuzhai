package com.example.anjoyosinaweibo.entry;

import java.io.Serializable;

import android.graphics.Bitmap;

public class Emotion implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    /**
     * 
     */
    String name;
    Bitmap bitmap;
    public Emotion(){}
    public Emotion(String name, Bitmap bitmap) {
        super();
        this.name = name;
        this.bitmap = bitmap;
    }
    
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Bitmap getBitmap() {
        return bitmap;
    }
    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
    
    
}

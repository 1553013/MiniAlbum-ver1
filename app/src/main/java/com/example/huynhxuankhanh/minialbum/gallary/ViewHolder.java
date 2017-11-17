package com.example.huynhxuankhanh.minialbum.gallary;

import android.widget.ImageView;

/**
 * Created by HUYNHXUANKHANH on 11/2/2017.
 */

// design pattern: VIEW HOLDER
public class ViewHolder {
    private ImageView image;
    public ImageView getImageView(){
        return image;
    }
    public void setImageView(ImageView image){
        this.image = image;
    }

}

package com.example.huynhxuankhanh.minialbum.gallary;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.huynhxuankhanh.minialbum.R;

/**
 * Created by HUYNHXUANKHANH on 11/2/2017.
 */
// design pattern: VIEW HOLDER
public class ViewHolder {
    private static ImageView image;
    public ImageView getImageView(){
        return image;
    }
    public void setImageView(ImageView image){
        this.image = image;
    }

}

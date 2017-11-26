package com.example.huynhxuankhanh.minialbum.gallery;

import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by HUYNHXUANKHANH on 11/2/2017.
 */

// design pattern: VIEW HOLDER
public class ViewHolder {
    private ImageView image;
    private TextView text1;
    private TextView text2;

    public ImageView getImageView() {
        return image;
    }

    public void setImageView(ImageView image) {
        this.image = image;
    }

    public ImageView getImage() {
        return image;
    }

    public void setImage(ImageView image) {
        this.image = image;
    }

    public TextView getText1() {
        return text1;
    }

    public void setText1(TextView text1) {
        this.text1 = text1;
    }

    public TextView getText2() {
        return text2;
    }

    public void setText2(TextView text2) {
        this.text2 = text2;
    }
}

package com.example.huynhxuankhanh.minialbum.process;

import android.graphics.Bitmap;

import com.example.huynhxuankhanh.minialbum.gallery.InfoImage;

/**
 * Created by HUYNHXUANKHANH on 12/12/2017.
 */

public interface SetView {
    Bitmap onSetView(Bitmap bitmap,InfoImage infoImage);
}

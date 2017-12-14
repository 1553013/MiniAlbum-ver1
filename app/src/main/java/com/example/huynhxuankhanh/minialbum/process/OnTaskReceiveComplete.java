package com.example.huynhxuankhanh.minialbum.process;

import com.example.huynhxuankhanh.minialbum.gallery.InfoImage;

import java.util.ArrayList;

/**
 * Created by HUYNHXUANKHANH on 12/10/2017.
 */

public interface OnTaskReceiveComplete {
    void OnTaskReceiveComplete(InfoImage infoImage, ArrayList<String> PathFiles);
}

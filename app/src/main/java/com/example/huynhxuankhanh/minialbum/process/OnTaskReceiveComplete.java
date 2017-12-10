package com.example.huynhxuankhanh.minialbum.process;

import com.example.huynhxuankhanh.minialbum.gallery.InfoImage;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by HUYNHXUANKHANH on 12/10/2017.
 */

public interface OnTaskReceiveComplete {
    public void OnTaskReceiveComplete(InfoImage infoImage, ArrayList<String> PathFiles);
}

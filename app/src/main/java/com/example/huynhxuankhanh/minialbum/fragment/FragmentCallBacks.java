package com.example.huynhxuankhanh.minialbum.fragment;

import com.example.huynhxuankhanh.minialbum.gallery.InfoFolder;
import com.example.huynhxuankhanh.minialbum.gallery.InfoImage;
import com.example.huynhxuankhanh.minialbum.gallery.LoadGallary;

import java.util.List;

/**
 * Created by HUYNHXUANKHANH on 11/25/2017.
 */

public interface FragmentCallBacks {
    public void onMsgFromMainToFragmentImage(List<InfoImage> listImage);
    public void onMsgFromMainToFragmentFolder(List<InfoFolder> listFolder);
}

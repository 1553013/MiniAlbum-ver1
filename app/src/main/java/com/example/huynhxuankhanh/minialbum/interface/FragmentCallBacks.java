package com.example.huynhxuankhanh.minialbum.fragment;

import com.example.huynhxuankhanh.minialbum.gallery.InfoFolder;
import com.example.huynhxuankhanh.minialbum.gallery.InfoImage;

import java.util.List;

/**
 * Created by HUYNHXUANKHANH on 11/25/2017.
 */

public interface FragmentCallBacks {
    void onMsgFromMainToFragmentImage(List<InfoImage> listImage);

    void onMsgFromMainToFragmentFolder(List<InfoFolder> listFolder);
}

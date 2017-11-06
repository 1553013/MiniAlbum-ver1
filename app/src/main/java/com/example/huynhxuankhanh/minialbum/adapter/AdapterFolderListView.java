package com.example.huynhxuankhanh.minialbum.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created by HUYNHXUANKHANH on 11/4/2017.
 */

public class AdapterFolderListView extends ArrayAdapter<String> {
    public AdapterFolderListView(@NonNull Context context, @LayoutRes int resource, @NonNull List<String> objects) {
        super(context, resource, objects);
    }
}

package com.example.huynhxuankhanh.minialbum.adapter;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.GridView;

import com.example.huynhxuankhanh.minialbum.gallery.InfoImage;

import java.util.List;

/**
 * Created by HUYNHXUANKHANH on 12/23/2017.
 */

public class ImageWorkerTask extends AsyncTask<AdapterImageGridView, Void,AdapterImageGridView> {
    private Context context;
    private int idLayout;
    private List<com.example.huynhxuankhanh.minialbum.gallery.InfoImage> InfoImage;


    public ImageWorkerTask(Context context, int idLayout, List<InfoImage> infoImage) {
        this.context = context;
        this.idLayout = idLayout;
        InfoImage = infoImage;

    }


    @Override
    protected AdapterImageGridView doInBackground(AdapterImageGridView... adapterImageGridViews) {
        return new AdapterImageGridView(context,idLayout,InfoImage);
    }
}

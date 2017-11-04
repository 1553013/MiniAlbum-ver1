package com.example.huynhxuankhanh.minialbum.fragment;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.huynhxuankhanh.minialbum.R;
import com.example.huynhxuankhanh.minialbum.activity.Main2Activity;
import com.example.huynhxuankhanh.minialbum.activity.MainActivity;
import com.example.huynhxuankhanh.minialbum.adapter.AdapterImageGridView;
import com.example.huynhxuankhanh.minialbum.gallary.LoadGallary;

/**
 * Created by HUYNHXUANKHANH on 11/2/2017.
 */

public class FragmentPicture extends Fragment{
    private final Uri Image_URI_EXTERNAL = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
    private View view;
    private AdapterImageGridView myArrayAdapterGridView;
    private GridView gridView;
    private static final int  MY_REQUEST_ACCESS_EXTERNAL_STORAGE = 100;
    private LoadGallary loadGallary;
    private Intent fragPictureIntent;
    FragmentActivity activity;
    // caching technique
    private static LruCache<String,Bitmap> memCache;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_picture,container,false);
        gridView = (GridView) view.findViewById(R.id.grd_Image);

        fragPictureIntent = new Intent(getActivity(), Main2Activity.class);
        final  int maxMemorySize = (int)Runtime.getRuntime().maxMemory() / 1024;
        final int cacheSize = maxMemorySize / 10;

        // cache size represents for number of bitmap
        memCache = new LruCache<String,Bitmap>(cacheSize){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount()/1024;
            }
        };

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), position + "", Toast.LENGTH_SHORT).show();
                fragPictureIntent.putExtra("image-view",loadGallary.getLink(position));
                // check putExtra is it ok or position is ok ?
                startActivity(fragPictureIntent);
            }
        });

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
        requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},MY_REQUEST_ACCESS_EXTERNAL_STORAGE);


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode){
            case MY_REQUEST_ACCESS_EXTERNAL_STORAGE:{

                loadGallary = new LoadGallary();
                loadGallary.setContentResolver(activity.getContentResolver());
                loadGallary.query_PathImage(Image_URI_EXTERNAL);
                myArrayAdapterGridView = new AdapterImageGridView(getActivity(),R.layout.imageview_layout,loadGallary.getListImage());
                gridView.setAdapter(myArrayAdapterGridView);

                break;
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }





    // return bitmap we need from mem with key
    public static Bitmap getBitmapFromMemCache(String key){
        return memCache.get(key);
    }
    public static void setBitmapToMemCache(String key,Bitmap bm){
        // check key is alreay in cache ?
        if(getBitmapFromMemCache(key)!=null){
            memCache.put(key,bm);
        }
    }
}

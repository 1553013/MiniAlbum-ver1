package com.example.huynhxuankhanh.minialbum.bitmap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.example.huynhxuankhanh.minialbum.R;
import com.example.huynhxuankhanh.minialbum.activity.MainActivity;
import com.example.huynhxuankhanh.minialbum.adapter.AdapterImageGridView;
import com.example.huynhxuankhanh.minialbum.fragment.FragmentPicture;

import java.lang.ref.WeakReference;

/**
 * Created by HUYNHXUANKHANH on 11/2/2017.
 */

public class BitMapWorkTask extends AsyncTask<String,Void,Bitmap> {
    // to colect garbage on thread
    WeakReference<ImageView> imageViewWeakReference;
    // to record last path
    private String savePath;

    public BitMapWorkTask(ImageView imageView){
        imageViewWeakReference =new WeakReference<ImageView>(imageView);
    }
    // load bitmap in UI thread
    @Override
    protected Bitmap doInBackground(String... params) {
        if(params[0]!=null)
            savePath = params[0];

        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;
        Bitmap bitmap = BitmapFactory.decodeFile(params[0],options);
        FragmentPicture.setBitmapToMemCache(params[0],bitmap);
        return bitmap;

    }
    // manipulate bitmap on UI thread
    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if(isCancelled()){
            bitmap = null;
        }
        if(bitmap!=null && imageViewWeakReference!=null){
            ImageView imageView = imageViewWeakReference.get();
            BitMapWorkTask bitMapWorkTask = AdapterImageGridView.getBitmapWorkerTask(imageView);
            if(this == bitMapWorkTask){
                imageView.setImageBitmap(bitmap);
            }
        }
    }
    public String getSavePath(){
        return savePath;
    }
}

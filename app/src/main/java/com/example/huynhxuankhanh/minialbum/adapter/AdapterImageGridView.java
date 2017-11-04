package com.example.huynhxuankhanh.minialbum.adapter;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.AsyncPlayer;
import android.media.ThumbnailUtils;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.huynhxuankhanh.minialbum.R;
import com.example.huynhxuankhanh.minialbum.activity.MainActivity;
import com.example.huynhxuankhanh.minialbum.bitmap.BitMapWorkTask;
import com.example.huynhxuankhanh.minialbum.fragment.FragmentPicture;
import com.example.huynhxuankhanh.minialbum.gallary.ViewHolder;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by HUYNHXUANKHANH on 11/2/2017.
 */

public class AdapterImageGridView extends ArrayAdapter<String>{
   // declare some variable tool for create adapter pathImage
    private Context context; // context from fragment picture
    private int resource;     // main resource from fragment picture
    private View view;       // return view for grid view
    private List<String> listPathImage; // store list path of image
    private Bitmap saveHolderBitmap;
    ViewHolder viewHolder;
    // class for reparing for draw before pushing to thread( it already draw or not)
    public static class AsyncDrawale extends BitmapDrawable{
        final WeakReference<BitMapWorkTask> myTaskRef ;
        public AsyncDrawale(Resources resources,
                            Bitmap bitmap,
                            BitMapWorkTask task){
            super(resources,bitmap);
            myTaskRef = new WeakReference(task);
        }
        public BitMapWorkTask getBitmapWorkerTask(){
            return myTaskRef.get();
        }
    }
    // main constructor for adapter grid view
    public AdapterImageGridView(@NonNull Context context, @LayoutRes int resource, @NonNull List<String> objects) {
        super(context, resource, objects);

        this.context = context;
        this.resource = resource;
        this.listPathImage = new ArrayList<String>( objects);
        viewHolder = new ViewHolder();
    }
    // return a view of grid | push data to grid view
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if(convertView==null){
           // viewHolder = new ViewHolder();
            convertView = view.inflate(context,R.layout.imageview_layout,null);
            convertView.setTag(viewHolder);

        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }
        viewHolder.image = (ImageView)convertView.findViewById(R.id.view_img);
        String item = listPathImage.get(position);

        Glide.with(viewHolder.image.getContext()).load(item).into(viewHolder.image);
        /*
        Bitmap bitmap = FragmentPicture.getBitmapFromMemCache(item);
        if(bitmap!=null){
            viewHolder.image.setImageBitmap(bitmap);
        }
        if(isBitmapWorkTask(item,viewHolder.image)){

            BitMapWorkTask bitMapWorkTask = new BitMapWorkTask(viewHolder.image);
            AsyncDrawale asyncDrawale = new AsyncDrawale(viewHolder.image.getResources(),
                    saveHolderBitmap,bitMapWorkTask);
            viewHolder.image.setImageDrawable(asyncDrawale);
            bitMapWorkTask.execute(item);
        }
        */
        return convertView;
    }

    // let take a look that this image is push or not
    public static boolean isBitmapWorkTask(String path,ImageView imageView){
        BitMapWorkTask bitMapWorkTask = getBitmapWorkerTask(imageView);
        if(bitMapWorkTask!=null){
            final String workingPath = bitMapWorkTask.getSavePath();
            if(workingPath!=null && workingPath!=path) {
                bitMapWorkTask.cancel(true);
            }
            else{
                // bitmap is working is the same with image view.
                return false;
            }
        }
        return true;
    }
    //
    public static BitMapWorkTask getBitmapWorkerTask(ImageView imageView){
        Drawable drawable = imageView.getDrawable();
        if(drawable instanceof AsyncDrawale){
            AsyncDrawale asyncDrawale = (AsyncDrawale) drawable;
            return asyncDrawale.getBitmapWorkerTask();
        }
        return null;
    }
}

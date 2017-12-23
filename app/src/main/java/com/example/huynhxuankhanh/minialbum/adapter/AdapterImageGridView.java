package com.example.huynhxuankhanh.minialbum.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.huynhxuankhanh.minialbum.R;
import com.example.huynhxuankhanh.minialbum.gallery.InfoImage;
import com.example.huynhxuankhanh.minialbum.gallery.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

/**
 * Created by HUYNHXUANKHANH on 11/2/2017.
 */

public class AdapterImageGridView extends ArrayAdapter<InfoImage> {
    // declare some variable tool for create adapter pathImage
    private Context context; // context from fragment picture
    private int resource;     // main resource from fragment picture
    private View view;       // return view for grid view
    private List<InfoImage> listImage; // store list path of image
    private ViewHolder viewHolder;

    // main constructor for adapter grid view
    public AdapterImageGridView(@NonNull Context context, @LayoutRes int resource, @NonNull List<InfoImage> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.listImage = new ArrayList<InfoImage>(objects);
        viewHolder = new ViewHolder();
    }

    // return a view of grid | push data to grid view
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = view.inflate(context, R.layout.imageview_layout, null);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.setImageView((ImageView) convertView.findViewById(R.id.view_img));
        String item = listImage.get(position).getPathFile();

        Glide.with(viewHolder.getImageView().getContext()).load(item).into(viewHolder.getImageView());
        return convertView;
    }
}

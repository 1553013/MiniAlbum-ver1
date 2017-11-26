package com.example.huynhxuankhanh.minialbum.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.huynhxuankhanh.minialbum.R;
import com.example.huynhxuankhanh.minialbum.gallery.InfoFolder;
import com.example.huynhxuankhanh.minialbum.gallery.InfoImage;
import com.example.huynhxuankhanh.minialbum.gallery.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HUYNHXUANKHANH on 11/4/2017.
 */

public class AdapterFolderListView extends ArrayAdapter<InfoFolder> {
    // declare some variable tool for create adapter pathImage
    private Context context; // context from fragment picture
    private int resource;     // main resource from fragment picture
    private View view;       // return view for grid view
    private List<InfoFolder> listFolder; // store list path of image
    private ViewHolder viewHolder;


    public AdapterFolderListView(@NonNull Context context, int resource, @NonNull List<InfoFolder> objects) {
        super(context, resource, objects);

        this.context = context;
        this.resource = resource;
        this.listFolder = new ArrayList<InfoFolder>(objects);
        viewHolder = new ViewHolder();

    }

    // return a view of grid | push data to grid view
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView = view.inflate(context, R.layout.folderview_layout, null);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.setText1((TextView)convertView.findViewById(R.id.text_nameFolder));
        viewHolder.setText2((TextView)convertView.findViewById(R.id.text_numImageInFolder));

//        Glide.with(viewHolder.getImageView().getContext()).load(item).into(viewHolder.getImageView());
        viewHolder.getText1().setText(listFolder.get(position).getNameBucket());
        viewHolder.getText2().setText(String.format("%d images",listFolder.get(position).getListImage().size()));
        return convertView;
    }
}

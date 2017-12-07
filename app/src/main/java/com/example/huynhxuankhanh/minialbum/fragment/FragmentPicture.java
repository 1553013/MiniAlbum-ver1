package com.example.huynhxuankhanh.minialbum.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.huynhxuankhanh.minialbum.R;
import com.example.huynhxuankhanh.minialbum.activity.ImageActivity;
import com.example.huynhxuankhanh.minialbum.activity.MainActivity;
import com.example.huynhxuankhanh.minialbum.adapter.AdapterImageGridView;
import com.example.huynhxuankhanh.minialbum.gallery.InfoFolder;
import com.example.huynhxuankhanh.minialbum.gallery.InfoImage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HUYNHXUANKHANH on 11/2/2017.
 */

public class FragmentPicture extends Fragment implements FragmentCallBacks {
    private View view;
    private AdapterImageGridView myArrayAdapterGridView;
    private GridView gridView;
    private Intent fragPictureIntent;
    private FragmentActivity activity;
    private List<InfoImage> listImage;
    private int currentPos = 0;
    private int lastSelected = 0;

    public static FragmentPicture newInstance(String StrArg) {
        FragmentPicture fragment = new FragmentPicture();
        Bundle args = new Bundle();
        args.putString("image-bundle", StrArg);
        fragment.setArguments(args);
        return fragment;
    }

    public void setListImage(List<InfoImage> listImage) {
        this.listImage = listImage;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_picture, container, false);
        gridView = (GridView) view.findViewById(R.id.grd_Image);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // send data to activity2: view image full screen
                InfoImage temp = listImage.get(position);
                lastSelected = position;
                fragPictureIntent.putExtra("image-info", (Parcelable) temp);
                // check putExtra is it ok or position is ok ?
                currentPos = gridView.getFirstVisiblePosition();
                startActivityForResult(fragPictureIntent,0);
            }
        });
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
        fragPictureIntent = new Intent(getActivity(), ImageActivity.class);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) { // means reload data: An image is removed from image activity.
            // listImage = new ArrayList<>();
            if(resultCode == Activity.RESULT_OK){
                Toast.makeText(activity, "Deleted...", Toast.LENGTH_SHORT).show();
                // remove an image from list image loaded from database.
                listImage.remove(lastSelected);
            }
            if(resultCode==222){
                Intent result = new Intent();
                int num = 0;
                num = data.getIntExtra("crop-image",num);
                if(num!=0)
                    ((MainActivity) getActivity()).onMsgFromFragToMain(Integer.toString(num));
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        // reload data
        if (listImage == null)
            ((MainActivity) getActivity()).onMsgFromFragToMain("load-images");
        myArrayAdapterGridView = new AdapterImageGridView(getActivity(), R.layout.imageview_layout, listImage);
        gridView.setAdapter(myArrayAdapterGridView);
        gridView.setSelection(currentPos);

    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onMsgFromMainToFragmentImage(List<InfoImage> listImage) {
        if (listImage != null) {
            this.listImage = listImage;
        }
    }

    @Override
    public void onMsgFromMainToFragmentFolder(List<InfoFolder> listFolder) {
    }
}

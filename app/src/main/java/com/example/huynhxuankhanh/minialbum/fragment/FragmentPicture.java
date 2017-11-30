package com.example.huynhxuankhanh.minialbum.fragment;

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

import com.example.huynhxuankhanh.minialbum.R;
import com.example.huynhxuankhanh.minialbum.activity.ImageActivity;
import com.example.huynhxuankhanh.minialbum.activity.MainActivity;
import com.example.huynhxuankhanh.minialbum.adapter.AdapterImageGridView;
import com.example.huynhxuankhanh.minialbum.gallery.InfoFolder;
import com.example.huynhxuankhanh.minialbum.gallery.InfoImage;

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
                fragPictureIntent.putExtra("image-info", (Parcelable) temp);
                // check putExtra is it ok or position is ok ?
                currentPos = gridView.getFirstVisiblePosition();
                startActivity(fragPictureIntent);
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

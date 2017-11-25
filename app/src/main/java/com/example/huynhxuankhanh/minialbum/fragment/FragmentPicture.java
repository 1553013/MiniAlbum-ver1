package com.example.huynhxuankhanh.minialbum.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
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
import com.example.huynhxuankhanh.minialbum.gallery.InfoImage;
import com.example.huynhxuankhanh.minialbum.gallery.LoadGallary;

/**
 * Created by HUYNHXUANKHANH on 11/2/2017.
 */

public class FragmentPicture extends Fragment{
    private final Uri Image_URI_EXTERNAL = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
    public LoadGallary loadGallary;
    private View view;
    private AdapterImageGridView myArrayAdapterGridView;
    private GridView gridView;
    private Intent fragPictureIntent;
    private FragmentActivity activity;
    private int currentPos = 0;

    public static FragmentPicture newInstance(String StrArg) {
        FragmentPicture fragment = new FragmentPicture();
        Bundle args = new Bundle();
        args.putString("image-bundle", StrArg);
        fragment.setArguments(args);
        return fragment;
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
                //fragPictureIntent.putExtra("image-info",loadGallary.getInfoImage(position));
                InfoImage temp = loadGallary.getInfoImage(position);
                fragPictureIntent.putExtra("image-info", (Parcelable) loadGallary.getInfoImage(position));
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
        loadGallary = new LoadGallary();
        loadGallary.setContentResolver(activity.getContentResolver());
        loadGallary.query_PathImage(Image_URI_EXTERNAL);
        myArrayAdapterGridView = new AdapterImageGridView(getActivity(), R.layout.imageview_layout, loadGallary.getListImage());
        gridView.setAdapter(myArrayAdapterGridView);
        gridView.setSelection(currentPos);

        ((MainActivity)getActivity()).onMsgFromFragToMain("frag-picture",loadGallary);

        //Toast.makeText(activity, "on Fragment Picture", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPause() {
        super.onPause();
/*
        Bundle bundle = new Bundle();
        bundle.putParcelable("image-bundle",loadGallary);
        FragmentFolder fragmentFolder = new FragmentFolder();
        fragmentFolder.setArguments(bundle);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frag_folder,fragmentFolder);
   */
    }
}

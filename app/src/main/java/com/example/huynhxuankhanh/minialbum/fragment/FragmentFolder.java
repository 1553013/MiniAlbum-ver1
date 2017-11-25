package com.example.huynhxuankhanh.minialbum.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.huynhxuankhanh.minialbum.R;
import com.example.huynhxuankhanh.minialbum.adapter.AdapterImageGridView;
import com.example.huynhxuankhanh.minialbum.gallery.LoadFolder;
import com.example.huynhxuankhanh.minialbum.gallery.LoadGallary;

/**
 * Created by HUYNHXUANKHANH on 11/2/2017.
 */

public class FragmentFolder extends Fragment implements FragmentCallBacks{
    private View view;
    private LoadGallary loadGallary;
    public static FragmentFolder newInstance(String StrArg) {
        FragmentFolder fragment = new FragmentFolder();
        Bundle args = new Bundle();
        args.putString("image-bundle", StrArg);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_folder, container, false);






        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        if(loadGallary!=null){
            Toast.makeText(getActivity(), loadGallary.getInfoImage(0).getPathFile(), Toast.LENGTH_SHORT).show();

        }

        // Toast.makeText(getActivity(), "on Fragment Folder", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onMsgFromMainToFragment(LoadGallary loadGallary) {
        this.loadGallary = loadGallary;
    }
}

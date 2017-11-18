package com.example.huynhxuankhanh.minialbum.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.huynhxuankhanh.minialbum.R;

/**
 * Created by HUYNHXUANKHANH on 11/2/2017.
 */

public class FragmentFolder extends Fragment {
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_folder, container, false);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
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

    public static FragmentFolder newInstance(String StrArg) {
        FragmentFolder fragment = new FragmentFolder();
        Bundle args = new Bundle();
        args.putString("strArg1", StrArg);
        fragment.setArguments(args);
        return fragment;
    }
}

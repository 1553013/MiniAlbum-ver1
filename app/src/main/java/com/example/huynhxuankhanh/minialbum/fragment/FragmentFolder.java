package com.example.huynhxuankhanh.minialbum.fragment;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
        view = inflater.inflate(R.layout.fragment_folder,container,false);
        return view;
    }
}

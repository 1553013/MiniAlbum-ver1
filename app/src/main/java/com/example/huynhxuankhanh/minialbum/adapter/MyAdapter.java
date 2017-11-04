package com.example.huynhxuankhanh.minialbum.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.huynhxuankhanh.minialbum.activity.MainActivity;
import com.example.huynhxuankhanh.minialbum.fragment.FragmentFolder;
import com.example.huynhxuankhanh.minialbum.fragment.FragmentPicture;

/**
 * Created by HUYNHXUANKHANH on 11/2/2017.
 */
public class MyAdapter extends FragmentPagerAdapter {
    private String[] listTab = {"PICTURES","FOLDER"};

    public MyAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:{
                FragmentPicture fragmentPicture = new FragmentPicture();
                return fragmentPicture;
            }
            case 1:{
                FragmentFolder fragmentFolder = new FragmentFolder();
                return fragmentFolder;
            }
        }
        return null;
    }

    @Override
    public int getCount() {
        return listTab.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return listTab[0];
            case 1:
                return listTab[1];
        }
        return null;
    }
}
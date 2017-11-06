package com.example.huynhxuankhanh.minialbum.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


import com.example.huynhxuankhanh.minialbum.fragment.FragmentFolder;
import com.example.huynhxuankhanh.minialbum.fragment.FragmentPicture;
import com.example.huynhxuankhanh.minialbum.gallary.LoadGallary;

/**
 * Created by HUYNHXUANKHANH on 11/2/2017.
 */
public class MyAdapter extends FragmentPagerAdapter {
    private String[] listTab = {"PICTURES","FOLDER"};
    private FragmentPicture fragmentPicture;
    private  FragmentFolder fragmentFolder;
    public MyAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:{
                fragmentPicture = new FragmentPicture().newInstance(listTab[0]);
                return fragmentPicture;
            }
            case 1:{
                fragmentFolder = new FragmentFolder().newInstance(listTab[1]);
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
    public FragmentFolder getFragmentFolder(){
        return fragmentFolder;
    }

}
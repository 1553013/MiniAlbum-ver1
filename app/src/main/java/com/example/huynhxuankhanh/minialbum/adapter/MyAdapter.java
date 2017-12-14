package com.example.huynhxuankhanh.minialbum.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.huynhxuankhanh.minialbum.fragment.FragmentFavor;
import com.example.huynhxuankhanh.minialbum.fragment.FragmentFolder;
import com.example.huynhxuankhanh.minialbum.fragment.FragmentPicture;

/**
 * Created by HUYNHXUANKHANH on 11/2/2017.
 */
public class MyAdapter extends FragmentPagerAdapter {
    private final String[] listTab = {"PICTURES", "FOLDER", "FAVORITE"};
    private FragmentPicture fragmentPicture;
    private FragmentFolder fragmentFolder;
    private FragmentFavor fragmentFavor;

    public MyAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: {
                fragmentPicture = new FragmentPicture().newInstance(listTab[0]);
                return fragmentPicture;
            }
            case 1: {
                fragmentFolder = new FragmentFolder().newInstance(listTab[1]);
                return fragmentFolder;
            }
            case 2: {
                fragmentFavor = new FragmentFavor().newInstance(listTab[2]);
                return fragmentFavor;
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
            case 2:
                return listTab[2];
        }
        return null;
    }

    public FragmentFolder getFragmentFolder() {
        return fragmentFolder;
    }

    public FragmentPicture getFragmentPicture() {
        return fragmentPicture;
    }

    public FragmentFavor getFragmentFavor() {
        return fragmentFavor;
    }

}
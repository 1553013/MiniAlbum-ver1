package com.example.huynhxuankhanh.minialbum.activity;


import android.support.design.widget.TabLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.example.huynhxuankhanh.minialbum.R;
import com.example.huynhxuankhanh.minialbum.adapter.MyAdapter;

public class MainActivity extends AppCompatActivity {
    // adapter for each fragment/pager
    private MyAdapter mSectionsPagerAdapter;
    // Viewpager tool
    private ViewPager mViewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // create tool bar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new MyAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // push each fragment to tab.
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);





        // make interface better after loading image
        tabLayout.getTabAt(0).setIcon(R.mipmap.icon_picture);
        tabLayout.getTabAt(1).setIcon(R.mipmap.icon_folder);

        /*
        for(int i=0; i < tabLayout.getTabCount(); i++) {
            View tab = ((ViewGroup) tabLayout.getChildAt(0)).getChildAt(i);
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) tab.getLayoutParams();
            p.setMargins(0, 0, 50, 0);
            tab.requestLayout();
        }
        */
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
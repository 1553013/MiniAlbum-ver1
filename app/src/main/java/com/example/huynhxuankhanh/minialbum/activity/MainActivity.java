package com.example.huynhxuankhanh.minialbum.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.huynhxuankhanh.minialbum.R;
import com.example.huynhxuankhanh.minialbum.adapter.MyAdapter;

public class MainActivity extends AppCompatActivity {
    private static final int MY_REQUEST_ACCESS_EXTERNAL_STORAGE = 100;
    // adapter for each fragment/pager
    private MyAdapter mSectionsPagerAdapter;
    // Viewpager tool
    private ViewPager mViewPager;
    private TabLayout tabLayout;

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
        mViewPager.setOffscreenPageLimit(3);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_REQUEST_ACCESS_EXTERNAL_STORAGE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_REQUEST_ACCESS_EXTERNAL_STORAGE: {
                if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.READ_EXTERNAL_STORAGE) ==
                        PackageManager.PERMISSION_GRANTED) {
                    tabLayout = (TabLayout) findViewById(R.id.tabs);
                    tabLayout.setupWithViewPager(mViewPager);
                    // make interface better after loading image
                    tabLayout.getTabAt(0).setIcon(R.mipmap.icon_picture_white);
                    tabLayout.getTabAt(1).setIcon(R.mipmap.icon_folder_white);
                    tabLayout.getTabAt(2).setIcon(R.mipmap.icon_favorite_white);
                } else
                    finish();
                break;
            }
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return (super.onCreateOptionsMenu(menu));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try {
            switch (item.getItemId()) {
                case R.id.action_settings:
                    Toast.makeText(MainActivity.this, R.string.action_settings, Toast.LENGTH_LONG).show();
                    Intent i = new Intent(this, SettingsActivity.class);
                    startActivity(i);
                    return (true);
            }
        } catch (Exception e) {
            Log.e("<<foregroundTask>>", e.getMessage());
        }
        return (super.onOptionsItemSelected(item));
    }
}
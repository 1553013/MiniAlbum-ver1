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
import com.example.huynhxuankhanh.minialbum.fragment.FragmentFolder;
import com.example.huynhxuankhanh.minialbum.fragment.MainCallBacks;
import com.example.huynhxuankhanh.minialbum.gallery.LoadGallary;

public class MainActivity extends AppCompatActivity implements MainCallBacks {
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
        // mSectionsPagerAdapter-> quan ly fragment



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
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    tabLayout = (TabLayout) findViewById(R.id.tabs);
                    tabLayout.setupWithViewPager(mViewPager);
                    // make interface better after loading image
                    tabLayout.getTabAt(0).setIcon(R.mipmap.icon_picture);
                    tabLayout.getTabAt(1).setIcon(R.mipmap.icon_folder);
                    tabLayout.getTabAt(2).setIcon(R.mipmap.icon_favorite);
                }else
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


    @Override
    public void onMsgFromFragToMain(final String sender, LoadGallary loadGallary) {
        if(sender.equals("frag-picture")){
            if(loadGallary!=null){
                Toast.makeText(this, "Main recieves package from fragment pictures", Toast.LENGTH_SHORT).show();
                mSectionsPagerAdapter.getFragmentFolder().onMsgFromMainToFragment(loadGallary);
            }
        }

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
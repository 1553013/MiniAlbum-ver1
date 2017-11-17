package com.example.huynhxuankhanh.minialbum.activity;

import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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
        mViewPager.setOffscreenPageLimit(3);
        // push each fragment to tab.
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        // make interface better after loading image
        tabLayout.getTabAt(0).setIcon(R.mipmap.icon_picture);
        tabLayout.getTabAt(1).setIcon(R.mipmap.icon_folder);
<<<<<<< HEAD
        tabLayout.getTabAt(2).setIcon(R.mipmap.icon_favorite);

        /*
        for(int i=0; i < tabLayout.getTabCount(); i++) {
            View tab = ((ViewGroup) tabLayout.getChildAt(0)).getChildAt(i);
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) tab.getLayoutParams();
            p.setMargins(0, 0, 50, 0);
            tab.requestLayout();
        }
        */
=======
>>>>>>> 533f5dc85ae4150ee1a2635ad5c26a38a2bb3145
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return(super.onCreateOptionsMenu(menu));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Toast.makeText(MainActivity.this, R.string.action_settings, Toast.LENGTH_LONG).show();
                //startActivity(new Intent(MainActivity.this, ContentActivity.class));
                return(true);

            case R.id.action_help:
                Toast.makeText(MainActivity.this, R.string.action_help, Toast.LENGTH_LONG).show();
                return(true);

            case R.id.action_about:
                Toast.makeText(MainActivity.this, R.string.action_about, Toast.LENGTH_LONG).show();

                return(true);
        }
        return(super.onOptionsItemSelected(item));
    }
}
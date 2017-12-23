package com.example.huynhxuankhanh.minialbum.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.huynhxuankhanh.minialbum.R;
import com.example.huynhxuankhanh.minialbum.Utility;
import com.example.huynhxuankhanh.minialbum.adapter.MyAdapter;
import com.example.huynhxuankhanh.minialbum.fragment.MainCallBacks;
import com.example.huynhxuankhanh.minialbum.gallery.LoadGallery;

import java.io.File;


public class MainActivity extends AppCompatActivity implements MainCallBacks {
    private static final int MY_REQUEST_ACCESS_EXTERNAL_STORAGE = 100;
    private final Uri Image_URI_EXTERNAL = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
    // adapter for each fragment/pager
    private MyAdapter mSectionsPagerAdapter;
    // Viewpager tool
    private ViewPager mViewPager;
    private TabLayout tabLayout;
    private LoadGallery loadGallery;
    private String curPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // create tool bar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tabLayout = (TabLayout) findViewById(R.id.tabs);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new MyAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setOffscreenPageLimit(2);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_REQUEST_ACCESS_EXTERNAL_STORAGE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_REQUEST_ACCESS_EXTERNAL_STORAGE: {
                if (grantResults.length > 1
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {

                    tabLayout.setupWithViewPager(mViewPager);
                    // make interface better after loading image
                    tabLayout.getTabAt(0).setIcon(R.mipmap.icon_picture_white);
                    tabLayout.getTabAt(1).setIcon(R.mipmap.icon_folder_white);
                    tabLayout.getTabAt(2).setIcon(R.mipmap.icon_favorite_white);

                    // after receiving the accepting permission from phone, load data
                    loadGallery = new LoadGallery();
                    loadGallery.setContentResolver(this.getContentResolver());
                    loadGallery.queryPathImage(Image_URI_EXTERNAL);

                    // create a folder to store changed pictures in app
                    final File f = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "MiniAlbum");
                    if (!f.exists()) {
                        Toast.makeText(this, "Folder MiniAlbum doesn't exist, creating it for the fist using...", Toast.LENGTH_SHORT).show();
                        // check whether this file have been created or not
                        if (!f.exists()) {
                            f.mkdir();
                        }
                    }
                } else
                    finish();
                break;
            }
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
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
                    Context context = this;
                    // get user input and check it
                    curPass = PreferenceManager
                            .getDefaultSharedPreferences
                                    (getApplicationContext())
                            .getString(Utility.PASSWORD_KEY, "");
                    curPass = new String(Base64.decode(curPass, Base64.DEFAULT));
                    //if has password protection
                    if (!curPass.isEmpty()) {
                        LayoutInflater li = LayoutInflater.from(context);
                        final View promptsView = li.inflate(R.layout.dialog_input_password, null);
                        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                        // set prompts.xml to alert dialog builder
                        alertDialogBuilder.setView(promptsView);
                        final EditText userInput = (EditText) promptsView
                                .findViewById(R.id.password_et);
                        // set dialog message
                        alertDialogBuilder.setCancelable(false).setPositiveButton("CONFIRM", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                if (userInput.getText().toString().equals(curPass)) {
                                    ((ViewGroup) promptsView.getParent()).removeView(promptsView);
                                    Intent i = new Intent(context, SettingsActivity.class);
                                    startActivity(i);
                                } else {
                                    Toast.makeText(context, "Wrong " +
                                            "password", Toast.LENGTH_LONG).show();
                                }
                            }
                        }).setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                        alertDialogBuilder.show();
                    } else {
                        Intent i = new Intent(context, SettingsActivity.class);
                        startActivity(i);
                    }
                    return (true);
            }
        } catch (Exception e) {
            Log.e("<<Bug>>", e.getMessage());
        }
        return (super.onOptionsItemSelected(item));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
        }
    }

    @Override
    public void onMsgFromFragToMain(String message) {
        if (message.equals("load-images")) {
            loadGallery = new LoadGallery();
            loadGallery.setContentResolver(this.getContentResolver());
            loadGallery.queryPathImage(Image_URI_EXTERNAL);
            mSectionsPagerAdapter.getFragmentPicture().onMsgFromMainToFragmentImage(loadGallery.getListImage());
        } else if (message.length() < 3 && Integer.parseInt(message) != 0) {
            loadGallery.updateLastItem(Image_URI_EXTERNAL, Integer.parseInt(message));
            mSectionsPagerAdapter.getFragmentPicture().onMsgFromMainToFragmentImage(loadGallery.getListImage());
            mSectionsPagerAdapter.getFragmentFolder().onMsgFromMainToFragmentFolder(loadGallery.getListBucketName());
        } else if (message.equals("load-folders"))
            mSectionsPagerAdapter.getFragmentFolder().onMsgFromMainToFragmentFolder(loadGallery.getListBucketName());
    }
}
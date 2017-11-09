package com.example.huynhxuankhanh.minialbum.activity;

import android.app.WallpaperManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huynhxuankhanh.minialbum.R;

import java.io.IOException;

public class Main2Activity extends AppCompatActivity {

    private ImageView imageView ;
    private Button btnShare,btnFav,btnSetWall,btnEdit,btnRemove,btnBack,btnDetail;
    private TextView textViewName;
    private String recieve;
    private Bitmap bm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        doLoadInterface();

        recieve = getIntent().getStringExtra("image-view");
        if(recieve!=null) {
            bm = BitmapFactory.decodeFile(recieve);
            if (bm != null) {
                textViewName.setText(recieve);
                imageView.setImageBitmap(bm);
                //Toast.makeText(this, getIntent().getStringExtra("image-view"), Toast.LENGTH_SHORT).show();
                // set top text view name
                // text_view_name.setText(displayname);

                // when user press back Button - on the top left
                btnBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                });
                // view detail of image, will get from intent.
                btnDetail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
                // send back to the previous activity to query delete this image from database
                btnRemove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
                // call set wallpaper to set bitmap to wallpaper.
                btnSetWall.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        WallpaperManager myWallpaperManager
                                = WallpaperManager.getInstance(getApplicationContext());
                        try {
                            myWallpaperManager.setBitmap(bm);
                            Toast.makeText(Main2Activity.this, "Wallpaper is set", Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                // add 1 column to the original database to show that it is my favorite image.
                btnFav.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
                // call api facebook to share image(bitmap)
                btnShare.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
                // call activity edit image
                btnEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
            } else {
                Toast.makeText(this, "Not enough memory to load image", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(this, "Error: Wrong path of image", Toast.LENGTH_SHORT).show();
        }

    }
    public void doLoadInterface(){
        textViewName = (TextView)findViewById(R.id.text_view_name);
        imageView = (ImageView)findViewById(R.id.img_view);
        btnShare = (Button)findViewById(R.id.btn_share);
        btnBack = (Button)findViewById(R.id.btn_back);
        btnFav = (Button)findViewById(R.id.btn_favor);
        btnSetWall = (Button)findViewById(R.id.btn_setscreen);
        btnEdit = (Button)findViewById(R.id.btn_edit);
        btnRemove = (Button)findViewById(R.id.btn_delete);
        btnDetail = (Button)findViewById(R.id.btn_detail);

    }
}

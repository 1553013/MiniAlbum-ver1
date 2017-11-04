package com.example.huynhxuankhanh.minialbum.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.huynhxuankhanh.minialbum.R;

public class Main2Activity extends AppCompatActivity {

    private ImageView imageView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        imageView = (ImageView)findViewById(R.id.img_view);

        Bitmap bm = BitmapFactory.decodeFile(getIntent().getStringExtra("image-view"));
        if(bm!=null)
            imageView.setImageBitmap(bm);
    }
}

package com.example.huynhxuankhanh.minialbum.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huynhxuankhanh.minialbum.R;

import org.w3c.dom.Text;

public class Main2Activity extends AppCompatActivity {

    private ImageView imageView ;
    private TextView textViewTop,textViewBot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        imageView = (ImageView)findViewById(R.id.img_view);



        Bitmap bm = BitmapFactory.decodeFile(getIntent().getStringExtra("image-view"));
        if(bm!=null){
            imageView.setImageBitmap(bm);
            Toast.makeText(this, getIntent().getStringExtra("image-view"), Toast.LENGTH_SHORT).show();
        }

    }

}

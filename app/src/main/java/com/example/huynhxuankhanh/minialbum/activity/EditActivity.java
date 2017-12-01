package com.example.huynhxuankhanh.minialbum.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.huynhxuankhanh.minialbum.R;
import com.example.huynhxuankhanh.minialbum.gallery.InfoImage;

/**
 * Created by HUYNHXUANKHANH on 12/1/2017.
 */

public class EditActivity extends AppCompatActivity{
    private InfoImage receive;
    private Button btnCrop;
    private Boolean isFav;
    private Bitmap bm;
    private ImageView imageView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        initInterface();

        receive = getIntent().getParcelableExtra("image-info-edit");
        if(receive==null){
            receive = getIntent().getParcelableExtra("image-info-fav-edit");
            isFav = true;
        }
        if(receive!=null) {

            bm = BitmapFactory.decodeFile(receive.getPathFile());
            imageView.setImageBitmap(bm);


            btnCrop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PopupMenu popupMenu = new PopupMenu(EditActivity.this, btnCrop);
                    popupMenu.getMenuInflater().inflate(R.menu.menupop_crop, popupMenu.getMenu());

                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            switch (menuItem.getItemId()){
                                case R.id.mncrop_23:
                                    Canvas cnvs = new Canvas();
                                    Paint paint=new Paint();
                                    paint.setColor(Color.RED);
                                    cnvs.drawBitmap(BitmapFactory.decodeFile(receive.getPathFile()), 0, 0, null);
                                    cnvs.drawRect(20, 20,50,50 , paint);
                                    imageView.setImageBitmap(bm);
                                    break;
                            }
                            return false;
                        }
                    });
                    popupMenu.show();
                }
            });
        }
    }
    public void initInterface(){
        btnCrop = (Button) findViewById(R.id.btn_crop);
        imageView = (ImageView) findViewById(R.id.img_view_edit);
    }
}

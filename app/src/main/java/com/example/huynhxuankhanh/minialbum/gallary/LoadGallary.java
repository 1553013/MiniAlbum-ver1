package com.example.huynhxuankhanh.minialbum.gallary;

import android.content.ContentResolver;
import android.database.Cursor;
import android.icu.text.IDNA;
import android.net.Uri;
import android.provider.MediaStore;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HUYNHXUANKHANH on 11/2/2017.
 */

public class LoadGallary {

    // declare some tool variable for querying database.
    private ContentResolver contentResolver;
    private Cursor cursor;
    private List<String> listPathImage;

    public LoadGallary(){
        contentResolver = null;
        cursor = null;
        listPathImage = new ArrayList<String>();

    }

    public List<String> getListImage() {
        return listPathImage;
    }

    public void setContentResolver(ContentResolver contentResolver) {
        this.contentResolver = contentResolver;
    }
    public void query_PathImage(Uri url){
        cursor = contentResolver.query(url,null,null,null,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            String pathFile = cursor.getString(cursor.getColumnIndex( MediaStore.Images.Thumbnails.DATA));
            //set item for list
            listPathImage.add(pathFile);
            cursor.moveToNext();
        }
    }
    public String getLink(int position){
        return listPathImage.get(position);
    }
}

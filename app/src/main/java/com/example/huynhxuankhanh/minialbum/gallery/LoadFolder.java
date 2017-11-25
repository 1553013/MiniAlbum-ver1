package com.example.huynhxuankhanh.minialbum.gallery;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by HUYNHXUANKHANH on 11/24/2017.
 */

public class LoadFolder {
    private ArrayList<InfoFolder> listFolder;
    private ContentResolver contentResolver;
    private Cursor cursor;
    private final String[] projection = new String[]{
            MediaStore.Images.ImageColumns._ID,
            MediaStore.Images.ImageColumns.DATA, // path file
            MediaStore.Images.ImageColumns.DISPLAY_NAME,
            MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME, // folder name
            MediaStore.Images.ImageColumns.SIZE,
            MediaStore.Images.ImageColumns.DATE_TAKEN, // date taken
    };
    public ArrayList<InfoFolder> getListFolder() {
        return listFolder;
    }

    public void setListFolder(ArrayList<InfoFolder> listFolder) {
        this.listFolder = listFolder;
    }

    public ContentResolver getContentResolver() {
        return contentResolver;
    }

    public void setContentResolver(ContentResolver contentResolver) {
        this.contentResolver = contentResolver;
    }






    public Cursor getCursor() {
        return cursor;
    }

    public void setCursor(Cursor cursor) {
        this.cursor = cursor;
    }

    public LoadFolder(ArrayList<InfoFolder> listFolder, ContentResolver contentResolver, Cursor cursor) {
        this.listFolder = listFolder;
        this.contentResolver = contentResolver;
        this.cursor = cursor;
    }


}

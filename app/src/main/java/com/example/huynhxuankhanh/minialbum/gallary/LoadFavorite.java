package com.example.huynhxuankhanh.minialbum.gallary;

import android.database.Cursor;

import com.example.huynhxuankhanh.minialbum.database.Database;

import java.util.ArrayList;

/**
 * Created by HUYNHXUANKHANH on 11/18/2017.
 */

public class LoadFavorite {
    private ArrayList<InfoImage> listImage;
    private Cursor cursor;
    private Database database;

    public LoadFavorite() {
        this.listImage = new ArrayList<>();
        this.cursor = null;
        this.database = null;
    }

    public LoadFavorite(ArrayList<InfoImage> listImage, Cursor cursor, Database database) {
        this.listImage = listImage;
        this.cursor = cursor;
        this.database = database;
    }

    public ArrayList<InfoImage> getListImage() {
        return listImage;
    }

    public void setListImage(ArrayList<InfoImage> listImage) {
        this.listImage = listImage;
    }

    public Cursor getCursor() {
        return cursor;
    }

    public void setCursor(Cursor cursor) {
        this.cursor = cursor;
    }

    public Database getDatabase() {
        return database;
    }

    public void setDatabase(Database database) {
        this.database = database;
    }

    // query data from sql string
    // Favorite(Id INTEGER PRIMARY KEY AUTOINCREMENT,Path VARCHAR,Title VARCHAR,Bucket VARCHAR,Size LONG,Time VARCHAR)");
    public boolean loadDataFromDB(String sql) {
        cursor = database.getData(sql);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                int iD = cursor.getInt(0);
                String path = cursor.getString(1);
                String title = cursor.getString(2);
                String bucket = cursor.getString(3);
                Long size = cursor.getLong(4);
                String date = cursor.getString(5);
                InfoImage infoImage = new InfoImage(iD, size, path, title, bucket, date);
                if (infoImage != null)
                    listImage.add(infoImage);
            }
            return true;
        }
        return false;
    }

    public InfoImage getInfoImage(int position) {
        return listImage.get(position);
    }
}

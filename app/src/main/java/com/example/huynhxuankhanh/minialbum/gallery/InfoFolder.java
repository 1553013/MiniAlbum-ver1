package com.example.huynhxuankhanh.minialbum.gallery;

import java.util.ArrayList;

/**
 * Created by HUYNHXUANKHANH on 11/24/2017.
 */

public class InfoFolder {
    private String nameBucket;
    private ArrayList<InfoImage> listImage;

    public InfoFolder(){
        this.nameBucket = "";
        this.listImage = null;
    }

    public String getNameBucket() {
        return nameBucket;
    }

    public void setNameBucket(String nameBucket) {
        this.nameBucket = nameBucket;
    }

    public ArrayList<InfoImage> getListImage() {
        return listImage;
    }

    public void setListImage(ArrayList<InfoImage> listImage) {
        this.listImage = listImage;
    }

    public InfoFolder(String nameBucket, ArrayList<InfoImage> listImage) {

        this.nameBucket = nameBucket;
        this.listImage = listImage;
    }
}

package com.example.huynhxuankhanh.minialbum.gallery;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.MediaStore;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by HUYNHXUANKHANH on 11/2/2017.
 */

public class LoadGallary {

    // declare some tool variable for querying database.
    private ContentResolver contentResolver;
    private Cursor cursor;
    //  private List<String> listPathImage;
    private ArrayList<InfoImage> listImage;
    private final String[] projection = new String[]{
            MediaStore.Images.ImageColumns._ID,
            MediaStore.Images.ImageColumns.DATA, // path file
            MediaStore.Images.ImageColumns.DISPLAY_NAME,
            MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME, // folder name
            MediaStore.Images.ImageColumns.SIZE,
            MediaStore.Images.ImageColumns.DATE_TAKEN, // date taken
    };

    public LoadGallary() {
        contentResolver = null;
        cursor = null;
        listImage = new ArrayList<InfoImage>();
    }

    public ArrayList<InfoImage> getListImage() {
        return listImage;
    }

    public void setContentResolver(ContentResolver contentResolver) {
        this.contentResolver = contentResolver;
    }

    public void query_PathImage(Uri url) {
        cursor = contentResolver.query(url, projection, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                // load data to temp.
                int iD = Integer.parseInt(cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media._ID)));
                String pathFile = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                String nameFile = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME));
                String nameBucket = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_DISPLAY_NAME));
                long sizeFile = Long.parseLong(cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.SIZE)));
                Long date = cursor.getLong(cursor.getColumnIndex(MediaStore.Images.Media.DATE_TAKEN));
                //Calendar cal = Calendar.getInstance();

                // cal.setTimeInMillis(date);
                // SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
                //Date DATE = cal.getTime();
                Date tempdate = new Date();
                tempdate.setTime(date);
                // dateFormat.format(cal.getTime());
                //set item for list
                InfoImage infoImage = new InfoImage(iD, sizeFile, pathFile, nameFile, nameBucket, tempdate.toString());
                if (infoImage != null)
                    listImage.add(infoImage);
                cursor.moveToNext();
            }
        }
    }

    public InfoImage getInfoImage(int position) {
        return listImage.get(position);
    }

    public ArrayList<InfoFolder> getListBucketName(){
        ArrayList<String> tempNameFolder = new ArrayList<>();
        ArrayList<InfoImage> tempListImage = new ArrayList<>();
        ArrayList<InfoFolder> listFolder = new ArrayList<>();
        InfoFolder infoFolder=null;
        for(int i=0;i<listImage.size();++i){
            if(!tempNameFolder.contains(listImage.get(i).getNameBucket())){
                tempNameFolder.add(listImage.get(i).getNameBucket());

                if(infoFolder!=null) {
                    infoFolder.setListImage(tempListImage);
                    listFolder.add(infoFolder);

                    infoFolder = null;
                    tempListImage = new ArrayList<>();

                    infoFolder = new InfoFolder();
                    infoFolder.setNameBucket(listImage.get(i).getNameBucket());
                    tempListImage.add(listImage.get(i));
                }
                else {
                    infoFolder = new InfoFolder();
                    infoFolder.setNameBucket(listImage.get(i).getNameBucket());
                    tempListImage.add(listImage.get(i));
                }
            }
            else{
                tempListImage.add(listImage.get(i));
            }
        }
        return listFolder;
    }
/*
    public ArrayList<InfoFolder> filtImageToFolder(){
        int index = 0;

        ArrayList<String> nameBucket = getListBucketName();
        for(int i=0;i<nameBucket.size();++i){
            InfoFolder infoFolder = new InfoFolder();
            infoFolder.setNameBucket(nameBucket.get(i));
            listFolder.add(infoFolder);
        }
        for (int i=0;i<listImage.size();++i){

        }
        return listFolder;
    }
    */
}

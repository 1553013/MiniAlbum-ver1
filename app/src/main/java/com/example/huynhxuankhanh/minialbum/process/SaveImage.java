package com.example.huynhxuankhanh.minialbum.process;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.graphics.BitmapCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.huynhxuankhanh.minialbum.R;
import com.example.huynhxuankhanh.minialbum.gallery.InfoImage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 * Created by HUYNHXUANKHANH on 12/10/2017.
 */

public class SaveImage extends AsyncTask<InfoImage, InfoImage, InfoImage> {
    public OnTaskReceiveComplete listener = null;
    private InfoImage receive;
    private Context context;
    private Bitmap bm;
    private ArrayList<Bitmap> arrayBitmap;
    private MediaScannerConnection msConn;
    private Dialog dialog;
    private int type;
    private ArrayList<String> listFile;

    public SaveImage(InfoImage receive, Context context, Bitmap bm, ArrayList<Bitmap> arrayBitmap, int type) {
        this.receive = receive;
        this.context = context;
        this.bm = bm;
        this.type = type;
        this.arrayBitmap = arrayBitmap;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // set process dialog
        dialog = new Dialog(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.dialog_process, (ViewGroup) dialog.findViewById(R.id.progress_bar));
        dialog.setContentView(layout);
        dialog.setCancelable(false);
        dialog.show();
        if (type == 1)
            listFile = new ArrayList<>();
    }

    @Override
    protected InfoImage doInBackground(InfoImage... infoImages) {
        if (type == 0) // save 1 tam hinh
        {
            // xử lí lưu ảnh vào gallery store của phone
            String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/MiniAlbum";
            String fileName = "crop_" + receive.getNameFile();
            File file = new File(path, fileName);
            int distinct = 1;
            // kiểm tra tên trùng: nếu trùng thì đặt tên khác
            while (file.exists()) {
                fileName = "crop_" + Integer.toString(distinct) + receive.getNameFile();
                file = new File(path, fileName);
                distinct++;
            }

            // get current ID
            Cursor cursor = context
                    .getContentResolver()
                    .query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            new String[]{MediaStore.Images.ImageColumns._ID}, null, null, null);
            if (cursor != null)
                cursor.moveToLast();
            int newId = Integer.parseInt(cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media._ID)));

            // đây là một tuple của Gallery Database
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media._ID, newId + 1);
            receive.setiD(newId + 1);

            values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());
            Date tempdate = new Date();
            tempdate.setTime(System.currentTimeMillis());
            receive.setDateTaken(tempdate.toString());

            values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
            values.put(MediaStore.Images.Media.DATA, file.toString());
            receive.setPathFile(file.toString());

            values.put(MediaStore.Images.Media.BUCKET_DISPLAY_NAME, "MiniAlbum");
            receive.setNameBucket("MiniAlbum");

            values.put(MediaStore.Images.Media.DISPLAY_NAME, fileName);
            receive.setNameFile(fileName);

            long sizeBm = BitmapCompat.getAllocationByteCount(bm);
            values.put(MediaStore.Images.Media.SIZE, sizeBm);
            receive.setSize(sizeBm);

            String orientation = "0";
            values.put(MediaStore.Images.Media.ORIENTATION, orientation);
            receive.setOrientation(orientation);

            // insert 1 tuple vào bảng Gallery Image, chỉ có thông tin tuple ko có hình ảnh
            context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            // Xuất file ảnh ra folder MiniAlbum
            FileOutputStream fOut = null;
            try {
                fOut = new FileOutputStream(file);

                bm.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
                fOut.flush(); // Not really required
                fOut.close(); // do not forget to close the stream
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            scanPhoto(file.toString());
        }
        if (type == 1) {
            InfoImage temp = receive;
            for (int i = 0; i < arrayBitmap.size() - 1; ++i) {
                // xử lí lưu ảnh vào gallery store của phone
                String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/MiniAlbum";
                String fileName = "crop-" + temp.getNameFile();
                File file = new File(path, fileName);
                int distinct = 1;
                // kiểm tra tên trùng: nếu trùng thì đặt tên khác
                while (file.exists()) {
                    fileName = "crop_" + Integer.toString(distinct) + "-" + temp.getNameFile();
                    file = new File(path, fileName);
                    distinct++;
                }

                // get current ID
                Cursor cursor = context
                        .getContentResolver()
                        .query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                new String[]{MediaStore.Images.ImageColumns._ID}, null, null, null);
                if (cursor != null)
                    cursor.moveToLast();
                int newId = Integer.parseInt(cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media._ID)));

                // đây là một tuple của Gallery Database
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media._ID, newId + 1);

                values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());
                Date tempdate = new Date();
                tempdate.setTime(System.currentTimeMillis());

                values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
                values.put(MediaStore.Images.Media.DATA, file.toString());
                values.put(MediaStore.Images.Media.BUCKET_DISPLAY_NAME, "MiniAlbum");
                values.put(MediaStore.Images.Media.DISPLAY_NAME, fileName);

                long sizeBm = BitmapCompat.getAllocationByteCount(bm);
                values.put(MediaStore.Images.Media.SIZE, sizeBm);

                // insert 1 tuple vào bảng Gallery Image, chỉ có thông tin tuple ko có hình ảnh
                context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                // Xuất file ảnh ra folder MiniAlbum
                FileOutputStream fOut = null;
                try {
                    fOut = new FileOutputStream(file);

                    arrayBitmap.get(i).compress(Bitmap.CompressFormat.JPEG, 100, fOut);
                    fOut.flush(); // Not really required
                    fOut.close(); // do not forget to close the stream
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                listFile.add(file.toString());
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(InfoImage s) {
        super.onPostExecute(s);
        listener.OnTaskReceiveComplete(receive, listFile);
        if (dialog.isShowing())
            dialog.dismiss();
    }

    // refresh lại media
    public void scanPhoto(final String imageFileName) {
        msConn = new MediaScannerConnection(context, new MediaScannerConnection.MediaScannerConnectionClient() {
            public void onMediaScannerConnected() {
                msConn.scanFile(imageFileName, null);
                Toast.makeText(context, "Scan completely !!!", Toast.LENGTH_SHORT).show();
            }

            public void onScanCompleted(String path, Uri uri) {
                msConn.disconnect();
            }
        });
        msConn.connect();
    }
}

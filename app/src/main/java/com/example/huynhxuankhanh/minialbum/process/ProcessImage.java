package com.example.huynhxuankhanh.minialbum.process;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.huynhxuankhanh.minialbum.R;

import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;
import org.opencv.photo.Photo;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 * Created by HUYNHXUANKHANH on 12/9/2017.
 */

public class ProcessImage extends AsyncTask<Bitmap, Bitmap, Bitmap> {
    public OnTaskCompleted listener = null;
    private Bitmap bm;
    private int type;
    private Mat source, dest;
    private Dialog dialog;
    private Context context;

    public ProcessImage(Bitmap bm, Context context, int type) {
        this.bm = bm;
        this.type = type;
        this.context = context;
        source = new Mat();
        dest = new Mat();
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
        // may have some inits before processing
        Utils.bitmapToMat(this.bm, source);
    }

    @Override
    protected Bitmap doInBackground(Bitmap... bitmaps) {
        switch (type) {
            case 5: {
                Imgproc.cvtColor(source, source, Imgproc.COLOR_BGRA2BGR);
                Photo.detailEnhance(source, source, 10, 0.15f);
                Utils.matToBitmap(source, bm);
                break;
            }
            case 4: {
                Imgproc.cvtColor(source, source, Imgproc.COLOR_BGRA2BGR);
                Photo.stylization(source, source, 200, 0.80f);
                Utils.matToBitmap(source, bm);
                break;
            }
            case 3: {
                Imgproc.cvtColor(source, source, Imgproc.COLOR_BGRA2BGR);
                Photo.pencilSketch(source, source, source, 10, 0.08f, 0.05f);
                Utils.matToBitmap(source, bm);
                break;
            }
            case 2: {
                Imgproc.cvtColor(source, source, Imgproc.COLOR_BGRA2BGR);
                Photo.edgePreservingFilter(source, source, 1, 50, 0.4f);
                Utils.matToBitmap(source, bm);
                break;
            }
            case 1: {
                Mat detected_Edge = new Mat();
                // convert to gray
                Imgproc.cvtColor(source, source, Imgproc.COLOR_RGB2GRAY);
                Imgproc.GaussianBlur(source, detected_Edge, new org.opencv.core.Size(3, 3), 0);
                Imgproc.adaptiveThreshold(detected_Edge, detected_Edge, 255, Imgproc.ADAPTIVE_THRESH_MEAN_C, Imgproc.THRESH_BINARY, 5, 4);
                Utils.matToBitmap(detected_Edge, bm);

                break;
            }
            case 0: {
                Imgproc.cvtColor(source, source, Imgproc.COLOR_RGB2GRAY);
                bm = Bitmap.createBitmap(source.width(), source.height(), Bitmap.Config
                        .ARGB_8888);
                Utils.matToBitmap(source, bm);
                break;
            }
        }

        return bm;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        this.bm = bitmap;

        listener.onTaskCompleted(bitmap);
        if (dialog.isShowing())
            dialog.dismiss();
    }
}

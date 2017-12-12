package com.example.huynhxuankhanh.minialbum.process;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.AsyncTask;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.huynhxuankhanh.minialbum.R;
import com.example.huynhxuankhanh.minialbum.activity.EditActivity;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;

import org.opencv.core.Mat;

import java.util.ArrayList;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 * Created by HUYNHXUANKHANH on 12/10/2017.
 */

public class FaceRecognition extends AsyncTask<Bitmap[], Bitmap[], Bitmap[]> {
    private Bitmap mainBm;
    private Bitmap tempBm;
    private ArrayList<Bitmap> retFaceBm;
    private Mat source, dest;
    private Dialog dialog;
    private Context context;
    public OnTaskArrayCompleted listener = null;
    private Paint rect;
    private Canvas canvas;
    private  FaceDetector faceDetector;
    private Frame frame;
    private SparseArray<Face> sparseArray;
    private ArrayList<RectF> rectFS;

    public FaceRecognition(Bitmap mainBm, Context context) {
        this.mainBm = mainBm;
        this.context = context;
        source = new Mat();
        dest = new Mat();
        retFaceBm = new ArrayList<>();
        rectFS = new ArrayList<>();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog = new Dialog(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.dialog_process, (ViewGroup) dialog.findViewById(R.id.progress_bar));
        dialog.setContentView(layout);
        dialog.setCancelable(false);
        dialog.show();


        rect = new Paint();
        rect.setStrokeWidth(5);
        rect.setColor(Color.RED);
        rect.setStyle(Paint.Style.STROKE);
        tempBm = mainBm.copy(Bitmap.Config.ARGB_8888, true);

        canvas = new Canvas(tempBm);
        canvas.drawBitmap(tempBm,0,0,null);
        faceDetector = new FaceDetector.Builder(context)
                .setTrackingEnabled(false)
                .setLandmarkType(FaceDetector.ALL_LANDMARKS)
                .setMode(FaceDetector.FAST_MODE)
                .build();
        if (!faceDetector.isOperational()) {
            Toast.makeText(context, "Face Detector can't recognize in your device", Toast.LENGTH_SHORT).show();
            return ;
        }



    }

    @Override
    protected Bitmap[] doInBackground(Bitmap[]... bitmaps) {
        frame = new Frame.Builder().setBitmap(tempBm).build();
        sparseArray = faceDetector.detect(frame);
        for (int i = 0; i < sparseArray.size(); ++i) {
            Face face = sparseArray.valueAt(i);
            float x1 = face.getPosition().x;
            float y1 = face.getPosition().y;
            float x2 = x1 + face.getWidth();
            float y2 = y1 + face.getHeight();

            if(x1<0)
                x1 = 0;
            if(y1<0)
                y1=0;

            try {
                Bitmap croppedBmp = Bitmap.createBitmap(tempBm, (int) x1, (int) y1, (int) face.getWidth(), (int) face.getHeight());
                retFaceBm.add(croppedBmp);
                RectF rectF = new RectF(x1, y1, x2, y2);
                rectFS.add(rectF);
            }
            catch (Exception e){

            }

        }
        for(int i=0;i<rectFS.size();++i)
            canvas.drawRoundRect(rectFS.get(i), 2, 2, rect);
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap[] bitmaps) {
        super.onPostExecute(bitmaps);
        retFaceBm.add(tempBm);
        listener.onTaskArrayCompleted(retFaceBm);
        if (dialog.isShowing())
            dialog.dismiss();
    }
}

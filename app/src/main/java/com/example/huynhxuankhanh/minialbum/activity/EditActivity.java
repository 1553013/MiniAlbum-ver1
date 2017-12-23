package com.example.huynhxuankhanh.minialbum.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huynhxuankhanh.minialbum.R;
import com.example.huynhxuankhanh.minialbum.gallery.InfoImage;
import com.example.huynhxuankhanh.minialbum.process.FaceRecognition;
import com.example.huynhxuankhanh.minialbum.process.OnTaskArrayCompleted;
import com.example.huynhxuankhanh.minialbum.process.OnTaskCompleted;
import com.example.huynhxuankhanh.minialbum.process.OnTaskReceiveComplete;
import com.example.huynhxuankhanh.minialbum.process.ProcessImage;
import com.example.huynhxuankhanh.minialbum.process.SaveImage;
import com.example.huynhxuankhanh.minialbum.process.SetView;
import com.theartofdev.edmodo.cropper.CropImage;

import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by HUYNHXUANKHANH on 12/1/2017.
 */

public class EditActivity extends AppCompatActivity implements OnTaskCompleted, OnTaskArrayCompleted, OnTaskReceiveComplete, SetView {
    Mat source, dest;
    private InfoImage receive;
    private Button btnCrop, btnEffect, btnFaceDetect, btnBright, btnContrast;
    private Bitmap bm;
    Bitmap currentBM = bm;
    private ImageView imageView;
    private Uri lastBmUri = null;
    private boolean isEdit = false;
    private MediaScannerConnection msConn;
    private int middle;
    private ArrayList<Bitmap> mainArrayBm;
    private boolean isFaceDetector = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        if (!OpenCVLoader.initDebug()) {
            Log.d("OpenCV", "Internal OpenCV library not found. Using OpenCV Manager for initialization");
            //OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION, this, mOpenCVCallBack);
        } else {
            Log.d("OpenCV", "OpenCV library found inside package. Using it!");
            //mOpenCVCallBack.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }
        source = new Mat();
        dest = new Mat();
        mainArrayBm = new ArrayList<>();
        initInterface();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_image);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        //create back button on top-left of toolbar
        toolbar.setNavigationIcon(R.drawable.ic_action_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        receive = getIntent().getParcelableExtra("image-info-edit");
        if (receive == null) {
            receive = getIntent().getParcelableExtra("image-info-fav-edit");
        }
        if (receive != null) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            options.inSampleSize = 2;

            bm = BitmapFactory.decodeFile(receive.getPathFile(), options);
            bm = onSetView(bm, receive);
            imageView.setImageBitmap(bm);

            btnCrop.setOnClickListener((View v) -> {
                PopupMenu popupMenu = new PopupMenu(EditActivity.this, btnCrop);
                popupMenu.getMenuInflater().inflate(R.menu.menupop_crop, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if (lastBmUri == null)
                            lastBmUri = Uri.fromFile(new File(receive.getPathFile()));
                        switch (menuItem.getItemId()) {
                            case R.id.mncrop_23: {
                                startActivityCropper(2, 3);
                                break;
                            }
                            case R.id.mncrop_34: {
                                startActivityCropper(3, 4);
                                break;
                            }
                            case R.id.mncrop_56: {
                                startActivityCropper(5, 6);
                                break;
                            }
                        }
                        return false;
                    }
                });
                popupMenu.show();
            });
            btnEffect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PopupMenu popupMenu = new PopupMenu(EditActivity.this, btnEffect);
                    popupMenu.getMenuInflater().inflate(R.menu.menupop_effect, popupMenu.getMenu());
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            switch (menuItem.getItemId()) {
                                case R.id.mneffect_bw: {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            ProcessImage pro = new ProcessImage(bm, EditActivity.this, 0);
                                            pro.listener = EditActivity.this;
                                            pro.execute();
                                        }
                                    });
                                    isEdit = true;
                                }
                                break;
                                case R.id.mneffect_doc: {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            ProcessImage pro = new ProcessImage(bm, EditActivity.this, 1);
                                            pro.listener = EditActivity.this;
                                            pro.execute();
                                        }
                                    });
                                    isEdit = true;
                                }
                                break;
                                case R.id.mneffect_edPre: {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            ProcessImage pro = new ProcessImage(bm, EditActivity.this, 2);
                                            pro.listener = EditActivity.this;
                                            pro.execute();
                                        }
                                    });
                                    isEdit = true;
                                }
                                break;
                                case R.id.mneffect_pencil: {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            ProcessImage pro = new ProcessImage(bm, EditActivity.this, 3);
                                            pro.listener = EditActivity.this;
                                            pro.execute();
                                        }
                                    });
                                    isEdit = true;
                                }
                                break;
                                case R.id.mneffect_stylization: {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            ProcessImage pro = new ProcessImage(bm, EditActivity.this, 4);
                                            pro.listener = EditActivity.this;
                                            pro.execute();
                                        }
                                    });
                                    isEdit = true;
                                }
                                break;
                                case R.id.mneffect_detail: {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            ProcessImage pro = new ProcessImage(bm, EditActivity.this, 5);
                                            pro.listener = EditActivity.this;
                                            pro.execute();
                                        }
                                    });
                                    isEdit = true;
                                }
                                break;
                            }
                            return false;
                        }
                    });
                    popupMenu.show();
                }
            });
            btnFaceDetect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            FaceRecognition pro = new FaceRecognition(bm, EditActivity.this);
                            pro.listener = EditActivity.this;
                            pro.execute();
                        }
                    });

                }
            });
            btnBright.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    doDialog(0);
                }
            });
            btnContrast.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    doDialog(1);
                }
            });
        }
    }

    @Override
    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            final CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (result != null) { // tức là đã có crop ảnh mới có ảnh trả về
                if (resultCode == RESULT_OK) {
                    // vì kết quả cropper image trả về là một uri
                    Uri resultUri = result.getUri();
                    lastBmUri = resultUri;
                    try {
                        bm = MediaStore.Images.Media.getBitmap(EditActivity.this.getContentResolver(), resultUri);
                        isEdit = true;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (bm != null) {
                        bm = onSetView(bm, receive);
                        imageView.setImageBitmap(bm);
                    }
                } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                    Exception error = result.getError();
                }
            }
        }
    }

    public void initInterface() {
        btnCrop = (Button) findViewById(R.id.btn_crop);
        btnEffect = (Button) findViewById(R.id.btn_effect);
        btnFaceDetect = (Button) findViewById(R.id.btn_facedetect);
        btnBright = (Button) findViewById(R.id.btn_bright);
        btnContrast = (Button) findViewById(R.id.btn_contrast);
        imageView = (ImageView) findViewById(R.id.img_view_edit);
    }

    @Override
    public void onBackPressed() {
        if (isEdit) {
            // khởi tạo một dialog khi mà quá trình chỉnh sửa ảnh được diễn ra.
            android.app.AlertDialog.Builder alertDialogConfirm = new android.app.AlertDialog.Builder(EditActivity.this);
            alertDialogConfirm.setTitle("Do you want to save before quitting?").setPositiveButton("Save and Quit", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (isFaceDetector == false) {
                                SaveImage pro = new SaveImage(receive, EditActivity.this, bm, null, 0);
                                pro.listener = EditActivity.this;
                                pro.execute();
                            } else {
                                SaveImage pro = new SaveImage(receive, EditActivity.this, bm, mainArrayBm, 1);
                                pro.listener = EditActivity.this;
                                pro.execute();
                            }
                        }
                    });

                }
            }).setNegativeButton("Quit", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    // do nothing, just quit
                    EditActivity.super.onBackPressed();
                }
            }).show();
        } else
            EditActivity.super.onBackPressed();

    }

    public void startActivityCropper(int ratioX, int ratioY) {
        Intent intent =
                CropImage
                        .activity(lastBmUri)
                        .setAspectRatio(ratioX, ratioY)
                        .getIntent(EditActivity.this);
        startActivityForResult(intent, CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE);
    }

    // refresh lại media
    public void scanPhoto(final String imageFileName) {
        msConn = new MediaScannerConnection(EditActivity.this, new MediaScannerConnection.MediaScannerConnectionClient() {
            public void onMediaScannerConnected() {
                msConn.scanFile(imageFileName, null);
                Toast.makeText(EditActivity.this, "Scan completely !!!", Toast.LENGTH_SHORT).show();
            }

            public void onScanCompleted(String path, Uri uri) {
                msConn.disconnect();
            }
        });
        msConn.connect();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!OpenCVLoader.initDebug()) {
            Log.d("OpenCV", "Internal OpenCV library not found. Using OpenCV Manager for initialization");
            //OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION, this, mOpenCVCallBack);
        } else {
            Log.d("OpenCV", "OpenCV library found inside package. Using it!");
            //mOpenCVCallBack.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }
    }

    public void doDialog(int type) {
        Dialog yourDialog = new Dialog(EditActivity.this);
        LayoutInflater inflater = (LayoutInflater) EditActivity.this.getSystemService(LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.dialog_seek, (ViewGroup) findViewById(R.id.your_dialog_root_element));
        yourDialog.setContentView(layout);
        yourDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        Window window = yourDialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        yourDialog.setCancelable(false);

        SeekBar seekBar = (SeekBar) layout.findViewById(R.id.dialog_seek);
        Button buttonSet = (Button) layout.findViewById(R.id.btn_accept);
        Button buttonCancel = (Button) layout.findViewById(R.id.btn_cancel);
        TextView txt_progress = (TextView) layout.findViewById(R.id.txt_number);
        middle = 50;
        seekBar.setProgress(middle);
        txt_progress.setText(Integer.toString(0));
        currentBM = bm;
        SeekBar.OnSeekBarChangeListener yourSeekBarListener = new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int value, boolean b) {
                txt_progress.setText(Integer.toString(value - middle));
                int newValue = Math.abs(middle - value); //0-50
                float alpha = (float) newValue / (float) 50;
                Mat src = new Mat(bm.getHeight(), bm.getWidth(), CvType.CV_8UC1);
                //lấy ảnh bitmap gốc gán vào Mat
                Utils.bitmapToMat(bm, src);
                switch (type) {
                    case 0: {
                        if (value < middle) {
                            src.convertTo(src, -1, 1, -newValue * 2);
                        } else {
                            src.convertTo(src, -1, 1, newValue * 2);
                        }
                        break;
                    }
                    case 1: {
                        if (value < middle) {
                            src.convertTo(src, -1, 1 - alpha, 0);
                        } else {
                            src.convertTo(src, -1, 1 + alpha, 0);
                        }
                    }
                    break;
                }
                currentBM = Bitmap.createBitmap(src.cols(), src.rows(), Bitmap.Config
                        .ARGB_8888);
                Utils.matToBitmap(src, currentBM);
                imageView.setImageBitmap(currentBM);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        };
        seekBar.setOnSeekBarChangeListener(yourSeekBarListener);
        wlp.gravity = Gravity.BOTTOM;
        yourDialog.show();
        buttonSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bm = currentBM;
                imageView.setImageBitmap(currentBM);
                yourDialog.cancel();
                isEdit = true;
            }
        });
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageView.setImageBitmap(bm);
                yourDialog.cancel();
                isEdit = false;
            }
        });
    }

    @Override
    public void onTaskCompleted(Bitmap bm) {
        this.bm = bm;
        imageView.setImageBitmap(bm);
    }

    @Override
    public void onTaskArrayCompleted(ArrayList<Bitmap> arrayListBm) {
        if (arrayListBm.size() != 1)// khac tam anh ban dau
        {
            imageView.setImageBitmap(onSetView(arrayListBm.get(arrayListBm.size() - 1), receive));
            mainArrayBm = arrayListBm;
            isEdit = true;
            isFaceDetector = true;
        } else
            Toast.makeText(this, "No faces are found ...", Toast.LENGTH_SHORT).show();
        // store here
    }

    @Override
    public void OnTaskReceiveComplete(InfoImage infoImage, ArrayList<String> PathFiles) {
        Intent resultCrop = new Intent(EditActivity.this, ImageActivity.class);
        receive = infoImage;
        resultCrop.putExtra("crop-image", infoImage);
        if (isFaceDetector)
            resultCrop.putExtra("num-face", mainArrayBm.size() - 1);
        setResult(123, resultCrop);
        if (PathFiles != null) {
            for (int i = 0; i < PathFiles.size(); ++i) {
                //scanPhoto(PathFiles.get(i));
                Intent intent =
                        new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                intent.setData(Uri.parse(PathFiles.get(i)));
                sendBroadcast(intent);
            }
        }
        this.finish();
    }

    @Override
    public Bitmap onSetView(Bitmap bitmap, InfoImage infoImage) {
        Matrix matrix = new Matrix();
        int currentOrientation;
        if (infoImage.getOrientation() == null)
            currentOrientation = 0;
        else
            currentOrientation = Integer.parseInt(infoImage.getOrientation());
        matrix.postRotate(currentOrientation);
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return bitmap;
    }
}

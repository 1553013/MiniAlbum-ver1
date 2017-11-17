package com.example.huynhxuankhanh.minialbum.activity;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huynhxuankhanh.minialbum.R;
import com.example.huynhxuankhanh.minialbum.database.Database;
import com.example.huynhxuankhanh.minialbum.gallary.InfoImage;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;

import java.io.File;
import java.text.SimpleDateFormat;

import uk.co.senab.photoview.PhotoView;
import android.support.v7.app.AppCompatActivity;
public class Main2Activity extends AppCompatActivity {

   // private ImageView imageView ;
    private PhotoView imageView;
    private Button btnShare,btnFav,btnSetWall,btnEdit,btnRemove,btnBack,btnDetail;
    private TextView textViewName;
    private InfoImage receive;
    private Bitmap bm;
    private boolean isFav = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        doLoadInterface();

        receive = getIntent().getParcelableExtra("image-info");
        if(receive==null) {
            receive = getIntent().getParcelableExtra("image-info-fav");
            isFav = true;
        }
        if(receive!=null) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            options.inSampleSize=2;
            bm = BitmapFactory.decodeFile(receive.getPathFile(),options);

            if (bm != null) {

                //gán tên file lên textview

                textViewName.setText(receive.getNameFile());
                imageView.setImageBitmap(bm);
                //Toast.makeText(this, getIntent().getStringExtra("image-view"), Toast.LENGTH_SHORT).show();
                // set top text view name
                // text_view_name.setText(displayname);

                // when user press back Button - on the top left
                btnBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                });
                // view detail of image, will get from intent.
                btnDetail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(Main2Activity.this);

                        String details = String.format("Title: %s \n\nTime: %s \n\nSize: %.2f MB \n\nWidth: %d \n\nHeight: %d\n\nPath: %s",
                                receive.getNameFile(),receive.getDateTaken(),(float)receive.getSize()/1048576,
                                bm.getWidth(),bm.getHeight(),receive.getPathFile());
                        builder.setTitle("Details")
                                .setMessage(details)
                                .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // it user click Close
                                // do nothing, just back the main screen
                            }
                        });
                        AlertDialog alert = builder.create();
                        alert.show();
                    }
                });
                // show a alert dialog to request user delete or not
                btnRemove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // create a alert dialog
                        AlertDialog.Builder builder = new AlertDialog.Builder(Main2Activity.this);
                        // set information for dialog
                        builder.setMessage("Are you sure to delete this image ?")
                                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        // it user click cancel
                                        // do nothing, just back the main screen
                                    }
                                })
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        if(isFav==false) {
                                            //if user click ok => delete image
                                            ContentResolver contentResolver = getContentResolver();
                                            contentResolver.delete(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                                    MediaStore.Images.ImageColumns.DATA + "=?", new String[]{receive.getPathFile()});
                                            finish();
                                        }
                                        else{ // nếu intent đc gửi từ fragment fav thì sẽ xóa trong database của fav
                                            Database database = new Database(Main2Activity.this,"Favorite.sqlite",null,1);
                                            String sql = "DELETE FROM Favorite" + " WHERE Path = '"+receive.getPathFile()+"'";
                                            database.QuerySQL(sql);
                                            finish();
                                        }
                                    }
                                });

                        AlertDialog alert = builder.create();
                        alert.show();
                    }
                });
                // call set wallpaper to set bitmap to wallpaper.
                btnSetWall.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(Intent.ACTION_ATTACH_DATA);
                        intent.addCategory(Intent.CATEGORY_DEFAULT);
                        //lấy đường dẫn file ảnh gắn vào Uri
                        intent.setDataAndType(Uri.fromFile(new File(receive.getPathFile())), "image/*");
                        intent.putExtra("mimeType", "image/*");
                        startActivity(Intent.createChooser(intent, "Set as:"));

                    }
                });
                // add 1 column to the original database to show that it is my favorite image.
                btnFav.setOnClickListener(new View.OnClickListener() {
                    Database database = new Database(Main2Activity.this,"Favorite.sqlite",null,1);
                    @Override
                    public void onClick(View view) {
                        // create table if table is not exist
                        database.QuerySQL("CREATE TABLE IF NOT EXISTS Favorite(Id INTEGER PRIMARY KEY AUTOINCREMENT," +
                                "Path VARCHAR,Title VARCHAR,Bucket VARCHAR,Size LONG,Time VARCHAR)");
                        // check current path is already in database ?
                        // no exist
                        Cursor cursor = database.getData("SELECT * FROM Favorite");
                        if(cursor!=null) {
                            if (checkImageAlreadyInDatabase(cursor, receive.getPathFile(), 1) == false) {
                                String sql = "INSERT INTO Favorite VALUES(null" +
                                        ",'" + receive.getPathFile() + "'" +
                                        ",'"+receive.getNameFile()+"'" +
                                        ",'"+receive.getNameBucket()+"'" +
                                        ","+receive.getSize() +
                                        ",'"+receive.getDateTaken()+"')";
                                database.QuerySQL(sql);
                                Toast.makeText(Main2Activity.this, "Added this image to Favorite album", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(Main2Activity.this, "This image is already in your Favorite album !!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
                // call api facebook to share image(bitmap)
                btnShare.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // to do this: do many steps:
                        /*
                        * Step 1: Include facebook sdk by gradle
                        * Step 2: Request the app's ID for MiniAlbum in facebook developer.
                        * Step 3: Supply provider for facebook in Manifest.
                        * Step 4: check Connection internet
                        * Step 5: Share image by function below
                        *
                        * */
                        if(isOnline()) {
                            SharePhoto photo = new SharePhoto.Builder()
                                    .setBitmap(bm)
                                    .build();
                            SharePhotoContent content = new SharePhotoContent.Builder()
                                    .addPhoto(photo)
                                    .build();
                            ShareDialog.show(Main2Activity.this, content);
                        }
                        else{
                            Toast.makeText(Main2Activity.this, "Please access the Internet", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                // call activity edit image
                btnEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // call another activity Edit

                    }
                });
            } else {
                Toast.makeText(this, "Not enough memory to load image", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
        else{
            Toast.makeText(this, "Error: Wrong path of image", Toast.LENGTH_SHORT).show();
        }

    }
    public void doLoadInterface(){
        textViewName = (TextView)findViewById(R.id.text_view_name);
        imageView = (PhotoView) findViewById(R.id.img_view);
        btnShare = (Button)findViewById(R.id.btn_share);
        btnBack = (Button)findViewById(R.id.btn_back);
        btnFav = (Button)findViewById(R.id.btn_favor);
        btnSetWall = (Button)findViewById(R.id.btn_setscreen);
        btnEdit = (Button)findViewById(R.id.btn_edit);
        btnRemove = (Button)findViewById(R.id.btn_delete);
        btnDetail = (Button)findViewById(R.id.btn_detail);

    }
    public boolean checkImageAlreadyInDatabase(Cursor cursor,String path,int column){
        while(cursor.moveToNext()){
            if(cursor.getString(column).equals(path))
                return true;
        }
        return false;
    }
    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Main2Activity.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}

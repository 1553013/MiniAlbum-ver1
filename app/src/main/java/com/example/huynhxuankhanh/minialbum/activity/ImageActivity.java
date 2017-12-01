package com.example.huynhxuankhanh.minialbum.activity;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huynhxuankhanh.minialbum.R;
import com.example.huynhxuankhanh.minialbum.Utility;
import com.example.huynhxuankhanh.minialbum.database.Database;
import com.example.huynhxuankhanh.minialbum.gallery.InfoImage;
import com.facebook.CallbackManager;
import com.facebook.share.model.ShareContent;
import com.facebook.share.model.ShareHashtag;
import com.facebook.share.model.ShareMediaContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.widget.ShareDialog;

import java.io.File;

import uk.co.senab.photoview.PhotoView;

public class ImageActivity extends AppCompatActivity {

    // private ImageView imageView ;
    private Context context;
    private PhotoView imageView;
    private TextView toolbar_title;
    private Button btnShare, btnFav, btnSetWall, btnEdit, btnRemove, btnDetail;
    private InfoImage receive;
    private Bitmap bm;
    private boolean isFav = false;
    private CallbackManager callbackManager;
    private ShareDialog shareDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        context = this;
        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);
        initInterface();

        // create tool bar
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
        toolbar_title = (TextView) findViewById(R.id.toolbar_image_title);
//        toolbar_title.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                LayoutInflater li = LayoutInflater.from(context);
//                final View promptsView = li.inflate(R.layout.dialog_change_name, null);
//                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
//                // set prompts.xml to alertdialog builder
//                alertDialogBuilder.setView(promptsView);
//
//                final EditText userInput = (EditText) promptsView
//                        .findViewById(R.id.filename_et);
//                final String oldName = receive.getNameFile();
//                final int idStartExtension = oldName.lastIndexOf(".");
//                userInput.setText(oldName.substring(0, idStartExtension));
//                // set dialog message
//                alertDialogBuilder.setCancelable(false).setPositiveButton("Rename", new
//                        DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        String fullPathName = receive.getPathFile();
//                        String pathName = fullPathName.substring(0, fullPathName.length() -
//                                oldName.length());
//                        String extensionFilename = oldName.substring(idStartExtension, oldName.length());
//                        EditText userInput = (EditText) promptsView.findViewById(R.id.filename_et);
//                        String newFileName = userInput.getText().toString() + extensionFilename;
//                        String newFullPathName = pathName + newFileName;
//                        File from = new File(fullPathName);
//                        if(from.renameTo(new File(newFullPathName))) {
//                            toolbar_title.setText(newFileName);
//                            Toast.makeText(ImageActivity.this, "Changed filename " +
//                                    "successfully", Toast
//                                    .LENGTH_LONG)
//                                    .show();
//                        }
//                        else {
//                            Toast.makeText(ImageActivity.this, "Cannot change filename", Toast
//                                    .LENGTH_LONG)
//                                    .show();
//                        }
//                    }
//                })
//                        .setNegativeButton("Cancel",
//                                new DialogInterface.OnClickListener() {
//                                    public void onClick(DialogInterface dialog, int id) {
//                                        dialog.cancel();
//                                    }
//                                });
//                alertDialogBuilder.show();
//            }
//        });

        receive = getIntent().getParcelableExtra("image-info");
        if (receive == null) {
            receive = getIntent().getParcelableExtra("image-info-fav");
            isFav = true;
        }
        if (receive != null) {
            toolbar_title.setText(receive.getNameFile());
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            options.inSampleSize = 2;
            bm = BitmapFactory.decodeFile(receive.getPathFile(), options);

            if (bm != null) {
                imageView.setImageBitmap(bm);
                //Toast.makeText(this, getIntent().getStringExtra("image-view"), Toast.LENGTH_SHORT).show();

                // show a alert dialog to request user delete or not
                btnRemove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // create a alert dialog
                        AlertDialog.Builder alertDialog_confirm = new AlertDialog.Builder(ImageActivity.this);
                        // set information for dialog
                        alertDialog_confirm.setMessage("Are you sure to delete this image?")
                                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        // it user click cancel
                                        // do nothing, just back the main screen
                                    }
                                })
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        //if apply password protection
                                        if (PreferenceManager
                                                .getDefaultSharedPreferences(getApplicationContext())
                                                .getBoolean("security_del", false)) { //has password protection
                                            LayoutInflater li = LayoutInflater.from(context);
                                            final View promptsView = li.inflate(R.layout.dialog_input_password, null);
                                            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                                            // set prompts.xml to alertdialog builder
                                            alertDialogBuilder.setView(promptsView);

                                            final EditText userInput = (EditText) promptsView
                                                    .findViewById(R.id.password_et);

                                            // set dialog message
                                            alertDialogBuilder.setCancelable(false).setPositiveButton("CONFIRM", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    // get user input and check it
                                                    if (userInput.getText().toString().equals(PreferenceManager
                                                            .getDefaultSharedPreferences
                                                                    (getApplicationContext())
                                                            .getString(Utility.PASSWORD_KEY, ""))) {
                                                        ((ViewGroup) promptsView.getParent()).removeView(promptsView);
                                                        Toast.makeText(ImageActivity.this, "Correct" +
                                                                " password", Toast.LENGTH_LONG).show();
                                                        if (!isFav) {
                                                            //if user click ok => delete image
                                                            ContentResolver contentResolver = getContentResolver();
                                                            contentResolver.delete(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                                                    MediaStore.Images.ImageColumns.DATA + "=?", new String[]{receive.getPathFile()});
                                                            finish();
                                                        } else { // nếu intent đc gửi từ fragment fav thì sẽ xóa trong database của fav
                                                            Database database = new Database(ImageActivity.this);
                                                            String sql = "DELETE FROM Favorite" + " WHERE Path = '" + receive.getPathFile() + "'";
                                                            database.QuerySQL(sql);
                                                            finish();
                                                        }
                                                    } else {
                                                        Toast.makeText(ImageActivity.this, "Wrong " +
                                                                "password", Toast.LENGTH_LONG).show();
                                                    }
                                                }
                                            })
                                                    .setNegativeButton("Cancel",
                                                            new DialogInterface.OnClickListener() {
                                                                public void onClick(DialogInterface dialog, int id) {
                                                                    dialog.cancel();
                                                                }
                                                            });
                                            alertDialogBuilder.show();
                                        } else {
                                            if (!isFav) {
                                                //if user click ok => delete image
                                                ContentResolver contentResolver = getContentResolver();
                                                contentResolver.delete(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                                        MediaStore.Images.ImageColumns.DATA + "=?", new String[]{receive.getPathFile()});
                                                finish();
                                            } else { // nếu intent đc gửi từ fragment fav thì sẽ xóa trong database của fav
                                                Database database = new Database(ImageActivity.this);
                                                String sql = "DELETE FROM Favorite" + " WHERE Path = '" + receive.getPathFile() + "'";
                                                database.QuerySQL(sql);
                                                finish();
                                            }
                                        }
                                    }
                                });

                        AlertDialog alert = alertDialog_confirm.create();
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
                    Database database = new Database(ImageActivity.this);

                    @Override
                    public void onClick(View view) {
                        // check current path is already in database ?
                        // no exist
                        Cursor cursor = database.getData("SELECT * FROM Favorite");
                        if (cursor != null) {
                            if (checkImageAlreadyInDatabase(cursor, receive.getPathFile(), 1) == false) {
                                String sql = "INSERT INTO Favorite VALUES(null" +
                                        ",'" + receive.getPathFile() + "'" +
                                        ",'" + receive.getNameFile() + "'" +
                                        ",'" + receive.getNameBucket() + "'" +
                                        "," + receive.getSize() +
                                        ",'" + receive.getDateTaken() + "')";
                                database.QuerySQL(sql);
                                Toast.makeText(ImageActivity.this, "Added this image to Favorite album", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(ImageActivity.this, "This image is already in your Favorite album !!", Toast.LENGTH_SHORT).show();
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
                        if (isOnline()) {
                            SharePhoto photo = new SharePhoto.Builder()
                                    .setBitmap(bm)
                                    .build();
                            ShareContent content = new ShareMediaContent.Builder()
                                    .addMedium(photo)
                                    .setShareHashtag(new ShareHashtag.Builder()
                                            .build())
                                    .build();
                            ShareDialog.show(ImageActivity.this, content);
                        } else {
                            Toast.makeText(ImageActivity.this, "Please access the Internet", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                // call activity edit image
                btnEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // call another activity Edit
//                        Database database = new Database(Main2Activity.this,"Favorite.sqlite",null,1);
//                        database.QuerySQL("DROP TABLE Favourite");
                    }
                });
            } else {
                Toast.makeText(this, "Not enough memory to load image", Toast.LENGTH_SHORT).show();
                finish();
            }
        } else {
            Toast.makeText(this, "Error: Wrong path of image", Toast.LENGTH_SHORT).show();
        }

    }

    public void initInterface() {
        imageView = (PhotoView) findViewById(R.id.img_view);
        btnShare = (Button) findViewById(R.id.btn_share);
        btnFav = (Button) findViewById(R.id.btn_favor);
        btnSetWall = (Button) findViewById(R.id.btn_setscreen);
        btnEdit = (Button) findViewById(R.id.btn_edit);
        btnRemove = (Button) findViewById(R.id.btn_delete);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_image, menu);
        return (super.onCreateOptionsMenu(menu));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btn_detail:
                AlertDialog.Builder builder = new AlertDialog.Builder(ImageActivity.this);

                String details = String.format("Title: %s \n\nTime: %s \n\nSize: %.2f MB \n\nWidth: %d \n\nHeight: %d\n\nPath: %s",
                        receive.getNameFile(), receive.getDateTaken(), (float) receive.getSize() / 1048576,
                        bm.getWidth(), bm.getHeight(), receive.getPathFile());
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
                return (true);
        }

        return (super.onOptionsItemSelected(item));
    }

    public boolean checkImageAlreadyInDatabase(Cursor cursor, String path, int column) {
        while (cursor.moveToNext()) {
            if (cursor.getString(column).equals(path))
                return true;
        }
        return false;
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(ImageActivity.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}

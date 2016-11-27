package com.sohba_travel.sohba.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.sohba_travel.sohba.R;
import com.sohba_travel.sohba.Utility.font;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ActivityProfileImage extends AppCompatActivity {

    public static String ActivityProfileImageS = "ActivityProfileImage";
    private static int status;

    public static final int REQUEST_IMAGE_CAPTURE = 1;
    private String filePath;
    private Uri selectedImage;


    String ba1;

    final String IMAGEFOLDER = "sohba";

    //Request codes

    final int GALLERY = 0;
    final int CAMERA = 1;

    //Context
    Context context;


    //Uri used when picking from the camera
    Uri imageURI;

    ImageView imageView;
    ImageView imageHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_profile_image);

        context = this;
        imageView = (ImageView) findViewById(R.id.imageView);
        imageHolder = (ImageView) findViewById(R.id.imageHolder);


        status = getIntent().getIntExtra(ContinueAs_activity.ContinueAs_activityS, 0);


        font font = new font();
        font.changeFonts(this, (LinearLayout) findViewById(R.id.parent_layout));
    }

    public void imageHolder(View view) {

        activeTakePhoto();
    }


    private void activeTakePhoto() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Choose Image Source");
        builder.setItems(new CharSequence[]{"Gallery", "Camera"},
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:

                                //Launching the gallery
                                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                startActivityForResult(i, GALLERY);

                                break;

                            case 1:
                                //Specify a camera intent
                                Intent getCameraImage = new Intent("android.media.action.IMAGE_CAPTURE");

                                File cameraFolder;

                                //Check to see if there is an SD card mounted
                                if (android.os.Environment.getExternalStorageState().equals
                                        (android.os.Environment.MEDIA_MOUNTED))
                                    cameraFolder = new File(android.os.Environment.getExternalStorageDirectory(),
                                            IMAGEFOLDER);
                                else
                                    cameraFolder = ActivityProfileImage.this.getCacheDir();
                                if (!cameraFolder.exists())
                                    cameraFolder.mkdirs();

                                //Appending timestamp to "picture_"
                                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd'T'HHmmss");
                                String timeStamp = dateFormat.format(new Date());
                                String imageFileName = "picture_" + timeStamp + ".jpg";

                                File photo = new File(Environment.getExternalStorageDirectory(),
                                        IMAGEFOLDER + imageFileName);
                                getCameraImage.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photo));

                                //Setting a global variable to be used in the OnActivityResult
                                imageURI = Uri.fromFile(photo);

                                startActivityForResult(getCameraImage, CAMERA);

                                break;
                            default:
                                break;
                        }
                    }
                });

        builder.show();


    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Uri selectedImage;
        String selectedImagePath = null;
        if (resultCode == RESULT_OK) {
            imageHolder.setVisibility(View.INVISIBLE);
            switch (requestCode) {

                case GALLERY:
                    selectedImage = data.getData();
//                    selectedImagePath=selectedImage.getEncodedPath();
                    Picasso.with(context)
                            .load(selectedImage)
                            .fit()
                            .into(imageView);


                    break;

                case CAMERA:
//                    selectedImagePath=imageURI.getPath();
                    Picasso.with(context)
                            .load(imageURI)
                            .fit()
                            .into(imageView);

                    break;
            }


        }

    }


    public void goToNext(View view) {

        if (imageView.getDrawable()==null) {
            Toast.makeText(context, "Please select Image Required ! ", Toast.LENGTH_LONG).show();
            return;
        }
        Bitmap bm = null;
        bm = ((BitmapDrawable) imageView.getDrawable()).getBitmap();

        if (bm.equals(null)) {
            Toast.makeText(context, "Please select Image Required ! ", Toast.LENGTH_LONG).show();
            return;
        }


        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        ba1 = Base64.encodeToString(byteArray, Base64.DEFAULT);
//        Toast.makeText(context,ba1,Toast.LENGTH_LONG).show();



        if (status == 1) {

            Intent i = new Intent(ActivityProfileImage.this, RegisterTakeSelfie.class);
            startActivity(i);
        } else {
            Intent i = new Intent(ActivityProfileImage.this, RegisterAddLater.class);
            startActivity(i);
        }
    }
}

package com.sohba_travel.sohba.activities;

import android.content.Context;
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


public class ImageIdActivity extends AppCompatActivity {


    public static String ImageIdActivitys1="ImageIdActivity1";
    public static String ImageIdActivitys2="ImageIdActivity2";

    private static int status;
    private static String imageProfile;
    private static String imageSelfie;

    public static final int REQUEST_IMAGE_CAPTURE = 1;
    private String filePath;
    private Uri selectedImage;






    String ba1;
    String ba2;

    final String IMAGEFOLDER = "sohba";

    //Request codes

    final int GALLERY = 0;
    final int CAMERA = 1;

    //Context
    Context context;


    //Uri used when picking from the camera
    Uri imageURI;

    ImageView imageView1,imageView2;
    ImageView imageHolder1,imageHolder2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_id);
        context=this;
        imageView1= (ImageView) findViewById(R.id.imageView1);
        imageHolder1= (ImageView) findViewById(R.id.imageHolder1);
        imageView2= (ImageView) findViewById(R.id.imageView2);
        imageHolder2= (ImageView) findViewById(R.id.imageHolder2);


        status = getIntent().getIntExtra(ContinueAs_activity.ContinueAs_activityS, 0);
        imageProfile=getIntent().getStringExtra(ActivityProfileImage.ActivityProfileImageS);
        imageSelfie=getIntent().getStringExtra(RegisterTakeSelfie.RegisterTakePhotos);


        font font = new font();

        font.changeFonts(this, (LinearLayout) findViewById(R.id.parent_layout));
    }


    private static int  x;
    public void imageHolder1(View view) {

        activeTakePhoto();
        x=1;
    }
    public void imageHolder2(View view) {

        activeTakePhoto();
        x=2;
    }


    private void activeTakePhoto() {
        //Specify a camera intent
        Intent getCameraImage = new Intent("android.media.action.IMAGE_CAPTURE");

        File cameraFolder;

        //Check to see if there is an SD card mounted
        if (Environment.getExternalStorageState().equals
                (Environment.MEDIA_MOUNTED))
            cameraFolder = new File(Environment.getExternalStorageDirectory(),
                    IMAGEFOLDER);
        else
            cameraFolder = ImageIdActivity.this.getCacheDir();
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

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (resultCode == RESULT_OK) {


            if (x==1){
                imageHolder1.setVisibility(View.INVISIBLE);
                switch (requestCode) {


                    case CAMERA:
                        Picasso.with(context)
                                .load(imageURI)
                                .fit()
                                .into(imageView1);
                        break;
                }
            }else {

                imageHolder2.setVisibility(View.INVISIBLE);
                switch (requestCode) {


                    case CAMERA:
                        Picasso.with(context)
                                .load(imageURI)
                                .fit()
                                .into(imageView2);

                        break;
                }


            }

        }



    }

    public void goToNext(View view){


        if (imageView1.getDrawable()==null||imageView2.getDrawable()==null) {
            Toast.makeText(context, "Please select Image Required ! ", Toast.LENGTH_LONG).show();
            return;
        }
        Bitmap bm1 = null;
        bm1 = ((BitmapDrawable)imageView1.getDrawable()).getBitmap();

        Bitmap bm2 = null;
        bm2 = ((BitmapDrawable)imageView2.getDrawable()).getBitmap();
        if (bm1.equals(null)||bm2.equals(null)){
            imageHolder1.setVisibility(View.VISIBLE);
            imageHolder2.setVisibility(View.VISIBLE);
            Toast.makeText(context,"Please select Images Required ! ", Toast.LENGTH_LONG).show();
            return;
        }
        ByteArrayOutputStream byteArrayOutputStream1 = new ByteArrayOutputStream();
        bm1.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream1);
        byte[] byteArray1 = byteArrayOutputStream1.toByteArray();
        ba1 = Base64.encodeToString(byteArray1, Base64.DEFAULT);

        ByteArrayOutputStream byteArrayOutputStream2= new ByteArrayOutputStream();
        bm2.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream2);
        byte[] byteArray2 = byteArrayOutputStream2.toByteArray();
        ba2 = Base64.encodeToString(byteArray2, Base64.DEFAULT);


//        Intent i=new Intent(ImageIdActivity.this,DetailOne_name.class);
//        i.putExtra(ContinueAs_activity.ContinueAs_activityS,1);
//        i.putExtra(ActivityProfileImage.ActivityProfileImageS,imageProfile);
//        i.putExtra(RegisterTakeSelfie.RegisterTakePhotos,imageSelfie);
//        i.putExtra(ImageIdActivitys1,ba1);
//        i.putExtra(ImageIdActivitys1,ba2);
//
//        startActivity(i);

        startActivity(new Intent(ImageIdActivity.this,RegisterAddLater.class));

    }
}

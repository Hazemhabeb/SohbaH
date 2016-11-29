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
import com.sohba_travel.sohba.Utility.RegisterData;
import com.sohba_travel.sohba.Utility.font;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;


public class RegisterTakeSelfie extends AppCompatActivity {


    public static String RegisterTakePhotos="RegisterTakeSelfie";
    private static int status;
    private static String imageProfile;

    public static final int REQUEST_IMAGE_CAPTURE = 1;
    private String filePath;
    private Uri selectedImage;



    //data from intent
    private static String fn;
    private static String ln;
    private static String ph;
    private static String pass = "";
    private static String mail = "";
    private static String gen;
    private static String birth;
    private static String profile_image;
    private static  byte[] byteArray_profile;



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
        setContentView(R.layout.take_selfie);

        context=this;
        imageView= (ImageView) findViewById(R.id.imageView);
        imageHolder= (ImageView) findViewById(R.id.imageHolder);



        status = getIntent().getIntExtra(ContinueAs_activity.ContinueAs_activityS, 0);
        fn = getIntent().getStringExtra(RegisterData.fn);
        ln = getIntent().getStringExtra(RegisterData.ln);
        ph = getIntent().getStringExtra(RegisterData.ph);
        pass = getIntent().getStringExtra(RegisterData.pass);
        mail = getIntent().getStringExtra(RegisterData.mail);
        gen = getIntent().getStringExtra(RegisterData.gen);
        birth = getIntent().getStringExtra(RegisterData.birth);
        byteArray_profile = getIntent().getByteArrayExtra(RegisterData.image_profile);



        font font = new font();
        font.changeFonts(this, (LinearLayout) findViewById(R.id.parent_layout));
    }
    public void imageHolder(View view) {

        activeTakePhoto();
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
            cameraFolder = RegisterTakeSelfie.this.getCacheDir();
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


            imageHolder.setVisibility(View.INVISIBLE);
            switch (requestCode) {


                case CAMERA:
                    Picasso.with(context)
                            .load(imageURI)
                            .fit()
                            .into(imageView);

//                    String selectedImagePath=imageURI.getPath();
//                    Bitmap bm = null;
//                    BitmapFactory.Options options = new BitmapFactory.Options();
//                    options.inJustDecodeBounds = true;
//                    BitmapFactory.decodeFile(selectedImagePath, options);
//                    final int REQUIRED_SIZE = 200;
//                    int scale = 1;
//                    while (options.outWidth / scale / 2 >= REQUIRED_SIZE
//                            && options.outHeight / scale / 2 >= REQUIRED_SIZE)
//                        scale *= 2;
//                    options.inSampleSize = scale;
//                    options.inJustDecodeBounds = false;
//                    bm = BitmapFactory.decodeFile
//                            (selectedImagePath, options);
//                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//                    bm.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
//                    byte[] byteArray = byteArrayOutputStream.toByteArray();
//                    ba1 = Base64.encodeToString(byteArray, Base64.DEFAULT);
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

        bm = ((BitmapDrawable)imageView.getDrawable()).getBitmap();

        if (bm.equals(null)){
            imageHolder.setVisibility(View.VISIBLE);
            Toast.makeText(context,"Please select Image Required ! ", Toast.LENGTH_LONG).show();
            return;
        }

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        ba1 = Base64.encodeToString(byteArray, Base64.DEFAULT);



//        Toast.makeText(context,imageProfile,Toast.LENGTH_LONG).show();

        Intent i=new Intent(RegisterTakeSelfie.this,ImageIdActivity.class);
        i.putExtra(ContinueAs_activity.ContinueAs_activityS, status);
        i.putExtra(RegisterData.fn, fn);
        i.putExtra(RegisterData.ln, ln);
        i.putExtra(RegisterData.pass, pass);
        i.putExtra(RegisterData.birth, birth);
        i.putExtra(RegisterData.gen, gen);
        i.putExtra(RegisterData.ph, ph);
        i.putExtra(RegisterData.mail, mail);
        i.putExtra(RegisterData.image_profile, byteArray_profile);
        i.putExtra(RegisterData.image_selfie, byteArray);


        startActivity(i);
    }
}

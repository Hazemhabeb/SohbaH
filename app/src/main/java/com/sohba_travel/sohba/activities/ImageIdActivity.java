package com.sohba_travel.sohba.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.sohba_travel.sohba.R;
import com.sohba_travel.sohba.Utility.RegisterData;
import com.sohba_travel.sohba.Utility.font;
import com.sohba_travel.sohba.models.NewUserHost;
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



    //data from intent
    private static String fn;
    private static String ln;
    private static String ph;
    private static String pass = "";
    private static String mail = "";
    private static String gen;
    private static String birth;
    private static String profile_image;
    private static String selfie_image;
    private static String IdFront_image;
    private static String Idback_image;
    private static byte[] byteArray_profile;
    private static byte[] byteArray_selfie;

    private static String user_id;



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



    //firebase
    private FirebaseAuth mAuth;
    private FirebaseUser mFirebaseUser;
    private DatabaseReference mFirebaseDatabaseReference;
    // Create a storage reference from our app
    StorageReference storageRef;
    FirebaseStorage storage;
    StorageReference imagesRef;
    StorageReference spaceRef;
    StorageReference mountainsRef;

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
        fn = getIntent().getStringExtra(RegisterData.fn);
        ln = getIntent().getStringExtra(RegisterData.ln);
        ph = getIntent().getStringExtra(RegisterData.ph);
        pass = getIntent().getStringExtra(RegisterData.pass);
        mail = getIntent().getStringExtra(RegisterData.mail);
        gen = getIntent().getStringExtra(RegisterData.gen);
        birth = getIntent().getStringExtra(RegisterData.birth);
        byteArray_profile = getIntent().getByteArrayExtra(RegisterData.image_profile);
        byteArray_selfie= getIntent().getByteArrayExtra(RegisterData.image_selfie);


        //firebase intial
        mAuth = FirebaseAuth.getInstance();

        // New child entries
        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();

        storage = FirebaseStorage.getInstance();
        // Points to the root reference
        storageRef = storage.getReferenceFromUrl("gs://sohba-717ca.appspot.com/");
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

    static ProgressDialog pDialog;
    public void goToNext(View view){

//        if (status==1){
//            Toast.makeText(context,"profil"+ byteArray_profile.toString()+"    " +
//                    ""+byteArray_selfie, Toast.LENGTH_LONG).show();
//            return;
//        }

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
        final byte[] byteArray1 = byteArrayOutputStream1.toByteArray();
        ba1 = Base64.encodeToString(byteArray1, Base64.DEFAULT);

        ByteArrayOutputStream byteArrayOutputStream2= new ByteArrayOutputStream();
        bm2.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream2);
        final byte[] byteArray2 = byteArrayOutputStream2.toByteArray();
        ba2 = Base64.encodeToString(byteArray2, Base64.DEFAULT);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();
        mAuth.createUserWithEmailAndPassword(mail, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("h", "createUserWithEmail:onComplete:" + task.isSuccessful());


                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(ImageIdActivity.this, "Can't Create account",
                                    Toast.LENGTH_SHORT).show();
                            pDialog.hide();
                        } else {
                            Toast.makeText(ImageIdActivity.this, "created",
                                    Toast.LENGTH_SHORT).show();

//                            startActivity(new Intent(RegisterNew.this, DetialFive_interest.class));

                            mFirebaseUser = mAuth.getCurrentUser();
                            user_id = mFirebaseUser.getUid();

                            uploadImage(byteArray_profile,1);
                            uploadImage(byteArray_selfie,2);
                            uploadImage(byteArray1,3);
                            uploadImage(byteArray2,4);
//                                addNewUserData();

                        }

                        // [START_EXCLUDE]
//                        hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });

//        Intent i=new Intent(ImageIdActivity.this,DetailOne_name.class);
//        i.putExtra(ContinueAs_activity.ContinueAs_activityS,1);
//        i.putExtra(ActivityProfileImage.ActivityProfileImageS,imageProfile);
//        i.putExtra(RegisterTakeSelfie.RegisterTakePhotos,imageSelfie);
//        i.putExtra(ImageIdActivitys1,ba1);
//        i.putExtra(ImageIdActivitys1,ba2);
//
//        startActivity(i);

//        startActivity(new Intent(ImageIdActivity.this,RegisterAddLater.class));

    }
    public void uploadImage(byte[] byteArray, final int i) {
        if (i==1){
            mountainsRef = storageRef.child("imageProfile" + user_id + ".png");
        }else if (i==2){
            mountainsRef = storageRef.child("imageSelfie" + user_id + ".png");
        }else if(i==3){
            mountainsRef = storageRef.child("imageId_front" + user_id + ".png");
        }else if(i==4){
            mountainsRef = storageRef.child("imageId_back" + user_id + ".png");
        }

        UploadTask uploadTask = mountainsRef.putBytes(byteArray);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                Toast.makeText(ImageIdActivity.this," error upload images",Toast.LENGTH_LONG).show();
                pDialog.hide();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                if (i==1){
                    profile_image = downloadUrl.toString();
                }else if (i==2){
                    selfie_image = downloadUrl.toString();
                }else if (i==3){
                    IdFront_image = downloadUrl.toString();
                }else if (i==4){
                    Idback_image = downloadUrl.toString();
                    addNewUserData();
                }



//                Toast.makeText(ActivityProfileImage.this,downloadUrl+"   done in signin",Toast.LENGTH_LONG).show();
//                addNewUserData();
            }
        });
    }
    private void addNewUserData() {
        pDialog.hide();
        NewUserHost newUserHost = new
                NewUserHost(user_id, status + "", fn
                , ln, birth
                , gen, ph,
                mail,
                "empty", 0 + "", profile_image,IdFront_image,Idback_image,selfie_image,
                "empty", "empty", "empty");

        mFirebaseDatabaseReference.child("users")
                .child(user_id).setValue(newUserHost);
//        mFirebaseDatabaseReference.child("users")
//                .child(user_id).child("profileImage").setValue("image_test");

        Intent i=new Intent(ImageIdActivity.this, RegisterAddLater.class);
        i.putExtra(RegisterData.User_id,user_id);
        startActivity(i);


    }
}

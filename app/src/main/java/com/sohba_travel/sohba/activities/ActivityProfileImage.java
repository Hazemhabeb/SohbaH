package com.sohba_travel.sohba.Activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.IntentCompat;
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
import com.sohba_travel.sohba.Models.NewUserGuest;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ActivityProfileImage extends AppCompatActivity {

    public static String ActivityProfileImageS = "ActivityProfileImage";

    public static final int REQUEST_IMAGE_CAPTURE = 1;
    private String filePath;
    private Uri selectedImage;


    String ba1;

    final String IMAGEFOLDER = "sohba";


    private static int status;
    private static String user_id;

    //Request codes

    final int GALLERY = 0;
    final int CAMERA = 1;

    //Context
    Context context;


    //Uri used when picking from the camera
    Uri imageURI;

    ImageView imageView;
    ImageView imageHolder;

    //data from intent
    private static String fn;
    private static String ln;
    private static String ph;
    private static String pass = "";
    private static String mail = "";
    private static String gen;
    private static String birth;
    private static String profile_image;

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
        setContentView(R.layout.activity_activity_profile_image);

        context = this;
        imageView = (ImageView) findViewById(R.id.imageView);
        imageHolder = (ImageView) findViewById(R.id.imageHolder);


        status = getIntent().getIntExtra(ContinueAs_activity.ContinueAs_activityS, 0);
        fn = getIntent().getStringExtra(RegisterData.fn);
        ln = getIntent().getStringExtra(RegisterData.ln);
        ph = getIntent().getStringExtra(RegisterData.ph);
        pass = getIntent().getStringExtra(RegisterData.pass);
        mail = getIntent().getStringExtra(RegisterData.mail);
        gen = getIntent().getStringExtra(RegisterData.gen);
        birth = getIntent().getStringExtra(RegisterData.birth);


        //firebase intial
        mAuth = FirebaseAuth.getInstance();

        // New child entries
        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();

        storage = FirebaseStorage.getInstance();
        // Points to the root reference
        storageRef = storage.getReferenceFromUrl("gs://sohba-717ca.appspot.com/");

        // Create a reference to "mountains.jpg"


        // Create a reference to 'images/mountains.jpg'
        StorageReference mountainImagesRef = storageRef.child("images/mountains.jpg");
        // Create a child reference
        // imagesRef now points to "images"
//         imagesRef = storageRef.child("images");
//
//
//        // Child references can also take paths
//        // spaceRef now points to "users/me/profile.png
//        // imagesRef still points to "images"
//         spaceRef = storageRef.child("images/space.jpg");
//
//        // Note that you can use variables to create child values
//        String fileName = "space.jpg";
//        spaceRef = imagesRef.child(fileName);
//
//        // File path is "images/space.jpg"
//        String path = spaceRef.getPath();
//
//        // File name is "space.jpg"
//        String name = spaceRef.getName();
//
//        // Points to "images"
//        imagesRef = spaceRef.getParent();

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

     static  ProgressDialog pDialog;

    public void goToNext(View view) {

        if (imageView.getDrawable() == null) {
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
        final byte[] byteArray = byteArrayOutputStream.toByteArray();
        ba1 = Base64.encodeToString(byteArray, Base64.DEFAULT);
//        Toast.makeText(context,ba1,Toast.LENGTH_LONG).show();


        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");

        if (status == 1) {

            Intent i = new Intent(ActivityProfileImage.this, RegisterTakeSelfie.class);
            i.putExtra(ContinueAs_activity.ContinueAs_activityS, status);
            i.putExtra(RegisterData.fn, fn);
            i.putExtra(RegisterData.ln, ln);
            i.putExtra(RegisterData.pass, pass);
            i.putExtra(RegisterData.birth, birth);
            i.putExtra(RegisterData.gen, gen);
            i.putExtra(RegisterData.ph, ph);
            i.putExtra(RegisterData.mail, mail);
            i.putExtra(RegisterData.image_profile, byteArray);

            startActivity(i);

        } else {
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
                                Toast.makeText(ActivityProfileImage.this, "Can't Create account",
                                        Toast.LENGTH_SHORT).show();
                                pDialog.hide();
                            } else {
                                Toast.makeText(ActivityProfileImage.this, "created",
                                        Toast.LENGTH_SHORT).show();

//                            startActivity(new Intent(RegisterNew.this, DetialFive_interest.class));

                                mFirebaseUser = mAuth.getCurrentUser();
                                user_id = mFirebaseUser.getUid();

                                uploadImage(byteArray);
//                                addNewUserData();

                            }

                            // [START_EXCLUDE]
//                        hideProgressDialog();
                            // [END_EXCLUDE]
                        }
                    });
//            Intent i = new Intent(ActivityProfileImage.this, RegisterAddLater.class);
//            startActivity(i);
        }
    }

    public void uploadImage(byte[] byteArray) {
        mountainsRef = storageRef.child("imageProfile" + user_id + ".png");
        UploadTask uploadTask = mountainsRef.putBytes(byteArray);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                Toast.makeText(ActivityProfileImage.this," error upload images",Toast.LENGTH_LONG).show();
                pDialog.hide();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                profile_image = downloadUrl.toString();
//                Toast.makeText(ActivityProfileImage.this,downloadUrl+"   done in signin",Toast.LENGTH_LONG).show();
                addNewUserData();
            }
        });
    }

    private void addNewUserData() {
        NewUserGuest newUserGuest = new
                NewUserGuest(user_id, status + "", fn
                , ln, birth
                , gen, ph,
                mail,
                "empty", 0 + "", profile_image,
                "empty", "empty", "empty");

        mFirebaseDatabaseReference.child("users")
                .child(user_id).setValue(newUserGuest);

//        mFirebaseDatabaseReference.child("users")
//                .child(user_id).child("profileImage").setValue("image_test");

        pDialog.hide();
        Intent i=new Intent(ActivityProfileImage.this, RegisterAddLater.class);
        i.putExtra(RegisterData.User_id,user_id);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);


    }
}

package com.sohba_travel.sohba.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.AppCompatActivity;
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
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.sohba_travel.sohba.Models.NewUserGuest;
import com.sohba_travel.sohba.R;
import com.sohba_travel.sohba.Utility.RegisterData;
import com.sohba_travel.sohba.Utility.font;
import com.vansuita.pickimage.PickImageDialog;
import com.vansuita.pickimage.PickSetup;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.listeners.IPickResult;

import java.io.ByteArrayOutputStream;


public class ActivityProfileImage extends AppCompatActivity implements IPickResult {

    public static String ActivityProfileImageS = "ActivityProfileImage";

    public static final int REQUEST_IMAGE_CAPTURE = 1;
    private String filePath;
    private Uri selectedImage;



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
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_profile_image);

        context = this;
        imageView = (ImageView) findViewById(R.id.imageView);
        imageHolder = (ImageView) findViewById(R.id.imageHolder);


        status = getIntent().getIntExtra(com.sohba_travel.sohba.Activities.ContinueAs_activity.ContinueAs_activityS, 0);
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


        font font = new font();
        font.changeFonts(this, (LinearLayout) findViewById(R.id.parent_layout));
    }

    public void imageHolder(View view) {
        PickImageDialog.on(ActivityProfileImage.this, new PickSetup());
    }

    static ProgressDialog pDialog;

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


        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");

        if (status == 1) {

            Log.d("hazem","here in profile image ");
            Intent i = new Intent(ActivityProfileImage.this,RegisterTakeSelfie.class);
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

                            if (!task.isSuccessful()) {
                                Toast.makeText(ActivityProfileImage.this, "Can't Create account",
                                        Toast.LENGTH_SHORT).show();
                                pDialog.hide();
                            } else {
                                Toast.makeText(ActivityProfileImage.this, "created",
                                        Toast.LENGTH_SHORT).show();

                                mFirebaseUser = mAuth.getCurrentUser();
                                user_id = mFirebaseUser.getUid();

                                uploadImage(byteArray);
                            }

                        }
                    });
        }
    }

    public void uploadImage(byte[] byteArray) {
        mountainsRef = storageRef.child("imageProfile" + user_id + ".png");
        UploadTask uploadTask = mountainsRef.putBytes(byteArray);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(ActivityProfileImage.this, " error upload images", Toast.LENGTH_LONG).show();
                pDialog.hide();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                profile_image = downloadUrl.toString();
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

        String token = FirebaseInstanceId.getInstance().getToken();

        mFirebaseDatabaseReference.child("users")
                .child(user_id).setValue(newUserGuest);
        mFirebaseDatabaseReference.child("users")
                .child(user_id).child("token").setValue(token);


        pDialog.hide();
        Intent i = new Intent(ActivityProfileImage.this, RegisterAddLater.class);
        i.putExtra(RegisterData.User_id, user_id);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);


    }

    @Override
    public void onPickResult(PickResult r) {
        if (r.getError() == null) {
            imageHolder.setImageBitmap(null);
            imageView.setImageBitmap(r.getBitmap());

        } else {
            //Handle possible errors
            //TODO: do what you have to do with r.getError();
        }
    }
}

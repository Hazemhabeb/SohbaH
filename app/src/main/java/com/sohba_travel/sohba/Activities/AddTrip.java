package com.sohba_travel.sohba.Activities;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.github.javiersantos.bottomdialogs.BottomDialog;
import com.sohba_travel.sohba.Adapters.TimelineAdapter;
import com.sohba_travel.sohba.Models.Timeline;
import com.sohba_travel.sohba.Models.Trip;
import com.sohba_travel.sohba.R;
import com.sohba_travel.sohba.UI.SohbaEditText;
import com.vansuita.pickimage.PickImageDialog;
import com.vansuita.pickimage.PickSetup;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.listeners.IPickResult;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddTrip extends AppCompatActivity implements IPickResult {

    @BindView(R.id.tripImage)
    ImageView tripImage;
    @BindView(R.id.etTripName)
    SohbaEditText etTripName;
    @BindView(R.id.etCity)
    SohbaEditText etCity;
    @BindView(R.id.etPrice)
    SohbaEditText etPrice;
    @BindView(R.id.etCategory)
    SohbaEditText etCategory;
    @BindView(R.id.etTripDescrition)
    SohbaEditText etTripDescrition;
    @BindView(R.id.etDuration)
    SohbaEditText etDuration;
    @BindView(R.id.ivAddTimeline)
    ImageButton ivAddTimeline;
    String imageUrl;
    @BindView(R.id.rvTimeline)
    RecyclerView rvTimeline;
    private StorageReference mStorageRef;
    HashMap<String, Timeline> timelineHashMap = new HashMap<String, Timeline>();
    View customView;
    TimelineAdapter timelineAdapter;
    List<Timeline> timelines = new ArrayList<>();
    BottomDialog bottomDialog;
    String tripId = "trip:" + random();
    private FirebaseAuth mAuth;
    FirebaseUser user;
    ProgressDialog progressDialog;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trip);
        ButterKnife.bind(this);
        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        user = mAuth.getCurrentUser();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        LinearLayoutManager horizontalLayoutManagaer
                = new LinearLayoutManager(AddTrip.this, LinearLayoutManager.HORIZONTAL, false);
        rvTimeline.setLayoutManager(horizontalLayoutManagaer);
        timelineAdapter = new TimelineAdapter(timelines);
        rvTimeline.setAdapter(timelineAdapter);


    }

    @OnClick({R.id.tripImage, R.id.ivAddTimeline, R.id.bAddTrip})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tripImage:
                PickImageDialog.on(AddTrip.this, new PickSetup());
                break;
            case R.id.ivAddTimeline:
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                customView = inflater.inflate(R.layout.add_timeline_item, null);

                final EditText etFrom = (EditText) customView.findViewById(R.id.etFrom);
                final EditText etTo = (EditText) customView.findViewById(R.id.etTo);
                final EditText etDescritopn = (EditText) customView.findViewById(R.id.etDescrition);
                Button bAdd = (Button) customView.findViewById(R.id.bAddTimeline);


                bottomDialog = new BottomDialog.Builder(AddTrip.this)
                        .setTitle("Add Timeline Item")
                        .setContent("You need to fill the following fields")
                        .setCustomView(customView)
                        .show();
                bAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        timelines.add(new Timeline(etFrom.getText().toString(), etTo.getText().toString(), etDescritopn.getText().toString()));
                        timelineAdapter.notifyDataSetChanged();
                        timelineHashMap.put(etFrom.getText().toString() + "-" + etTo.getText().toString(), new Timeline(etFrom.getText().toString(), etTo.getText().toString(), etDescritopn.getText().toString()));
                        bottomDialog.dismiss();
                    }
                });

                break;
            case R.id.bAddTrip:
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("trips").child(tripId);
                progressDialog.setTitle("Adding Trip");
                progressDialog.show();
                Trip trip = new Trip(
                        tripId, user.getUid(),
                        etTripDescrition.getText().toString(),
                        etTripName.getText().toString(),
                        etCity.getText().toString(),
                        etCategory.getText().toString(),
                        etPrice.getText().toString(),
                        "0", imageUrl, timelineHashMap, null
                );
                myRef.setValue(trip);
                progressDialog.dismiss();
                finish();
                break;
        }
    }

    @Override
    public void onPickResult(PickResult r) {
        if (r.getError() == null) {

            progressDialog.setTitle("Uploading Image");
            progressDialog.show();
            //If you want the Bitmap.
            tripImage.setImageBitmap(r.getBitmap());
            StorageReference tripsRef = mStorageRef.child("images/trips/" + random() + ".jpg");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            r.getBitmap().compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] data = baos.toByteArray();

            UploadTask uploadTask = tripsRef.putBytes(data);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle unsuccessful uploads
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                    imageUrl = taskSnapshot.getDownloadUrl().toString();
                    progressDialog.dismiss();
                }
            });
        } else {
            //Handle possible errors
            //TODO: do what you have to do with r.getError();
        }


    }


    protected String random() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 18) {
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }


}

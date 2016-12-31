package com.sohba_travel.sohba.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.sohba_travel.sohba.Adapters.TripAdapter;
import com.sohba_travel.sohba.Adapters.profileDetailAdapter;
import com.sohba_travel.sohba.Models.NewUserHost;
import com.sohba_travel.sohba.Models.Timeline;
import com.sohba_travel.sohba.Models.Trip;
import com.sohba_travel.sohba.Models.detail_profile;
import com.sohba_travel.sohba.R;
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

public class ProfileActivity extends AppCompatActivity implements IPickResult {


    @BindView(R.id.image)
    ImageView profileImage;
    @BindView(R.id.tvPname)
    TextView tvPname;
    @BindView(R.id.tvPabout)
    TextView tvPabout;

    String imageUrl;


    // firebase
    private DatabaseReference mFirebaseDatabaseReference;
    private FirebaseUser mFirebaseUser;



    // user trip
    private RecyclerView.Adapter mAdapter;
    private List<Trip> mContentItems = new ArrayList<>();

    HashMap<String, Timeline> timelineHashMap = new HashMap<>();

    // firebase to show only vertifed trips
    private DatabaseReference mFirebaseDatabaseReference_;
    private static String userType;

    private static String type, vertified;


    // detail of profile
    private List<detail_profile> mContentItems_detail = new ArrayList<>();
    // user trip
    private RecyclerView.Adapter mAdapter_detail;

    ProgressDialog progressDialog;
    private StorageReference mStorageRef;

    private static String acess;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);

        mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        // to now if he owen this profile or not
        acess=getIntent().getStringExtra("profile");

        View edit_photo_profile_view=findViewById(R.id.edit_photo_profile_view);
        View edit_data=findViewById(R.id.edit_data_profile_view);

        if (acess!=null){
            if (!mFirebaseUser.getUid().equals(acess)){
                edit_data.setVisibility(View.GONE);
                edit_photo_profile_view.setVisibility(View.GONE);
            }
        }


        mStorageRef = FirebaseStorage.getInstance().getReference();
        // to add the detail of the profile
        final RecyclerView recycleDetail = (RecyclerView) findViewById(R.id.rvDetailProfile);
        recycleDetail.setNestedScrollingEnabled(false);
        RecyclerView.LayoutManager
                layoutManagerDetail = new LinearLayoutManager(ProfileActivity.this);
        recycleDetail.setLayoutManager(layoutManagerDetail);
        recycleDetail.setItemAnimator(new DefaultItemAnimator());
        mAdapter_detail = new profileDetailAdapter(mContentItems_detail, ProfileActivity.this);
        recycleDetail.setAdapter(mAdapter_detail);


        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rvCards);
        final TextView trips_text = (TextView) findViewById(R.id.trips_text);
        // New child entries

        if (acess!=null){
            mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(acess);
        }else{
            mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(mFirebaseUser.getUid());
        }


        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                NewUserHost post = dataSnapshot.getValue(NewUserHost.class);
                tvPname.setText(post.getfName() + " " + post.getlName());

                if (post.getType().equals("0")) {
                    type = "Guest";
                    recyclerView.setVisibility(View.GONE);
                    trips_text.setVisibility(View.GONE);
                } else {
                    type = "Host";
                }
                if (post.getVerified().equals("1")) {
                    vertified = "Vitrified";
                } else {
                    vertified = "Not Vitrified";
                }
                tvPabout.setText(type + " | " + vertified);


                if (!post.getInterests().equals("empty")) {
                    String[] intrest = post.getInterests().split(",");
                    for (int i = 0; i < intrest.length; i++) {
                        mContentItems_detail.add(new detail_profile(R.drawable.interests, intrest[i]));
                    }
                }
                if (!post.getJob().equals("empty")) {
                    mContentItems_detail.add(new detail_profile(R.drawable.job, post.getJob()));
                }
                mContentItems_detail.add(new detail_profile(R.drawable.mail, post.getEmail()));
                if (!post.getNationality().equals("empty")) {
                    mContentItems_detail.add(new detail_profile(R.drawable.nationality, post.getNationality()));
                }
                if (!post.getLanguage().equals("empty")) {
                    mContentItems_detail.add(new detail_profile(R.drawable.language, post.getLanguage()));
                }

                mAdapter_detail.notifyDataSetChanged();


                if (!ProfileActivity.this.isDestroyed())
                    Glide.with(ProfileActivity.this).load(post.getProfileImage()).
                            diskCacheStrategy(DiskCacheStrategy.ALL).into(profileImage);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        mFirebaseDatabaseReference.addValueEventListener(postListener);


        recyclerView.setNestedScrollingEnabled(false);
        RecyclerView.LayoutManager
                layoutManager = new LinearLayoutManager(ProfileActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new TripAdapter(mContentItems, ProfileActivity.this);
        recyclerView.setAdapter(mAdapter);

        prepareData();



        //.... to edit photo

        edit_photo_profile_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PickImageDialog.on(ProfileActivity.this, new PickSetup());
            }
        });

        edit_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(ProfileActivity.this,RegisterAddLater.class);
                i.putExtra("profile",1);
                startActivity(i);
            }
        });


    }

    private void prepareData() {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("trips");

        Query q;
        if (acess!=null){
            q= myRef.orderByChild("userId").equalTo(acess);
        }else {
            q = myRef.orderByChild("userId").equalTo(mFirebaseUser.getUid());
        }

        mContentItems.clear();

        q.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                progressDialog.hide();
                Trip trip = new Trip();
                trip.tripDescription = ((String) dataSnapshot.child("tripDescription").getValue());
                trip.tripId = ((String) dataSnapshot.child("tripId").getValue());
                trip.tripImage = ((String) dataSnapshot.child("tripImage").getValue());
                trip.tripName = ((String) dataSnapshot.child("tripName").getValue());
                trip.tripPlace = ((String) dataSnapshot.child("tripPlace").getValue());
                trip.tripPrice = ((String) dataSnapshot.child("tripPrice").getValue());
                trip.tripRate = ((String) dataSnapshot.child("tripRate").getValue());
                trip.tripType = ((String) dataSnapshot.child("tripType").getValue());
                trip.userId = ((String) dataSnapshot.child("userId").getValue());

                for (DataSnapshot timelinesnap : dataSnapshot.child("timelineHashMap").getChildren()) {
                    Timeline timeline = timelinesnap.getValue(Timeline.class);
                    trip.timelineHashMap.put(timelinesnap.getKey(), timeline);

                }
//                trip.timelineHashMap = timelineHashMap;
                mContentItems.add(trip);

                mAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onPickResult(PickResult r) {
        if (r.getError() == null) {

            progressDialog.setTitle("Uploading Image");
            progressDialog.show();
            //If you want the Bitmap.
            profileImage.setImageBitmap(r.getBitmap());
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
                    DatabaseReference dr = FirebaseDatabase.getInstance().getReference().
                            child("users").child(mFirebaseUser.getUid()).child("profileImage");
                    dr.setValue(imageUrl);
                    Toast.makeText(ProfileActivity.this,"Profile Photo Updated",Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }
            });
        } else {
            Toast.makeText(ProfileActivity.this,"error occur try again",Toast.LENGTH_LONG).show();
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

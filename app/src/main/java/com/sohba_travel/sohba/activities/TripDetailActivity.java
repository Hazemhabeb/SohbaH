package com.sohba_travel.sohba.Activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.sohba_travel.sohba.Adapters.TimelineAdapter;
import com.sohba_travel.sohba.Models.Booking;
import com.sohba_travel.sohba.Models.NewUserHost;
import com.sohba_travel.sohba.Models.Timeline;
import com.sohba_travel.sohba.Models.Trip;
import com.sohba_travel.sohba.Notification_.DownstreamMessage;
import com.sohba_travel.sohba.R;
import com.sohba_travel.sohba.UI.SohbaTextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class TripDetailActivity extends AppCompatActivity {
    Trip trip;
    @BindView(R.id.ivTripImage)
    ImageView ivTripImage;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout toolbarLayout;
    @BindView(R.id.tvTPlace)
    SohbaTextView tvTPlace;
    @BindView(R.id.DRatingBar)
    RatingBar DRatingBar;
    @BindView(R.id.ivHostImage)
    CircleImageView ivHostImage;
    @BindView(R.id.tvHostName)
    SohbaTextView tvHostName;
    private FirebaseAuth mAuth;
    FirebaseUser user;
    @BindView(R.id.tvDTripDescription)
    SohbaTextView tvDTripDescription;
    @BindView(R.id.tvCategory)
    SohbaTextView tvCategory;
    @BindView(R.id.rvDRecyclerView)
    RecyclerView rvDRecyclerView;
    @BindView(R.id.bBooking)
    Button bBooking;
    List<Timeline> timelines = new ArrayList<>();
    TimelineAdapter timelineAdapter;
    String BookingId = "Booking: " + random();
    String NotifiactionId = "Notification: " + random();


    // to know if i book this trip or not
    // firebase
    private DatabaseReference mFirebaseDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_detail);
        ButterKnife.bind(this);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        trip = (Trip) getIntent().getExtras().getSerializable("trip");
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(trip.tripName);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // to know if i book this trip or not
        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference("trips").child(trip.tripId)
                .child("Booking");

        //this is make user book trip once only one
        Query q = mFirebaseDatabaseReference.orderByChild("userBooking").equalTo(user.getUid());

        q.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                bBooking.setVisibility(View.GONE);
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


        LinearLayoutManager horizontalLayoutManagaer
                = new LinearLayoutManager(TripDetailActivity.this, LinearLayoutManager.HORIZONTAL, false);
        rvDRecyclerView.setLayoutManager(horizontalLayoutManagaer);
        for (Map.Entry<String, Timeline> enry : trip.timelineHashMap.entrySet()) {
            timelines.add(enry.getValue());
        }
        timelineAdapter = new TimelineAdapter(timelines);
        rvDRecyclerView.setAdapter(timelineAdapter);
        Glide.with(this).load(trip.tripImage).diskCacheStrategy(DiskCacheStrategy.ALL).into(ivTripImage);
        tvTPlace.setText(trip.tripPlace);
        tvCategory.setText(trip.tripType);
        tvDTripDescription.setText(trip.tripDescription);
        DRatingBar.setRating(Float.parseFloat(trip.tripRate));
        bBooking.setText("book now " + trip.tripPrice + " EGP");

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users").child(trip.userId);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tvHostName.setText((String) dataSnapshot.child("fName").getValue() + " " + (String) dataSnapshot.child("lName").getValue());
                Glide.with(TripDetailActivity.this).load(dataSnapshot.child("profileImage").getValue()).diskCacheStrategy(DiskCacheStrategy.ALL).into(ivHostImage);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    private static String token;

    @OnClick(R.id.bBooking)
    public void onClick() {
        AlertDialog.Builder adb = new AlertDialog.Builder(this);

        adb.setTitle("Book A Trip");


        adb.setIcon(R.mipmap.ic_launcher);
        adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                FirebaseDatabase database = FirebaseDatabase.getInstance();

                // notification to user how add this trip

                DatabaseReference df = FirebaseDatabase.getInstance().getReference().child("users").child(trip.userId);
                ValueEventListener postListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        NewUserHost post = dataSnapshot.getValue(NewUserHost.class);
                        token = post.getToken();
                        DownstreamMessage down = new DownstreamMessage();
                        String [] send={token,user.getUid(),trip.tripId};
                        down.execute(send);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                };
                df.addValueEventListener(postListener);


                DatabaseReference myRef = database.getReference("trips").child(trip.tripId).child("Booking").child(BookingId);
                myRef.setValue(new Booking(user.getUid(), BookingId, trip.userId, System.currentTimeMillis() + "", false, false, trip.tripId));
//                DatabaseReference myRNotifiaction = database.getReference("notifications").child(trip.userId);
//                myRNotifiaction.setValue(new Notification(BookingId, NotifiactionId, trip.userId, user.getUid()));
//                Log.d("hazem", token);

            }
        });


        adb.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        adb.show();

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

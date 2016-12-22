package com.sohba_travel.sohba.Activities;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.sohba_travel.sohba.Adapters.TimelineAdapter;
import com.sohba_travel.sohba.Models.Booking;
import com.sohba_travel.sohba.Models.Notification;
import com.sohba_travel.sohba.Models.Timeline;
import com.sohba_travel.sohba.Models.Trip;
import com.sohba_travel.sohba.R;
import com.sohba_travel.sohba.UI.SohbaTextView;

import de.hdodenhof.circleimageview.CircleImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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

    @OnClick(R.id.bBooking)
    public void onClick() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("trips").child(trip.tripId).child("Booking").child(BookingId);
        myRef.setValue(new Booking(user.getUid(), BookingId, trip.userId, System.currentTimeMillis() + "", false, false, trip.tripId));
        DatabaseReference myRNotifiaction = database.getReference("hostNotifications").child(trip.userId);
        myRNotifiaction.setValue(new Notification(BookingId, NotifiactionId, trip.userId, user.getUid()));


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

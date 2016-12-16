package com.sohba_travel.sohba;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sohba_travel.sohba.Activities.AddTrip;
import com.sohba_travel.sohba.Adapters.TripAdapter;
import com.sohba_travel.sohba.Models.Timeline;
import com.sohba_travel.sohba.Models.Trip;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by M on 11/21/2016.
 */

public class HomeFragment extends Fragment {
    private RecyclerView.Adapter mAdapter;
    private List<Trip> mContentItems = new ArrayList<>();
    HashMap<String, Timeline> timelineHashMap = new HashMap<>();

    public HomeFragment() {
        // Required empty public constructor
    }


    public static HomeFragment newInstance() {
        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rvCards);
        RecyclerView.LayoutManager
                layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new TripAdapter(mContentItems, getActivity());
        recyclerView.setAdapter(mAdapter);
        prepareData();


    }

    private void prepareData() {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("trips");
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading");
        progressDialog.show();

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                progressDialog.hide();
                Trip trip = new Trip();
                trip.tripDescription = ((String) dataSnapshot.child("description").getValue());
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
                    timelineHashMap.put(timelinesnap.getKey(), timeline);

                }
                trip.timelineHashMap = timelineHashMap;
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


    @OnClick(R.id.fabAddTrip)
    public void onClick() {
        startActivity(new Intent(getActivity(), AddTrip.class));
    }
}

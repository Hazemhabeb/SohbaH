package com.sohba_travel.sohba;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.sohba_travel.sohba.Activities.AddTrip;
import com.sohba_travel.sohba.Activities.SearchActivity;
import com.sohba_travel.sohba.Adapters.TripAdapter;
import com.sohba_travel.sohba.Models.NewUserHost;
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

    // firebase to show only vertifed trips
    private DatabaseReference mFirebaseDatabaseReference;
    private FirebaseUser mFirebaseUser;
    private static String userType;

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


        final FloatingActionButton f = (FloatingActionButton) view.findViewById(R.id.fabAddTrip);
        f.setVisibility(View.INVISIBLE);
        // this to diffrenate between host and gest
        mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference().child("users")
                .child(mFirebaseUser.getUid().toString());

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                NewUserHost post = dataSnapshot.getValue(NewUserHost.class);
                userType = post.getType();
                f.setVisibility(View.VISIBLE);
                if (isAdded()) {
                    getResources().getString(R.string.app_name);

                    if (post.getType().equals("0"))
                        f.setImageDrawable(getResources().getDrawable(R.drawable.ic_search, null));
                    else
                        f.setImageDrawable(getResources().getDrawable(R.drawable.ic_add, null));

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        mFirebaseDatabaseReference.addValueEventListener(postListener);


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


        //this is to get the vertified trips only
        Query q = myRef.orderByChild("userVertifed").equalTo("1");

        mContentItems.clear();

        q.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                progressDialog.hide();
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


    @OnClick(R.id.fabAddTrip)
    public void onClick() {
        if (userType.equals("0")) {
            startActivity(new Intent(getActivity(), SearchActivity.class));
        } else {
            startActivity(new Intent(getActivity(), AddTrip.class));
        }
    }
}

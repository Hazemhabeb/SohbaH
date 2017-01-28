package com.sohba_travel.sohba.Activities;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.sohba_travel.sohba.Adapters.TripAdapter;
import com.sohba_travel.sohba.Models.Timeline;
import com.sohba_travel.sohba.Models.Trip;
import com.sohba_travel.sohba.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private MaterialSearchView searchView;

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

    // user trip
    private RecyclerView.Adapter mAdapter_detail;


    String [] suggestions={"cairo","luxor","fayoum","aswan","alexandria","giza","sharm elshikh","sinai"};

    private RecyclerView recyclerView;
    private TextView text_empty_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Search");

        searchView = (MaterialSearchView) findViewById(R.id.search_view);
        searchView.setVoiceSearch(false);
        searchView.setCursorDrawable(R.drawable.custom_cursor);
        searchView.setEllipsize(true);
        searchView.setSuggestions(suggestions);


         recyclerView= (RecyclerView) findViewById(R.id.rvSearch);
        text_empty_search= (TextView) findViewById(R.id.search_text_);

        recyclerView.setNestedScrollingEnabled(false);
        RecyclerView.LayoutManager
                layoutManager = new LinearLayoutManager(SearchActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new TripAdapter(mContentItems, SearchActivity.this);
        recyclerView.setAdapter(mAdapter);


        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
//                Toast.makeText(SearchActivity.this,query,Toast.LENGTH_LONG).show();
                prepareData(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Do some magic
                return false;
            }
        });
        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                //Do some magic
            }

            @Override
            public void onSearchViewClosed() {
                //Do some magic
            }
        });


    }


    private void prepareData(String query_) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("trips");
//        final ProgressDialog progressDialog = new ProgressDialog(ProfileActivity.this);
//        progressDialog.setMessage("Loading");
//        progressDialog.show();


        //this is to get the vertified trips only
        Query q = myRef.orderByChild("tripPlace").startAt(query_);

        mContentItems.clear();

        q.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                progressDialog.hide();
//                recyclerView.setVisibility(View.VISIBLE);
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
                if (mContentItems.isEmpty()) {
                    recyclerView.setVisibility(View.GONE);
                    text_empty_search.setVisibility(View.VISIBLE);
                    text_empty_search.setText("Sorry Not Found Try Again");
                }
                else {
                    recyclerView.setVisibility(View.VISIBLE);
                    text_empty_search.setVisibility(View.GONE);
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

                if (mContentItems.isEmpty()) {
                    recyclerView.setVisibility(View.GONE);
                    text_empty_search.setVisibility(View.VISIBLE);
                    text_empty_search.setText("Sorry Not Found Try Again");
                }
                else {
                    recyclerView.setVisibility(View.VISIBLE);
                    text_empty_search.setVisibility(View.GONE);

                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);

        return true;
    }

    @Override
    public void onBackPressed() {
        if (searchView.isSearchOpen()) {
            searchView.closeSearch();
        } else {

            super.onBackPressed();

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MaterialSearchView.REQUEST_VOICE && resultCode == RESULT_OK) {
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (matches != null && matches.size() > 0) {
                String searchWrd = matches.get(0);
                if (!TextUtils.isEmpty(searchWrd)) {
                    searchView.setQuery(searchWrd, false);
                }
            }

            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}

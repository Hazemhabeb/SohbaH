package com.sohba_travel.sohba;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sohba_travel.sohba.Adapters.TripAdapter;
import com.sohba_travel.sohba.Models.Trip;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by M on 11/21/2016.
 */

public class HomeFragment extends Fragment {
    private RecyclerView.Adapter mAdapter;
    private List<Trip> mContentItems = new ArrayList<>();
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
        return inflater.inflate(R.layout.home_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rvCards);
        RecyclerView.LayoutManager
                layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        prepareFakeData();
        mAdapter = new TripAdapter(mContentItems, getActivity());
        recyclerView.setAdapter(mAdapter);


    }

    private void prepareFakeData() {
        for (int i = 0; i < 20; i++) {
            Trip trip = new Trip();
            mContentItems.add(trip);
        }
    }


}

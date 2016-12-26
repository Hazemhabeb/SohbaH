package com.sohba_travel.sohba.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sohba_travel.sohba.Activities.TripDetailActivity;
import com.sohba_travel.sohba.Models.Trip;
import com.sohba_travel.sohba.R;
import com.sohba_travel.sohba.UI.SohbaTextView;
import com.sohba_travel.sohba.Views.CircleImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by M on 12/8/2016.
 */

public class TripAdapter extends RecyclerView.Adapter<TripAdapter.ViewHolder> {

    List<Trip> contents;
    Context mContext;

    public TripAdapter(List<Trip> contents, Context mContext) {
        this.contents = contents;
        this.mContext = mContext;
    }


    @Override
    public int getItemCount() {
        return contents.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;


        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.trip_item, parent, false);
        return new ViewHolder(view);


    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Trip trip = contents.get(position);
        Glide.with(mContext).load(trip.tripImage).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.programImage);
        holder.salaryTv.setText(trip.tripPrice+" EGP");
        holder.cityTv.setText(trip.tripPlace);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users").child(trip.userId).child("profileImage");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Glide.with(mContext).load(dataSnapshot.getValue()).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.hostImage);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        holder.tripName.setText(trip.tripName);
        holder.ratingBar.setRating(Float.parseFloat(trip.tripRate));

        holder.tripCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.startActivity(new Intent(mContext, TripDetailActivity.class).putExtra("trip", trip));
            }
        });

    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.program_image)
        ImageView programImage;
        @BindView(R.id.salary_tv)
        SohbaTextView salaryTv;
        @BindView(R.id.tripName)
        SohbaTextView tripName;
        @BindView(R.id.city_tv)
        SohbaTextView cityTv;
        @BindView(R.id.ratingBar)
        RatingBar ratingBar;
        @BindView(R.id.host_image)
        CircleImageView hostImage;
        @BindView(R.id.tripCard)
        CardView tripCard;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}
package com.sohba_travel.sohba.Adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;

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
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.program_image)
        ImageView programImage;
        @BindView(R.id.salary_tv)
        SohbaTextView salaryTv;
        @BindView(R.id.placename)
        SohbaTextView placename;
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
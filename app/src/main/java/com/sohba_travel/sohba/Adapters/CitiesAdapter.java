package com.sohba_travel.sohba.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.sohba_travel.sohba.Models.City;
import com.sohba_travel.sohba.R;
import com.sohba_travel.sohba.UI.SohbaTextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by M on 12/8/2016.
 */

public class CitiesAdapter extends RecyclerView.Adapter<CitiesAdapter.ViewHolder> {

    List<City> contents;
    Context mContext;

    public CitiesAdapter(List<City> contents, Context mContext) {
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
                .inflate(R.layout.city_item, parent, false);
        return new ViewHolder(view);


    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        City city =contents.get(position);
        holder.ivCityImage.setImageResource(city.getImage());
        holder.tvCityName.setText(city.getName());

    }



    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ivCityImage)
        ImageView ivCityImage;
        @BindView(R.id.tvCityName)
        SohbaTextView tvCityName;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
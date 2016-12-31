package com.sohba_travel.sohba.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sohba_travel.sohba.Models.detail_profile;
import com.sohba_travel.sohba.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hazem on 12/8/2016.
 */

public class profileDetailAdapter extends RecyclerView.Adapter<profileDetailAdapter.ViewHolder> {

    List<detail_profile> contents;
    Context mContext;

    public profileDetailAdapter(List<detail_profile> contents, Context mContext) {
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
                .inflate(R.layout.profile_detail_item, parent, false);
        return new ViewHolder(view);


    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final detail_profile detail = contents.get(position);
        holder.text.setText(detail.getWord());
        holder.image.setImageDrawable(mContext.getResources().getDrawable(detail.getIcon()));
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.image_detail_profile)
        ImageView image;

        @BindView(R.id.text_detail_profile)
        TextView text;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}
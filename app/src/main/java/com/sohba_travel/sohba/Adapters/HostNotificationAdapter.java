package com.sohba_travel.sohba.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.sohba_travel.sohba.Models.Notification;
import com.sohba_travel.sohba.R;

import de.hdodenhof.circleimageview.CircleImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by M on 12/8/2016.
 */

public class HostNotificationAdapter extends RecyclerView.Adapter<HostNotificationAdapter.ViewHolder> {

    List<Notification> contents;
    Context mContext;

    public HostNotificationAdapter(List<Notification> contents, Context mContext) {
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
                .inflate(R.layout.host_notification_item, parent, false);
        return new ViewHolder(view);


    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Notification notification = contents.get(position);


    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ivBookedUserPic)
        CircleImageView ivBookedUserPic;
        @BindView(R.id.tvBookedUsername)
        TextView tvBookedUsername;
        @BindView(R.id.tvQITitle)
        TextView tvQITitle;
        @BindView(R.id.bAccept)
        Button bAccept;
        @BindView(R.id.bDecline)
        Button bDecline;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
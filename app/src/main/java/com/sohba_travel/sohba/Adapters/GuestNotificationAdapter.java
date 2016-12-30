package com.sohba_travel.sohba.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sohba_travel.sohba.Models.Notification;
import com.sohba_travel.sohba.R;

import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by M on 12/8/2016.
 */

public class GuestNotificationAdapter extends RecyclerView.Adapter<GuestNotificationAdapter.ViewHolder> {

    List<Notification> contents;
    Context mContext;
    String Payment = "Payment:" + random();

    public GuestNotificationAdapter(List<Notification> contents, Context mContext) {
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
                .inflate(R.layout.guest_notification_item, parent, false);
        return new ViewHolder(view);


    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Notification notification = contents.get(position);
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userRef = database.getReference("users").child(notification.tripUserId);
        DatabaseReference tripRef = database.getReference("trips").child(notification.tripId);
        tripRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String tripName = (String) dataSnapshot.child("tripName").getValue();
                Boolean Accept = (Boolean) dataSnapshot.child("Booking").child(notification.BookingId).child("Accept").getValue();
                if (Accept) {
                    holder.tvQITitle.setText("Has Accepted Your Booking for trip " + tripName + "." +
                            "Please transfer " + dataSnapshot.child("tripPrice").getValue() + " LE to " +
                            "01092614593" + " Within 24 Hours");
                    DatabaseReference bookingRef = database.getReference("Payment").child(Payment);
                    bookingRef.setValue(notification);
                } else {
                    holder.tvQITitle.setText("Has Refused Your Booking for trip " + tripName + ".");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                holder.tvBookedUsername.setText(dataSnapshot.child("fName").getValue() + " " + dataSnapshot.child("lName").getValue());
                if (mContext != null)
                    Glide.with(mContext).load(dataSnapshot.child("profileImage").getValue()).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.ivBookedUserPic);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ivBookedUserPic)
        CircleImageView ivBookedUserPic;
        @BindView(R.id.tvBookedUsername)
        TextView tvBookedUsername;
        @BindView(R.id.tvQITitle)
        TextView tvQITitle;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
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
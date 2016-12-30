package com.sohba_travel.sohba.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sohba_travel.sohba.Models.NewUserHost;
import com.sohba_travel.sohba.Models.Notification;
import com.sohba_travel.sohba.Notification_.DownstreamMessage_gest;
import com.sohba_travel.sohba.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by M on 12/8/2016.
 */

public class HostNotificationAdapter extends RecyclerView.Adapter<HostNotificationAdapter.ViewHolder> {

    List<Notification> contents;
    Context mContext;

    private static String token;

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
        final Notification notification = contents.get(position);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userRef = database.getReference("users").child(notification.BookedUserId);
        DatabaseReference tripRef = database.getReference("trips").child(notification.tripId).child("tripName");
        tripRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                holder.tvQITitle.setText("Wants to book for your trip " + dataSnapshot.getValue());
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
                    Glide.with(mContext).load(dataSnapshot.child("profileImage").getValue()).
                            diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.ivBookedUserPic);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        holder.bAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("trips").child(notification.tripId).child("Booking").child(notification.BookingId).child("Accept");
                myRef.setValue(true);
                DatabaseReference GnotificationRef = database.getReference("guestNotifications").child(notification.BookedUserId).child(notification.NotificationId);
                GnotificationRef.setValue(notification);
                // notification to user how add this trip

                DatabaseReference df = FirebaseDatabase.getInstance().getReference().child("users").child(notification.BookedUserId);
                ValueEventListener postListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        NewUserHost post = dataSnapshot.getValue(NewUserHost.class);
                        token = post.getToken();
                        DownstreamMessage_gest down = new DownstreamMessage_gest();
                        String[] send = {token, notification.tripUserId, notification.tripId, "true"};
                        down.execute(send);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                };
                df.addValueEventListener(postListener);


            }
        });
        holder.bDecline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("trips").child(notification.tripId).child("Booking").child(notification.BookingId).child("Accept");
                myRef.setValue(false);
                DatabaseReference GnotificationRef = database.getReference("guestNotifications").child(notification.BookedUserId).child(notification.NotificationId);
                GnotificationRef.setValue(notification);
                DatabaseReference df = FirebaseDatabase.getInstance().getReference().child("users").child(notification.BookedUserId);
                ValueEventListener postListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        NewUserHost post = dataSnapshot.getValue(NewUserHost.class);
                        token = post.getToken();
                        DownstreamMessage_gest down = new DownstreamMessage_gest();
                        String[] send = {token, notification.tripUserId, notification.tripId, "false"};
                        down.execute(send);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                };
                df.addValueEventListener(postListener);
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
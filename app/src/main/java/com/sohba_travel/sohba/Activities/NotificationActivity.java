package com.sohba_travel.sohba.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sohba_travel.sohba.Adapters.GuestNotificationAdapter;
import com.sohba_travel.sohba.Adapters.HostNotificationAdapter;
import com.sohba_travel.sohba.Models.Notification;
import com.sohba_travel.sohba.R;

import java.util.ArrayList;
import java.util.List;

public class NotificationActivity extends AppCompatActivity {
    List<Notification> notificationList = new ArrayList<>();
    private FirebaseAuth mAuth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rvNotification);
        final TextView text_empty_notification= (TextView) findViewById(R.id.text_empty_notification);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users").child(user.getUid()).child("type");
        final DatabaseReference HostRef = database.getReference("hostNotifications").child(user.getUid());
        final DatabaseReference GuestRef = database.getReference("guestNotifications").child(user.getUid());
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue().equals("1")) {
                    final HostNotificationAdapter adapter = new HostNotificationAdapter(notificationList, NotificationActivity.this);
                    recyclerView.setAdapter(adapter);
                    HostRef.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            Notification notification = dataSnapshot.getValue(Notification.class);
                            notificationList.add(notification);
                            adapter.notifyDataSetChanged();
                            if (notificationList.isEmpty()) {
                                recyclerView.setVisibility(View.GONE);
                                text_empty_notification.setVisibility(View.VISIBLE);
                            }
                            else {
                                recyclerView.setVisibility(View.VISIBLE);
                                text_empty_notification.setVisibility(View.GONE);
                            }
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

                            if (notificationList.isEmpty()) {
                                recyclerView.setVisibility(View.GONE);
                                text_empty_notification.setVisibility(View.VISIBLE);
                            }
                            else {
                                recyclerView.setVisibility(View.VISIBLE);
                                text_empty_notification.setVisibility(View.GONE);
                            }
                        }
                    });

                } else {
                    final GuestNotificationAdapter adapter = new GuestNotificationAdapter(notificationList, NotificationActivity.this);
                    recyclerView.setAdapter(adapter);
                    GuestRef.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            Notification notification =dataSnapshot.getValue(Notification.class);
                            notificationList.add(notification);
                            adapter.notifyDataSetChanged();
                            if (notificationList.isEmpty()) {
                                recyclerView.setVisibility(View.GONE);
                                text_empty_notification.setVisibility(View.VISIBLE);
                            }
                            else {
                                recyclerView.setVisibility(View.VISIBLE);
                                text_empty_notification.setVisibility(View.GONE);
                            }
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
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                if (notificationList.isEmpty()) {
                    recyclerView.setVisibility(View.GONE);
                    text_empty_notification.setVisibility(View.VISIBLE);
                }
                else {
                    recyclerView.setVisibility(View.VISIBLE);
                    text_empty_notification.setVisibility(View.GONE);
                }
            }
        });


    }
}

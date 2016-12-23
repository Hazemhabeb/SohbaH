package com.sohba_travel.sohba.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sohba_travel.sohba.Models.NewUserHost;
import com.sohba_travel.sohba.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileActivity extends AppCompatActivity {


    @BindView(R.id.image)
    ImageView profileImage;
    @BindView(R.id.tvPname)
    TextView tvPname;
    @BindView(R.id.tvPabout)
    TextView tvPabout;
    @BindView(R.id.tvMail)
    TextView tvMail;
    @BindView(R.id.tvInterest)
    TextView tvInterest;
    @BindView(R.id.tvJob)
    TextView tvJob;


    // firebase
    private DatabaseReference mFirebaseDatabaseReference;
    private FirebaseUser mFirebaseUser;

    private static String type,vertified;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);

        // New child entries
        mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(mFirebaseUser.getUid());
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                NewUserHost post = dataSnapshot.getValue(NewUserHost.class);
                tvPname.setText(post.getfName()+" "+post.getlName());

                if (post.getType().equals("0")){
                    type="Guest";
                }else{
                    type="Host";
                }
                if (post.getVerified().equals("0")){
                    vertified="Vertified";
                }else{
                    vertified="Not Vertified";
                }
                tvPabout.setText(type+" | "+vertified);
                tvMail.setText(post.getEmail());
                tvInterest.setText(post.getInterests());
                tvJob.setText(post.getJob());

                Glide.with(ProfileActivity.this).load(post.getProfileImage()).
                        diskCacheStrategy(DiskCacheStrategy.ALL).into(profileImage);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        mFirebaseDatabaseReference.addValueEventListener(postListener);
    }
}

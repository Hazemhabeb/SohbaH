package com.sohba_travel.sohba;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.app.Application;

/**
 * Created by M on 12/16/2016.
 */

public class mApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        DatabaseReference scoresRef = FirebaseDatabase.getInstance().getReference("trips");
        scoresRef.keepSynced(true);
    }
}

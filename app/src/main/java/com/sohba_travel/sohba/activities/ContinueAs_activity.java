package com.sohba_travel.sohba.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.sohba_travel.sohba.R;
import com.sohba_travel.sohba.Utility.font;


public class ContinueAs_activity extends AppCompatActivity {

    public static String ContinueAs_activityS="ContinueAs_activity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.continue_as);
        font font = new font();
        font.changeFonts(this, (LinearLayout) findViewById(R.id.parent_layout));
    }
    public void signupHost(View view){
        Intent i=new Intent(ContinueAs_activity.this,RegisterNew.class);
        i.putExtra(ContinueAs_activityS,1);
        startActivity(i);
    }
    public void signupGuest(View view){
        Intent i=new Intent(ContinueAs_activity.this,RegisterNew.class);
        i.putExtra(ContinueAs_activityS,0);
        startActivity(i);
    }
}

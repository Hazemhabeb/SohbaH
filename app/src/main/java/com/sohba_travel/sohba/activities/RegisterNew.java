package com.sohba_travel.sohba.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.sohba_travel.sohba.R;
import com.sohba_travel.sohba.Utility.font;


public class RegisterNew extends AppCompatActivity {

    public static String Frist="DetailOne_name1";
    public static String Last="DetailOne_name2";

    EditText fristname_tv;
    EditText lastname_tv;

    private static int status;
    private static String imageProfile;
    private static String imageSelfie;
    private static String imageid1;
    private static String imageid2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_new);

        status = getIntent().getIntExtra(ContinueAs_activity.ContinueAs_activityS, 0);

        fristname_tv= (EditText) findViewById(R.id.fristname_tv);
        lastname_tv= (EditText) findViewById(R.id.lastname_tv);


        font font = new font();

        font.changeFonts(this, (LinearLayout) findViewById(R.id.parent_layout));
        font.changeFonts(this, (LinearLayout) findViewById(R.id.parent_layout1));
        font.changeFonts(this, (LinearLayout) findViewById(R.id.parent_layout2));
        font.changeFonts(this, (LinearLayout) findViewById(R.id.parent_layout3));
    }

    public void goToNext(View view){

//        if (fristname_tv.getText().toString().isEmpty()||lastname_tv.getText().toString().isEmpty()){
//            Toast.makeText(RegisterNew.this, "Please fill all informations ! ", Toast.LENGTH_LONG).show();
//            return;
//        }
        Intent i=new Intent(RegisterNew.this,ActivityProfileImage.class);
        i.putExtra(ContinueAs_activity.ContinueAs_activityS,status);

        startActivity(i);

    }
}

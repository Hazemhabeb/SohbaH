package com.sohba_travel.sohba.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.sohba_travel.sohba.R;
import com.sohba_travel.sohba.Utility.font;
import com.sohba_travel.sohba.Views.ShowHidePasswordEditText;


public class Login extends AppCompatActivity {

    AutoCompleteTextView Login_email_ET;
    ShowHidePasswordEditText Login_Password_ET;
    static String email, password;
    Button btn_login;
    Button Btn_register;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_new);

        Login_email_ET = (AutoCompleteTextView) findViewById(R.id.Login_email_ET);
        Login_Password_ET = (ShowHidePasswordEditText) findViewById(R.id.Login_Password_ET);
        btn_login = (Button) findViewById(R.id.btn_login);
        Btn_register = (Button) findViewById(R.id.Btn_register);


        font font = new font();

        font.changeFonts(this, (LinearLayout) findViewById(R.id.login_parent));

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = Login_email_ET.getText().toString().trim();
                password = Login_Password_ET.getText().toString().trim();

                if (!email.isEmpty() && !password.isEmpty()) {

                } else {
                    Toast.makeText(getApplicationContext(), "Please enter your mail and password", Toast.LENGTH_LONG)
                            .show();
                }


            }
        });
        Btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this,ContinueAs_activity.class));

            }
        });


    }




}

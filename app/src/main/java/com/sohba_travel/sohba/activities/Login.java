package com.sohba_travel.sohba.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.sohba_travel.sohba.MainActivity;
import com.sohba_travel.sohba.R;
import com.sohba_travel.sohba.Utility.font;
import com.sohba_travel.sohba.Views.ShowHidePasswordEditText;


public class Login extends AppCompatActivity {

    AutoCompleteTextView Login_email_ET;
    ShowHidePasswordEditText Login_Password_ET;
    static String email, password;
    Button btn_login;
    Button Btn_register;


    //firebase
    private FirebaseAuth mAuth;

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

        //firebase intial
        mAuth = FirebaseAuth.getInstance();

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = Login_email_ET.getText().toString().trim();
                password = Login_Password_ET.getText().toString().trim();

                if (!email.isEmpty() && !password.isEmpty()) {
                   login();
                    // [END sign_in_with_email]

                } else {
                    Toast.makeText(getApplicationContext(), "Please enter your mail and password", Toast.LENGTH_LONG)
                            .show();
                }


            }
        });
        Btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, ContinueAs_activity.class));

            }
        });


    }

    public void login(){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("hazem", "signInWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "can't login", Toast.LENGTH_LONG)
                                    .show();
                        }else{
                            Toast.makeText(getApplicationContext(), "login sucess", Toast.LENGTH_LONG)
                                    .show();
                            Intent i=new Intent(Login.this, MainActivity.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(i);
                        }


                    }
                });
    }


}

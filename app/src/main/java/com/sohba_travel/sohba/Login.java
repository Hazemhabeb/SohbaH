package com.sohba_travel.sohba;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;

import com.sohba_travel.sohba.UI.SohbaEditText;
import com.sohba_travel.sohba.UI.SohbaTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Login extends AppCompatActivity {

    @BindView(R.id.etLEmail)
    SohbaEditText etLEmail;
    @BindView(R.id.etLPass)
    SohbaEditText etLPass;
    @BindView(R.id.bLLogin)
    AppCompatButton bLLogin;
    @BindView(R.id.tvLSignup)
    SohbaTextView tvLSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.bLLogin, R.id.tvLSignup})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bLLogin:
                break;
            case R.id.tvLSignup:
                break;
        }
    }
}

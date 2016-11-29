package com.sohba_travel.sohba.activities;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.sohba_travel.sohba.R;
import com.sohba_travel.sohba.Utility.RegisterData;
import com.sohba_travel.sohba.Utility.font;
import com.sohba_travel.sohba.Views.ShowHidePasswordEditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class RegisterNew extends AppCompatActivity {


    private EditText fristname_tv_;
    private EditText lastname_tv_;
    private EditText phone_;
    private AutoCompleteTextView email_;
    private ShowHidePasswordEditText password_;
    private EditText gender_;
    private EditText inputBirthDate_;

    private String fn;
    private String ln;
    private String ph;
    private String pass;
    private String mail;
    private String gen;
    private String birth;


    private static int status;
    private static String user_id;

    private DatePickerDialog fromDatePickerDialog;

    private String re_brith = "";

    private SimpleDateFormat dateFormatter;
    private DatePickerDialog toDatePickerDialog;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_new);

        status = getIntent().getIntExtra(ContinueAs_activity.ContinueAs_activityS, 0);

        fristname_tv_ = (EditText) findViewById(R.id.fristname_tv);
        lastname_tv_ = (EditText) findViewById(R.id.lastname_tv);
        phone_ = (EditText) findViewById(R.id.phone);
        email_ = (AutoCompleteTextView) findViewById(R.id.email);
        password_ = (ShowHidePasswordEditText) findViewById(R.id.password);

        gender_ = (EditText) findViewById(R.id.gender);
        gender_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showListAlertDialog();
            }
        });

        inputBirthDate_ = (EditText) findViewById(R.id.Birth);
        inputBirthDate_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fromDatePickerDialog.show();
            }
        });

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        setDateTimeField();

        // to set font t the entire activity
        font font = new font();

        font.changeFonts(this, (LinearLayout) findViewById(R.id.parent_layout));
        font.changeFonts(this, (LinearLayout) findViewById(R.id.parent_layout1));
        font.changeFonts(this, (LinearLayout) findViewById(R.id.parent_layout2));
        font.changeFonts(this, (LinearLayout) findViewById(R.id.parent_layout3));
    }

    private void setDateTimeField() {


        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                re_brith = dateFormatter.format(newDate.getTime());
                inputBirthDate_.setText(re_brith);
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        toDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }


    private void showListAlertDialog() {
        final String[] list = new String[]{"Male", "Female"};
        createAlertDialogBuilder()
                .setTitle("Choose your Gender")
                .setItems(list, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        gender_.setText(list[which]);
                    }
                })
                .show();
    }

    private AlertDialog.Builder createAlertDialogBuilder() {
        return new AlertDialog.Builder(this, R.style.AlertDialogCustom);
    }


    public void goToNext(View view) {


        if (!validate()) {
            return;
        }


        fn = fristname_tv_.getText().toString();
        ln = lastname_tv_.getText().toString();
        birth = inputBirthDate_.getText().toString();
        gen = gender_.getText().toString();
        ph = phone_.getText().toString();
        mail = email_.getText().toString();
        pass = password_.getText().toString();


        Intent i=new Intent(RegisterNew.this,ActivityProfileImage.class);
        i.putExtra(ContinueAs_activity.ContinueAs_activityS,status);
        i.putExtra(RegisterData.fn,fn);
        i.putExtra(RegisterData.ln,ln);
        i.putExtra(RegisterData.pass,pass);
        i.putExtra(RegisterData.birth,birth);
        i.putExtra(RegisterData.gen,gen);
        i.putExtra(RegisterData.ph,ph);
        i.putExtra(RegisterData.mail,mail);



        startActivity(i);

    }





    private Boolean validate() {
        if (fristname_tv_.getText().toString().isEmpty() || lastname_tv_.getText().toString().isEmpty()) {
            Toast.makeText(RegisterNew.this, "Please fill all informations ! ", Toast.LENGTH_LONG).show();
            return false;
        }
        if (phone_.getText().toString().isEmpty() || email_.getText().toString().isEmpty() || password_.getText().toString().isEmpty()) {
            Toast.makeText(RegisterNew.this, "Please fill all informations ! ", Toast.LENGTH_LONG).show();
            return false;
        }
        if (inputBirthDate_.getText().toString().isEmpty()
                || gender_.getText().toString().isEmpty()) {
            Toast.makeText(RegisterNew.this, "Please fill all informations ! ", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}

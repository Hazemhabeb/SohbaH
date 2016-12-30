package com.sohba_travel.sohba.Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.github.javiersantos.bottomdialogs.BottomDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sohba_travel.sohba.Adapters.mItemAdabter;
import com.sohba_travel.sohba.Models.mItem;
import com.sohba_travel.sohba.R;
import com.sohba_travel.sohba.Utility.RegisterData;
import com.sohba_travel.sohba.Utility.font;

import java.util.ArrayList;
import java.util.List;

public class RegisterAddLater extends AppCompatActivity {

    public static String nationals = "pasf";
    public static String languaes = "fasfi23";
    public static String jobs = "sadaoer9";

    EditText national;
    EditText language;
    EditText job;
    Button add_interest_btn, Btn_register;
    private static String user_id;


    //firebase
    private FirebaseAuth mAuth;
    private FirebaseUser mFirebaseUser;
    private DatabaseReference mFirebaseDatabaseReference;


    private GridView listView1;
    static mItemAdabter adapter;
    static ArrayList<mItem> users;
    BottomDialog bottomDialog;
    View customView;

    private static int edit = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_add_later);

        edit = getIntent().getIntExtra("profile", 0);
        Btn_register = (Button) findViewById(R.id.Btn_register);
        if (edit == 1)
            Btn_register.setVisibility(View.GONE);


        add_interest_btn = (Button) findViewById(R.id.add_interest_btn);
        national = (EditText) findViewById(R.id.national);
        language = (EditText) findViewById(R.id.language);
        job = (EditText) findViewById(R.id.job);
        language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMultiChoiceListAlertDialog(list);
            }
        });

        user_id = getIntent().getStringExtra(RegisterData.User_id);
        font font = new font();
        font.changeFonts(this, (LinearLayout) findViewById(R.id.parent_layout));
        font.changeFonts(this, (LinearLayout) findViewById(R.id.parent_layout1));


        mItem User[] = new mItem[]{
        };
        users = new ArrayList<>();

        for (int i = 0; i < User.length; i++) {
            users.add(User[i]);
        }
        adapter = new mItemAdabter(this,
                R.layout.interest_item, users);
        listView1 = (GridView) findViewById(R.id.listView1);

        listView1.setAdapter(adapter);

        add_interest_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                customView = inflater.inflate(R.layout.add_interest_item, null);

                final EditText etDescritopn = (EditText) customView.findViewById(R.id.etDescrition);
                Button bAdd = (Button) customView.findViewById(R.id.bAddTimeline);

                bottomDialog = new BottomDialog.Builder(RegisterAddLater.this)
                        .setTitle("Add New Interest")
                        .setContent("You need to fill the following field")
                        .setCustomView(customView)
                        .show();
                bAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (etDescritopn.getText().toString().isEmpty()) {
                            Toast.makeText(getApplicationContext(), "Type Your Interest", Toast.LENGTH_LONG).show();
                            return;
                        }
                        users.add(new mItem(etDescritopn.getText().toString()));
                        adapter.notifyDataSetChanged();
//                        timelines.add(new com.sohba_travel.sohba.Models.Timeline(etFrom.getText().toString(), etTo.getText().toString(), etDescritopn.getText().toString()));
//                        timelineAdapter.notifyDataSetChanged();
//                        timelineHashMap.put(etFrom.getText().toString() + "-" + etTo.getText().toString(), new com.sohba_travel.sohba.Models.Timeline(etFrom.getText().toString(), etTo.getText().toString(), etDescritopn.getText().toString()));
                        bottomDialog.dismiss();
                    }
                });
            }
        });


//        firebase intial
        mAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mAuth.getCurrentUser();

        // New child entries
        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();

    }


    final String[] list = new String[]{"Chinese", "Spanish", "English", "Hindi", "Arabic", "Portuguese", "Bengali", "Russian", "Japanese", "German", "Turkish", "Italian", "French"};
    final String[] list_ = new String[]{"Chinese", "Spanish", "English", "Hindi", "Arabic", "Portuguese", "Bengali", "Russian", "Japanese", "German", "Turkish", "Italian", "French"};


    private List<String> mCheckedItems = new ArrayList<>();

    private void showMultiChoiceListAlertDialog(final String[] list) {
        mCheckedItems.clear();


        createAlertDialogBuilder()
                .setTitle("Language")


                .setNegativeButton("Cancel", new ButtonClickedListener(1))
                .setPositiveButton("Choose", new ButtonClickedListener())
                .setMultiChoiceItems(list,
                        new boolean[]{false, false, false, false, false, false, false, false, false, false, false, false, false},
                        new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                if (isChecked) {
                                    mCheckedItems.add(list[which]);
                                } else {
                                    mCheckedItems.remove(list[which]);
                                }

                            }
                        })
                .show();

    }

    private android.app.AlertDialog.Builder createAlertDialogBuilder() {
        return new android.app.AlertDialog.Builder(this, R.style.AlertDialogCustom);
    }

    private class ButtonClickedListener implements DialogInterface.OnClickListener {
        private StringBuffer c = new StringBuffer();

        int x = 0;

        public ButtonClickedListener(int i) {
            x = i;
        }

        public ButtonClickedListener() {

        }

        @Override
        public void onClick(DialogInterface dialog, int which) {

            if (x == 0) {

                for (int i = 0; i < mCheckedItems.size(); i++) {
                    if (i != 0) {
                        c.append(" , ");
                    }
                    c.append(mCheckedItems.get(i));

                }

                language.setText(c.toString());
            } else {
                dialog.cancel();

            }

        }
    }

    public void goToNext(View view) {

        if (national.getText().toString().isEmpty() || language.getText().toString()
                .isEmpty() || job.getText().toString().isEmpty()) {
            Toast.makeText(RegisterAddLater.this, "Plz Fill All Information", Toast.LENGTH_LONG).show();
            return;
        }
        String interset = "";
        for (int i = 0; i < users.size(); i++) {
            interset += users.get(i).getInterest() + " ,";
        }
        if (interset.isEmpty()) {
            Toast.makeText(RegisterAddLater.this, "Plz Add your Intrests", Toast.LENGTH_LONG).show();
            return;
        }

        mFirebaseDatabaseReference.child("users")
                .child(mFirebaseUser.getUid()).child("nationality").setValue(national.getText().toString());
        mFirebaseDatabaseReference.child("users")
                .child(mFirebaseUser.getUid()).child("language").setValue(language.getText().toString());
        mFirebaseDatabaseReference.child("users")
                .child(mFirebaseUser.getUid()).child("job").setValue(job.getText().toString());


        mFirebaseDatabaseReference.child("users")
                .child(mFirebaseUser.getUid()).child("interests").setValue(interset);


        if (edit == 0) {
            Intent i = new Intent(RegisterAddLater.this, MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        } else {
            finish();
        }
    }

    public void AddLater(View view) {
        Intent i = new Intent(RegisterAddLater.this, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }


}

package com.sohba_travel.sohba.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.sohba_travel.sohba.MainActivity;
import com.sohba_travel.sohba.R;
import com.sohba_travel.sohba.Utility.RegisterData;
import com.sohba_travel.sohba.Utility.font;
import com.sohba_travel.sohba.adapters.ItemAdabter;
import com.sohba_travel.sohba.models.Item;

import java.util.ArrayList;
import java.util.List;

public class RegisterAddLater extends AppCompatActivity {

    public static String nationals = "pasf";
    public static String languaes = "fasfi23";
    public static String jobs = "sadaoer9";

    EditText national;
    EditText language;
    EditText job;
    private static String user_id;


    //firebase
//    private FirebaseAuth mAuth;
//    private FirebaseUser mFirebaseUser;
//    private DatabaseReference mFirebaseDatabaseReference;


    private GridView listView1;
    static ItemAdabter adapter;
    static ArrayList<Item> users;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_add_later);

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


        Item User[] = new Item[]
                {
//                        new Item("Food"),
//                        new Item( "Art"),
//                        new Item("Friendship"),
//                        new Item("Music"),
//                        new Item("Reading"),
//                        new Item("Programming"),
//                        new Item("Football"),
//                        new Item("Swimming")
                };
        users = new ArrayList<>();

        for (int i=0;i<User.length;i++){
            users.add(User[i]);
        }
        adapter = new ItemAdabter(this,
                R.layout.interest_item, users);
        listView1 = (GridView) findViewById(R.id.listView1);

        listView1.setAdapter(adapter);
        //firebase intial
//        mAuth = FirebaseAuth.getInstance();
//
//        // New child entries
//        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();

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
        //        private List<String> mShowWhenClicked;
        private StringBuffer c = new StringBuffer();

        int x = 0;

        public ButtonClickedListener(int i) {
            x = i;
        }

        public ButtonClickedListener() {
//            c.append("dsa");

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
//                languages=c.toString();
            } else {
                dialog.cancel();

            }

        }
    }

    public void goToNext(View view){
//        if (email.isEmpty() || password.isEmpty()) {
//            Toast.makeText(RegisterAddLater.this, "email empty or pass", Toast.LENGTH_LONG).show();
//            return;
//        }

//        mAuth.createUserWithEmailAndPassword(email, password)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        Log.d("h", "createUserWithEmail:onComplete:" + task.isSuccessful());
//
//                        // If sign in fails, display a message to the user. If sign in succeeds
//                        // the auth state listener will be notified and logic to handle the
//                        // signed in user can be handled in the listener.
//                        if (!task.isSuccessful()) {
//                            Toast.makeText(RegisterAddLater.this, "faild",
//                                    Toast.LENGTH_SHORT).show();
//                        } else {
//                            Toast.makeText(RegisterAddLater.this, "created",
//                                    Toast.LENGTH_SHORT).show();
//
                            startActivity(new Intent(RegisterAddLater.this, MainActivity.class));
//
//                            mFirebaseUser = mAuth.getCurrentUser();
//                            user_id = mFirebaseUser.getUid();
//                            addNewUserData();
//
//                        }
//
//                        // [START_EXCLUDE]
////                        hideProgressDialog();
//                        // [END_EXCLUDE]
//                    }
//                });
    }


    private void addNewUserData() {
//        NewUserHost newUserHost = new
//                NewUserHost(user_id, userType + "", firstname, lastname, birthdate, gender, phone,
//                email,  language.getText().toString(), 0 + "","empty","empty","empty","empty",
//                "sport , programming ",national.getText().toString(),job.getText().toString());
//
//            NewUserGuest newUserGuest = new
//                    NewUserGuest(user_id, userType + "", firstname, lastname, birthdate, gender, phone,
//                    email,  language.getText().toString(), 0 + "","empty",
//                    "sport , programming ",national.getText().toString(),job.getText().toString());
//
//        if (userType==0){
//            mFirebaseDatabaseReference.child("users")
//                    .child(user_id).setValue(newUserGuest);
//        }else{
//            mFirebaseDatabaseReference.child("users")
//                    .child(user_id).setValue(newUserHost);
//        }

    }

//    (String uId, String type, String fName, String lName, String birthdate, String gender, String mobile, String email,
//    String language, String verified, String profileImage, String frontIdImage,
//    String backIdImage, String selfieImage, String interests, String nationality, String job) {



}

package com.sohba_travel.sohba.Utility;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.sohba_travel.sohba.R;
import com.sohba_travel.sohba.Views.ShowHidePasswordEditText;


/**
 * Created by zooma on 3/30/2016.
 */
public class font {
    public   void changeFonts(Context c, ViewGroup root) {
        try
        {
            for(int i = 0; i <root.getChildCount(); i++) {
                View v = root.getChildAt(i);
                if(v instanceof Button) {
                    ((Button)v).setTypeface(Typeface.createFromAsset(c.getResources().getAssets(), c.getString(R.string.font_regular)));
                } else if(v instanceof TextView) {
                    ((TextView)v).setTypeface(Typeface.createFromAsset(c.getResources().getAssets(), c.getString(R.string.font_regular)));
                } else if(v instanceof EditText) {
                    ((EditText)v).setTypeface(Typeface.createFromAsset(c.getResources().getAssets(), c.getString(R.string.font_regular)));
                }else if (v instanceof ShowHidePasswordEditText){
                    ((ShowHidePasswordEditText)v).setTypeface(Typeface.createFromAsset(c.getResources().getAssets(), c.getString(R.string.font_regular)));
                }else if(v instanceof AutoCompleteTextView){
                    ((AutoCompleteTextView)v).setTypeface(Typeface.createFromAsset(c.getResources().getAssets(), c.getString(R.string.font_regular)));

                }else if(v instanceof ViewGroup) {
                    changeFonts(c,(ViewGroup) v);
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


}

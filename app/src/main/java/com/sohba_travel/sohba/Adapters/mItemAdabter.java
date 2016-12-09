package com.sohba_travel.sohba.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.sohba_travel.sohba.Models.mItem;
import com.sohba_travel.sohba.R;

import java.util.ArrayList;

/**
 * Created by M on 12/8/2016.
 */
public class mItemAdabter extends ArrayAdapter<mItem> {
    Context context;
    int layoutResourceId;
    ArrayList<mItem> data = null;
    mItemAdabter adabter = this;

    public mItemAdabter(Context context, int layoutResourceId, ArrayList<mItem> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View row = convertView;
        UserHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new UserHolder();
            holder.button_interest = (Button) row.findViewById(R.id.button_interest);


            row.setTag(holder);
        } else {
            holder = (UserHolder) row.getTag();
        }

        mItem myData = data.get(position);
        holder.button_interest.setText(myData.getInterest());

//        holder.remove.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                data.remove(position);
//                adabter.notifyDataSetChanged();
//            }
//        });


        return row;
    }

    static class UserHolder {
        Button button_interest;

    }
}
package com.sohba_travel.sohba.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.sohba_travel.sohba.R;
import com.sohba_travel.sohba.models.Item;

import java.util.ArrayList;



public class ItemAdabter extends ArrayAdapter<Item> {
    Context context;
    int layoutResourceId;
    ArrayList<Item> data = null;
    ItemAdabter adabter=this;

    public ItemAdabter(Context context, int layoutResourceId, ArrayList<Item> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View row = convertView;
        UserHolder holder = null;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new UserHolder();
            holder.button_interest= (Button) row.findViewById(R.id.button_interest);




            row.setTag(holder);
        }
        else
        {
            holder = (UserHolder)row.getTag();
        }

        Item myData = data.get(position);
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
    static class UserHolder
    {
        Button button_interest;

    }
}

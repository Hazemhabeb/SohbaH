package com.sohba_travel.sohba.Adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sohba_travel.sohba.Models.Timeline;
import com.sohba_travel.sohba.R;
import com.sohba_travel.sohba.UI.SohbaTextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by M on 12/14/2016.
 */

public class TimelineAdapter extends RecyclerView.Adapter<TimelineAdapter.ViewHolder> {
    private List<Timeline> TimelineList;


    public TimelineAdapter(List<Timeline> TimelineList) {
        this.TimelineList = TimelineList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.timeline_item, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Timeline timeline = TimelineList.get(position);
        holder.tvFromTo.setText(timeline.getFrom() + " - " + timeline.getTo());
        holder.tvTimlineDescription.setText(timeline.getDescription());


    }

    @Override
    public int getItemCount() {
        return TimelineList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvFromTo)
        SohbaTextView tvFromTo;
        @BindView(R.id.tvTimlineDescription)
        SohbaTextView tvTimlineDescription;
        @BindView(R.id.card_view)
        CardView cardView;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}



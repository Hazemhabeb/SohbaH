package com.sohba_travel.sohba;

import android.animation.Animator;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.transition.Fade;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.cleveroad.fanlayoutmanager.FanLayoutManager;
import com.cleveroad.fanlayoutmanager.FanLayoutManagerSettings;
import com.cleveroad.fanlayoutmanager.callbacks.FanChildDrawingOrderCallback;

/**
 * Created by M on 11/21/2016.
 */

public class HomeFragment extends Fragment {
    public HomeFragment() {
        // Required empty public constructor
    }

    private FanLayoutManager fanLayoutManager;

    private SportCardsAdapter adapter;

    public static HomeFragment newInstance() {
        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rvCards);
        ImageView imageView= (ImageView) view.findViewById(R.id.ivHMain);
        Glide.with(getActivity()).load(R.drawable.city).into(imageView);

        FanLayoutManagerSettings fanLayoutManagerSettings = FanLayoutManagerSettings
                .newBuilder(getContext())
                .withFanRadius(true)
                .withAngleItemBounce(5)
                .withViewHeightDp(200)
                .withViewWidthDp(120)
                .build();

        fanLayoutManager = new FanLayoutManager(getContext(), fanLayoutManagerSettings);
        fanLayoutManager.canScrollHorizontally();

        recyclerView.setLayoutManager(fanLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        adapter = new SportCardsAdapter(getContext());
        adapter.addAll(SportCardsUtils.generateSportCards());

        adapter.setItemClickListener(new SportCardsAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(int itemPosition, final View view) {
                if (fanLayoutManager.getSelectedItemPosition() != itemPosition) {
                    fanLayoutManager.switchItem(recyclerView, itemPosition);
                } else {
                    fanLayoutManager.straightenSelectedItem(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animator) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animator) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                onClick(view, fanLayoutManager.getSelectedItemPosition());
                            } else {
                                onClick(fanLayoutManager.getSelectedItemPosition());
                            }
                        }

                        @Override
                        public void onAnimationCancel(Animator animator) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animator) {

                        }
                    });
                }

            }
        });

        recyclerView.setAdapter(adapter);

        recyclerView.setChildDrawingOrderCallback(new FanChildDrawingOrderCallback(fanLayoutManager));

//        (view.findViewById(R.id.logo)).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                fanLayoutManager.collapseViews();
//            }
//        });

    }

    @android.support.annotation.RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void onClick(View view, int pos) {
        fanLayoutManager.collapseViews();
        startActivity(new Intent(getActivity(),TripDetailActivity.class));


    }

    public void onClick(int pos) {
        fanLayoutManager.collapseViews();
        startActivity(new Intent(getActivity(),TripDetailActivity.class));


    }

}

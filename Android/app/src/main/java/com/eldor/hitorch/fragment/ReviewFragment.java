package com.eldor.hitorch.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;

import com.eldor.hitorch.R;
import com.eldor.hitorch.adapter.ReviewAdapter;
import com.eldor.hitorch.model.MyReviews;

import java.util.ArrayList;


public class ReviewFragment extends Fragment implements View.OnClickListener{

    private RecyclerView recyclerView;
    private ReviewAdapter adapter;
    private FloatingActionButton floatingActionButton;

    private ArrayList<MyReviews> reviews;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_review, container, false);

        recyclerView = view.findViewById(R.id.review_recyclerView);
        floatingActionButton = view.findViewById(R.id.add_review_button);
        floatingActionButton.setOnClickListener(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        reviews = new ArrayList<>();
        adapter = new ReviewAdapter(getContext(), reviews);
        recyclerView.setAdapter(adapter);

        createList();

        scrollchangelistener();

        return view;
    }

    private void scrollchangelistener() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(dy>0 && floatingActionButton.getVisibility()==View.VISIBLE){
                    floatingActionButton.hide();
                }else if(dy<0 && floatingActionButton.getVisibility()!=View.VISIBLE){
                    floatingActionButton.show();
                }
            }
        });
    }


    private void createList() {
        MyReviews myReviews = new MyReviews("Hello Restaurant","Visited as a guest in the Echo restaurant for lunch just today. We were entertaining friends from California, and enjoyed our ocean side table. We chose to stay indoors - to enjoy the air conditioning ", "Sardor Ibrohimov", "01/12/2018", 4.8);
        reviews.add(myReviews);
        myReviews = new MyReviews("Bukhara Cafe","We had lunch here a few times while on the island visiting family and friends. The servers here are just wonderful and have great memories it seems. We sat on the ocean front patio and enjoyed the view with our delicious wine and lunch. Must try!", "Sardor Ibrohimov", "01/12/2018", 2.8);
        reviews.add(myReviews);
        myReviews = new MyReviews("Alies","Hello. Please give our thanks to the Manager(s) and others for the wonderful room and bottle of sparkling wine for our Anniversary stay. We had an amazing time. ", "Sardor Ibrohimov", "01/12/2018", 3.5);
        reviews.add(myReviews);
        myReviews = new MyReviews("Sardor","Rachel at the Pool (drinks server) was so gorgeous. We chatted with her all weekend and she played with the kids. She's an asset to the hotel esp for people with families. I saw other attendants playing with the kids too which is so welcoming. Rachel was gorgeous. Give her a raise! ", "Sardor Ibrohimov", "01/12/2018", 4.0);
        reviews.add(myReviews);
        myReviews = new MyReviews("Muhammadsher","I had lunch with some of my colleagues at Echo on Day 1. I had the wedge salad - it was delicious. On Night 2, I enjoyed a drink at the bar. I had a Margarita. The service was excellent.", "Sardor Ibrohimov", "01/12/2018", 1);
        reviews.add(myReviews);
        myReviews = new MyReviews("Bye","Dinner in the King and Prince's new restaurant-ECHO (open only 12 days) interesting menu, wonderful meal that was perfectly served by our waiter, Peter, who also filled us in on the local community. Room service breakfast was exactly on time, hot (not warm) food and oh so good. All in all, a perfect stay!!", "Sardor Ibrohimov", "01/12/2018", 4.1);
        reviews.add(myReviews);
        myReviews = new MyReviews("Diamond","The food was absolutely wonderful, from preparation to presentation, very pleasing. We especially enjoyed the special bar drinks, the cucumber/cilantro infused vodka martini and the K&P Aquarium was great (even took photos so we could try to replicate at home).", "Sardor Ibrohimov", "01/12/2018", 5);
        reviews.add(myReviews);
    }


    @Override
    public void onClick(View view) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("dialog");
        if(prev!=null){
            ft.remove(prev);
        }

        ft.addToBackStack(null);

        DialogFragment dialogFragment = new AddReviewFragment();
        dialogFragment.show(ft, "dialog");
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}

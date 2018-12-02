package com.eldor.hitorch.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.eldor.hitorch.R;
import com.eldor.hitorch.model.MyReviews;


import java.util.ArrayList;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewHolder> {

    private Context context;
    private ArrayList<MyReviews> reviews;

    public ReviewAdapter(Context context, ArrayList<MyReviews> reviews) {
        this.context = context;
        this.reviews = reviews;
    }

    @Override
    public ReviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_review, parent, false);
        return new ReviewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReviewHolder holder, int position) {
        MyReviews myReviews = reviews.get(position);
        holder.setDetails(myReviews);
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }


    public class ReviewHolder extends RecyclerView.ViewHolder{

        private TextView txt_content;
        private TextView txt_title;
        private TextView txt_fullname;
        private TextView txt_date;
        private RatingBar ratingBar;

        public ReviewHolder(View itemView) {
            super(itemView);
            txt_title = itemView.findViewById(R.id.title);
            txt_content = itemView.findViewById(R.id.txt_reviews);
            txt_fullname = itemView.findViewById(R.id.txt_fullname);
            txt_date = itemView.findViewById(R.id.txt_date);
            ratingBar = itemView.findViewById(R.id.rating);
        }


        public void setDetails(MyReviews myReviews){
            txt_title.setText(myReviews.getTitle());
            txt_content.setText(myReviews.getContent());
            txt_fullname.setText(myReviews.getFullname());
            txt_date.setText(myReviews.getDate());
            ratingBar.setRating((float)myReviews.getRate());
        }
    }

}

package com.eldor.hitorch.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.eldor.hitorch.R;
import com.eldor.hitorch.library.FlowTagLayout;
import com.eldor.hitorch.model.Feedback;
import com.eldor.hitorch.model.Hotels;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;

import java.util.ArrayList;

public class FeedbackAdapter extends RecyclerView.Adapter<FeedbackAdapter.FeedbackHolder> {


    private Hotels hotel;

    public FeedbackAdapter(Hotels hotel) {
        this.hotel = hotel;
    }

    @Override
    public FeedbackHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.feedback_item, parent, false);

        return new FeedbackHolder(view);
    }

    @Override
    public void onBindViewHolder(FeedbackHolder holder, int position) {
        holder.setData(hotel);
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class FeedbackHolder extends RecyclerView.ViewHolder{

        private TextView title;
        private TextView full_name;
        private TextView date;
        private RatingBar ratingBar;
        private  TextView content;

        public FeedbackHolder(View itemView) {

            super(itemView);
            title = itemView.findViewById(R.id.f_title);
            full_name = itemView.findViewById(R.id.f_fullname);
            date = itemView.findViewById(R.id.f_txt_date);
            ratingBar = itemView.findViewById(R.id.f_rating);
            content = itemView.findViewById(R.id.f_txt_reviews);
        }

        public void setData(Hotels list){

                JsonArray array = list.getFeedback();
                title.setText(list.getTitle());
                for(int j=0; j<array.size(); j++){
                    JsonObject object = array.get(j).getAsJsonObject();
                   if(object != null){
                        full_name.setText(object.get("title").getAsString());
                        date.setText(object.get("the_date").getAsString());
                        ratingBar.setRating(Float.parseFloat(object.get("sentiment_ratio").getAsString()));
                        content.setText(object.get("feed_text").getAsString());
                    }

            }
        }
    }
}

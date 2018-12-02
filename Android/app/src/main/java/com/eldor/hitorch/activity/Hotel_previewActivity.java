package com.eldor.hitorch.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.eldor.hitorch.R;
import com.eldor.hitorch.adapter.FeedbackAdapter;
import com.eldor.hitorch.data.Common;
import com.eldor.hitorch.model.Hotels;

import java.io.Serializable;

public class Hotel_previewActivity extends AppCompatActivity{

    private TextView title;
    private RatingBar ratingBar;
    private TextView cost;
    private RecyclerView recyclerView;
    private FeedbackAdapter adapter;
    Hotels hotel = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_preview);
        hotel = Common.commonobject;
        init_data();
    }

    private void init_data() {

        recyclerView = findViewById(R.id.feedback_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        adapter = new FeedbackAdapter(hotel);
        recyclerView.setAdapter(adapter);

        title = findViewById(R.id.h_name);
        ratingBar = findViewById(R.id.h_ratingbar);
        cost = findViewById(R.id.h_txt_cost);

        if(hotel!=null){
            title.setText(hotel.getTitle());
            ratingBar.setRating(hotel.getRating_bar());
            cost.setText("$"+hotel.getCost());
        }

    }
}

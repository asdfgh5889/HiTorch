package com.eldor.hitorch.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.eldor.hitorch.R;
import com.eldor.hitorch.adapter.HotelListAdapter;
import com.eldor.hitorch.data.Common;
import com.eldor.hitorch.model.Hotels;


public class HotelListActivity extends AppCompatActivity{

    private RecyclerView recyclerView;
    private HotelListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_list);

        init();
    }

    private void init() {
        recyclerView = findViewById(R.id.recyclerView_hotellist);
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));

        recyclerView.setAdapter(new HotelListAdapter(Common.list, new HotelListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Hotels hotels) {
                showDetailData(hotels);
            }
        }));

    }

    private void showDetailData(Hotels hotel) {
        Intent intent = new Intent(this, Hotel_previewActivity.class);
        Common.commonobject = hotel;
        startActivity(intent);
    }

}

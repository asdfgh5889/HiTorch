package com.eldor.hitorch.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.eldor.hitorch.R;
import com.eldor.hitorch.interfaces.OnItemClickListener;
import com.eldor.hitorch.model.Hotels;

import java.util.ArrayList;

public class HotelListAdapter extends RecyclerView.Adapter<HotelListAdapter.HotelListHolder> implements View.OnClickListener {

   private ArrayList<Hotels> hotels;
   private final OnItemClickListener listener;

   public interface OnItemClickListener{
       void onItemClick(Hotels hotels);
   }

    public HotelListAdapter(ArrayList<Hotels> hotels, OnItemClickListener listener) {
        this.hotels = hotels;
        this.listener = listener;
    }

    @Override
    public HotelListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, parent, false);
        view.setOnClickListener(this);
        return new HotelListHolder(view);
    }

    @Override
    public void onBindViewHolder(HotelListHolder holder, int position) {
        Hotels hotel = hotels.get(position);
        holder.bind(hotels.get(position), listener);
        holder.setHolder(hotel);
    }

    @Override
    public int getItemCount() {
        return hotels.size();
    }

    @Override
    public void onClick(View view) {



    }

    public class HotelListHolder extends RecyclerView.ViewHolder{

        private TextView hotel_name;
        private ImageView image;
        private RatingBar ratingBar;
        private TextView cost;
        private TextView link;

        public HotelListHolder(View itemView) {
            super(itemView);
            hotel_name = itemView.findViewById(R.id.hotel_title);
            image = itemView.findViewById(R.id.hotel_image);
            ratingBar = itemView.findViewById(R.id.rating_hotel);
            cost = itemView.findViewById(R.id.hotel_cost);
            link = itemView.findViewById(R.id.linkToHotelweb);
        }

        public void setHolder(Hotels hotel){
            hotel_name.setText(hotel.getTitle());
            //image.setImageResource(Integer.parseInt(hotel.getImage()));
            ratingBar.setRating(hotel.getRating_bar());
            cost.setText("$" + hotel.getCost());
            link.setText(hotel.getLink());
        }

        public void bind(final Hotels hotel, final OnItemClickListener listener) {

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(hotel);
                }
            });

        }
    }
}

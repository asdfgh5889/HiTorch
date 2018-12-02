package com.eldor.hitorch.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.eldor.hitorch.R;
import com.eldor.hitorch.activity.HotelListActivity;
import com.eldor.hitorch.data.Common;
import com.eldor.hitorch.model.Hotels;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

public class HomeFragment extends Fragment implements TextView.OnEditorActionListener, View.OnClickListener {



    private CardView cardView_hotel;
    private CardView cardView_things_to_do;
    private CardView cardView_rentals;
    private CardView cardView_flight;
    private CardView cardView_restaurant;
    private CardView cardView_reviews;

    private AutoCompleteTextView textView = null;
    private LinearLayoutManager layoutManager = null;
    static List<String> myOptions;

    private String region;
    private int type;

    private ArrayAdapter<String> autocompletetexts = null;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        textView = view.findViewById(R.id.find_place);
        textView.setCursorVisible(false);
        autocompletetexts = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.regions));
        textView.setThreshold(1);
        textView.setAdapter(autocompletetexts);
        textView.setOnEditorActionListener(this);

        myOptions = Arrays.asList((getResources().getStringArray(R.array.regions)));

        initButton(view);

        //hidekeyboard();

        return view;
    }

    private void initButton(View view) {

        cardView_hotel = view.findViewById(R.id.cdv_hotel);
        cardView_things_to_do = view.findViewById(R.id.cdv_things_to_do);
        cardView_rentals = view.findViewById(R.id.cdv_rentals);
        cardView_flight = view.findViewById(R.id.cdv_flight);
        cardView_restaurant = view.findViewById(R.id.cdv_restaurant);
        cardView_reviews = view.findViewById(R.id.cdv_reviews);

        cardView_hotel.setOnClickListener(this);
        cardView_things_to_do.setOnClickListener(this);
        cardView_rentals.setOnClickListener(this);
        cardView_flight.setOnClickListener(this);
        cardView_restaurant.setOnClickListener(this);
        cardView_reviews.setOnClickListener(this);

    }


    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        if(i == EditorInfo.IME_ACTION_GO){
            hidekeyboard();
        }
        return false;
    }

    private void hidekeyboard() {
        InputMethodManager imm = (InputMethodManager)getActivity()
                .getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),0);
    }


    @Override
    public void onClick(View view) {
        switch(view.getId()){

            case R.id.cdv_hotel:
                region = textView.getText().toString();
                type = 2;
                sendserver();

                break;
            case R.id.cdv_things_to_do:

                break;
            case R.id.cdv_rentals:

                break;
            case R.id.cdv_flight:

                break;
            case R.id.cdv_restaurant:

                break;
            case R.id.cdv_reviews:

                break;

        }
    }

    private void sendserver() {
        if(!textView.getText().toString().equals("")){
            Ion.with(getContext()).load("POST",Common.BASE_URL+"getPlaces")
                    .setBodyParameter("type",String.valueOf(type))
                    .setBodyParameter("region", String.valueOf(myOptions.indexOf(region)+1))
                    .asJsonObject()
                    .setCallback(new FutureCallback<JsonObject>() {
                        @Override
                        public void onCompleted(Exception e, JsonObject result) {

                            JsonArray array = result.get("respond").getAsJsonArray();
                            Common.list.clear();
                            for(int i=0; i<array.size(); i++){
                                JsonObject object = array.get(i).getAsJsonObject();
                                Hotels hotel = new Hotels(object.get("name").getAsString(),
                                        "", object.get("totalRating").getAsFloat(),
                                        object.get("cost").getAsString(),"www.uzbekistan.uz", object.get("feedback").getAsJsonArray());
                                Common.list.add(hotel);
                            }

                            Intent intent = new Intent(getContext(), HotelListActivity.class);
                            startActivity(intent);
                        }
                    });
        }
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

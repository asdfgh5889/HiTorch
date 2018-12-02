package com.eldor.hitorch.activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.eldor.hitorch.R;
import com.eldor.hitorch.adapter.PlanAdapter;
import com.eldor.hitorch.data.Common;
import com.eldor.hitorch.library.FlowTagLayout;
import com.eldor.hitorch.library.OnTagSelectListener;
import com.eldor.hitorch.model.NewPlan;
import com.eldor.hitorch.model.Plan;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.gujun.android.taggroup.TagGroup;

import static java.sql.Types.NULL;

public class NewPlanActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btn;

    Map<String, Integer> map = new HashMap<>();

    private FlowTagLayout region_tag;
    private FlowTagLayout trip_type_tag;

    private PlanAdapter<String> regions;
    private PlanAdapter<String> trip_types;

    private List<String> value;

    private EditText days;
    private EditText money;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_plan);
        map.put("historical",6);
        map.put("ecoturism",9);
        map.put("hiking",11);
        map.put("festival",10);
        init();
    }

    private void init() {

        days = findViewById(R.id.edt_days);
        money = findViewById(R.id.edt_money);

        region_tag = findViewById(R.id.tag_destination);
        trip_type_tag = findViewById(R.id.tag_trip_type);
        regions = new PlanAdapter<>(this);
        trip_types = new PlanAdapter<>(this);
        region_tag.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_MULTI);
        region_tag.setAdapter(regions);

        trip_type_tag.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_MULTI);
        trip_type_tag.setAdapter(trip_types);

        Common.init();

        region_tag.setOnTagSelectListener(new OnTagSelectListener() {
            @Override
            public void onItemSelect(FlowTagLayout parent, List<Integer> selectedList) {
                if(selectedList!=null && selectedList.size()>0){
                    Common.destination.clear();
                    for (int i:selectedList){
                        Common.destination.add(i+1);
                    }
                }else{
                    Common.destination.clear();
                }
            }
        });

        trip_type_tag.setOnTagSelectListener(new OnTagSelectListener() {
            @Override
            public void onItemSelect(FlowTagLayout parent, List<Integer> selectedList) {
                if(selectedList!=null && selectedList.size()>0){
                    Common.trip_types.clear();
                    for (int i:selectedList){
                        Common.trip_types.add(map.get(value.get(i)));
                    }
                }else{
                    Common.trip_types.clear();
                }
            }
        });
        initData();
    }

    private void initData() {

        btn = findViewById(R.id.btn_create_plan);
        btn.setOnClickListener(this);

        List<String> list = new ArrayList<>();
        String[] region_list = getResources().getStringArray(R.array.regions);
        list.addAll(Arrays.asList(region_list));
        regions.onlyAddAll(list);
        value = new ArrayList<String>();
        value.add("historical");
        value.add("ecoturism");
        value.add("hiking");
        value.add("festival");
        trip_types.onlyAddAll(value);

    }

    @Override
    public void onClick(View view) {
        int day = -1;
        int money_ = -1;

        if(!days.getText().toString().equals(""))
            day = Integer.parseInt(days.getText().toString());
        if(!money.getText().toString().equals(""))
            money_ = Integer.parseInt(money.getText().toString());
        NewPlan plan = new NewPlan(Common.destination.toArray(new Integer[Common.destination.size()]),Common.trip_types.toArray(new Integer[Common.trip_types.size()]), day, money_);
        String gson = plan.convertToJson(plan);
        JsonParser parser = new JsonParser();
        final JsonObject object = (JsonObject) parser.parse(gson);

        Ion.with(getApplicationContext())
                .load("POST", Common.BASE_URL+"createTripPlan")
                .setJsonObjectBody(object)
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        Common.plans.clear();
                        for(int i=0; i<result.size(); i++){
                            JsonObject object1 = result.get(i).getAsJsonObject();
                            Plan plan1 = new Plan(object1.get("time").getAsString(),
                                    object1.get("type").getAsInt()-1,
                                    object1.get("name").getAsString(),
                                    object1.get("location").getAsString(),
                                    object1.get("cost").getAsString());
                            Common.plans.add(plan1);
                        }
                        Common.clearData(NewPlanActivity.this);
                        finish();
                    }
                });
//                .asJsonObject()
//                .setCallback(new FutureCallback<JsonObject>() {
//                    @Override
//                    public void onCompleted(Exception e, JsonObject result) {
//                        Common.plans.clear();
//                        Log.i("Eldor", result.toString());
//                        JsonArray array = result.getAsJsonArray();
//                        for(int i=0; i<array.size(); i++){
//                            JsonObject object1 = array.get(i).getAsJsonObject();
//                            Plan plan1 = new Plan(object1.get("time").getAsString(),
//                                    object1.get("type").getAsInt()-1,
//                                    object1.get("name").getAsString(),
//                                    object1.get("location").getAsString(),
//                                    object1.get("cost").getAsString());
//                            Common.plans.add(plan1);
//                        }
//                    }
//                });

    }
}

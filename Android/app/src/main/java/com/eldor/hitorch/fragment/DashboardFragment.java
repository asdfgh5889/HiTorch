package com.eldor.hitorch.fragment;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.eldor.hitorch.R;
import com.eldor.hitorch.activity.NewPlanActivity;
import com.eldor.hitorch.data.Common;
import com.eldor.hitorch.model.Plan;

import org.qap.ctimelineview.TimelineRow;
import org.qap.ctimelineview.TimelineViewAdapter;

import java.util.ArrayList;

public class DashboardFragment extends Fragment implements View.OnClickListener{

    private ArrayList<TimelineRow> timelineRows = new ArrayList<>();
    ArrayAdapter<TimelineRow> myAdapter;
    private ArrayList<Plan> plans;
    private ListView listView;
    private FloatingActionButton floatingActionButton;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        plans = Common.takeData(getContext());
        if(plans!=null && !plans.isEmpty()){
            filldata(plans);
        }else{
            plans = Common.plans;
            filldata(plans);
        }

    }

//    private ArrayList<Plan> fakedata() {
//        ArrayList<Plan> plans = new ArrayList<>();
//        Plan plan = new Plan( "48", 6, "Registon trip", "1.1.1.1", "70");
//        plans.add(plan);
//        plan = new Plan( "75", 3, "Bibikhanum ", "1.1.1.1", "32");
//        plans.add(plan);
//        plan = new Plan( "30", 3, "Ulug`bek madrasasi", "1.1.1.1", "10");
//        plans.add(plan);
//        plan = new Plan( "50", 3, "Imom Buxoriy", "1.1.1.1", "20");
//        plans.add(plan);
//        plan = new Plan( "40", 11, "Samarkand Plov", "1.1.1.1", "10");
//        plans.add(plan);
//        plan = new Plan( "200", 2, "Samarkand Hotel", "1.1.1.1", "120");
//        plans.add(plan);
//        plan = new Plan( "200", 2, "Samarkand Motel", "1.1.1.1", "120");
//        plans.add(plan);
//        return plans;
//    }
//

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        floatingActionButton = view.findViewById(R.id.add_plan_button);
        floatingActionButton.setOnClickListener(this);
        if(!timelineRows.isEmpty()){
            myAdapter = new TimelineViewAdapter(getContext(),0, timelineRows, true);
            listView = view.findViewById(R.id.timeline_listview);
            listView.setAdapter(myAdapter);
            final AdapterView.OnItemClickListener adapter = new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    TimelineRow row = timelineRows.get(i);
                    if(!row.getImage().sameAs(BitmapFactory.decodeResource(getResources(),R.drawable.confirm))){
                        Toast.makeText(getContext(), row.getTitle(), Toast.LENGTH_SHORT).show();
                    }

                }
            };

            AdapterView.OnItemLongClickListener adapt= new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                    TimelineRow row = timelineRows.get(i);
                    if(!row.getImage().sameAs(BitmapFactory.decodeResource(getResources(),R.drawable.confirm))){
                        row.setImage(BitmapFactory.decodeResource(getResources(), R.drawable.confirm));
                        plans.get(i).setComplete(true);
                        listView.setAdapter(myAdapter);
                    }
                    return false;
                }
            };

            listView.setOnItemClickListener(adapter);
            listView.setOnItemLongClickListener(adapt);
        }else{
            Intent intent = new Intent(getContext(), NewPlanActivity.class);
            startActivity(intent);
        }


        //listView.setOnScrollListener(this);


        return view;
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(getContext(), NewPlanActivity.class);
        startActivity(intent);
    }

    private void filldata(ArrayList<Plan> plans) {
        for (int i = 0; i < plans.size(); i++) {
            timelineRows.add(createTimeLine(plans.get(i), i));
        }

    }

    private TimelineRow createTimeLine(Plan plan, int i) {

        TimelineRow row = new TimelineRow(i);

        row.setTitle(plan.getTitle());
        row.setDescription(plan.getDate()+" minutes" + "\n" + "$" + plan.getMoney());
        //String imageURL = checkImage(plan.getType());
        if(plan.isComplete()){
            row.setImage(BitmapFactory.decodeResource(getResources(), R.drawable.confirm));
        }else{
            row.setImage(BitmapFactory.decodeResource(getResources(), checkImage(plan.getType())));
        }
        row.setBellowLineColor(Color.parseColor("#0f5998"));
        row.setBellowLineSize(2);
        row.setImageSize(40);
        row.setBackgroundColor(Color.parseColor("#f7f8f9"));
        row.setBackgroundSize(45);
        row.setDateColor(Color.parseColor("#278be3"));
        row.setTitleColor(Color.parseColor("#278be3"));
        row.setDescriptionColor(Color.parseColor("#0f5998"));

        return row;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private int checkImage(int type) {

        int test = getContext().getResources().getIdentifier(Common.types[type-1], "drawable", getContext().getPackageName());

        if(test!=0){
            return test;
        }
        return R.drawable.foot;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Common.clearData(getContext());
        if(plans!=null && !plans.isEmpty()){
            Common.saveData(getContext(),plans);
        }
    }

}

package com.eldor.hitorch.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eldor.hitorch.R;
import com.eldor.hitorch.adapter.NotificationAdapter;
import com.eldor.hitorch.model.Notification;

import java.util.ArrayList;


public class NotificationFragment extends Fragment {


    private RecyclerView recyclerView;
    private NotificationAdapter adapter;

    private ArrayList<Notification> notifications;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_notification, container, false);
        recyclerView = view.findViewById(R.id.recyclerView_notification);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        notifications = new ArrayList<>();
        adapter = new NotificationAdapter(getContext(), notifications);
        recyclerView.setAdapter(adapter);
        create_notification_list();

        return view;
    }

    private void create_notification_list() {
        Notification note = new Notification("Uzbekistan", "Welcome to Uzbekistan!", "01/12/2018");
        notifications.add(note);
        note = new Notification("Restaurant","The restaurant you visited is  highly-demanded by tourists.", "26/11/2018");
        notifications.add(note);
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

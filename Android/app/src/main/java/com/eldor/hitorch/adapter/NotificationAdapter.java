package com.eldor.hitorch.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eldor.hitorch.model.Notification;

import java.util.ArrayList;
import com.eldor.hitorch.R;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationHolder>{


    private ArrayList<Notification> notifications;

    public NotificationAdapter(Context context, ArrayList<Notification> notifications) {
        this.notifications = notifications;
    }

    @Override
    public NotificationHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_page_cardview, parent, false);

        return new NotificationHolder(view);
    }

    @Override
    public void onBindViewHolder(NotificationHolder holder, int position) {
        Notification notification = notifications.get(position);
        holder.setHolder(notification);
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }


    public class NotificationHolder extends RecyclerView.ViewHolder{

        private TextView title;
        private TextView content;
        private TextView date;

        public NotificationHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.txt_notify_title);
            content = itemView.findViewById(R.id.txt_notify_content);
            date = itemView.findViewById(R.id.txt_notification_date);
        }

        public void setHolder(Notification notification){
            title.setText(notification.getTitle());
            content.setText(notification.getContent());
            date.setText(notification.getDate());
        }

    }
}

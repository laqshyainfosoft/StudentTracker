package com.app.laqshya.studenttracker.activity.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.app.laqshya.studenttracker.activity.model.FacultyNotification;
import com.app.laqshya.studenttracker.databinding.NotificationItemBinding;

import java.util.List;

public class FacultyNotificationAdapter extends RecyclerView.Adapter<FacultyNotificationAdapter.MyViewHolder> {
    private List<FacultyNotification> facultyNotificationList;

    public void setFacultyNotificationList(List<FacultyNotification> facultyNotificationList) {
        this.facultyNotificationList = facultyNotificationList;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        NotificationItemBinding notificationItemBinding = NotificationItemBinding.inflate(LayoutInflater.from(viewGroup.getContext()), viewGroup, false);
        return new MyViewHolder(notificationItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.bind(facultyNotificationList.get(i));


    }

    @Override
    public int getItemCount() {
        if (facultyNotificationList == null) return 0;
        else return facultyNotificationList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        NotificationItemBinding notificationItemBinding;

         MyViewHolder(@NonNull NotificationItemBinding itemView) {
            super(itemView.getRoot());
            this.notificationItemBinding = itemView;
        }

        void bind(FacultyNotification facultyNotification) {
            notificationItemBinding.setNotificationItem(facultyNotification);
            notificationItemBinding.executePendingBindings();
        }
    }
}

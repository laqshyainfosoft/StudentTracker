package com.app.laqshya.studenttracker.activity.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.laqshya.studenttracker.activity.model.FacultyNotification;
import com.app.laqshya.studenttracker.databinding.NotificationItemBinding;

import java.util.List;

public class StudentNotificationAdapter extends RecyclerView.Adapter<StudentNotificationAdapter.MyViewHolder> {
    private List<FacultyNotification> facultyNotificationList;
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        NotificationItemBinding notificationItemBinding = NotificationItemBinding.inflate(LayoutInflater.from(viewGroup.getContext()), viewGroup, false);
        return new StudentNotificationAdapter.MyViewHolder(notificationItemBinding);

    }

    public void setFacultyNotificationList(List<FacultyNotification> facultyNotificationList) {
        this.facultyNotificationList = facultyNotificationList;
        notifyDataSetChanged();
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
            notificationItemBinding.centerImg.setVisibility(View.GONE);
            notificationItemBinding.centerName.setVisibility(View.GONE);
            notificationItemBinding.centerTitle.setVisibility(View.GONE);
        }
    }
}

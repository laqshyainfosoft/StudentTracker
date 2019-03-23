package com.app.laqshya.studenttracker.activity.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import com.app.laqshya.studenttracker.activity.model.AdminNotification;
import com.app.laqshya.studenttracker.databinding.NotificationAdminBinding;

import java.util.ArrayList;
import java.util.List;

public class AdminNotificationAdapter extends RecyclerView.Adapter<AdminNotificationAdapter.CustomHolder>  implements Filterable {
    private List<AdminNotification> adminNotificationList;
    private List<AdminNotification> adminNotificationsFilteredList;

    public void setAdminNotificationsFilteredList(List<AdminNotification> adminNotificationsFilteredList) {
        this.adminNotificationsFilteredList = adminNotificationsFilteredList;
        notifyDataSetChanged();
    }

    public void setAdminNotificationList(List<AdminNotification> adminNotificationList) {
        this.adminNotificationList = adminNotificationList;
        notifyDataSetChanged();
    }



    @NonNull
    @Override
    public AdminNotificationAdapter.CustomHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        NotificationAdminBinding notificationAdminBinding = NotificationAdminBinding.inflate(LayoutInflater.from(viewGroup.getContext()), viewGroup, false);
        return new AdminNotificationAdapter.CustomHolder(notificationAdminBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminNotificationAdapter.CustomHolder myViewHolder, int i) {
        myViewHolder.bind(adminNotificationList.get(i));


    }

    @Override
    public int getItemCount() {
        if (adminNotificationsFilteredList == null) return 0;
        else return adminNotificationsFilteredList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence message) {
                String charString = message.toString();
                if (charString.isEmpty()) {
                    adminNotificationsFilteredList = adminNotificationList;
                } else {
                    List<AdminNotification> filteredList = new ArrayList<>();
                    for (AdminNotification row : adminNotificationList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getMessage().contains(message)) {
                            filteredList.add(row);
                        }
                    }

                    adminNotificationsFilteredList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = adminNotificationsFilteredList;
                return filterResults;


            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                adminNotificationsFilteredList= (List<AdminNotification>) results.values;
                notifyDataSetChanged();

            }
        };
    }

    class CustomHolder extends RecyclerView.ViewHolder {
        NotificationAdminBinding notificationAdminBinding;

        CustomHolder(@NonNull NotificationAdminBinding itemView) {
            super(itemView.getRoot());
            this.notificationAdminBinding = itemView;
        }

        void bind(AdminNotification adminNotification) {
            notificationAdminBinding.setNotificationItem(adminNotification);
            notificationAdminBinding.executePendingBindings();
        }
    }
}

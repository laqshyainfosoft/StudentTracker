package com.app.laqshya.studenttracker.activity.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import com.app.laqshya.studenttracker.activity.model.AdminNotification;
import com.app.laqshya.studenttracker.databinding.NotificationAdminBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import timber.log.Timber;

public class AdminNotificationAdapter extends RecyclerView.Adapter<AdminNotificationAdapter.CustomHolder>  implements Filterable {
    private List<AdminNotification> adminNotificationList;
    private List<AdminNotification> adminNotificationsFilteredList;



    public void setAdminNotificationList(List<AdminNotification> adminNotificationList) {
        this.adminNotificationList = adminNotificationList;
        this.adminNotificationsFilteredList=adminNotificationList;
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
        myViewHolder.bind(adminNotificationsFilteredList.get(i));


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
                String charString = message.toString().toLowerCase();
                if (charString.isEmpty()) {
                    adminNotificationsFilteredList = adminNotificationList;
                } else {
                    List<AdminNotification> filteredList = new ArrayList<>();
                    for (AdminNotification row : adminNotificationList) {
//                        Timber.d(row.getMessage());
//                        Timber.d(message.toString());
//                        Timber.d(String.valueOf("test message for fees 1000000 please donate for MacBook pro wireless air".contains(message)));
//                        Timber.d(String.valueOf(row.getMessage().toLowerCase().contains(charString)));



                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getMessage().toLowerCase().contains(charString)) {
                            filteredList.add(row);
                            Timber.d("Message %s %s",row.getMessage(),message);
                        }
                    }

                    adminNotificationsFilteredList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = adminNotificationsFilteredList;
                Timber.d("%d",adminNotificationsFilteredList.size());
                for (AdminNotification adminNotification:adminNotificationsFilteredList){
                    Timber.d(adminNotification.getMessage());
                }

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

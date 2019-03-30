package com.app.laqshya.studenttracker.activity.fragments;

import android.app.ProgressDialog;
import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.appcompat.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.app.laqshya.studenttracker.R;
import com.app.laqshya.studenttracker.activity.adapter.StudentNotificationAdapter;
import com.app.laqshya.studenttracker.activity.factory.NotificationFactory;
import com.app.laqshya.studenttracker.activity.utils.SessionManager;
import com.app.laqshya.studenttracker.activity.utils.Utils;
import com.app.laqshya.studenttracker.activity.viewmodel.NotificationAndStudentViewModel;
import com.app.laqshya.studenttracker.databinding.FragmentNotificationBinding;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

public class StudentReceivedNotificationFragment extends Fragment {
    FragmentNotificationBinding fragmentNotificationBinding;
    StudentNotificationAdapter facultyNotificationAdapter;

    @Inject
    NotificationFactory notificationFactory;
    NotificationAndStudentViewModel notificationAndStudentViewModel;
    @Inject
    SessionManager sessionManager;
    private ProgressDialog progressDialog;
    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fragmentNotificationBinding.recyclerViewNotification.setHasFixedSize(false);
        facultyNotificationAdapter = new StudentNotificationAdapter();
        fragmentNotificationBinding.recyclerViewNotification.setLayoutManager(new LinearLayoutManager(getActivity()));
        fragmentNotificationBinding.recyclerViewNotification.setAdapter(facultyNotificationAdapter);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Please wait");
        Toolbar toolbar=getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Received Notifications");
        getData();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentNotificationBinding = FragmentNotificationBinding.inflate(inflater, container, false);
        notificationAndStudentViewModel = ViewModelProviders.of(this, notificationFactory).get(NotificationAndStudentViewModel.class);
        return fragmentNotificationBinding.getRoot();
    }
    private void getData() {
        progressDialog.show();
        notificationAndStudentViewModel.getStudent(sessionManager.getLoggedInUserName()).observe(this, facultyNotifications -> {
            if (getActivity() != null && Utils.isNetworkConnected(getActivity())) {
                progressDialog.dismiss();
                if (facultyNotifications != null) {
                    if(facultyNotifications.size()==0){
                        showToast("No Notifications to Show");
                    }
                    facultyNotificationAdapter.setFacultyNotificationList(facultyNotifications);
                } else {
                    showToast(getString(R.string.errorwhilefetchingdata));
                }
            } else {
                showToast(getString(R.string.internet_connection));
            }


        });

    }

    private void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();

    }
}

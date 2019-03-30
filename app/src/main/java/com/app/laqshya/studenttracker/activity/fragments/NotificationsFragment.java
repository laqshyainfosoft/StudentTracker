package com.app.laqshya.studenttracker.activity.fragments;

import android.app.ProgressDialog;
import android.app.SearchManager;
import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.appcompat.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.app.laqshya.studenttracker.R;
import com.app.laqshya.studenttracker.activity.adapter.AdminNotificationAdapter;
import com.app.laqshya.studenttracker.activity.adapter.FacultyNotificationAdapter;
import com.app.laqshya.studenttracker.activity.factory.NotificationFactory;
import com.app.laqshya.studenttracker.activity.model.AdminNotification;
import com.app.laqshya.studenttracker.activity.utils.Constants;
import com.app.laqshya.studenttracker.activity.utils.SessionManager;
import com.app.laqshya.studenttracker.activity.utils.Utils;
import com.app.laqshya.studenttracker.activity.viewmodel.NotificationAndStudentViewModel;
import com.app.laqshya.studenttracker.databinding.FragmentNotificationBinding;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;
import timber.log.Timber;

public class NotificationsFragment extends Fragment {
    FragmentNotificationBinding fragmentNotificationBinding;
    FacultyNotificationAdapter facultyNotificationAdapter;
    AdminNotificationAdapter adminNotificationAdapter;

    @Inject
    NotificationFactory notificationFactory;
    NotificationAndStudentViewModel notificationAndStudentViewModel;
    @Inject
    SessionManager sessionManager;
    private ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentNotificationBinding = FragmentNotificationBinding.inflate(inflater, container, false);
        notificationAndStudentViewModel = ViewModelProviders.of(this, notificationFactory).get(NotificationAndStudentViewModel.class);
        return fragmentNotificationBinding.getRoot();
    }

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fragmentNotificationBinding.recyclerViewNotification.setHasFixedSize(false);
        facultyNotificationAdapter = new FacultyNotificationAdapter();
        adminNotificationAdapter=new AdminNotificationAdapter();
        fragmentNotificationBinding.recyclerViewNotification.setLayoutManager(new LinearLayoutManager(getActivity()));
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Loading");

        progressDialog.setMessage("Please wait");
        getData();

    }

    private void getData() {

        progressDialog.show();
        if(!sessionManager.getLoggedInType().equals(Constants.ADMIN)) {


            notificationAndStudentViewModel.getFaculty(sessionManager.getLoggedInUserName()).observe(this, facultyNotifications -> {
                if (getActivity() != null && Utils.isNetworkConnected(getActivity())) {
                    progressDialog.dismiss();
                    if (facultyNotifications != null) {
                        if (facultyNotifications.size() == 0) {
                            showToast("No Notifications to Show");
                        }
                        fragmentNotificationBinding.recyclerViewNotification.setAdapter(facultyNotificationAdapter);
                        facultyNotificationAdapter.setFacultyNotificationList(facultyNotifications);
                    } else {
                        showToast(getString(R.string.errorwhilefetchingdata));
                    }
                } else {
                    showToast(getString(R.string.internet_connection));
                }


            });
        }
        else {

            notificationAndStudentViewModel.getAllNotifications().observe(this, adminNotifications -> {
                if (getActivity() != null && Utils.isNetworkConnected(getActivity())) {
                    progressDialog.dismiss();
                    if (adminNotifications!= null) {
                        if (adminNotifications.size() == 0) {
                            showToast("No Notifications to Show");
                        }
                        else {

                            fragmentNotificationBinding.searchNotification.setVisibility(View.VISIBLE);
                            fragmentNotificationBinding.recyclerViewNotification.setAdapter(facultyNotificationAdapter);
                            fragmentNotificationBinding.recyclerViewNotification.setAdapter(adminNotificationAdapter);

                            adminNotificationAdapter.setAdminNotificationList(adminNotifications);
                            setUpSearch();
                        }
                    } else {
                        showToast(getString(R.string.errorwhilefetchingdata));
                    }
                } else {
                    showToast(getString(R.string.internet_connection));
                }


            });

        }

    }

    private void setUpSearch() {
        fragmentNotificationBinding.searchNotification.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Timber.d(s);

                        adminNotificationAdapter.getFilter().filter(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                Timber.d(s);
                adminNotificationAdapter.getFilter().filter(s);
//                adminNotificationAdapter.getFilter().filter("MacBook");
                return false;
            }
        });

    }

    private void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();

    }
}

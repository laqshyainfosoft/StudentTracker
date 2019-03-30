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
import com.app.laqshya.studenttracker.activity.adapter.SyllabusAdapter;
import com.app.laqshya.studenttracker.activity.factory.NotificationFactory;
import com.app.laqshya.studenttracker.activity.utils.SessionManager;
import com.app.laqshya.studenttracker.activity.utils.Utils;
import com.app.laqshya.studenttracker.activity.viewmodel.NotificationAndStudentViewModel;
import com.app.laqshya.studenttracker.databinding.ScheduleViewBinding;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

public class SyllabusFragment extends Fragment {
    SyllabusAdapter syllabusAdapter;
    @Inject
    NotificationFactory notificationFactory;
    NotificationAndStudentViewModel notificationAndStudentViewModel;
    @Inject
    SessionManager sessionManager;
    private ProgressDialog progressDialog;
    ScheduleViewBinding scheduleViewBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        scheduleViewBinding = ScheduleViewBinding.inflate(inflater, container, false);
        notificationAndStudentViewModel = ViewModelProviders.of(this, notificationFactory).get(NotificationAndStudentViewModel.class);

        return scheduleViewBinding.getRoot();

    }

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        scheduleViewBinding.recyclerViewSyllabus.setHasFixedSize(false);
        syllabusAdapter = new SyllabusAdapter();
        scheduleViewBinding.recyclerViewSyllabus.setAdapter(syllabusAdapter);
        scheduleViewBinding.recyclerViewSyllabus.setLayoutManager(new LinearLayoutManager(getActivity()));
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Please wait");
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Syllabus");
        getData();
    }


    private void getData() {
        progressDialog.show();
        notificationAndStudentViewModel.getSyllabus(sessionManager.getLoggedInUserName()).observe(this, syllabus -> {
            if (getActivity() != null && Utils.isNetworkConnected(getActivity())) {
                progressDialog.dismiss();
                if (syllabus != null) {
                    if (syllabus.getSyllabus() == null || syllabus.getSyllabus().size() == 0) {
                        showToast("No Notifications to Show");
                    } else {
                        syllabusAdapter.setFacultyNotificationList(syllabus);
                    }


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

package com.app.laqshya.studenttracker.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;

import com.app.laqshya.studenttracker.R;
import com.app.laqshya.studenttracker.activity.factory.AddScheduleFactory;
import com.app.laqshya.studenttracker.activity.model.CourseList;
import com.app.laqshya.studenttracker.activity.service.EduTrackerService;
import com.app.laqshya.studenttracker.activity.utils.SessionManager;
import com.app.laqshya.studenttracker.activity.viewmodel.AddSchedulesViewModel;
import com.app.laqshya.studenttracker.databinding.ActivityAddSchedulesBinding;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class AddSchedules extends AppCompatActivity {
    private ActivityAddSchedulesBinding activityAddSchedulesBinding;
    @Inject
    EduTrackerService eduTrackerService;
    @Inject
    SessionManager sessionManager;
    @Inject
    AddScheduleFactory addScheduleFactory;
    AddSchedulesViewModel addSchedulesViewModel;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        activityAddSchedulesBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_schedules);
        addSchedulesViewModel = ViewModelProviders.of(this, addScheduleFactory).get(AddSchedulesViewModel.class);
        activityAddSchedulesBinding.txtAtlocation.setText(sessionManager.getLoggedInuserCenter());
        addSchedulesViewModel.getCourseList().observe(this, courseLists -> {
            if (courseLists != null && courseLists.size() > 0) {
                ArrayAdapter<CourseList> courses = new ArrayAdapter<>(this, R.layout.spinner_layout, courseLists);
                activityAddSchedulesBinding.Atcoursename.setAdapter(courses);

            }

        });


    }
}

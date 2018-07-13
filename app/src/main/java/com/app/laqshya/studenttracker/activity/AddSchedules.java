package com.app.laqshya.studenttracker.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.app.laqshya.studenttracker.R;
import com.app.laqshya.studenttracker.databinding.ActivityAddSchedulesBinding;

public class AddSchedules extends AppCompatActivity {
    private ActivityAddSchedulesBinding activityAddSchedulesBinding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityAddSchedulesBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_schedules);


    }
}

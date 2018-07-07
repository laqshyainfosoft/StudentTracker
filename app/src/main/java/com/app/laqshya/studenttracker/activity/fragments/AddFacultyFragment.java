package com.app.laqshya.studenttracker.activity.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.app.laqshya.studenttracker.R;
import com.app.laqshya.studenttracker.activity.viewmodel.NavDrawerViewModel;
import com.app.laqshya.studenttracker.databinding.RegisterFacultyBinding;

public class AddFacultyFragment extends Fragment {
    private RegisterFacultyBinding registerFacultyBinding;
    private NavDrawerViewModel navDrawerViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        registerFacultyBinding = DataBindingUtil.inflate(inflater, R.layout.register_faculty, container, false);
        return registerFacultyBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        navDrawerViewModel = ViewModelProviders.of(getActivity()).get(NavDrawerViewModel.class);
        registerFacultyBinding.facultyCourses.setText(getString(R.string.course_name));
        navDrawerViewModel.getCourseList().observe(this, strings -> {
            ArrayAdapter<String> courses = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item,
                    strings);
            registerFacultyBinding.facultyCourses.setAdapter(courses, false, selected -> {


            });


        });

    }
}
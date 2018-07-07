package com.app.laqshya.studenttracker.activity.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.laqshya.studenttracker.R;
import com.app.laqshya.studenttracker.databinding.RegisterFacultyBinding;

public class AddFacultyFragment extends Fragment {
    private RegisterFacultyBinding registerFacultyBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        registerFacultyBinding = DataBindingUtil.inflate(inflater, R.layout.register_faculty, container, false);
        return registerFacultyBinding.getRoot();
    }
}
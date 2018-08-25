package com.app.laqshya.studenttracker.activity.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.laqshya.studenttracker.R;
import com.app.laqshya.studenttracker.activity.viewmodel.NavDrawerViewModel;
import com.app.laqshya.studenttracker.databinding.FragmentHomeAdminBinding;

import timber.log.Timber;

public class HomeFragmentCounsellor extends Fragment {
    FragmentHomeAdminBinding fragmentHomeAdminBinding;
    NavDrawerViewModel navDrawerViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Timber.d("onCreated");
        fragmentHomeAdminBinding = FragmentHomeAdminBinding.inflate(inflater, container, false);
        return fragmentHomeAdminBinding.getRoot();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Timber.d("I was called here");
        navDrawerViewModel = ViewModelProviders.of(getActivity()).get(NavDrawerViewModel.class);
        fragmentHomeAdminBinding.setNavViewModel(navDrawerViewModel);
        fragmentHomeAdminBinding.addStudents.setOnClickListener(v -> fragmentTransact(new AddStudentFragment()));
        fragmentHomeAdminBinding.scheduleBatches.setOnClickListener(v -> fragmentTransact(new AttendanceFragment()));
//        fragmentHomeAdminBinding.schedule_batches.setOnClickListener(v -> fragmentTransact(new AttendanceFragment()));
        fragmentHomeAdminBinding.broadcastBatches.setOnClickListener(v -> fragmentTransact(new NotificationsFragment()));


    }

    private void fragmentTransact(Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction;
        if (fragmentManager != null) {
            fragmentTransaction = fragmentManager.beginTransaction()
                    .replace(R.id.frame, fragment).addToBackStack(null);
            fragmentTransaction.commit();
        }


    }

}

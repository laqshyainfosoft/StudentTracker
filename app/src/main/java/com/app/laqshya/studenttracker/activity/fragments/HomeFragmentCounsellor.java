package com.app.laqshya.studenttracker.activity.fragments;

import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.laqshya.studenttracker.R;
import com.app.laqshya.studenttracker.activity.fragments.notifications.SingleStudentNotificationFragment;
import com.app.laqshya.studenttracker.activity.viewmodel.NavDrawerViewModel;
import com.app.laqshya.studenttracker.databinding.FragmentHomeAdminBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;

import timber.log.Timber;

public class HomeFragmentCounsellor extends Fragment {
    FragmentHomeAdminBinding fragmentHomeAdminBinding;
    NavDrawerViewModel navDrawerViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentHomeAdminBinding = FragmentHomeAdminBinding.inflate(inflater, container, false);
        return fragmentHomeAdminBinding.getRoot();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
       FloatingActionButton floatingActionButton = Objects.requireNonNull(getActivity()).findViewById(R.id.fab_attendance);
       floatingActionButton.hide();

        navDrawerViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(NavDrawerViewModel.class);
        fragmentHomeAdminBinding.setNavViewModel(navDrawerViewModel);
        fragmentHomeAdminBinding.addStudents.setOnClickListener(v -> fragmentTransact(new AddStudentFragment()));

        fragmentHomeAdminBinding.manageStudents.setOnClickListener(v->fragmentTransact(new ManageStudentFragment()));
        fragmentHomeAdminBinding.scheduleBatches.setOnClickListener(v -> fragmentTransact(new AttendanceFragment()));

//        fragmentHomeAdminBinding.schedule_batches.setOnClickListener(v -> fragmentTransact(new AttendanceFragment()));
        fragmentHomeAdminBinding.broadcastBatches.setVisibility(View.VISIBLE);
        fragmentHomeAdminBinding.broadcastBatches.setOnClickListener(v -> fragmentTransact(new SingleStudentNotificationFragment()));


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

    @Override
    public void onResume() {
        super.onResume();
        if(getActivity()!=null)
        getActivity().findViewById(R.id.bottom_navigation_admin).setVisibility(View.GONE);
    }
}

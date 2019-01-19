package com.app.laqshya.studenttracker.activity.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.laqshya.studenttracker.R;
import com.app.laqshya.studenttracker.activity.fragments.notifications.SameBatchFacultyFragment;
import com.app.laqshya.studenttracker.databinding.FragmentHomeFacultyBinding;

import timber.log.Timber;

public class HomeFragmentFaculty extends Fragment implements View.OnClickListener {
    FragmentHomeFacultyBinding fragmentHomeFacultyBinding;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentHomeFacultyBinding = FragmentHomeFacultyBinding.inflate(inflater, container, false);


        fragmentHomeFacultyBinding.attendanceFaculty.setOnClickListener(this);
        fragmentHomeFacultyBinding.broadcastFaculty.setOnClickListener(this);
        fragmentHomeFacultyBinding.receivedNotificationFaculty.setOnClickListener(this);

        return fragmentHomeFacultyBinding.getRoot();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.attendance_faculty:
                fragmentTransact(new FacultyBatchCardsFragment());

                break;
            case R.id.broadcast_faculty:
                fragmentTransact(new SameBatchFacultyFragment());

                break;
            case R.id.received_notification_faculty:
                fragmentTransact(new NotificationsFragment());
                break;
        }
    }

    private void fragmentTransact(Fragment fragment) {
        Timber.d("Fragment Called");
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction;
        if (fragmentManager != null) {
            fragmentTransaction = fragmentManager.beginTransaction()
                    .replace(R.id.frame, fragment).addToBackStack(null);
            fragmentTransaction.commit();
        }



    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(getActivity()!=null) {
            Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
            toolbar.setTitle("Home");
        }
    }
}

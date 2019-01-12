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
import com.app.laqshya.studenttracker.databinding.FragmentHomeStudentBinding;

import timber.log.Timber;

public class HomeFragmentStudent extends Fragment implements View.OnClickListener {
    FragmentHomeStudentBinding fragmentHomeStudentBinding;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentHomeStudentBinding=FragmentHomeStudentBinding.inflate(inflater,container,false);
        return fragmentHomeStudentBinding.getRoot();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fragmentHomeStudentBinding.syllabus.setOnClickListener(this);
        fragmentHomeStudentBinding.recievedNotification.setOnClickListener(this);
        fragmentHomeStudentBinding.fees.setOnClickListener(this);
        Toolbar toolbar=getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Home");


    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.syllabus:
                fragmentTransact(new SyllabusFragment());
                break;
            case R.id.recieved_notification:
                fragmentTransact(new StudentReceivedNotificationFragment());
                break;
            case R.id.fees:
                fragmentTransact(new FeesStatusFragment());
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
}

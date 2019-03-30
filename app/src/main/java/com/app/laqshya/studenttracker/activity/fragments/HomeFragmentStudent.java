package com.app.laqshya.studenttracker.activity.fragments;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.widget.Toolbar;
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

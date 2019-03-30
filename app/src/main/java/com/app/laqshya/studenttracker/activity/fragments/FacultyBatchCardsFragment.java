package com.app.laqshya.studenttracker.activity.fragments;

import android.app.ProgressDialog;
import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.laqshya.studenttracker.R;
import com.app.laqshya.studenttracker.activity.adapter.FacultyBatchCardAdapter;
import com.app.laqshya.studenttracker.activity.factory.EditSchedulesViewModelFactory;
import com.app.laqshya.studenttracker.activity.listeners.OnFacultyBatchClickListner;
import com.app.laqshya.studenttracker.activity.utils.Constants;
import com.app.laqshya.studenttracker.activity.utils.SessionManager;
import com.app.laqshya.studenttracker.activity.utils.Utils;
import com.app.laqshya.studenttracker.activity.viewmodel.EditSchedulesViewModel;
import com.app.laqshya.studenttracker.databinding.ScheduleViewBinding;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;
import timber.log.Timber;

public class FacultyBatchCardsFragment extends Fragment implements OnFacultyBatchClickListner {
    String selectedBatchId;
    FacultyBatchCardAdapter facultyBatchCardAdapter;
    private ProgressDialog progressDialog;
    ScheduleViewBinding scheduleViewBinding;
    EditSchedulesViewModel editSchedulesViewModel;
    @Inject
    EditSchedulesViewModelFactory editSchedulesViewModelFactory;
    @Inject
    SessionManager sessionManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        scheduleViewBinding = ScheduleViewBinding.inflate(inflater, container, false);
        editSchedulesViewModel = ViewModelProviders.of(this, editSchedulesViewModelFactory).get(EditSchedulesViewModel.class);
        return scheduleViewBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity() != null && Utils.isNetworkConnected(getActivity())) {
            scheduleViewBinding.recyclerViewSyllabus.setLayoutManager(new LinearLayoutManager(getActivity()));
            scheduleViewBinding.recyclerViewSyllabus.setHasFixedSize(false);
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setTitle("Loading");
            progressDialog.setMessage("Please wait");
            progressDialog.show();
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.empty_view, null, false);
            scheduleViewBinding.recyclerViewSyllabus.setEmptyView(view);
            editSchedulesViewModel.getFacultyCardCourses(sessionManager.getLoggedInUserName())
                    .observe(this, facultyCourses -> {
                        progressDialog.dismiss();
                        if (facultyCourses == null || facultyCourses.size() == 0) {
                            showSnackbar(getString(R.string.errorwhilefetchingdata));
                        } else {
                            facultyBatchCardAdapter = new FacultyBatchCardAdapter(this);
                            facultyBatchCardAdapter.setFacultyCourses(facultyCourses);
                            scheduleViewBinding.recyclerViewSyllabus.setAdapter(facultyBatchCardAdapter);
                        }

                    });
        } else {
            showSnackbar(getString(R.string.internet_connection));
        }

    }

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    private void showSnackbar(String message) {
        Snackbar.make(scheduleViewBinding.recyclerViewSyllabus, message, Snackbar.LENGTH_SHORT).show();
    }


    @Override
    public void onClickFacultyCard(String batchid) {
        selectedBatchId = batchid;
        fragmentTransact(new StudentAttendanceByFacultyFragment());

    }

    private void fragmentTransact(Fragment fragment) {
        Timber.d("Fragment Called");
        FragmentManager fragmentManager = getFragmentManager();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.BATCHID, selectedBatchId);
        fragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction;
        if (fragmentManager != null) {
            fragmentTransaction = fragmentManager.beginTransaction()
                    .replace(R.id.frame, fragment).addToBackStack(null);
            fragmentTransaction.commit();
        }
    }
}

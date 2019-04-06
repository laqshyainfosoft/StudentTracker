package com.app.laqshya.studenttracker.activity.fragments;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.app.laqshya.studenttracker.R;
import com.app.laqshya.studenttracker.activity.AddSchedules;
import com.app.laqshya.studenttracker.activity.EditSchedules;
import com.app.laqshya.studenttracker.activity.adapter.CurrentBatchAdapter;
import com.app.laqshya.studenttracker.activity.factory.EditSchedulesViewModelFactory;
import com.app.laqshya.studenttracker.activity.listeners.MyBatchClickListener;
import com.app.laqshya.studenttracker.activity.model.BatchInformationResponse;
import com.app.laqshya.studenttracker.activity.utils.Constants;
import com.app.laqshya.studenttracker.activity.utils.SessionManager;
import com.app.laqshya.studenttracker.activity.utils.Utils;
import com.app.laqshya.studenttracker.activity.viewmodel.EditSchedulesViewModel;
import com.app.laqshya.studenttracker.databinding.FragmentListBatchesBinding;

import java.util.Objects;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

import static android.app.Activity.RESULT_OK;

public class AttendanceFragment extends Fragment implements MyBatchClickListener {
    FragmentListBatchesBinding fragmentListBatchesBinding;
    FloatingActionButton floatingActionButton;
    EditSchedulesViewModel editSchedulesViewModel;
    @Inject
    EditSchedulesViewModelFactory editSchedulesViewModelFactory;
    @Inject

    SessionManager sessionManager;
    CurrentBatchAdapter currentBatchAdapter;
    private static final int REQUEST_CODE = 100;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentListBatchesBinding = FragmentListBatchesBinding.inflate(inflater, container, false);
        floatingActionButton = Objects.requireNonNull(getActivity()).findViewById(R.id.fab_attendance);
        editSchedulesViewModel = ViewModelProviders.of(this, editSchedulesViewModelFactory).get(EditSchedulesViewModel.class);
        return fragmentListBatchesBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        floatingActionButton.hide();
        floatingActionButton.setOnClickListener(v -> startActivity(new Intent(getActivity(), AddSchedules.class)));
        fragmentListBatchesBinding.swifeRefreshAttendanceSchedule.setOnRefreshListener(this::getBatchForUserType);
        getBatchForUserType();


    }

    private void getBatchForUserType() {
        switch (sessionManager.getLoggedInType()) {
            case Constants.COUNSELLOR:
                floatingActionButton.show();
                editSchedulesViewModel.getBatchesForCounsellor(sessionManager.getLoggedInuserCenter())
                        .observe(this, batchInformationResponse -> {
                            fragmentListBatchesBinding.swifeRefreshAttendanceSchedule.setRefreshing(false);
                            if (batchInformationResponse == null || batchInformationResponse.getBatchInformationList() == null
                                    || batchInformationResponse.getThrowable() != null) {
                                Toast.makeText(getActivity(), "Failed to get Batches", Toast.LENGTH_SHORT).show();
                            } else
                                showbatchforcounsellor(batchInformationResponse);

                        });

                break;
//



        }

    }


    private void showbatchforcounsellor(BatchInformationResponse batchInformationResponse) {
//        Timber.d(batchInformationResponse.getBatchInformationList().get(0).getBatchid());
        fragmentListBatchesBinding.recyclerViewAttendance.setLayoutManager(new LinearLayoutManager(getActivity()));
        fragmentListBatchesBinding.recyclerViewAttendance.setHasFixedSize(false);
        if(batchInformationResponse.getBatchInformationList().size()>0) {
            fragmentListBatchesBinding.imageView2Attend.setVisibility(View.GONE);
            fragmentListBatchesBinding.textViewAttend.setVisibility(View.GONE);

             currentBatchAdapter = new CurrentBatchAdapter(getActivity(),this,sessionManager);
            fragmentListBatchesBinding.recyclerViewAttendance.setAdapter(currentBatchAdapter);
            currentBatchAdapter.update(batchInformationResponse.getBatchInformationList());
        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        AndroidSupportInjection.inject(this);
    }


    @Override
    public void OnClick(View view, int position, BatchInformationResponse.BatchInformation batchInformation) {
        String coursename=batchInformation.getCoursename();
        String coursemod=batchInformation.getCourse_module_name();
        String faculty=batchInformation.getFacultyName();
        String date=batchInformation.getStartDate();
        Intent intent=new Intent(getActivity(),EditSchedules.class);
        intent.putExtra(Constants.COURSE_NAME,coursename);
        intent.putExtra(Constants.FACULTY,faculty);
        intent.putExtra(Constants.COURSE_CATEGORY,coursemod);
        intent.putExtra(Constants.BATCHID,batchInformation.getBatchid());
        intent.putExtra(Constants.BATCHSTARTDATE,date);
        intent.putExtra(Constants.LOCATION,sessionManager.getLoggedInuserCenter());
        intent.putExtra(Constants.Phone,batchInformation.getFaculty_id());

        startActivityForResult(intent,REQUEST_CODE);
    }

    @Override
    public void onDelete(int position, String bid, boolean deleteOrComplete) {
        String batchid=bid.substring(5);
        if(deleteOrComplete){
            if(getActivity()!=null && Utils.isNetworkConnected(getActivity())) {
                editSchedulesViewModel.markEditedBatches(batchid, true).observe(this, s -> {
                    Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
                    if (s != null && s.contains("Success")) {
                        currentBatchAdapter.batchChanged(position);
                    }


                });
            }else {
                Toast.makeText(getActivity(), getString(R.string.internet_connection), Toast.LENGTH_SHORT).show();
            }
        }
        else {
            if(getActivity()!=null && Utils.isNetworkConnected(getActivity())) {
                editSchedulesViewModel.markEditedBatches(batchid, false).observe(this, s -> {
                    Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
                    if (s != null && s.contains("Success")) {
                        currentBatchAdapter.batchChanged(position);
                    }

                });
            }
            else {
                Toast.makeText(getActivity(), getString(R.string.internet_connection), Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE){
            if(resultCode==RESULT_OK){
                getBatchForUserType();

            }



        }
    }

}


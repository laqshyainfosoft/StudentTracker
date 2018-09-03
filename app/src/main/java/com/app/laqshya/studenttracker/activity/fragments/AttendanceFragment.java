package com.app.laqshya.studenttracker.activity.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.app.laqshya.studenttracker.R;
import com.app.laqshya.studenttracker.activity.AddSchedules;
import com.app.laqshya.studenttracker.activity.adapter.CurrentBatchAdapter;
import com.app.laqshya.studenttracker.activity.factory.EditSchedulesViewModelFactory;
import com.app.laqshya.studenttracker.activity.model.BatchInformationResponse;
import com.app.laqshya.studenttracker.activity.utils.Constants;
import com.app.laqshya.studenttracker.activity.utils.SessionManager;
import com.app.laqshya.studenttracker.activity.viewmodel.EditSchedulesViewModel;
import com.app.laqshya.studenttracker.databinding.FragmentListBatchesBinding;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

public class AttendanceFragment extends Fragment {
    FragmentListBatchesBinding fragmentListBatchesBinding;
    FloatingActionButton floatingActionButton;
    EditSchedulesViewModel editSchedulesViewModel;
    @Inject
    EditSchedulesViewModelFactory editSchedulesViewModelFactory;
    @Inject
    SessionManager sessionManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentListBatchesBinding = FragmentListBatchesBinding.inflate(inflater, container, false);
        floatingActionButton = getActivity().findViewById(R.id.fab_attendance);
        editSchedulesViewModel = ViewModelProviders.of(this, editSchedulesViewModelFactory).get(EditSchedulesViewModel.class);
        return fragmentListBatchesBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        floatingActionButton.setVisibility(View.VISIBLE);
        floatingActionButton.setOnClickListener(v -> startActivity(new Intent(getActivity(), AddSchedules.class)));
        getBatchForUserType();


    }

    private void getBatchForUserType() {
        switch (sessionManager.getLoggedInType()) {
            case Constants.COUNSELLOR:
                editSchedulesViewModel.getBatchesForCounsellor(sessionManager.getLoggedInuserCenter())
                        .observe(this, batchInformationResponse -> {
                            if (batchInformationResponse == null || batchInformationResponse.getBatchInformationList() == null
                                    || batchInformationResponse.getBatchInformationList().size() == 0 || batchInformationResponse.getThrowable() != null) {
                                Toast.makeText(getActivity(), "Failed to get Batches", Toast.LENGTH_SHORT).show();
                            } else
                                showbatchforcounsellor(batchInformationResponse);

                        });

                break;
        }

    }

    private void showbatchforcounsellor(BatchInformationResponse batchInformationResponse) {
//        List<BatchInformationResponse.BatchInformation> batchInformationListemp=new ArrayList<>();
//        String currentid=batchInformationResponse.getBatchInformationList().get(0).getBatchid();
//        int i=0;
//        while (i<batchInformationResponse.getBatchInformationList().size()){
//            if(!batchInformationResponse.getBatchInformationList().get(i).getBatchid().equalsIgnoreCase(currentid)){
//                i++;
//                currentid=batchInformationResponse.getBatchInformationList().get(i).getBatchid();
//            }
//            BatchInformationResponse.BatchInformation batchInformation=new BatchInformationResponse.BatchInformation();
//            batchInformation.setBatchid(currentid);
//            batchInformation.setFacultyName(batchInformationResponse.getBatchInformationList().get(i).getFacultyName());
//            batchInformationListemp.add(batchInformation);
//
//        }

//        Timber.d(batchInformationResponse.getBatchInformationList().get(0).getBatchid());
        fragmentListBatchesBinding.recyclerViewAttendance.setLayoutManager(new LinearLayoutManager(getActivity()));
        fragmentListBatchesBinding.recyclerViewAttendance.setHasFixedSize(false);
        fragmentListBatchesBinding.imageView2Attend.setVisibility(View.GONE);
        fragmentListBatchesBinding.textViewAttend.setVisibility(View.GONE);

        CurrentBatchAdapter currentBatchAdapter = new CurrentBatchAdapter(getActivity());
        fragmentListBatchesBinding.recyclerViewAttendance.setAdapter(currentBatchAdapter);
        currentBatchAdapter.update(batchInformationResponse.getBatchInformationList());
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        AndroidSupportInjection.inject(this);
    }
}


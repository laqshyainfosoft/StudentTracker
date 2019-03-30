package com.app.laqshya.studenttracker.activity.fragments;

import android.app.ProgressDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.app.laqshya.studenttracker.R;
import com.app.laqshya.studenttracker.activity.adapter.CompleteBatchAdapter;
import com.app.laqshya.studenttracker.activity.factory.EditSchedulesViewModelFactory;
import com.app.laqshya.studenttracker.activity.listeners.MyBatchClickListener;
import com.app.laqshya.studenttracker.activity.model.BatchInformationResponse;
import com.app.laqshya.studenttracker.activity.utils.Constants;
import com.app.laqshya.studenttracker.activity.utils.SessionManager;
import com.app.laqshya.studenttracker.activity.utils.Utils;
import com.app.laqshya.studenttracker.activity.viewmodel.EditSchedulesViewModel;
import com.app.laqshya.studenttracker.databinding.FragmentListBatchesBinding;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

public class DeletedBatchesFragment extends Fragment implements MyBatchClickListener, AdapterView.OnItemSelectedListener {
    FragmentListBatchesBinding fragmentListBatchesBinding;
    FloatingActionButton floatingActionButton;
    EditSchedulesViewModel editSchedulesViewModel;
    @Inject
    EditSchedulesViewModelFactory editSchedulesViewModelFactory;
    @Inject

    SessionManager sessionManager;
    CompleteBatchAdapter currentBatchAdapter;
    private ProgressDialog progressDialog;

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
        fragmentListBatchesBinding.swifeRefreshAttendanceSchedule.setOnRefreshListener(this::getBatchForUserType);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Loading");

        progressDialog.setMessage("Please wait");
        getBatchForUserType();


    }

    private void getBatchForUserType() {
        switch (sessionManager.getLoggedInType()) {
            case Constants.COUNSELLOR:
                if (getActivity() != null && Utils.isNetworkConnected(getActivity())) {
                    progressDialog.show();
                    editSchedulesViewModel.getDeletedBatches(sessionManager.getLoggedInuserCenter())
                            .observe(this, batchInformationResponse -> {
                                progressDialog.dismiss();
                                fragmentListBatchesBinding.swifeRefreshAttendanceSchedule.setRefreshing(false);
                                if (batchInformationResponse == null || batchInformationResponse.getBatchInformationList() == null
                                        || batchInformationResponse.getThrowable() != null) {
                                    Toast.makeText(getActivity(), "Failed to get Batches", Toast.LENGTH_SHORT).show();
                                } else {

                                    showbatchforcounsellor(batchInformationResponse);
                                }


                            });
                } else {
                    showToast(getString(R.string.internet_connection));
                }

                break;
            case Constants.ADMIN:
                if (getActivity() != null && Utils.isNetworkConnected(getActivity())) {
                    progressDialog.show();
                    editSchedulesViewModel.getDeletedBatchesForAdmin()
                            .observe(this, batchInformationResponse -> {
                                progressDialog.dismiss();
                                fragmentListBatchesBinding.swifeRefreshAttendanceSchedule.setRefreshing(false);
                                if (batchInformationResponse == null || batchInformationResponse.getBatchInformationList() == null
                                        || batchInformationResponse.getThrowable() != null) {
                                    Toast.makeText(getActivity(), "Failed to get Batches", Toast.LENGTH_SHORT).show();
                                } else {
                                    Set<String> locations = new LinkedHashSet<>();
                                    for (BatchInformationResponse.BatchInformation batchInformation : batchInformationResponse.getBatchInformationList()) {
                                        locations.add(batchInformation.getCentername());

                                    }

                                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item,
                                            new ArrayList<>(locations));
                                    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    fragmentListBatchesBinding.filterCenters.setAdapter(arrayAdapter);
                                    fragmentListBatchesBinding.filterCenters.setVisibility(View.VISIBLE);
                                    fragmentListBatchesBinding.filterCenters.setOnItemSelectedListener(this);
                                    showbatchforcounsellor(batchInformationResponse);
                                }


                            });
                } else {
                    showToast(getString(R.string.internet_connection));
                }

                break;
        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        AndroidSupportInjection.inject(this);
    }

    private void showbatchforcounsellor(BatchInformationResponse batchInformationResponse) {
//        Timber.d(batchInformationResponse.getBatchInformationList().get(0).getBatchid());
        fragmentListBatchesBinding.recyclerViewAttendance.setLayoutManager(new LinearLayoutManager(getActivity()));
        fragmentListBatchesBinding.recyclerViewAttendance.setHasFixedSize(false);
        if (batchInformationResponse.getBatchInformationList().size() > 0) {
            fragmentListBatchesBinding.imageView2Attend.setVisibility(View.GONE);
            fragmentListBatchesBinding.textViewAttend.setVisibility(View.GONE);

            currentBatchAdapter = new CompleteBatchAdapter(getActivity(), sessionManager);
            fragmentListBatchesBinding.recyclerViewAttendance.setAdapter(currentBatchAdapter);
            currentBatchAdapter.update(batchInformationResponse.getBatchInformationList());
        }

    }

    private void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        currentBatchAdapter.setFilteredList(fragmentListBatchesBinding.filterCenters.getSelectedItem().toString());
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


}




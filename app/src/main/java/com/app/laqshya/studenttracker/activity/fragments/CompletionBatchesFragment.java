package com.app.laqshya.studenttracker.activity.fragments;

import android.app.ProgressDialog;
import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.app.laqshya.studenttracker.R;
import com.app.laqshya.studenttracker.activity.AddSchedules;
import com.app.laqshya.studenttracker.activity.adapter.CompleteBatchAdapter;
import com.app.laqshya.studenttracker.activity.factory.EditSchedulesViewModelFactory;
import com.app.laqshya.studenttracker.activity.model.BatchInformationResponse;
import com.app.laqshya.studenttracker.activity.utils.Constants;
import com.app.laqshya.studenttracker.activity.utils.SessionManager;
import com.app.laqshya.studenttracker.activity.utils.Utils;
import com.app.laqshya.studenttracker.activity.viewmodel.EditSchedulesViewModel;
import com.app.laqshya.studenttracker.databinding.FragmentListBatchesBinding;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

public class CompletionBatchesFragment extends Fragment implements AdapterView.OnItemSelectedListener {
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
        floatingActionButton.setOnClickListener(v -> startActivity(new Intent(getActivity(), AddSchedules.class)));
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
                    editSchedulesViewModel.getCompletedBatches(sessionManager.getLoggedInuserCenter())
                            .observe(this, batchInformationResponse -> {
                                progressDialog.dismiss();
                                fragmentListBatchesBinding.swifeRefreshAttendanceSchedule.setRefreshing(false);
                                if (batchInformationResponse == null || batchInformationResponse.getBatchInformationList() == null
                                        || batchInformationResponse.getThrowable() != null) {
                                    Toast.makeText(getActivity(), "Failed to get Batches", Toast.LENGTH_SHORT).show();
                                } else
                                    showbatchforcounsellor(batchInformationResponse);

                            });
                } else {
                    showToast(getString(R.string.internet_connection));

                }

                break;
            case Constants.ADMIN:
                if (getActivity() != null && Utils.isNetworkConnected(getActivity())) {
                    progressDialog.show();
                    editSchedulesViewModel.getCompletedBatchesForAdmin()
                            .observe(this, batchInformationResponse -> {
                                progressDialog.dismiss();
                                fragmentListBatchesBinding.swifeRefreshAttendanceSchedule.setRefreshing(false);
                                if (batchInformationResponse == null || batchInformationResponse.getBatchInformationList() == null
                                        || batchInformationResponse.getThrowable() != null) {
                                    Toast.makeText(getActivity(), "Failed to get Batches", Toast.LENGTH_SHORT).show();
                                } else {
                                    Set<String> locations=new LinkedHashSet<>();
                                    for (BatchInformationResponse.BatchInformation batchInformation:batchInformationResponse.getBatchInformationList()){
                                        locations.add(batchInformation.getCentername());

                                    }

                                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_dropdown_item,
                                            new ArrayList<>(locations));
                                    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    fragmentListBatchesBinding.filterCenters.setAdapter(arrayAdapter);
                                    fragmentListBatchesBinding.filterCenters.setOnItemSelectedListener(this);
                                    fragmentListBatchesBinding.filterCenters.setVisibility(View.VISIBLE);
                                    showbatchforcounsellor(batchInformationResponse);

                                }


                            });
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

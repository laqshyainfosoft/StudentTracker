package com.app.laqshya.studenttracker.activity.fragments;

import android.app.ProgressDialog;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.app.laqshya.studenttracker.R;
import com.app.laqshya.studenttracker.activity.adapter.AllBatchAdapter;
import com.app.laqshya.studenttracker.activity.factory.EditSchedulesViewModelFactory;
import com.app.laqshya.studenttracker.activity.model.BatchInformationResponse;
import com.app.laqshya.studenttracker.activity.utils.Utils;
import com.app.laqshya.studenttracker.activity.viewmodel.EditSchedulesViewModel;
import com.app.laqshya.studenttracker.databinding.FragmentListBatchesBinding;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

public class ScheduleBatchesFragment extends Fragment implements AdapterView.OnItemSelectedListener {


    @Inject
    EditSchedulesViewModelFactory editSchedulesViewModelFactory;
    private EditSchedulesViewModel editSchedulesViewModel;
    ProgressDialog progressDialog;
    private FragmentListBatchesBinding fragmentListBatchesBinding;
    private AllBatchAdapter allBatchAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentListBatchesBinding = FragmentListBatchesBinding.inflate(inflater, container, false);
        editSchedulesViewModel = ViewModelProviders.of(this, editSchedulesViewModelFactory).get(EditSchedulesViewModel.class);
        fragmentListBatchesBinding.filterCenters.setVisibility(View.VISIBLE);
        Toolbar toolbar=getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Running Batches");
        return fragmentListBatchesBinding.getRoot();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String mSpinnerLabel = parent.getItemAtPosition(position).toString();
        allBatchAdapter.setFilteredList(mSpinnerLabel);

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fragmentListBatchesBinding.swifeRefreshAttendanceSchedule.setOnRefreshListener(this::getBatchForUserType);
        if (getActivity() != null) {
            ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.schedule_batch_details,
                    android.R.layout.simple_spinner_item);
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setTitle("Loading");

            progressDialog.setMessage("Please wait");
            getBatchForUserType();


        }
    }

    private void getBatchForUserType() {


        if (getActivity() != null && Utils.isNetworkConnected(getActivity())) {
            progressDialog.show();
            editSchedulesViewModel.getRunningBatchesForAdmin()
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
                            fragmentListBatchesBinding.filterCenters.setOnItemSelectedListener(this);
                            fragmentListBatchesBinding.filterCenters.setVisibility(View.VISIBLE);
                            showBatchForAdmin(batchInformationResponse);

                        }


                    });
        } else {
            Toast.makeText(getActivity(), getString(R.string.internet_connection), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    private void showBatchForAdmin(BatchInformationResponse batchInformationResponse) {
        fragmentListBatchesBinding.recyclerViewAttendance.setLayoutManager(new LinearLayoutManager(getActivity()));
        fragmentListBatchesBinding.recyclerViewAttendance.setHasFixedSize(false);
        if (batchInformationResponse.getBatchInformationList().size() > 0) {
            fragmentListBatchesBinding.imageView2Attend.setVisibility(View.GONE);
            fragmentListBatchesBinding.textViewAttend.setVisibility(View.GONE);

            allBatchAdapter = new AllBatchAdapter();
            fragmentListBatchesBinding.recyclerViewAttendance.setAdapter(allBatchAdapter);
            allBatchAdapter.setBatchInformationList(batchInformationResponse.getBatchInformationList());
        }


    }
}

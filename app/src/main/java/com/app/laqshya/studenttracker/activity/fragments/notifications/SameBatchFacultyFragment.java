package com.app.laqshya.studenttracker.activity.fragments.notifications;

import android.app.ProgressDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.app.laqshya.studenttracker.R;
import com.app.laqshya.studenttracker.activity.adapter.NotificationBatchAdapter;
import com.app.laqshya.studenttracker.activity.factory.BroadcastViewModelFactory;
import com.app.laqshya.studenttracker.activity.factory.EditSchedulesViewModelFactory;
import com.app.laqshya.studenttracker.activity.model.BatchInformationResponse;
import com.app.laqshya.studenttracker.activity.utils.SessionManager;
import com.app.laqshya.studenttracker.activity.utils.Utils;
import com.app.laqshya.studenttracker.activity.viewmodel.BroadcastViewModel;
import com.app.laqshya.studenttracker.activity.viewmodel.EditSchedulesViewModel;
import com.app.laqshya.studenttracker.databinding.BroadcastBatchBinding;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;
import timber.log.Timber;

public class SameBatchFacultyFragment extends Fragment {
    @Inject
    SessionManager sessionManager;
    @Inject
    EditSchedulesViewModelFactory editSchedulesViewModelFactory;
    BroadcastBatchBinding broadcastBatchBinding;
    NotificationBatchAdapter notificationBatchAdapter;
    EditSchedulesViewModel editSchedulesViewModel;
    BroadcastViewModel broadcastViewModel;
    @Inject
    BroadcastViewModelFactory broadcastViewModelFactory;
    ArrayList<String> bid;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        broadcastBatchBinding =BroadcastBatchBinding.inflate(inflater,container,false);
        editSchedulesViewModel=ViewModelProviders.of(this,editSchedulesViewModelFactory).get(EditSchedulesViewModel.class);
        broadcastViewModel=ViewModelProviders.of(this,broadcastViewModelFactory).get(BroadcastViewModel.class);
        bid=new ArrayList<>();
        return broadcastBatchBinding.getRoot();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(getActivity()!=null){
            Toolbar toolbar=getActivity().findViewById(R.id.toolbar);
            toolbar.setTitle("Send to Batch");
        }
        broadcastBatchBinding.messageBroadcast.setText(getString(R.string.selectbatchmessage));
        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Please Wait");
        progressDialog.setMessage("Getting Batches");
        progressDialog.show();
        editSchedulesViewModel.getBatchesForCounsellorNotification(sessionManager.getLoggedInuserCenter())
                .observe(this, batchInformationResponse -> {
                    progressDialog.dismiss();
                    if (batchInformationResponse == null || batchInformationResponse.getBatchInformationList() == null
                            || batchInformationResponse.getThrowable() != null) {
                        Toast.makeText(getActivity(), "Failed to get Batches", Toast.LENGTH_SHORT).show();
                    } else
                        showbatchforcounsellor(batchInformationResponse);

                });
        broadcastBatchBinding.btnMulSend.setOnClickListener(v -> {

            String title=broadcastBatchBinding.txtMulTitle.getText().toString();
            String message=broadcastBatchBinding.txtMulMessage.getText().toString();
            String batchid = notificationBatchAdapter.getIndexSelected();
            String selectedFacultyId=notificationBatchAdapter.getSelectedFaculty();
            Timber.d(selectedFacultyId);

            if(selectedFacultyId!=null &&  !TextUtils.isEmpty(selectedFacultyId) && !TextUtils.isEmpty(title) && !TextUtils.isEmpty(message) && !batchid.equalsIgnoreCase("0")) {

                progressDialog.setTitle("Please Wait");
                progressDialog.setMessage("Sending Notification");
                progressDialog.show();
                String flag = "0";
                if (broadcastBatchBinding.checkBoxMul.isChecked()) {
                    flag = "1";
                }
                Timber.d("Index %s", notificationBatchAdapter.getIndexSelected());


                String selbatchid = batchid.substring(batchid.lastIndexOf("Batch") + 5);
                if (Utils.isNetworkConnected(getActivity())) {

                    broadcastViewModel.sendSingleBatchNotification(sessionManager.getLoggedInUserName(), selbatchid, title, message,
                            selectedFacultyId,
                            flag).observe(getActivity(), s -> {
                        if (s != null && s.contains("Success")) {
                            Toast.makeText(getActivity(), "Notifications sent successfully", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        } else {
                            Toast.makeText(getActivity(), "Failed to send notification", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }

                    });
                }
                else {
                    Toast.makeText(getActivity(), "Please check you internet connection", Toast.LENGTH_SHORT).show();
                }
            }

            else
            {
                Toast.makeText(getActivity(), "Please fill all fields", Toast.LENGTH_SHORT).show();
            }


        });




    }




    private void showbatchforcounsellor(BatchInformationResponse batchInformationResponse) {
        broadcastBatchBinding.batchListing.setLayoutManager(new LinearLayoutManager(getActivity()));
        broadcastBatchBinding.batchListing.setHasFixedSize(false);
        if (batchInformationResponse.getBatchInformationList().size() > 0) {
            notificationBatchAdapter = new NotificationBatchAdapter(getActivity(), sessionManager);
            broadcastBatchBinding.batchListing.setAdapter(notificationBatchAdapter);
            for (BatchInformationResponse.BatchInformation batchInformation : batchInformationResponse.getBatchInformationList()) {
                bid.add(batchInformation.getBatchid());

            }
            notificationBatchAdapter.update(batchInformationResponse.getBatchInformationList());

        }
    }

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }
}


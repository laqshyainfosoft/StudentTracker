package com.app.laqshya.studenttracker.activity.fragments.notifications;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.app.laqshya.studenttracker.R;
import com.app.laqshya.studenttracker.activity.adapter.NotificationBatchAdapter;
import com.app.laqshya.studenttracker.activity.factory.EditSchedulesViewModelFactory;
import com.app.laqshya.studenttracker.activity.model.BatchInformationResponse;
import com.app.laqshya.studenttracker.activity.utils.SessionManager;
import com.app.laqshya.studenttracker.activity.viewmodel.EditSchedulesViewModel;
import com.app.laqshya.studenttracker.databinding.BroadcastBatchBinding;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

public class SameBatchFragment extends Fragment {
    @Inject
    SessionManager sessionManager;
    @Inject
    EditSchedulesViewModelFactory editSchedulesViewModelFactory;
    BroadcastBatchBinding broadcastBatchBinding;
    NotificationBatchAdapter notificationBatchAdapter;
    EditSchedulesViewModel editSchedulesViewModel;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        broadcastBatchBinding =BroadcastBatchBinding.inflate(inflater,container,false);
        editSchedulesViewModel=ViewModelProviders.of(this,editSchedulesViewModelFactory).get(EditSchedulesViewModel.class);
        return broadcastBatchBinding.getRoot();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        broadcastBatchBinding.messageBroadcast.setText(getString(R.string.selectbatchmessage));
        editSchedulesViewModel.getBatchesForCounsellorNotification(sessionManager.getLoggedInuserCenter())
                .observe(this, batchInformationResponse -> {
                    if (batchInformationResponse == null || batchInformationResponse.getBatchInformationList() == null
                            || batchInformationResponse.getThrowable() != null) {
                        Toast.makeText(getActivity(), "Failed to get Batches", Toast.LENGTH_SHORT).show();
                    } else
                        showbatchforcounsellor(batchInformationResponse);

                });


    }

    private void showbatchforcounsellor(BatchInformationResponse batchInformationResponse) {
        broadcastBatchBinding.batchListing.setLayoutManager(new LinearLayoutManager(getActivity()));
        broadcastBatchBinding.batchListing.setHasFixedSize(false);
        if(batchInformationResponse.getBatchInformationList().size()>0) {
            notificationBatchAdapter = new NotificationBatchAdapter(getActivity(),sessionManager);
            broadcastBatchBinding.batchListing.setAdapter(notificationBatchAdapter);
            notificationBatchAdapter.update(batchInformationResponse.getBatchInformationList());
        }

    }


    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }
}

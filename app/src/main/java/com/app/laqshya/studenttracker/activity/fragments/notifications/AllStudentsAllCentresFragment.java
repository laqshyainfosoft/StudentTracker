package com.app.laqshya.studenttracker.activity.fragments.notifications;

import android.app.ProgressDialog;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.app.laqshya.studenttracker.R;
import com.app.laqshya.studenttracker.activity.factory.BroadcastViewModelFactory;
import com.app.laqshya.studenttracker.activity.factory.EditSchedulesViewModelFactory;
import com.app.laqshya.studenttracker.activity.utils.SessionManager;
import com.app.laqshya.studenttracker.activity.utils.Utils;
import com.app.laqshya.studenttracker.activity.viewmodel.BroadcastViewModel;
import com.app.laqshya.studenttracker.databinding.BroadcastAllBinding;
import com.app.laqshya.studenttracker.databinding.BroadcastSingleStudentBinding;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

public class AllStudentsAllCentresFragment extends Fragment {
    BroadcastAllBinding broadcastAllBinding;
    @Inject
    SessionManager sessionManager;
    BroadcastViewModel broadcastViewModel;
    @Inject
    BroadcastViewModelFactory broadcastViewModelFactory;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        broadcastAllBinding=BroadcastAllBinding.inflate(inflater,container,false);
        return broadcastAllBinding.getRoot();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity() != null) {
            Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
            toolbar.setTitle("Send to All Students");
        }
        broadcastViewModel=ViewModelProviders.of(this,broadcastViewModelFactory).get(BroadcastViewModel.class);
        broadcastAllBinding.btnMulSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getActivity()!=null && Utils.isNetworkConnected(getActivity())){
                    if(!TextUtils.isEmpty(broadcastAllBinding.txtMulMessage.getText().toString()) &&
                            !TextUtils.isEmpty(broadcastAllBinding.txtMulMessage.getText().toString())){
                        ProgressDialog progressDialog = new ProgressDialog(getActivity());
                        progressDialog.setTitle("Please Wait");
                        progressDialog.setMessage("Sending Notification");
                        progressDialog.show();
                        broadcastViewModel.sendAllBatchNotification(sessionManager.getLoggedInUserName(),
                                broadcastAllBinding.txtMulTitle.getText().toString(),broadcastAllBinding.txtMulMessage.getText().toString()).observe(getActivity(), s -> {
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
                        Toast.makeText(getActivity(), "Please fill all fields", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(getActivity(), "Please check your internet connection", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }
}

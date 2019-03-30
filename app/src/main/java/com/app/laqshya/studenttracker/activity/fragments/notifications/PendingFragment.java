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
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.app.laqshya.studenttracker.R;
import com.app.laqshya.studenttracker.activity.factory.BroadcastViewModelFactory;
import com.app.laqshya.studenttracker.activity.factory.EditSchedulesViewModelFactory;
import com.app.laqshya.studenttracker.activity.model.StudentInfo;
import com.app.laqshya.studenttracker.activity.utils.SessionManager;
import com.app.laqshya.studenttracker.activity.utils.Utils;
import com.app.laqshya.studenttracker.activity.viewmodel.BroadcastViewModel;
import com.app.laqshya.studenttracker.activity.viewmodel.EditSchedulesViewModel;
import com.app.laqshya.studenttracker.databinding.PendingFeesNotificationBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;
import timber.log.Timber;

public class PendingFragment extends Fragment {
    PendingFeesNotificationBinding pendingFeesNotificationBinding;
    BroadcastViewModel broadcastViewModel;
    @Inject
    BroadcastViewModelFactory broadcastViewModelFactory;
    @Inject
    SessionManager sessionManager;
    @Inject
    EditSchedulesViewModelFactory editSchedulesViewModelFactory;
    EditSchedulesViewModel editSchedulesViewModel;
    ArrayList<String> phoneList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        pendingFeesNotificationBinding = PendingFeesNotificationBinding.inflate(inflater, container, false);

        return pendingFeesNotificationBinding.getRoot();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        broadcastViewModel = ViewModelProviders.of(this, broadcastViewModelFactory).get(BroadcastViewModel.class);
        editSchedulesViewModel = ViewModelProviders.of(this, editSchedulesViewModelFactory).get(EditSchedulesViewModel.class);
        if (getActivity() != null) {
            Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
            toolbar.setTitle("Send to Pending Student");
            if (Utils.isNetworkConnected(getActivity())) {
                editSchedulesViewModel.getDueFeesStudent().observe(this, studentInfoList -> {
                    if (studentInfoList != null) {
                        if (studentInfoList.getThrowable() != null) {
                            Toast.makeText(getActivity(), "Error while fetching data", Toast.LENGTH_SHORT).show();

                        } else {
                            List<StudentInfo> infoList = studentInfoList.getStudentInfo();
                            ArrayList<String> studentnames = new ArrayList<>();
                            for (int i = 0; i < infoList.size(); i++) {
                                String name = infoList.get(i).getName();
                                studentnames.add(name);

                            }

                            ArrayAdapter<String> namesAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, studentnames);
                            pendingFeesNotificationBinding.spinnerMultiNew.setAdapter(namesAdapter, false, selected -> {
                                phoneList = new ArrayList<>();
                                for (int i = 0; i < namesAdapter.getCount(); i++) {
                                    if (selected[i]) {
                                        phoneList.add(infoList.get(i).getPhone());

                                    }
                                }
                                Timber.d(Arrays.toString(phoneList.toArray()));

                            }, "Please select students with due installments");

                        }
                    }

                });
            } else {
                Toast.makeText(getActivity(), getString(R.string.internet_connection), Toast.LENGTH_SHORT).show();
            }
        }
        pendingFeesNotificationBinding.btnMulSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.isNetworkConnected(getActivity())) {
                    String title = pendingFeesNotificationBinding.txtMulTitle.getText().toString();
                    String message = pendingFeesNotificationBinding.txtMulMessage.getText().toString();
                    if (!Utils.isEmpty(title,message)) {
                        if (phoneList != null && phoneList.size() > 0) {
                            StringBuilder phoneBuilder = new StringBuilder();
                            for (int i = 0; i < phoneList.size(); i++) {
                                phoneBuilder.append(phoneList.get(i)).append("|");
                            }
                            if(phoneBuilder.length()>0) {
                                phoneBuilder.deleteCharAt(phoneBuilder.length() - 1);
                                ProgressDialog progressDialog=new ProgressDialog(getActivity());
                                progressDialog.setMessage("Please wait");
                                progressDialog.setTitle("Sending Notifications");
                                progressDialog.show();
                                broadcastViewModel.sendNotificationtopendingStudents(sessionManager.getLoggedInUserName(),
                                        title, message, phoneBuilder.toString()).observe(getActivity(), new Observer<String>() {
                                    @Override
                                    public void onChanged(@Nullable String s) {
                                        progressDialog.dismiss();
                                        if(s!=null && s.contains("Error")){
                                            Toast.makeText(getActivity(), "Error occured", Toast.LENGTH_SHORT).show();
                                        }
                                        else {
                                            Toast.makeText(getActivity(), "Successfully sent", Toast.LENGTH_SHORT).show();
                                        }


                                    }
                                });
                            }
                            else {
                                Toast.makeText(getActivity(), "Please select atleast one student", Toast.LENGTH_SHORT).show();

                            }
                        } else {
                            Toast.makeText(getActivity(), "Please select atleast one student", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getActivity(), getString(R.string.fieldsEmpty), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), getString(R.string.internet_connection), Toast.LENGTH_SHORT).show();
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

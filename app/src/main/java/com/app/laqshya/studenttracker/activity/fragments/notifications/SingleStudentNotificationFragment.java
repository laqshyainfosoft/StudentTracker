package com.app.laqshya.studenttracker.activity.fragments.notifications;

import android.app.ProgressDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.app.laqshya.studenttracker.R;
import com.app.laqshya.studenttracker.activity.factory.BroadcastViewModelFactory;
import com.app.laqshya.studenttracker.activity.model.StudentInfo;
import com.app.laqshya.studenttracker.activity.utils.SessionManager;
import com.app.laqshya.studenttracker.activity.viewmodel.BroadcastViewModel;
import com.app.laqshya.studenttracker.databinding.BroadcastSingleStudentBinding;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;
import timber.log.Timber;

public class SingleStudentNotificationFragment extends Fragment {
    BroadcastSingleStudentBinding broadcastSingleStudentBinding;
    BroadcastViewModel broadcastViewModel;
    @Inject
    BroadcastViewModelFactory broadcastViewModelFactory;
    @Inject
    SessionManager sessionManager;
    ArrayList<String> phoneNos;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        broadcastSingleStudentBinding=BroadcastSingleStudentBinding.inflate(inflater,container,false);
        broadcastViewModel=ViewModelProviders.of(this,broadcastViewModelFactory).get(BroadcastViewModel.class);
        return broadcastSingleStudentBinding.getRoot();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity() != null) {

                Toolbar toolbar=getActivity().findViewById(R.id.toolbar);
                toolbar.setTitle("Send to Single Student");

            getActivity().findViewById(R.id.bottom_navigation_admin).setVisibility(View.VISIBLE);
            broadcastViewModel.getStudents(sessionManager.getLoggedInuserCenter()).observe(this, new Observer<StudentInfo.StudentInfoList>() {
                @Override
                public void onChanged(@Nullable StudentInfo.StudentInfoList studentInfoList) {
                    if(studentInfoList!=null && studentInfoList.getStudentInfo()!=null)
                    {
                        ArrayList<String> names=new ArrayList<>();
                        phoneNos=new ArrayList<>();
                        for(StudentInfo studentInfo:studentInfoList.getStudentInfo()){
                            String name=studentInfo.getName();
                            names.add(name);
                            phoneNos.add(studentInfo.getPhone());



                        }
                    ArrayAdapter<String> arrayAdapter=new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_dropdown_item,names);

                        broadcastSingleStudentBinding.singleStudent.setPositiveButton("Okay");
                        broadcastSingleStudentBinding.singleStudent.setTitle("Please select one student");
                        broadcastSingleStudentBinding.singleStudent.setAdapter(arrayAdapter);
                        Timber.d("%d",broadcastSingleStudentBinding.singleStudent.getAdapter().getCount());
                    }
                    else {
                        Toast.makeText(getActivity(), "Failed to fetch Students", Toast.LENGTH_SHORT).show();
                    }

                }
            });
            broadcastSingleStudentBinding.btnMulSend.setOnClickListener(v -> {
                int position=broadcastSingleStudentBinding.singleStudent.getSelectedItemPosition();
                String title=broadcastSingleStudentBinding.txtMulTitle.getText().toString();
                String message=broadcastSingleStudentBinding.txtMulMessage.getText().toString();
                if(!TextUtils.isEmpty(title) && !TextUtils.isEmpty(message)) {
                    ProgressDialog progressDialog=new ProgressDialog(getActivity());
                    progressDialog.setTitle("Please Wait");
                    progressDialog.setMessage("Sending Notification");
                    progressDialog.show();
                    String flag="0";
                    if(broadcastSingleStudentBinding.checkBoxMul.isChecked()){
                         flag="1";
                    }

                    broadcastViewModel.sendSingleStudentNotification(sessionManager.getLoggedInUserName(),phoneNos.get(position), title, message,
                            flag).observe(getActivity(), new Observer<String>() {
                        @Override
                        public void onChanged(@Nullable String s) {
                            if(s!=null && s.contains("Success")){
                                Toast.makeText(getActivity(), "Notifications sent successfully", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }
                            else {
                                Toast.makeText(getActivity(), "Failed to send notification", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }

                        }
                    });
                }
                else {
                    Toast.makeText(getActivity(), "Please fill all fields", Toast.LENGTH_SHORT).show();
                }

            });



        }

    }

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);

    }
}


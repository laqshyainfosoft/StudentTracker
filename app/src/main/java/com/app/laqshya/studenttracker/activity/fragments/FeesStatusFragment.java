package com.app.laqshya.studenttracker.activity.fragments;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.appcompat.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.laqshya.studenttracker.R;
import com.app.laqshya.studenttracker.activity.adapter.InstallmentAdapter;
import com.app.laqshya.studenttracker.activity.factory.StudentDetailsFactory;
import com.app.laqshya.studenttracker.activity.model.Installments;
import com.app.laqshya.studenttracker.activity.service.EduTrackerService;
import com.app.laqshya.studenttracker.activity.utils.SessionManager;
import com.app.laqshya.studenttracker.activity.viewmodel.StudentDetailsViewModel;
import com.app.laqshya.studenttracker.databinding.StudentsDetailsBinding;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;


public class FeesStatusFragment extends Fragment {
    StudentsDetailsBinding studentsDetailsBinding;
    @Inject
    StudentDetailsFactory studentDetailsFactory;
    StudentDetailsViewModel studentDetailsViewModel;
    @Inject
    SessionManager sessionManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
     studentsDetailsBinding=StudentsDetailsBinding.inflate(inflater,container,false);
     return studentsDetailsBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Toolbar toolbar=getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Fees Info");

        studentDetailsViewModel=ViewModelProviders.of(this,studentDetailsFactory).get(StudentDetailsViewModel.class);
        studentDetailsViewModel.getStudentDetailsInstallments(sessionManager.getLoggedInUserName())
                .observe(this, installments -> {
                    if(installments!=null && installments.size()>0){
                        studentsDetailsBinding.noOfInstallmentsCollapse.setOnClickListener((v -> {
                            InstallmentAdapter installmentAdapter=new InstallmentAdapter();
                            studentsDetailsBinding.installmentContainer.setAdapter(installmentAdapter);
                            studentsDetailsBinding.installmentContainer.setLayoutManager(new LinearLayoutManager(getActivity()));
                            studentsDetailsBinding.installmentContainer.setHasFixedSize(false);
                            installmentAdapter.setInstallmentsList(installments);
                            studentsDetailsBinding.installmentContainer.setVisibility(View.VISIBLE);
                        }));

                    }
                });
        studentDetailsViewModel.getStudentDetails(sessionManager.getLoggedInUserName())
                .observe(this, studentDetailsModel -> {
                  if(studentDetailsModel!=null) {
                      studentsDetailsBinding.totalFees.setText(studentDetailsModel.getTotalfees());
                      studentsDetailsBinding.downPaymentAmount.setText(studentDetailsModel.getTotaldownpayment());
//

                  }
//                    Timber.d(studentDetailsModel.getStudentfees().toString());

                });
    }

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }
}

package com.app.laqshya.studenttracker.activity.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.app.laqshya.studenttracker.R;
import com.app.laqshya.studenttracker.activity.viewmodel.NavDrawerViewModel;
import com.app.laqshya.studenttracker.databinding.RegisterStudentBinding;

import java.util.Objects;

public class AddStudentFragment extends Fragment {
    RegisterStudentBinding registerStudentBinding;
    NavDrawerViewModel navDrawerViewModel;
    private int noOfInstallmentCount = 0;
    private DatePickerFragment datePickerFragment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        registerStudentBinding = DataBindingUtil.inflate(inflater, R.layout.register_student, container, false);
        return registerStudentBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        datePickerFragment = new DatePickerFragment();
        navDrawerViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(NavDrawerViewModel.class);
        registerStudentBinding.setNavViewmModel(navDrawerViewModel);
        navDrawerViewModel.getCourseList().observe(this, strings -> {
            if (strings != null && strings.size() > 0) {
                ArrayAdapter<String> courses = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item,
                        strings);
                registerStudentBinding.studentCourseSpinner.setAdapter(courses);
            }

        });
        navDrawerViewModel.noOfInstallments.observe(this, integer -> {
            if (integer != null) {
                if (integer > 0) {

                    noOfInstallmentCount = integer;

                }
                manageInstallments(integer);

            }
        });

        navDrawerViewModel.totalFees.observe(this, integer -> {
            if (integer != null) {
                manageInstallments(integer);
            }

        });
        navDrawerViewModel.downPayment.observe(this, integer -> {
            if (integer != null) {
                manageInstallments(integer);
            }

        });


    }

    //This method manages the dynamic installment layout.
    private void manageInstallments(int value) {
        registerStudentBinding.installmentLayout.removeAllViews();
        int amount = 0;
        if (value > 0) {
            registerStudentBinding.installmentLayout.setVisibility(View.VISIBLE);
            if (!registerStudentBinding.inputFees.getText().toString().trim().isEmpty()
                    && !registerStudentBinding.inputDownpayment.getText().toString().trim().isEmpty()
                    && !registerStudentBinding.inputNoOfInstallments.getText().toString().trim().isEmpty()) {
                int fees = Integer.parseInt(registerStudentBinding.inputFees.getText().toString());
                int downPayment = Integer.parseInt(registerStudentBinding.inputDownpayment.getText().toString()
                );

                amount = (fees - downPayment) / noOfInstallmentCount;

            } else {
                registerStudentBinding.installmentLayout.setVisibility(View.GONE);
            }

            for (int i = 0; i < noOfInstallmentCount; i++) {
                View view = LayoutInflater.from(getContext()).inflate(R.layout.installment_layout, null, false);
                registerStudentBinding.installmentLayout.addView(view);
                TextView installmentNumberTextView = view.findViewById(R.id.installmentNumber);
                installmentNumberTextView.setText(String.valueOf(i + 1));
                TextView datePicker = view.findViewById(R.id.installmentDate);
                datePicker.setOnClickListener(v -> {

                    datePickerFragment.show(Objects.requireNonNull(getActivity()).getFragmentManager(), "date_picker");
                    if (datePickerFragment.setDate.hasActiveObservers())
                        datePickerFragment.setDate.removeObservers(this);
                    datePickerFragment.setDate.observe(this, s -> {
                        if (s != null && s.length() > 0) {
                            datePicker.setText(s);
                        }

                    });
                });

                TextView amnt = view.findViewById(R.id.installmentAmount);
                amnt.setText(String.valueOf(amount));

            }

        }
    }


}


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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.app.laqshya.studenttracker.R;
import com.app.laqshya.studenttracker.activity.model.Installments;
import com.app.laqshya.studenttracker.activity.utils.Utils;
import com.app.laqshya.studenttracker.activity.viewmodel.NavDrawerViewModel;
import com.app.laqshya.studenttracker.activity.viewmodel.ValidationViewModel;
import com.app.laqshya.studenttracker.databinding.RegisterStudentBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import timber.log.Timber;

public class AddStudentFragment extends Fragment {
    RegisterStudentBinding registerStudentBinding;
    NavDrawerViewModel navDrawerViewModel;
//    ValidationViewModel validationViewModel;
    private int noOfInstallmentCount = 0;
    private List<Installments> installmentsList;
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
//        validationViewModel = ViewModelProviders.of(this).get(ValidationViewModel.class);
//        registerStudentBinding.setValidation(validationViewModel);
        installmentsList = new ArrayList<>();
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
                if (integer > 0 && integer <= 10) {

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
        registerStudentBinding.btnSignup.setOnClickListener(v -> {
            String name = registerStudentBinding.inputStudentName.getText().toString();
            String phone = registerStudentBinding.inputStudentNumber.getText().toString();
            String email = registerStudentBinding.inputEmail.getText().toString();
            String course = registerStudentBinding.studentCourseSpinner.getSelectedItem().toString();
            String totalFees = registerStudentBinding.inputFees.getText().toString();
            String downPayment = registerStudentBinding.inputDownpayment.getText().toString();
            String installments = registerStudentBinding.inputNoOfInstallments.getText().toString();
            boolean isValid = true;

            if (Utils.isEmpty(name)) {
                registerStudentBinding.inputStudentName.setError(getString(R.string.fieldsEmpty));
                isValid = false;
            }
            if (Utils.isEmpty(phone)) {
                registerStudentBinding.inputStudentNumber.setError(getString(R.string.fieldsEmpty));
                isValid = false;
            }
            if (Utils.isEmpty(email)) {
                registerStudentBinding.inputEmail.setError(getString(R.string.fieldsEmpty));
                isValid = false;
            }
            if (Utils.isEmpty(totalFees)) {
                registerStudentBinding.inputFees.setError(getString(R.string.fieldsEmpty));
                isValid = false;
            }
            if (Utils.isEmpty(downPayment)) {
                registerStudentBinding.inputDownpayment.setError(getString(R.string.fieldsEmpty));
                isValid = false;
            }
            if (Utils.isEmpty(installments)) {
                registerStudentBinding.inputNoOfInstallments.setError(getString(R.string.fieldsEmpty));
                isValid = false;
            }

            //Toast.makeText(getActivity(), getString(R.string.fieldsEmpty), Toast.LENGTH_SHORT).show();
            if (!Utils.isValidEmail(email)) {
//                Toast.makeText(getActivity(), getString(R.string.email_error), Toast.LENGTH_SHORT).show();
                registerStudentBinding.inputEmail.setError(getString(R.string.email_error));
                isValid = false;
            }
            if (!Utils.isValidPhone(phone)) {
//                Toast.makeText(getActivity(), getString(R.string.mobile_error), Toast.LENGTH_SHORT).show();
                registerStudentBinding.inputStudentNumber.setError(getString(R.string.mobile_error));
                isValid = false;
            }

            if (isValid && Utils.isNetworkConnected(getActivity())) {
                navDrawerViewModel.registerStudent(name, phone,
                        email, course, totalFees, downPayment, installments, installmentsList).observe(this, s -> {

                    if (s != null && s.length() > 0) {
                        Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
                        {
                            Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } else if (!Utils.isNetworkConnected(getActivity())) {
                Toast.makeText(getActivity(), "Please check your internet connection", Toast.LENGTH_SHORT).show();

            }


        });


    }

    //This method manages the dynamic installment layout.
    private void manageInstallments(int value) {
        //TODO fix views reset on amounts change.
        registerStudentBinding.installmentLayout.removeAllViews();
        if (installmentsList != null && installmentsList.size() > 0) {
            installmentsList.clear();
        }
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
                int finalI = i;
                int finalAmount = amount;
                datePicker.setOnClickListener(v -> {

                    datePickerFragment.show(Objects.requireNonNull(getActivity()).getFragmentManager(), "date_picker");
                    if (datePickerFragment.setDate.hasActiveObservers())
                        datePickerFragment.setDate.removeObservers(this);
                    datePickerFragment.setDate.setValue("");

                    datePickerFragment.setDate.observe(this, s -> {
                        if (s != null && s.length() > 0) {
                            datePicker.setText(s);
                            int number = finalI + 1;
                            installmentsList.add(new Installments(number, s, String.valueOf(finalAmount)));

                            Timber.d("%d", installmentsList.size());
                        }

                    });
                });

                TextView amnt = view.findViewById(R.id.installmentAmount);
                amnt.setText(String.valueOf(amount));

            }

        }
    }

//    private void setUpObservers() {
//        validationViewModel.errorEmail.observe(this, s -> setErrorMessage(registerStudentBinding.inputEmail, s));
//        validationViewModel.errorPhone.observe(this, s -> setErrorMessage(registerStudentBinding.inputStudentNumber, s));
//        validationViewModel.errorEmptyName.observe(this, s -> setErrorMessage(registerStudentBinding.inputStudentName, s));
//        validationViewModel.errorEmptyFees.observe(this, s -> setErrorMessage(registerStudentBinding.inputFees, s));
//        validationViewModel.errorEmptyDownpayment.observe(this, s -> setErrorMessage(registerStudentBinding.inputDownpayment, s));
//        validationViewModel.errorEmptyInstallments.observe(this, s -> setErrorMessage(registerStudentBinding.inputNoOfInstallments, s));
//
//    }
//
//    private void setErrorMessage(EditText editText, String message) {
//
//        if (message != null && message.length() > 0) {
//            editText.setError(message);
//        }
//    }


}


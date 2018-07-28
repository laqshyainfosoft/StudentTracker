package com.app.laqshya.studenttracker.activity.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.app.laqshya.studenttracker.R;
import com.app.laqshya.studenttracker.activity.model.CourseModuleList;
import com.app.laqshya.studenttracker.activity.model.CoursesStudent;
import com.app.laqshya.studenttracker.activity.model.InstallmentsList;
import com.app.laqshya.studenttracker.activity.utils.Utils;
import com.app.laqshya.studenttracker.activity.viewmodel.NavDrawerViewModel;
import com.app.laqshya.studenttracker.databinding.CourseLayoutBinding;
import com.app.laqshya.studenttracker.databinding.RegisterStudentBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import timber.log.Timber;

public class AddStudentFragment extends Fragment {
    RegisterStudentBinding registerStudentBinding;
    NavDrawerViewModel navDrawerViewModel;
    //    ValidationViewModel validationViewModel;
    CourseLayoutBinding courseLayoutBinding;
    private int noOfInstallmentCount = 0;

    private DatePickerFragment datePickerFragment;
    private CoursesStudent coursesStudent;
    private List<InstallmentsList> installmentsLists;
    private List<CourseModuleList> courseModuleList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        registerStudentBinding = DataBindingUtil.inflate(inflater, R.layout.register_student, container, false);
        return registerStudentBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        installmentsLists = new ArrayList<>();

        datePickerFragment = new DatePickerFragment();
        coursesStudent = new CoursesStudent();
        courseModuleList = new ArrayList<>();
        navDrawerViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(NavDrawerViewModel.class);
        registerStudentBinding.setNavViewmModel(navDrawerViewModel);

        registerStudentBinding.btnSignup.setOnClickListener(v -> {
            String name = registerStudentBinding.inputStudentName.getText().toString().trim();
            String phone = registerStudentBinding.inputStudentNumber.getText().toString().trim();
            String email = registerStudentBinding.inputEmail.getText().toString().trim();
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
//            if (Utils.isEmpty(totalFees)) {
//                registerStudentBinding.inputFees.setError(getString(R.string.fieldsEmpty));
//                isValid = false;
//            }
//            if (Utils.isEmpty(downPayment)) {
//                registerStudentBinding.inputDownpayment.setError(getString(R.string.fieldsEmpty));
//                isValid = false;
//            }
//            if (Utils.isEmpty(installments)) {
//                registerStudentBinding.inputNoOfInstallments.setError(getString(R.string.fieldsEmpty));
//                isValid = false;
//            }

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
                        email).observe(this, s -> {

                    if (s != null && s.length() > 0) {
                        Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
                        {
                            Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
                            manageStudentAdded(s);
                        }
                    }
                });
            } else if (!Utils.isNetworkConnected(getActivity())) {
                Toast.makeText(getActivity(), "Please check your internet connection", Toast.LENGTH_SHORT).show();

            }


        });


    }

    //Manages the courses to be registered for student in the student registration process.
    private void manageStudentAdded(String studentStatus) {
        if (studentStatus.contains("Successfully")) {

            registerStudentBinding.addCourses.setVisibility(View.VISIBLE);
            registerStudentBinding.saveCourses.setVisibility(View.VISIBLE);
            registerStudentBinding.saveCourses.setOnClickListener(v -> {
                saveCourses();
            });
            registerStudentBinding.btnSignup.setVisibility(View.GONE);
            registerStudentBinding.addCourses.setOnClickListener(v -> {
                if (courseLayoutBinding != null) {
                    resetViews();
                }
            });
            courseLayoutBinding = CourseLayoutBinding.inflate(getLayoutInflater(), null, false);
            courseLayoutBinding.setNavViewmModel(navDrawerViewModel);
            registerStudentBinding.coursesFillerlayout.addView(courseLayoutBinding.getRoot());
            navDrawerViewModel.getCourseList().observe(this, strings -> {
                if (strings != null && strings.size() > 0) {
                    ArrayAdapter<String> courses = new ArrayAdapter<>(Objects.requireNonNull(getActivity()), android.R.layout.simple_spinner_dropdown_item,
                            strings);
                    courseLayoutBinding.studentCourseSpinner.setAdapter(courses);
                }

            });
            courseLayoutBinding.studentCourseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String item = parent.getSelectedItem().toString();
                    changeViewState();
                    navDrawerViewModel.getCourseModule(item)
                            .observe(AddStudentFragment.this, strings -> {

                                if (strings != null && strings.size() > 0) {

                                    ArrayAdapter<CourseModuleList> courses = new ArrayAdapter<>(Objects.requireNonNull(getActivity()), android.R.layout.simple_spinner_dropdown_item,
                                            strings);
                                    courseLayoutBinding.studentCourseModuleSpinner.setAdapter(courses, false, selected -> {
                                        if (courseModuleList != null && courseModuleList.size() > 0) {
                                            courseModuleList.clear();
                                        }
                                        for (int i = 0; i < selected.length; i++) {
                                            if (selected[i]) {
                                                CourseModuleList courseModule = new CourseModuleList(
                                                        Objects.requireNonNull(courses.getItem(i)).toString()
                                                );
                                                courseModuleList.add(courseModule);
                                            }
                                        }
                                    }, "Please Select Course Module");
                                }

                            });

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
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


        }
    }

    //This method saves the courses of a registered student alongwith installments and fees details.
    private void saveCourses() {


        //TODO pending validation for incomplete installment sections.
        String totalFees = courseLayoutBinding.inputFees.getText().toString();
        String downPayment = courseLayoutBinding.inputDownpayment.getText().toString();
        String courseName=courseLayoutBinding.studentCourseSpinner.getSelectedItem().toString();
        coursesStudent.setDownpayment(downPayment);
        coursesStudent.setFees(totalFees);
        coursesStudent.setInstallmentsList(installmentsLists);
        coursesStudent.setCourseModule(courseModuleList);
        coursesStudent.setCourseName(courseName);
        coursesStudent.setMobile(registerStudentBinding.inputStudentNumber.getText().toString());
        navDrawerViewModel.registerCourses(coursesStudent).observe(this, s -> {
            if (s != null && !s.isEmpty()) {
                Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
                if (s.contains("Success")) {
                    StringBuilder courseList = new StringBuilder();
                    for (int i = 0; i < courseModuleList.size(); i++) {
                        courseList.append(courseModuleList.get(i).getCourse_name()).append(" ");
                    }
                    courseLayoutBinding.courseDetailViewCounsellor.append(courseList.toString());
                    courseLayoutBinding.courseDetailViewCounsellor.setTextColor(Color.RED);
                }
            }

        });


    }

    //Reset text fields for adding another course.
    private void resetViews() {
        courseLayoutBinding.inputFees.setText("");
        courseLayoutBinding.inputDownpayment.setText("");
        courseLayoutBinding.inputNoOfInstallments.setText("");
        courseLayoutBinding.studentCourseSpinner.setSelection(0);
        courseLayoutBinding.installmentLayout.removeAllViews();
        courseModuleList.clear();

    }

    //Disables the already submitted fields.
    private void changeViewState() {

        registerStudentBinding.inputEmail.setEnabled(false);
        registerStudentBinding.inputStudentName.setEnabled(false);
        registerStudentBinding.inputStudentNumber.setEnabled(false);


    }

    //This method manages the dynamic installment layout.
    private void manageInstallments(int value) {

        //TODO fix views reset on amounts change.


        courseLayoutBinding.installmentLayout.removeAllViews();

        Timber.d("Installment amnt is %d", value);
        if (installmentsLists != null && installmentsLists.size() > 0) {
            installmentsLists.clear();
        }
        int amount = 0;
        if (value > 0) {
            courseLayoutBinding.installmentLayout.setVisibility(View.VISIBLE);
            if (!courseLayoutBinding.inputFees.getText().toString().trim().isEmpty()
                    && !courseLayoutBinding.inputDownpayment.getText().toString().trim().isEmpty()
                    && !courseLayoutBinding.inputNoOfInstallments.getText().toString().trim().isEmpty()) {
                int fees = Integer.parseInt(courseLayoutBinding.inputFees.getText().toString());
                int downPayment = Integer.parseInt(courseLayoutBinding.inputDownpayment.getText().toString()
                );

                amount = (fees - downPayment) / noOfInstallmentCount;

            } else {
                courseLayoutBinding.installmentLayout.setVisibility(View.GONE);
            }

            for (int i = 0; i < noOfInstallmentCount; i++) {
                View view = LayoutInflater.from(getContext()).inflate(R.layout.installment_layout, null, false);
                courseLayoutBinding.installmentLayout.addView(view);
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
                            InstallmentsList installmentsList = new InstallmentsList();
                            installmentsList.setInstallmentAmnt(String.valueOf(finalAmount));
                            installmentsList.setInstallmentDate(s);
                            installmentsList.setInstallmentNo(String.valueOf(number));
                            installmentsLists.add(installmentsList);

//                            Timber.d("%d", installmentsList.size());
                        }

                    });
                });

                TextView amnt = view.findViewById(R.id.installmentAmount);
                amnt.setText(String.valueOf(amount));

            }

        }
    }

}


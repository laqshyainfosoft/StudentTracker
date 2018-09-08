package com.app.laqshya.studenttracker.activity.fragments;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
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
    int currentindex;
    private DatePickerFragment datePickerFragment;
    private CoursesStudent coursesStudent;
    private List<InstallmentsList> installmentsLists;
    private List<CourseModuleList> courseModuleList;
    private boolean[] isEditTextChanged;

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
            else {
                registerStudentBinding.progressBar.setVisibility(View.VISIBLE);
            }
            if (!Utils.isValidPhone(phone)) {
//                Toast.makeText(getActivity(), getString(R.string.mobile_error), Toast.LENGTH_SHORT).show();
                registerStudentBinding.inputStudentNumber.setError(getString(R.string.mobile_error));
                isValid = false;
            }
            else {
                registerStudentBinding.progressBar.setVisibility(View.VISIBLE);
            }
            registerStudentBinding.progressBar.setVisibility(View.INVISIBLE);

            if (isValid && Utils.isNetworkConnected(getActivity())) {
                navDrawerViewModel.registerStudent(name, phone,
                        email).observe(this, s -> {

                    if (s != null && s.length() > 0) {
//                        Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
                        {
                            Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
                            manageStudentAdded(s);
                            registerStudentBinding.progressBar.setVisibility(View.INVISIBLE);

                        }
                    }
                });
            } else if (!Utils.isNetworkConnected(getActivity())) {
                Toast.makeText(getActivity(), "Please check your internet connection", Toast.LENGTH_SHORT).show();
                registerStudentBinding.progressBar.setVisibility(View.INVISIBLE);

            }
        });


    }

    //Manages the courses to be registered for student in the student registration process.
    @SuppressLint("ClickableViewAccessibility")
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
            courseLayoutBinding.inputDownpayment.setOnTouchListener((v, event) -> {
                if(courseLayoutBinding.inputDownpayment.getText().toString().equals("0"))
                courseLayoutBinding.inputDownpayment.setText("");
                return false;
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
//                Timber.d("Downpayment is %d",integer);
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
        String courseName = courseLayoutBinding.studentCourseSpinner.getSelectedItem().toString();
        String installment = courseLayoutBinding.inputNoOfInstallments.getText().toString();
        boolean isValid = true;

        if (Utils.isEmpty(totalFees)) {
            courseLayoutBinding.inputFees.setError(getString(R.string.fieldsEmpty));
            isValid = false;
        }
        if (Utils.isEmpty(downPayment)) {
            courseLayoutBinding.inputDownpayment.setError(getString(R.string.fieldsEmpty));
            isValid = false;
        }

        if (courseModuleList.size() < 1) {
            Toast.makeText(getActivity(), "Please select course modules", Toast.LENGTH_SHORT).show();
            isValid = false;
        }

        if (Utils.isEmpty(installment)) {
            courseLayoutBinding.inputNoOfInstallments.setError(getString(R.string.fieldsEmpty));
            isValid = false;
        }
        Timber.d("Child count is %d", courseLayoutBinding.installmentLayout.getChildCount());

        if (courseLayoutBinding.installmentLayout.getChildCount() != installmentsLists.size()) {


            Toast.makeText(getActivity(), "Please enter all installment details", Toast.LENGTH_SHORT).show();
            isValid = false;
        }


        if (isValid && Utils.isNetworkConnected(getActivity())) {
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
                            courseList.append(courseModuleList.get(i).getCourse_name()).append(", ");
                        }
                        courseLayoutBinding.courseDetailViewCounsellor.append(courseList.toString());
                        courseLayoutBinding.courseDetailViewCounsellor.setTextColor(Color.RED);
                    }
                }

            });
        } else if (!Utils.isNetworkConnected(getActivity())) {
            Toast.makeText(getActivity(), "Please check your internet connection", Toast.LENGTH_SHORT).show();

        }
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
        List<EditText> editTextsList = new ArrayList<>();
        int fees = 0;
        int downPayment = 0;

        //TODO fix views reset on amounts change.


        courseLayoutBinding.installmentLayout.removeAllViews();

        Timber.d("Installment amnt is %d", value);
        if (installmentsLists != null && installmentsLists.size() > 0) {
            installmentsLists.clear();
        }
        final int[] amount = {0};
        if (value > 0) {
            courseLayoutBinding.installmentLayout.setVisibility(View.VISIBLE);
            if (!courseLayoutBinding.inputFees.getText().toString().trim().isEmpty()
                    && !courseLayoutBinding.inputDownpayment.getText().toString().trim().isEmpty()
                    && !courseLayoutBinding.inputNoOfInstallments.getText().toString().trim().isEmpty()) {
                fees = Integer.parseInt(courseLayoutBinding.inputFees.getText().toString());
                downPayment = Integer.parseInt(courseLayoutBinding.inputDownpayment.getText().toString());


                amount[0] = (fees - downPayment) / noOfInstallmentCount;
                Timber.d("");

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
                int finalAmount = amount[0];
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

                        }

                    });
                });

                EditText amnt = view.findViewById(R.id.installmentAmount);



                amnt.setText(String.valueOf(amount[0]));
                if (!editTextsList.contains(amnt)) {
                    editTextsList.add(amnt);
                    isEditTextChanged = new boolean[editTextsList.size()];
                    Timber.d(String.valueOf("Length" + isEditTextChanged.length));
                }
                MyTextWatcher myTextWatcher = new MyTextWatcher(finalI, fees, downPayment, editTextsList, amount, amnt);
                amnt.addTextChangedListener(myTextWatcher);


            }

        }
    }


    private class MyTextWatcher implements TextWatcher {
        int finalI;
        int finalFees;
        int finalDownPayment;
        EditText amnt;

        MyTextWatcher(int finalI, int finalFees, int finalDownPayment, List<EditText> editTextsList, int[] amount
                , EditText amnt) {
            this.finalI = finalI;
            this.finalFees = finalFees;
            this.finalDownPayment = finalDownPayment;
            this.editTextsList = editTextsList;
            this.amnt = amnt;
            this.amount = amount;
        }

        List<EditText> editTextsList;
        int amount[];
        EditText editTextTemp = null;

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            currentindex = finalI;
            Timber.d("Current index is %d",currentindex);
//            int editTextIndex=editTextsList.indexOf(amnt);
            if(isEditTextChanged!=null && isEditTextChanged.length>0) {
//                isEditTextChanged[editTextIndex] = true;
                for (int i = 0; i < isEditTextChanged.length; i++) {
                    Timber.d("Index is %d and value is %s", i, isEditTextChanged[i]);
                }
            }
            Timber.d("On Text Changed Called");
            String s1 = s.toString();

            s1 = s1.replaceAll("^\"|\"$", "").trim();
            if (!s1.isEmpty()) {
                int newAmount = Integer.parseInt(s1);

//                        Toast.makeText(getActivity(), ""+currentindex, Toast.LENGTH_SHORT).show();
//                        Toast.makeText(getActivity(), ""+s.toString(), Toast.LENGTH_SHORT).show();
                if(noOfInstallmentCount > 1) {
                    Timber.d("Values are %d %d %d %d",finalFees,finalDownPayment,newAmount,noOfInstallmentCount);
                    amount[0] = (finalFees - finalDownPayment - newAmount) / (noOfInstallmentCount - 1);
                }

                else if(noOfInstallmentCount==1){
                    amount[0] = (finalFees - finalDownPayment - newAmount) ;
                }
                Timber.d("Size %d", editTextsList.size());

                Toast.makeText(getActivity(), "" + amount[0], Toast.LENGTH_SHORT).show();
                Timber.d("Amount is%s", amount[0]);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
            for (int j = 0; j < editTextsList.size(); j++) {
                if (!editTextsList.get(j).equals(amnt)) {
                    editTextTemp = editTextsList.get(j);
                    Timber.d("Amnt is %d",amount[0]);
                    editTextTemp.removeTextChangedListener(this);
                    if(amnt.isFocused()) {
//                        editTextTemp.setTag("temporary installment");
                        isEditTextChanged[j]=true;
                        editTextTemp.setText(String.valueOf(amount[0]));
                        isEditTextChanged[j]=false;
                    }

                }
            }
//            if(editTextTemp!=null)
        }
    }
}
package com.app.laqshya.studenttracker.activity.fragments;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.app.laqshya.studenttracker.R;
import com.app.laqshya.studenttracker.activity.MainActivity;
import com.app.laqshya.studenttracker.activity.model.CourseModuleList;
import com.app.laqshya.studenttracker.activity.model.CoursesStudent;
import com.app.laqshya.studenttracker.activity.model.Installments;
import com.app.laqshya.studenttracker.activity.model.InstallmentsList;
import com.app.laqshya.studenttracker.activity.utils.Utils;
import com.app.laqshya.studenttracker.activity.viewmodel.NavDrawerViewModel;
import com.app.laqshya.studenttracker.databinding.CourseLayoutBinding;
import com.app.laqshya.studenttracker.databinding.RegisterStudentBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import timber.log.Timber;

public class AddStudentFragment extends Fragment {
    RegisterStudentBinding registerStudentBinding;
    ArrayAdapter<CourseModuleList> courses;
    NavDrawerViewModel navDrawerViewModel;
    CourseLayoutBinding courseLayoutBinding;
    private int noOfInstallmentCount = 0;
    private DatePickerFragment datePickerFragment;
    private CoursesStudent coursesStudent;
    private List<InstallmentsList> installmentsLists;
    private List<CourseModuleList> courseModuleList;
    ProgressDialog progressDialog;


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
                progressDialog=ProgressDialog.show(getActivity(),"Saving Student Details","Please wait");
                showProgressDialog();
                navDrawerViewModel.registerStudent(name, phone,
                        email).observe(this, s -> {
                            hidedialog();
                    registerStudentBinding.progressBar.setVisibility(View.VISIBLE);
                    Timber.d("Visibility is %s", registerStudentBinding.progressBar.getVisibility());

                    if (s != null && s.length() > 0) {
//                        Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
                        {
                            Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
                            manageStudentAdded(s);
                            registerStudentBinding.progressBar.setVisibility(View.INVISIBLE);
                            Timber.d("Visibility is %s", registerStudentBinding.progressBar.getVisibility());

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


            registerStudentBinding.saveCourses.setVisibility(View.VISIBLE);
//            registerStudentBinding.saveCourses.setOnClickListener(v -> {
//                saveCourses();
//            });
            registerStudentBinding.btnSignup.setVisibility(View.GONE);
            registerStudentBinding.saveCourses.setOnClickListener(v -> {

                if (courseLayoutBinding != null) {
                        saveCourses();
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

                                    courses = new ArrayAdapter<>(Objects.requireNonNull(getActivity()), android.R.layout.simple_spinner_dropdown_item,
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
                if (courseLayoutBinding.inputDownpayment.getText().toString().equals("0"))
                    courseLayoutBinding.inputDownpayment.setText("");
                return false;
            });
            courseLayoutBinding.inputNoOfInstallments.setOnTouchListener((v, event) -> {
                if (courseLayoutBinding.inputNoOfInstallments.getText().toString().equals("0"))
                    courseLayoutBinding.inputNoOfInstallments.setText("");
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



        //TODO fix no of installments.
        installmentsLists.clear();

        for (int i=0;i<noOfInstallmentCount;i++){
            View view = courseLayoutBinding.installmentLayout.getChildAt(i);
            TextView installmentNumberTextView = view.findViewById(R.id.installmentNumber);
            TextView datePicker = view.findViewById(R.id.installmentDate);
            EditText amnt = view.findViewById(R.id.installmentAmount);
            InstallmentsList installments=new InstallmentsList();
            installments.setInstallmentAmnt(amnt.getText().toString());
            installments.setInstallmentDate(datePicker.getText().toString());
            installments.setInstallmentNo(installmentNumberTextView.getText().toString());
            installmentsLists.add(installments);
        }


        if (courseLayoutBinding.installmentLayout.getChildCount() != installmentsLists.size()) {


            Toast.makeText(getActivity(), "Please enter all installment details", Toast.LENGTH_SHORT).show();
            isValid = false;
        }
        if (getActivity()!=null && isValid && Utils.isNetworkConnected(getActivity())) {
            coursesStudent.setDownpayment(downPayment);
            coursesStudent.setFees(totalFees);
            coursesStudent.setInstallmentsList(installmentsLists);
            coursesStudent.setCourseModule(courseModuleList);
            coursesStudent.setCourseName(courseName);
            coursesStudent.setMobile(registerStudentBinding.inputStudentNumber.getText().toString());
            progressDialog=ProgressDialog.show(getActivity(),"Saving Fees Details","Please wait");
            showProgressDialog();
            navDrawerViewModel.registerCourses(coursesStudent).observe(this, s -> {
                hidedialog();
                if (s != null && !s.isEmpty()) {

                    if (s.contains("Success")) {
                        Toast.makeText(getActivity(), "Successfully Added Fees and Course", Toast.LENGTH_SHORT).show();
                        resetViews();
                        StringBuilder courseList = new StringBuilder();
                        for (int i = 0; i < courseModuleList.size(); i++) {
                            courseList.append(courseModuleList.get(i).getCourse_name()).append(", ");
                        }
                        courseLayoutBinding.courseDetailViewCounsellor.append(courseList.toString());
                        courseLayoutBinding.courseDetailViewCounsellor.setTextColor(Color.RED);
                    }
                }

            });
        } else if (!Utils.isNetworkConnected(Objects.requireNonNull(getActivity()))) {
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
        boolean[] selectedItems = new boolean[courses.getCount()];
        courseLayoutBinding.studentCourseModuleSpinner.setSelected(selectedItems);
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
        int downPayment=0;

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

                if (noOfInstallmentCount > 0)
                    amount[0] = (fees - downPayment) / noOfInstallmentCount;


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
                EditText amnt = view.findViewById(R.id.installmentAmount);
                int finalAmount = amount[0];
                int finalI1 = i;
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

                            if( installmentsLists.size()> editTextsList.size()-1 && installmentsLists.get(finalI1)!=null){
                                installmentsLists.set(editTextsList.indexOf(amnt),installmentsList);
                            }
                            else {

                                installmentsLists.add(installmentsList);

                            }

                        }

                    });
                });


                Timber.d("amt %s",String.valueOf(amount[0]));
                amnt.setText(String.valueOf(amount[0]));
                if (!editTextsList.contains(amnt)) {
                    editTextsList.add(amnt);

                    Timber.d(String.valueOf("Length" + editTextsList.get(0).getText()));
                }

//                MyTextWatcher myTextWatcher = new MyTextWatcher(finalI, datePicker,installmentNumberTextView,fees, downPayment, editTextsList, amount, amnt);
//                amnt.addTextChangedListener(myTextWatcher);

            }

        }
    }
    private void showProgressDialog(){
        if(progressDialog!=null && !progressDialog.isShowing()){
            progressDialog.show();
        }
    }
    private void hidedialog(){
        if(progressDialog!=null && progressDialog.isShowing()){
            progressDialog.dismiss();
        }
    }

//
//    private class MyTextWatcher implements TextWatcher {
//        int finalI;
//        int finalFees;
//        int finalDownPayment;
//        EditText amnt;
//        List<EditText> editTextsList;
//        int amount[];
//        EditText editTextTemp = null;
//        TextView datePicker;
//        TextView installmentNumberTextView;
//
//        MyTextWatcher(int finalI, TextView datePicker, TextView installmentNumberTextView, int finalFees, int finalDownPayment, List<EditText> editTextsList, int[] amount
//                , EditText amnt) {
//            this.finalI = finalI;
//            this.finalFees = finalFees;
//            this.finalDownPayment = finalDownPayment;
//            this.editTextsList = editTextsList;
//            this.datePicker=datePicker;
//            this.installmentNumberTextView=installmentNumberTextView;
//            this.amnt = amnt;
//            this.amount = amount;
//        }
//
//        @Override
//        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//        }
//
//        @Override
//        public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//
//            currentindex = finalI;
//            Timber.d("Current index is %d", currentindex);
//            int editTextIndex=editTextsList.indexOf(amnt);
//            Timber.d("Edit Index %d",editTextIndex);
////                isEditTextChanged[editTextIndex] = true;
//
//                    if(installmentsLists==null || installmentsLists.size()<editTextsList.size()){
//                           InstallmentsList installmentsList = new InstallmentsList();
//                           Timber.d("Size in loop %s",s.toString());
//                        installmentsList.setInstallmentAmnt(String.valueOf(s.toString()));
//                        installmentsList.setInstallmentDate(datePicker.getText().toString());
//                        installmentsList.setInstallmentNo(String.valueOf(installmentNumberTextView.getText().toString()));
//                        installmentsLists.add(installmentsList);
//
//                    }
//                    else {
//
//                        installmentsLists.get(editTextIndex).setInstallmentAmnt(s.toString());
//
//                    }
//
//                    Timber.d("Size of installments below %d %s",installmentsLists.size(),installmentsLists.get(editTextIndex).getInstallmentAmnt());
//
//
//
//
//
//
//            String s1 = s.toString();
//
//            s1 = s1.replaceAll("^\"|\"$", "").trim();
//            if (!s1.isEmpty()) {
//                int newAmount = Integer.parseInt(s1);
//
//                if (noOfInstallmentCount > 1) {
//                    Timber.d("Values are %d %d %d %d", finalFees, finalDownPayment, newAmount, noOfInstallmentCount);
//                    if(editTextsList.size()<=2) {
//                        amount[0] = (finalFees - finalDownPayment - newAmount) / (noOfInstallmentCount - 1);
//                    }
//                    else {
//
//                    }
//                } else if (noOfInstallmentCount == 1) {
//                    amount[0] = (finalFees - finalDownPayment - newAmount);
//
//                }
//                Timber.d("Size %d", editTextsList.size());
//                Timber.d("Amount is%s", amount[0]);
//
//            }
//        }
//
//        @Override
//        public void afterTextChanged(Editable s) {
//            for (int j = 0; j < editTextsList.size(); j++) {
//                if (!editTextsList.get(j).equals(amnt)) {
//                    editTextTemp = editTextsList.get(j);
//                    Timber.d("Amnt is %d", amount[0]);
//                    amnt.removeTextChangedListener(this);
//                    if (amnt.isFocused()) {
////                        editTextTemp.setTag("temporary installment");
//
//                        editTextTemp.setText(String.valueOf(amount[0]));
//
//                    }
//
//                }
//            }
////            if(editTextTemp!=null)
//        }
//    }
}
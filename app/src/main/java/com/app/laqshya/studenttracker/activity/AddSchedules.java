package com.app.laqshya.studenttracker.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.app.laqshya.studenttracker.R;
import com.app.laqshya.studenttracker.activity.factory.AddScheduleFactory;
import com.app.laqshya.studenttracker.activity.model.BatchDetails;
import com.app.laqshya.studenttracker.activity.model.BatchList;
import com.app.laqshya.studenttracker.activity.model.CourseList;
import com.app.laqshya.studenttracker.activity.model.CourseModuleList;
import com.app.laqshya.studenttracker.activity.model.FacultyList;
import com.app.laqshya.studenttracker.activity.model.StudentInfo;
import com.app.laqshya.studenttracker.activity.model.StudentNames;
import com.app.laqshya.studenttracker.activity.service.EduTrackerService;
import com.app.laqshya.studenttracker.activity.utils.SessionManager;
import com.app.laqshya.studenttracker.activity.utils.Utils;
import com.app.laqshya.studenttracker.activity.viewmodel.AddSchedulesViewModel;
import com.app.laqshya.studenttracker.databinding.ActivityAddSchedulesBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import timber.log.Timber;

public class AddSchedules extends AppCompatActivity {
    @Inject
    EduTrackerService eduTrackerService;
    @Inject
    SessionManager sessionManager;
    @Inject
    AddScheduleFactory addScheduleFactory;
    AddSchedulesViewModel addSchedulesViewModel;
    private ActivityAddSchedulesBinding activityAddSchedulesBinding;
    private ArrayList<View> viewArrayList;
    private List<StudentInfo> studentInfoArrayList;
    private List<StudentNames> studentNamesList;
    private List<String> facultyListMobileNumber;
    private int facultyPosition;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        activityAddSchedulesBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_schedules);
        viewArrayList = new ArrayList<>();
        studentNamesList = new ArrayList<>();
        addSchedulesViewModel = ViewModelProviders.of(this, addScheduleFactory).get(AddSchedulesViewModel.class);
        activityAddSchedulesBinding.txtAtlocation.setText(sessionManager.getLoggedInuserCenter());
        addSchedulesViewModel.getCourseList().observe(this, courseLists -> {
            if (courseLists != null && courseLists.size() > 0) {
                ArrayAdapter<CourseList> courses = new ArrayAdapter<>(this, R.layout.spinner_layout, courseLists);
                activityAddSchedulesBinding.Atcoursename.setAdapter(courses);

            } else {
                showToast("Failed to Fetch courses");
            }

        });
        addSchedulesViewModel.getFacultyList().observe(this, facultyLists -> {
            if (facultyLists != null && facultyLists.size() > 0) {
                facultyListMobileNumber = new ArrayList<>();
                for (FacultyList facultyListItem : facultyLists) {
                    facultyListMobileNumber.add(facultyListItem.getMobile());
                }
                ArrayAdapter<FacultyList> courses = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, facultyLists);
                activityAddSchedulesBinding.Atteacher.setAdapter(courses);


            } else {
                showToast("Failed to fetch teachers");
            }


        });
        activityAddSchedulesBinding.fabsave.setOnClickListener(v -> {

            saveBatch();

        });
        activityAddSchedulesBinding.Atcoursename.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String course = parent.getSelectedItem().toString();
                if (course != null) {
                    addSchedulesViewModel.getCourseModule(course)
                            .observe(AddSchedules.this, courseModuleLists -> {
                                if (courseModuleLists != null && courseModuleLists.size() > 0) {


                                    ArrayAdapter<CourseModuleList> courses = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item,
                                            courseModuleLists);
                                    activityAddSchedulesBinding.studentCourseModuleSpinner.setAdapter(courses);

                                }

                            });

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        activityAddSchedulesBinding.calenderbatchstartdate.setOnClickListener(v ->
        {
            showDatepPicker(activityAddSchedulesBinding.calenderbatchstartdate);
        });
        activityAddSchedulesBinding.addnewschedule.setOnClickListener(v -> addnewSchedule());
        activityAddSchedulesBinding.addstudentbutton.setOnClickListener(v -> {
            if (isDataValid()) {
                showAlertDialog("Are you sure to save the above details?");

            }

        });
        activityAddSchedulesBinding.Atteacher.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                facultyPosition = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private void saveBatch() {
        //TODO add validations before saving.
        //Prepare the json for saving the batch.
        List<BatchList> batchList = new ArrayList<>();
        String course = activityAddSchedulesBinding.Atcoursename.getSelectedItem().toString();
        String courseModule = activityAddSchedulesBinding.studentCourseModuleSpinner.getSelectedItem().toString();

        String fPhone = facultyListMobileNumber.get(facultyPosition);
        Timber.d("Faculty is" + fPhone);
        String location = activityAddSchedulesBinding.txtAtlocation.getText().toString();
        String batchStartDate = activityAddSchedulesBinding.calenderbatchstartdate.getText().toString();
        batchList.clear();



        for (View view : viewArrayList) {
            Spinner spinnerDays = view.findViewById(R.id.spinnerdays);
            Button startTime = view.findViewById(R.id.startTime);
            Button endTime = view.findViewById(R.id.endTime);
            BatchList batchListItem = new BatchList();
            batchListItem.setDay(spinnerDays.getSelectedItem().toString());
            batchListItem.setEndTime(endTime.getText().toString());
            batchListItem.setStartTime(startTime.getText().toString());
            batchList.add(batchListItem);


        }


        if (studentInfoArrayList != null && studentInfoArrayList.size() > 0) {
            for (StudentInfo studentInfo : studentInfoArrayList) {

                StudentNames studentNames = new StudentNames();
                studentNames.setStudentMobile(studentInfo.getPhone());
                studentNamesList.add(studentNames);
                Timber.d("Student list size is %s", studentNamesList.get(0).getStudentMobile());

            }

        }
        Timber.d("Final Student list size is %d", studentNamesList.size());


        BatchDetails batchDetails = new BatchDetails();
        batchDetails.setCenter(location);
        batchDetails.setCourseName(course);
        batchDetails.setCourseModuleName(courseModule);
        batchDetails.setFacultyMobile(fPhone);
        batchDetails.setStartDate(batchStartDate);
        batchDetails.setBatchList(batchList);
        batchDetails.setStudentNames(studentNamesList);
//        Timber.d(batchDetails.getStudentNames().get(0).getStudentMobile());
        if (studentNamesList.size() <= 0) {
            showSnackBar("Please select atleast one student to add to batch");
        } else {

            //pr
//            addSchedulesViewModel.createBatch(batchDetails).observe(this, this::showToast);
            addSchedulesViewModel.createBatch(batchDetails).observe(this, s -> {
                activityAddSchedulesBinding.progressBar.setVisibility(View.VISIBLE);

                if (s != null && s.length() > 0) {
//                        Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
                    {
                        showToast(s);
                        activityAddSchedulesBinding.progressBar.setVisibility(View.INVISIBLE);

                    }
                }



            });

        }


//            batchDetailsList.s
//            batchDetails.setStudentNames(studentInfoArrayList);


//        Timber.d("%s", "The details of student to save batch are as follows:");
//        for (BatchDetails batchDetails : batchDetailsList) {
//            Timber.d(batchDetails.getCenter());
//            Timber.d(batchDetails.getCourseModuleName());
//            Timber.d(batchDetails.getFacultyMobile());
//            Timber.d(batchDetails.getStartDate());
//            Timber.d(batchDetails.getCourseName());
//        }
//        for (BatchList batchList1 : batchList) {
//            Timber.d(batchList1.getDay());
//            Timber.d(batchList1.getEndTime());
//            Timber.d(batchList1.getStartTime());
//        }
//        for (StudentNames studentNames : studentNamesList) {
//            Timber.d(studentNames.getStudentMobile());
//        }


    }

    private void showAlertDialog(String message) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(AddSchedules.this);
        alertBuilder.setMessage(message);
        alertBuilder.setTitle("Confirm?");
        alertBuilder.setPositiveButton("Yes", ((dialog, which) -> {
            getStudentsForBatch();

            dialog.dismiss();
        }));
        alertBuilder.setNegativeButton("No", ((dialog, which) -> {
            dialog.dismiss();
        }));
        alertBuilder.create().show();
    }

    private void getStudentsForBatch() {
        String coursename = activityAddSchedulesBinding.Atcoursename.getSelectedItem().toString();
        String coursemodulename = activityAddSchedulesBinding.studentCourseModuleSpinner.getSelectedItem().toString();
        if (coursename.isEmpty()) {
            showToast("Please select a course first");
        } else if (coursemodulename.isEmpty()) {
            showToast("Please select a course module first");
        } else {
            addSchedulesViewModel.showStudentsForBatch(coursename, coursemodulename).observe(this,
                    studentInfos -> {
                        if (studentInfos != null && studentInfos.size() > 0) {

                            ArrayAdapter<StudentInfo> studentInfoArrayAdapter = new ArrayAdapter<>(AddSchedules.this, android
                                    .R.layout.simple_spinner_dropdown_item, studentInfos);
                            activityAddSchedulesBinding.spinnerMultiNew.setAdapter(studentInfoArrayAdapter, false, selected -> {
                                disableViews();
                                studentInfoArrayList = new ArrayList<>();
                                if (studentInfoArrayList.size() > 0) {
                                    studentInfoArrayList.clear();
                                }
                                for (int i = 0; i < studentInfos.size(); i++) {
                                    if (selected[i]) {

                                        studentInfoArrayList.add(studentInfos.get(i));
                                    }
                                }


                            }, "Please Select Students to add to Batch");

                        } else {
                            showAlertDialogForCourse("No Admissions For This Course Yet!! Please Select a Different Course");


                        }
                    });
        }
    }

    private void showAlertDialogForCourse(String message) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(AddSchedules.this);
        alertBuilder.setMessage(message);
        alertBuilder.setTitle("Alert...");
        alertBuilder.setPositiveButton("Okay", ((dialog, which) -> {
            dialog.dismiss();
        }));

        alertBuilder.create().show();

    }

    private void disableViews() {
        activityAddSchedulesBinding.studentCourseModuleSpinner.setEnabled(false);
        activityAddSchedulesBinding.Atcoursename.setEnabled(false);
        activityAddSchedulesBinding.Atteacher.setEnabled(false);
        activityAddSchedulesBinding.calenderbatchstartdate.setClickable(false);
        activityAddSchedulesBinding.checkW.setEnabled(false);
        activityAddSchedulesBinding.checkR.setEnabled(false);
        activityAddSchedulesBinding.addnewschedule.setClickable(false);
        enableDisableView(activityAddSchedulesBinding.scheduleHolder, false);
        activityAddSchedulesBinding.addstudentbutton.setVisibility(View.INVISIBLE);

    }

    //This method uses the dynamic schedule layout
    private void addnewSchedule() {
        String[] daysArray = getdays();

        List<String> stringList = Arrays.asList(daysArray);

        View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.schedule_details, null);

        activityAddSchedulesBinding.scheduleHolder.addView(view);
        LinearLayout linearLayout = view.findViewById(R.id.schedulesRootLayout);
        viewArrayList.add(linearLayout);
        Timber.d("%d", viewArrayList.size());
        ArrayAdapter<String> dayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,
                stringList);
        Spinner spinnerDays = view.findViewById(R.id.spinnerdays);

        spinnerDays.setAdapter(dayAdapter);
        ImageButton close = view.findViewById(R.id.closelayoutSchedule);
        close.setOnClickListener((v -> {
            Timber.d("Clicked");
            activityAddSchedulesBinding.scheduleHolder.removeView(linearLayout);
            viewArrayList.remove(linearLayout);
            Timber.d("%s", v.getParent().toString());


        }));
        Button startTime = view.findViewById(R.id.startTime);
        Button endTime = view.findViewById(R.id.endTime);
        startTime.setText(getTime());
        endTime.setText(getTime());
        startTime.setOnClickListener(v -> {
            showTimePicker(startTime);
        });
        endTime.setOnClickListener(v -> showTimePicker(endTime));
        activityAddSchedulesBinding.checkR.setOnCheckedChangeListener((buttonView, isChecked) -> {
            activityAddSchedulesBinding.scheduleHolder.removeAllViews();
            viewArrayList.clear();

        });
        activityAddSchedulesBinding.checkW.setOnCheckedChangeListener((buttonView, isChecked) -> {
            activityAddSchedulesBinding.scheduleHolder.removeAllViews();
            viewArrayList.clear();

        });


    }

    private boolean isDataValid() {
        String teachername = null, coursename = null, date = null;
        try {
            teachername = activityAddSchedulesBinding.Atteacher.getSelectedItem().toString();
            coursename = activityAddSchedulesBinding.Atcoursename.getSelectedItem().toString();
            date = activityAddSchedulesBinding.calenderbatchstartdate.getText().toString();
        } catch (NullPointerException exception) {
            Timber.d(exception);

        }
        int childCount = activityAddSchedulesBinding.scheduleHolder.getChildCount();
        Timber.d("%d", activityAddSchedulesBinding.scheduleHolder.getChildCount());
        boolean isValid = true;
        if (!Utils.isNetworkConnected(getApplicationContext())) {
            isValid = false;
            showSnackBar(getString(R.string.internet_connection));
        } else if (teachername == null || teachername.isEmpty()) {
            showSnackBar(getString(R.string.facultyError));
            isValid = false;
        } else if (coursename == null || coursename.isEmpty()) {
            isValid = false;
            showSnackBar(getString(R.string.courseError));
        } else if (childCount <= 0) {
            showSnackBar(getString(R.string.batchError));
            isValid = false;
        } else if (date != null && date.equalsIgnoreCase("DD/MM/YYYY")) {
            showSnackBar(getString(R.string.dateError));
            isValid = false;
        }
        return isValid;

    }

    private String[] getdays() {
        if (activityAddSchedulesBinding.checkR.isChecked()) {
            return getResources().getStringArray(R.array.regularDays);
        } else {
            return getResources().getStringArray(R.array.weekendDays);
        }
    }

    //This method shows the date for batch start.
    private void showDatepPicker(Button v) {
        Calendar localCalendar = Calendar.getInstance();
        int mYear = localCalendar.get(Calendar.YEAR);
        int mMonth = localCalendar.get(Calendar.MONTH);
        int mDay = localCalendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog pickerDialog = new DatePickerDialog(AddSchedules.this, (view, year1, monthOfYear, dayOfMonth) -> {
            String calenderbatchstartdate1 = year1 + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
            Timber.d(calenderbatchstartdate1);
            try {
                Date localDate = new SimpleDateFormat("yyyy-mm-dd", Locale.getDefault()).parse(calenderbatchstartdate1);
                String calenderbatchstartdate = new SimpleDateFormat("yyyy-mm-dd", Locale.getDefault()).format(localDate);
                v.setText(calenderbatchstartdate);
            } catch (ParseException localParseExceptiofieldsEmptyn) {
                Timber.d("I crashed in date picker");
                Timber.d(localParseExceptiofieldsEmptyn);

            }

        }, mYear, mMonth, mDay);
        pickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        pickerDialog.show();
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private String getTime() {
        return new SimpleDateFormat("hh:mm ", Locale.getDefault()).format(new Date());

    }

    private void showTimePicker(Button localbtn) {

        Calendar localCalendar = Calendar.getInstance();

        new TimePickerDialog(AddSchedules.this, (paramAnonymous2TimePicker, paramAnonymous2Int1, paramAnonymous2Int2) -> {
            Calendar local = Calendar.getInstance();
            local.set(Calendar.HOUR_OF_DAY, paramAnonymous2Int1);
            local.set(Calendar.MINUTE, paramAnonymous2Int2);
            String str2 = new SimpleDateFormat("hh:mm  ", Locale.getDefault()).format(local.getTime());
            localbtn.setText(str2);
        }, localCalendar.get(Calendar.HOUR_OF_DAY), localCalendar.get(Calendar.MINUTE), false).show();

    }

    private void showSnackBar(String snackMessage) {
        Snackbar snackbar = Snackbar.make(activityAddSchedulesBinding.content,
                snackMessage, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

    private void enableDisableView(View view, boolean enabled) {
        view.setEnabled(enabled);

        if (view instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) view;

            for (int idx = 0; idx < group.getChildCount(); idx++) {
                enableDisableView(group.getChildAt(idx), enabled);
            }
        }
    }


}

package com.app.laqshya.studenttracker.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.app.laqshya.studenttracker.R;
import com.app.laqshya.studenttracker.activity.factory.AddScheduleFactory;
import com.app.laqshya.studenttracker.activity.model.CourseList;
import com.app.laqshya.studenttracker.activity.model.FacultyList;
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        activityAddSchedulesBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_schedules);
        addSchedulesViewModel = ViewModelProviders.of(this, addScheduleFactory).get(AddSchedulesViewModel.class);
        activityAddSchedulesBinding.txtAtlocation.setText(sessionManager.getLoggedInuserCenter());
        viewArrayList = new ArrayList<>();
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
                ArrayAdapter<FacultyList> courses = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, facultyLists);
                activityAddSchedulesBinding.Atteacher.setAdapter(courses);
            } else {
                showToast("Failed to fetch teachers");
            }


        });
        activityAddSchedulesBinding.calenderbatchstartdate.setOnClickListener(v ->
        {
            showDatepPicker(activityAddSchedulesBinding.calenderbatchstartdate);
        });
        activityAddSchedulesBinding.addnewschedule.setOnClickListener(v -> addnewSchedule());
        activityAddSchedulesBinding.addstudentbutton.setOnClickListener(v -> {
            if (isDataValid()) {
                Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();

            }

        });


    }

    //This method uses the dynamic schedule layout
    private void addnewSchedule() {
        String[] daysArray = getdays();
        viewArrayList = new ArrayList<>();
        List<String> stringList = Arrays.asList(daysArray);

        View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.schedule_details, null);

        activityAddSchedulesBinding.scheduleHolder.addView(view);
        LinearLayout linearLayout = view.findViewById(R.id.schedulesRootLayout);
        viewArrayList.add(linearLayout);
        ArrayAdapter<String> dayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,
                stringList);
        Spinner spinnerDays = view.findViewById(R.id.spinnerdays);

        spinnerDays.setAdapter(dayAdapter);
        ImageButton close = view.findViewById(R.id.closelayoutSchedule);
        close.setOnClickListener((v -> {
            Timber.d("Clicked");
//            activityAddSchedulesBinding.scheduleHolderLayout.removeView(v);

            activityAddSchedulesBinding.scheduleHolder.removeView(linearLayout);
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

        });
        activityAddSchedulesBinding.checkW.setOnCheckedChangeListener((buttonView, isChecked) -> {
            activityAddSchedulesBinding.scheduleHolder.removeAllViews();

        });


    }

    private boolean isDataValid() {
        String teachername=null,coursename=null,date=null;
        try {
             teachername = activityAddSchedulesBinding.Atteacher.getSelectedItem().toString();
             coursename = activityAddSchedulesBinding.Atcoursename.getSelectedItem().toString();
             date = activityAddSchedulesBinding.calenderbatchstartdate.getText().toString();
        }catch (NullPointerException exception){
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
            String calenderbatchstartdate1 = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year1;
            Timber.d(calenderbatchstartdate1);
            try {
                Date localDate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse(calenderbatchstartdate1);
                String calenderbatchstartdate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(localDate);
                v.setText(calenderbatchstartdate);
            } catch (ParseException localParseExceptiofieldsEmptyn) {
                Timber.d("I crashed in date picker");

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


}

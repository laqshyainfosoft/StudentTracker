package com.app.laqshya.studenttracker.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.app.laqshya.studenttracker.activity.factory.EditSchedulesViewModelFactory;
import com.app.laqshya.studenttracker.activity.model.FacultyList;
import com.app.laqshya.studenttracker.activity.utils.Constants;
import com.app.laqshya.studenttracker.activity.viewmodel.EditSchedulesViewModel;
import com.app.laqshya.studenttracker.databinding.EditscheduleBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import timber.log.Timber;

public class EditSchedules extends AppCompatActivity {
    EditscheduleBinding editscheduleBinding;
    EditSchedulesViewModel editSchedulesViewModel;
    @Inject
    EditSchedulesViewModelFactory editSchedulesViewModelFactory;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        editscheduleBinding = DataBindingUtil.setContentView(this, R.layout.editschedule);
        editSchedulesViewModel = ViewModelProviders.of(this, editSchedulesViewModelFactory).get(EditSchedulesViewModel.class);

        editscheduleBinding.myToolbar.setTitle("Edit Batches");
        checkIntent();
        if (isBatchStartChangeable()) {
            editscheduleBinding.calenderbatchstartdate.setOnClickListener(v -> showDatePicker());

        }
        setFaculty();
        getSelectedBatchSchedule();


    }

    private void getSelectedBatchSchedule() {
        String batchid = getIntent().getStringExtra(Constants.BATCHID).substring(5).trim();
        Toast.makeText(this, "" + batchid, Toast.LENGTH_SHORT).show();
        editSchedulesViewModel.getSchedule(batchid).observe(this, editBatchScheduleList -> {
            if (editBatchScheduleList != null) {
                if (editBatchScheduleList.getThrowable() == null) {
                    for (int i=0;i<editBatchScheduleList.getEditbatchScheduleList().size();i++){
                        String startTime=editBatchScheduleList.getEditbatchScheduleList().get(i).getStartTime();
                        String endTime=editBatchScheduleList.getEditbatchScheduleList().get(i).getEndTime();
                        int day=editBatchScheduleList.getEditbatchScheduleList().get(i).getDay();
                        if(day==6 || day==7){
                            editscheduleBinding.checkW.setChecked(true);
                        }
                        addnewSchedule(day,startTime,endTime);
                    }

                }
            }
        });


    }
    private String[] getdays() {
        if (editscheduleBinding.checkR.isChecked()) {
            return getResources().getStringArray(R.array.regularDays);
        } else {
            return getResources().getStringArray(R.array.weekendDays);
        }
    }
    private void addnewSchedule(int day, String startTimer, String endTimer) {
        String[] daysArray = getdays();

        List<String> stringList = Arrays.asList(daysArray);

        View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.schedule_details, null);

        editscheduleBinding.scheduleHolder.addView(view);
        LinearLayout linearLayout = view.findViewById(R.id.schedulesRootLayout);
//        viewArrayList.add(linearLayout);
//        Timber.d("%d", viewArrayList.size());
        ArrayAdapter<String> dayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,
                stringList);
        Spinner spinnerDays = view.findViewById(R.id.spinnerdays);

        spinnerDays.setAdapter(dayAdapter);
        if (daysArray.length>2)
        spinnerDays.setSelection(day-1);
        else spinnerDays.setSelection(day-6);
        ImageButton close = view.findViewById(R.id.closelayoutSchedule);
        close.setOnClickListener((v -> {
            Timber.d("Clicked");
            editscheduleBinding.scheduleHolder.removeView(linearLayout);
//            viewArrayList.remove(linearLayout);
            Timber.d("%s", v.getParent().toString());


        }));
        Button startTime = view.findViewById(R.id.startTime);
        Button endTime = view.findViewById(R.id.endTime);
        startTime.setText(startTimer);
        endTime.setText(endTimer);
        startTime.setOnClickListener(v -> {
            showTimePicker(startTime);
        });
        endTime.setOnClickListener(v -> showTimePicker(endTime));
        editscheduleBinding.checkR.setOnCheckedChangeListener((buttonView, isChecked) -> {
            editscheduleBinding.scheduleHolder.removeAllViews();
//            viewArrayList.clear();

        });
        editscheduleBinding.checkW.setOnCheckedChangeListener((buttonView, isChecked) -> {
            editscheduleBinding.scheduleHolder.removeAllViews();
//            viewArrayList.clear();

        });


    }

    private void setFaculty() {
        editSchedulesViewModel.getFacultyList().observe(this, facultyLists -> {
            if (facultyLists != null) {
//                Toast.makeText(this,""+facultyLists.size(), Toast.LENGTH_SHORT).show();
                ArrayAdapter<FacultyList> facultyListArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, facultyLists);
                editscheduleBinding.Atteacher.setAdapter(facultyListArrayAdapter);

            }

        });

    }

    private void showDatePicker() {
        Calendar localCalendar = Calendar.getInstance();
        int mYear = localCalendar.get(Calendar.YEAR);
        int mMonth = localCalendar.get(Calendar.MONTH);
        int mDay = localCalendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog pickerDialog = new DatePickerDialog(EditSchedules.this, (view, year1, monthOfYear, dayOfMonth) -> {
            String calenderbatchstartdate1 = year1 + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
            Timber.d(calenderbatchstartdate1);
            try {
                Date localDate = new SimpleDateFormat("yyyy-mm-dd", Locale.getDefault()).parse(calenderbatchstartdate1);
                String calenderbatchstartdate = new SimpleDateFormat("yyyy-mm-dd", Locale.getDefault()).format(localDate);
                editscheduleBinding.calenderbatchstartdate.setText(calenderbatchstartdate);
            } catch (ParseException localParseExceptiofieldsEmptyn) {
                Timber.d("I crashed in date picker");
                Timber.d(localParseExceptiofieldsEmptyn);

            }

        }, mYear, mMonth, mDay);
        pickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        pickerDialog.show();
    }

    private boolean isBatchStartChangeable() {
        String date = getTodaysDate();
        String batchDate = editscheduleBinding.calenderbatchstartdate.getText().toString();
        try {
            String myFormatString = "yyyy-MM-dd";
            SimpleDateFormat df = new SimpleDateFormat(myFormatString, Locale.getDefault());
            Date date1 = df.parse(batchDate);
            Date startingDate = df.parse(date);

            return date1.after(startingDate);
        } catch (ParseException e) {

            return false;
        }

    }

    private String getTodaysDate() {
        return new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
    }

    private void checkIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            editscheduleBinding.calenderbatchstartdate.setText(intent.getStringExtra(Constants.BATCHSTARTDATE));
            editscheduleBinding.txtAtlocation.setText(intent.getStringExtra(Constants.LOCATION));
            editscheduleBinding.Atcoursename.setText(intent.getStringExtra(Constants.COURSE_NAME));
            editscheduleBinding.studentCourseModuleSpinner.setText(intent.getStringExtra(Constants.COURSE_CATEGORY));

        }
    }
    private void showTimePicker(Button localbtn) {

        Calendar localCalendar = Calendar.getInstance();

        new TimePickerDialog(EditSchedules.this, (paramAnonymous2TimePicker, paramAnonymous2Int1, paramAnonymous2Int2) -> {
            Calendar local = Calendar.getInstance();
            local.set(Calendar.HOUR_OF_DAY, paramAnonymous2Int1);
            local.set(Calendar.MINUTE, paramAnonymous2Int2);
            String str2 = new SimpleDateFormat("hh:mm  ", Locale.getDefault()).format(local.getTime());
            localbtn.setText(str2);
        }, localCalendar.get(Calendar.HOUR_OF_DAY), localCalendar.get(Calendar.MINUTE), false).show();

    }
}

package com.app.laqshya.studenttracker.activity;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.app.laqshya.studenttracker.R;
import com.app.laqshya.studenttracker.activity.factory.EditSchedulesViewModelFactory;
import com.app.laqshya.studenttracker.activity.model.BatchDetails;
import com.app.laqshya.studenttracker.activity.model.EditBatchScheduleList;
import com.app.laqshya.studenttracker.activity.model.FacultyList;
import com.app.laqshya.studenttracker.activity.model.StudentInfo;
import com.app.laqshya.studenttracker.activity.model.StudentNames;
import com.app.laqshya.studenttracker.activity.utils.Constants;
import com.app.laqshya.studenttracker.activity.viewmodel.EditSchedulesViewModel;
import com.app.laqshya.studenttracker.databinding.EditscheduleBinding;
import com.example.custom_spinner_library.MultiSpinner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import timber.log.Timber;

public class EditSchedules extends AppCompatActivity {

    EditscheduleBinding editscheduleBinding;
    EditSchedulesViewModel editSchedulesViewModel;
    @Inject
    EditSchedulesViewModelFactory editSchedulesViewModelFactory;
    boolean[] selectedItems;
    ArrayAdapter<StudentInfo> studentInfoArrayAdapter = null;
    private String fPhone, coursename, coursemodulename;
    private List<StudentInfo> studentInfoArrayList;
    private int[] fieldsChanged;
    private int facultyInitialIndex = 0;
    private EditBatchScheduleList editBatchScheduleList;
    private boolean isRadioButtonSwitched;
//    private List<StudentInfo> tempStudenList;
//    private List<StudentNames> studentNamesList;
    private List<FacultyList> facultyLists;
    Set<String> allSchedules=new HashSet<>();
    Set<String> modifiedSchedules=new HashSet<>();
    Set<String> deletedSchedules=new HashSet<>();
    private List<EditSchedules.EditScheduleInsertion> scheduleInsertionList;
    private int flagSwitched=0;
    private int resultCode=0;


//    private MultiSpinner.MultiSpinnerListener multiSpinnerListener = new MultiSpinner.MultiSpinnerListener() {
//        @Override
//        public void onItemsSelected(boolean[] selected) {
//
////            studentInfoArrayList = new ArrayList<>();
//            tempStudenList.clear();
//
//            ;
//            Timber.d("Temp list size is %d", tempStudenList.size());
//            for (int i = 0; i < studentInfoArrayList.size(); i++) {
//                if (selected[i]) {
//                    tempStudenList.add(studentInfoArrayList.get(i));
//
//                    Timber.d("Size of list  is%s", String.valueOf(studentInfoArrayList.size()));
//                }
//            }
//
//
//        }

        ;
//
//        @Override
//        public void onDropoutStudent(int which, int flag_dropout) {
//            Toast.makeText(EditSchedules.this, "Hey Dropped", Toast.LENGTH_SHORT).show();
////            mSelected[which1] = isChecked;
//
//            if (studentInfoArrayList.get(which).getMarker() == 1 && tempStudenList.size() > 0) {
//
//
//                AlertDialog.Builder builder = new AlertDialog.Builder(EditSchedules.this);
//                builder.setTitle("Do you wish to remove this student?");
//                builder.setMessage("Please select desired option:");
//                builder.setCancelable(false);
//                builder.setNeutralButton("Cancel", (dialog, which1) -> {
////                        mListener.onDropoutStudent(which1,2,"");
//                    dialog.dismiss();
//
////                    editscheduleBinding.spinnerMultiNew.dismissDialog();
//
//                    if (studentInfoArrayAdapter != null) {
//
//                        selectedItems[which] = true;
//                        Toast.makeText(EditSchedules.this, "" + selectedItems[which] + which, Toast.LENGTH_SHORT).show();
////                        editscheduleBinding.spinnerMultiNew.dismissSpinner();
////                        editscheduleBinding.spinnerMultiNew.setSelected(selectedItems);
////                        editscheduleBinding.spinnerMultiNew.changespinner();
//
//
//                    }
//
//
//                    for (boolean selectedItem : selectedItems) {
//
//                        Timber.d(String.valueOf(selectedItem));
//                    }
//
//
//                });
//                builder.setNegativeButton("Change Batch", (dialog, which12) -> {
////                        mListener.onDropoutStudent(which1,0,"");
//                    selectedItems[which] = false;
//                    fieldsChanged[2] = 1;
////                        dialog1.dismiss();
//
//                    dialog.dismiss();
//                    for (boolean selectedItem : selectedItems) {
//                        Timber.d(String.valueOf(selectedItem));
//                    }
//
//                });
//                builder.setPositiveButton("Dropout Student", (dialog, which13) -> {
//                    //EditSchedules.this
////                        dialog1.dismiss();
//                    selectedItems[which] = false;
//                    fieldsChanged[2] = 1;
//                    dialog.dismiss();
//                    for (boolean selectedItem : selectedItems) {
//                        Timber.d(String.valueOf(selectedItem));
//                    }
//
//
//                });
//                builder.show();
//            }
//
//        }
//
//
//    };
    private ArrayList<View> viewArrayList;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
//        studentNamesList = new ArrayList<>();
        scheduleInsertionList=new ArrayList<>();
        editscheduleBinding = DataBindingUtil.setContentView(this, R.layout.editschedule);
        editSchedulesViewModel = ViewModelProviders.of(this, editSchedulesViewModelFactory).get(EditSchedulesViewModel.class);

        editscheduleBinding.myToolbar.setTitle("Edit Batches");
        fieldsChanged = new int[3];
        checkIntent();
//        if (isBatchStartChangeable()) {
//            editscheduleBinding.calenderbatchstartdate.setOnClickListener(v -> showDatePicker());
//
//        }
        setFaculty();
        getSelectedBatchSchedule();
//        setStudents();
        viewArrayList = new ArrayList<>();


        editscheduleBinding.addnewschedule.setOnClickListener(v -> addnewSchedule());
        editscheduleBinding.fabsave.setOnClickListener(v -> {
            updateData();
        });


    }

    private void updateData() {
        List<EditBatchScheduleList.EditbatchSchedule> batchList = new ArrayList<>();
        int spinnerFacultyIndex = editscheduleBinding.Atteacher.getSelectedItemPosition();
        Timber.d(Arrays.toString(scheduleInsertionList.toArray()));

        StringBuilder startTimeBuilder = new StringBuilder();
        StringBuilder endTimeBuilder = new StringBuilder();
        StringBuilder dayBuilder = new StringBuilder();
        Timber.d(Arrays.toString(deletedSchedules.toArray()));
        int index = 0;


//        String oldday, newday, oldstime, newstime, oldetime, newetime;
//        boolean schedulehasChanged = false;
//        Toast.makeText(this, "List Size is"+viewArrayList.size(), Toast.LENGTH_SHORT).show();

        for (View view : viewArrayList) {
            Spinner spinnerDays = view.findViewById(R.id.spinnerdays);
            Button startTime = view.findViewById(R.id.startTime);
            Button endTime = view.findViewById(R.id.endTime);

//
            EditBatchScheduleList.EditbatchSchedule editbatchSchedule = new EditBatchScheduleList.EditbatchSchedule();

            editbatchSchedule.setDay(spinnerDays.getSelectedItem().toString());
            editbatchSchedule.setEndTime(endTime.getText().toString());
            editbatchSchedule.setStartTime(startTime.getText().toString());

            //TODO fix schedule Ids in proper sequence.
            if (editBatchScheduleList != null && editBatchScheduleList.getEditbatchScheduleList().size() > index) {
                EditBatchScheduleList.EditbatchSchedule temporarySchedule = editBatchScheduleList.getEditbatchScheduleList().get(index);
                if (modifiedSchedules.contains(temporarySchedule.getScheduleId())) {
                    startTimeBuilder.append(startTime.getText().toString()).append("|");
                    endTimeBuilder.append(endTime.getText().toString()).append("|");
                    dayBuilder.append(getDayid(spinnerDays.getSelectedItem().toString())).append("|");
                }
                index++;
            }


            Timber.d("Schedule ID %s", editbatchSchedule.getScheduleId());

//            batchList.add(editbatchSchedule);

        }
        if (startTimeBuilder.length() > 0)
            startTimeBuilder.deleteCharAt(startTimeBuilder.length() - 1);
        if (dayBuilder.length() > 0)
            dayBuilder.deleteCharAt(dayBuilder.length() - 1);
        if (endTimeBuilder.length() > 0)
            endTimeBuilder.deleteCharAt(endTimeBuilder.length() - 1);
        EditBatchScheduleList editBatchScheduleListTemp = new EditBatchScheduleList(batchList);
        Timber.d(String.valueOf(editBatchScheduleListTemp.getEditbatchScheduleList().size()));


//        List<String> stringList = Arrays.asList(daysArray);


        if (isRadioButtonSwitched) {
            fieldsChanged[1] = 1;

        } else {


            if (editBatchScheduleList != null && editBatchScheduleListTemp.getEditbatchScheduleList().size() != this.editBatchScheduleList.getEditbatchScheduleList().size()) {
                fieldsChanged[1] = 1;

            } else {
                fieldsChanged[1] = 0;
            }

//            else if (schedulehasChanged) {
//                fieldsChanged[1] = 1;
//                Timber.d("Second ");
//            }
        }
        if (facultyInitialIndex != spinnerFacultyIndex) {
            fieldsChanged[0] = 1;

        } else {
            fieldsChanged[0] = 0;
        }
        Timber.d(String.valueOf("index" + fieldsChanged[0]));

//        for (int i = 0; i < editBatchScheduleListTemp.getEditbatchScheduleList().size(); i++) {
//
//            editBatchScheduleList.getEditbatchScheduleList().get(i).setDay(indexArray[i]);
//            //TODO fix days null.
//            Timber.d("Days in list are:%s", editBatchScheduleList.getEditbatchScheduleList().get(i).getDay());
//        }


//        Timber.d("List of students is %s", Arrays.toString(tempStudenList.toArray()));
//        Timber.d("Boolean array is %s", Arrays.toString(selectedItems));
//        int truelength = 0;
//        for (boolean issel : selectedItems) {
//            Timber.d(String.valueOf(issel));
//            if (issel) {
//                truelength++;
//            }
//
//        }

//        if (studentInfoArrayList != null && studentInfoArrayList.size() > 0) {
//
//            studentNamesList.clear();
//
//            for (StudentInfo studentInfo : tempStudenList) {
//
//                StudentNames studentNames = new StudentNames();
//                studentNames.setStudentMobile(studentInfo.getPhone());
//                studentNamesList.add(studentNames);
//                Timber.d("Loop is%s", studentInfo.getName());
//
//
//            }
//
//        }
//        int markerLength = 0;
//        for (int i = 0; i < tempStudenList.size(); i++) {
//            if (tempStudenList.get(i).getMarker() == 1) {
//                markerLength++;
//            }
//        }
//        if (fieldsChanged[2] == 1 || markerLength != truelength) {
//            fieldsChanged[2] = 1;
//        } else {
//            fieldsChanged[2] = 0;
//        }

//        Toast.makeText(this, "" + fieldsChanged[0] + " " + fieldsChanged[1] +
//                "" + fieldsChanged[2], Toast.LENGTH_SHORT).show();
        BatchDetails batchDetails = new BatchDetails();
        batchDetails.setFacultyMobile(facultyLists.get(spinnerFacultyIndex).getMobile());
        batchDetails.setBatchList(batchList);
//        batchDetails.setStudentNames(studentNamesList);
        String bid = getIntent().getStringExtra(Constants.BATCHID).substring(5).trim();
        batchDetails.setBid(bid);

        StringBuilder deletedBuilder = new StringBuilder();
        for (String s : deletedSchedules) {
            deletedBuilder.append(s).append("|");
        }

        if (deletedBuilder.length() > 0) {
            deletedBuilder.deleteCharAt(deletedBuilder.length() - 1);
            editSchedulesViewModel.deleteBatches(deletedBuilder.toString())
                    .observe(this, s -> {
                        Toast.makeText(EditSchedules.this, s, Toast.LENGTH_SHORT).show();
                        if (s != null) {
                            setResultCode(s);
                        }

                    });
//        editSchedulesViewModel.editBatches(batchDetails).observe(this, s -> {
//            Toast.makeText(this, "" + s, Toast.LENGTH_SHORT).show();
//
//        });
        }
        StringBuilder modifiedbuilder = new StringBuilder();
        for (String s : modifiedSchedules) {
            modifiedbuilder.append(s).append("|");
        }

        if (modifiedbuilder.length() > 0) {
//            Timber.d("I am in faculty");
            modifiedbuilder.deleteCharAt(modifiedbuilder.length() - 1);
        }
        editSchedulesViewModel.modifyBatches(modifiedbuilder.toString(), facultyLists.get(spinnerFacultyIndex).getMobile(), startTimeBuilder.toString(), endTimeBuilder.toString(), dayBuilder.toString(), bid).observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                Toast.makeText(EditSchedules.this, s, Toast.LENGTH_SHORT).show();
                Timber.d(s);
                if (s != null) {
                    setResultCode(s);
                }

            }
        });

        StringBuilder newSchedulesstarttime = new StringBuilder();
        StringBuilder newSchedulesendtime = new StringBuilder();
        StringBuilder newSchedulesdayidtime = new StringBuilder();

        for (int i = 0; i < scheduleInsertionList.size(); i++) {
            EditScheduleInsertion editScheduleInsertion = scheduleInsertionList.get(i);
            String stime = editScheduleInsertion.startTime;
            String etime = editScheduleInsertion.endTime;
            String dayid = editScheduleInsertion.day_id;
            newSchedulesdayidtime.append(dayid).append("|");
            newSchedulesstarttime.append(stime).append("|");
            newSchedulesendtime.append(etime).append("|");

        }
        if (newSchedulesdayidtime.length() > 0)
            newSchedulesdayidtime.deleteCharAt(newSchedulesdayidtime.length() - 1);
        if (newSchedulesendtime.length() > 0)
            newSchedulesendtime.deleteCharAt(newSchedulesendtime.length() - 1);
        if (newSchedulesstarttime.length() > 0)
            newSchedulesstarttime.deleteCharAt(newSchedulesstarttime.length() - 1);
        if (newSchedulesdayidtime.length() > 0 && newSchedulesendtime.length() > 0 && newSchedulesstarttime.length() > 0) {
            editSchedulesViewModel.insertEditedBatches(newSchedulesstarttime.toString(), newSchedulesendtime.toString(), newSchedulesdayidtime.toString(),
                    bid, flagSwitched).observe(this, s -> {
                Toast.makeText(EditSchedules.this, s, Toast.LENGTH_SHORT).show();
                if (s != null) {
                    setResultCode(s);
                }
            });


        }
    }

    private void setResultCode(String s) {
        if(s.contains("Success")){
            resultCode=1;
        }
    }

//    private void setStudents() {
//        editSchedulesViewModel.getStudents(coursename, coursemodulename).observe(this, studentInfos -> {
//
//            studentInfoArrayList = studentInfos;
//            if (studentInfos != null && studentInfos.size() > 0) {
//
//
//                studentInfoArrayAdapter = new ArrayAdapter<>(EditSchedules.this, android
//                        .R.layout.simple_spinner_dropdown_item, studentInfos);
//                tempStudenList = new ArrayList<>();
//                tempStudenList.addAll(studentInfos);
//
//                for (int i = 0; i < tempStudenList.size(); i++) {
//
//                    System.out.println(i);
//                    Timber.d("Names of students: %s%s", tempStudenList.get(i).getName(), tempStudenList.size());
//                }
//
//
////                editscheduleBinding.spinnerMultiNew.setAdapter(studentInfoArrayAdapter, false, multiSpinnerListener);
//
//
//            }
//            if (studentInfos != null) {
//                if (studentInfoArrayAdapter != null) {
//
//                    selectedItems = new boolean[studentInfoArrayAdapter.getCount()];
//                    for (int ii = 0; ii < studentInfoArrayAdapter.getCount(); ii++) {
//
//                        if (studentInfos.get(ii).getMarker() == 1) {
//                            selectedItems[ii] = true;
//
//
//                        }
//                    }
////                    editscheduleBinding.spinnerMultiNew.setSelected(selectedItems);
//
//                }
//
//            }
//
//        });
//    }

    private void getSelectedBatchSchedule() {
        String batchid = getIntent().getStringExtra(Constants.BATCHID).substring(5).trim();
//        Toast.makeText(this, "" + batchid, Toast.LENGTH_SHORT).show();
        editSchedulesViewModel.getSchedule(batchid).observe(this, editBatchScheduleList -> {
            if (editBatchScheduleList != null) {
                if (editBatchScheduleList.getThrowable() == null) {
                    for (int i = 0; i < editBatchScheduleList.getEditbatchScheduleList().size(); i++) {
                        this.editBatchScheduleList = editBatchScheduleList;
                        String startTime = editBatchScheduleList.getEditbatchScheduleList().get(i).getStartTime();
                        String endTime = editBatchScheduleList.getEditbatchScheduleList().get(i).getEndTime();
                        String day = editBatchScheduleList.getEditbatchScheduleList().get(i).getDay();
                        String sid=editBatchScheduleList.getEditbatchScheduleList().get(i).getScheduleId();
                        if (day.equals("6") || day.equals("7")) {
                            editscheduleBinding.checkW.setChecked(true);
                        }
                        addnewSchedule(sid,day, startTime, endTime);

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


    private void addnewSchedule(String scheduleId,String day, String startTimer, String endTimer) {
        String[] daysArray = getdays();
        allSchedules.add(scheduleId);
        final boolean[] itemSelectedAuto = {false};




        List<String> stringList = Arrays.asList(daysArray);

        View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.schedule_details, null);

        editscheduleBinding.scheduleHolder.addView(view);
        LinearLayout linearLayout = view.findViewById(R.id.schedulesRootLayout);
        viewArrayList.add(linearLayout);
//        Timber.d("%d", viewArrayList.size());
        ArrayAdapter<String> dayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,
                stringList);
        Spinner spinnerDays = view.findViewById(R.id.spinnerdays);

        spinnerDays.setAdapter(dayAdapter);
        if (daysArray.length > 2) {
            spinnerDays.setSelection(Integer.parseInt(day) - 1);
        } else spinnerDays.setSelection(Integer.parseInt(day) - 6);
       spinnerDays.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               if(itemSelectedAuto[0]) {
                   modifiedSchedules.add(scheduleId);
                   deletedSchedules.remove(scheduleId);
               }
               else {
                   itemSelectedAuto[0] =true;
               }
           }

           @Override
           public void onNothingSelected(AdapterView<?> parent) {

           }
       });
        ImageButton close = view.findViewById(R.id.closelayoutSchedule);
        Button startTime = view.findViewById(R.id.startTime);
        Button endTime = view.findViewById(R.id.endTime);
        startTime.setText(startTimer);
        endTime.setText(endTimer);
        startTime.setOnClickListener(v -> {

            modifiedSchedules.add(scheduleId);
            deletedSchedules.remove(scheduleId);
            showTimePicker(startTime);
        });
        close.setOnClickListener((v -> {
            Timber.d("Clicked");
            editscheduleBinding.scheduleHolder.removeView(linearLayout);
            viewArrayList.remove(linearLayout);
            Timber.d("%s", v.getParent().toString());
            modifiedSchedules.remove(scheduleId);
            deletedSchedules.add(scheduleId);


        }));

        endTime.setOnClickListener(v -> {
            showTimePicker(endTime);
            deletedSchedules.remove(scheduleId);
            modifiedSchedules.add(scheduleId);
        });
        editscheduleBinding.checkR.setOnCheckedChangeListener((buttonView, isChecked) -> {
            editscheduleBinding.scheduleHolder.removeAllViews();
            viewArrayList.clear();
            isRadioButtonSwitched = true;
            flagSwitched=1;

        });
        editscheduleBinding.checkW.setOnCheckedChangeListener((buttonView, isChecked) -> {
            editscheduleBinding.scheduleHolder.removeAllViews();
            viewArrayList.clear();
            isRadioButtonSwitched = true;
            flagSwitched=1;

        });


    }

    private void setFaculty() {
        editSchedulesViewModel.getFacultyList().observe(this, facultyLists -> {
            if (facultyLists != null) {
//                Toast.makeText(this,""+facultyLists.size(), Toast.LENGTH_SHORT).show();
                ArrayAdapter<FacultyList> facultyListArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, facultyLists);
                editscheduleBinding.Atteacher.setAdapter(facultyListArrayAdapter);
                this.facultyLists = facultyLists;
                for (int i = 0; i < facultyLists.size(); i++) {
                    if (facultyLists.get(i).getMobile().equalsIgnoreCase(fPhone)) {
                        editscheduleBinding.Atteacher.setSelection(i);
                        facultyInitialIndex = i;
                        break;
                    }
                }

            }

        });

    }

//    private void showDatePicker() {
//        Calendar localCalendar = Calendar.getInstance();
//        int mYear = localCalendar.get(Calendar.YEAR);
//        int mMonth = localCalendar.get(Calendar.MONTH);
//        int mDay = localCalendar.get(Calendar.DAY_OF_MONTH);
//        DatePickerDialog pickerDialog = new DatePickerDialog(EditSchedules.this, (view, year1, monthOfYear, dayOfMonth) -> {
//            String calenderbatchstartdate1 = year1 + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
//            Timber.d(calenderbatchstartdate1);
//            try {
//                Date localDate = new SimpleDateFormat("yyyy-mm-dd", Locale.getDefault()).parse(calenderbatchstartdate1);
//                String calenderbatchstartdate = new SimpleDateFormat("yyyy-mm-dd", Locale.getDefault()).format(localDate);
//                editscheduleBinding.calenderbatchstartdate.setText(calenderbatchstartdate);
//            } catch (ParseException localParseExceptiofieldsEmptyn) {
//                Timber.d("I crashed in date picker");
//                Timber.d(localParseExceptiofieldsEmptyn);
//
//            }
//
//        }, mYear, mMonth, mDay);
//        pickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
//        pickerDialog.show();
//    }

//    private boolean isBatchStartChangeable() {
//        String date = getTodaysDate();
//        String batchDate = editscheduleBinding.calenderbatchstartdate.getText().toString();
//        try {
//            String myFormatString = "yyyy-MM-dd";
//            SimpleDateFormat df = new SimpleDateFormat(myFormatString, Locale.getDefault());
//            Date date1 = df.parse(batchDate);
//            Date startingDate = df.parse(date);
//            return date1.after(startingDate);
//        } catch (ParseException e) {
//
//            return false;
//        }
//
//    }

//    private String getTodaysDate() {
//        return new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
//    }

    private void checkIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            editscheduleBinding.calenderbatchstartdate.setText(intent.getStringExtra(Constants.BATCHSTARTDATE));
            editscheduleBinding.txtAtlocation.setText(intent.getStringExtra(Constants.LOCATION));

            coursename = intent.getStringExtra(Constants.COURSE_NAME);
            coursemodulename = intent.getStringExtra(Constants.COURSE_CATEGORY);
            editscheduleBinding.Atcoursename.setText(coursename);
            editscheduleBinding.studentCourseModuleSpinner.setText(coursemodulename);
            fPhone = intent.getStringExtra(Constants.Phone);
        }
    }

    private void   showTimePicker(Button localbtn) {

        Calendar localCalendar = Calendar.getInstance();
        Timber.d("Button1 %s",localbtn.toString());


        new TimePickerDialog(EditSchedules.this, (paramAnonymous2TimePicker, paramAnonymous2Int1, paramAnonymous2Int2) -> {
            Calendar local = Calendar.getInstance();
            local.set(Calendar.HOUR_OF_DAY, paramAnonymous2Int1);
            local.set(Calendar.MINUTE, paramAnonymous2Int2);
            String str2 = new SimpleDateFormat("hh:mm  ", Locale.getDefault()).format(local.getTime());
            localbtn.setText(str2);
        }, localCalendar.get(Calendar.HOUR_OF_DAY), localCalendar.get(Calendar.MINUTE), false).show();


    }
    private void   showTimePicker(Button localbtn,EditScheduleInsertion editScheduleInsertion,boolean isStartorEnd) {

        Calendar localCalendar = Calendar.getInstance();
        Timber.d("Button1 %s",localbtn.toString());


        new TimePickerDialog(EditSchedules.this, (paramAnonymous2TimePicker, paramAnonymous2Int1, paramAnonymous2Int2) -> {
            Calendar local = Calendar.getInstance();
            local.set(Calendar.HOUR_OF_DAY, paramAnonymous2Int1);
            local.set(Calendar.MINUTE, paramAnonymous2Int2);
            String str2 = new SimpleDateFormat("hh:mm  ", Locale.getDefault()).format(local.getTime());
            localbtn.setText(str2);
            if(isStartorEnd)
            editScheduleInsertion.setStartTime(str2);
            else  editScheduleInsertion.setEndTime(str2);
        }, localCalendar.get(Calendar.HOUR_OF_DAY), localCalendar.get(Calendar.MINUTE), false).show();


    }

    private String getTime() {
        return new SimpleDateFormat("hh:mm ", Locale.getDefault()).format(new Date());

    }

    private void addnewSchedule() {
        String[] daysArray = getdays();
        EditScheduleInsertion editScheduleInsertion=new EditScheduleInsertion();
        scheduleInsertionList.add(editScheduleInsertion);


        List<String> stringList = Arrays.asList(daysArray);

        View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.schedule_details, null);

        editscheduleBinding.scheduleHolder.addView(view);
        LinearLayout linearLayout = view.findViewById(R.id.schedulesRootLayout);
        viewArrayList.add(linearLayout);
        Timber.d("%d", viewArrayList.size());
        ArrayAdapter<String> dayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,
                stringList);
        Spinner spinnerDays = view.findViewById(R.id.spinnerdays);
        spinnerDays.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                editScheduleInsertion.setDay_id(getDayid(parent.getSelectedItem().toString()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerDays.setAdapter(dayAdapter);

        ImageButton close = view.findViewById(R.id.closelayoutSchedule);
        close.setOnClickListener((v -> {

            editscheduleBinding.scheduleHolder.removeView(linearLayout);
            viewArrayList.remove(linearLayout);
            scheduleInsertionList.remove(editScheduleInsertion);



        }));
        Button startTime = view.findViewById(R.id.startTime);
        Button endTime = view.findViewById(R.id.endTime);
        startTime.setText(getTime());
        endTime.setText(getTime());
        editScheduleInsertion.setStartTime(startTime.getText().toString());
        editScheduleInsertion.setEndTime(endTime.getText().toString());
        startTime.setOnClickListener(v -> {
            showTimePicker(startTime,editScheduleInsertion,true);

            Timber.d("Button %s",startTime.toString());
        });
        endTime.setOnClickListener(v -> {

            showTimePicker(endTime,editScheduleInsertion,false);

        });
        editscheduleBinding.checkR.setOnCheckedChangeListener((buttonView, isChecked) -> {
            editscheduleBinding.scheduleHolder.removeAllViews();
            Timber.d("First Radio button %s", isRadioButtonSwitched);
            viewArrayList.clear();
            scheduleInsertionList.clear();
            isRadioButtonSwitched = true;
            flagSwitched=1;


        });
        editscheduleBinding.checkW.setOnCheckedChangeListener((buttonView, isChecked) -> {
            editscheduleBinding.scheduleHolder.removeAllViews();
            Timber.d("Second Radio button %s", isRadioButtonSwitched);
            viewArrayList.clear();
            scheduleInsertionList.clear();
            isRadioButtonSwitched = true;
            flagSwitched=1;


        });


    }
    private String getDayid(String day){
        switch (day){
            case "Mon":return "1";
            case "Tues":return "2";
            case "Wed":return "3";
            case "Thu":return "4";
            case "Fri":return "5";
            case "Sat":return "6";
            case "Sun":return "7";
            default:return "1";
        }
    }
    private class EditScheduleInsertion{

        private String startTime;
        private String endTime;

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        void setDay_id(String day_id) {
            this.day_id = day_id;
        }

        private String day_id;
    }

    @Override
    public void onBackPressed() {

        Intent intent=new Intent();
        intent.putExtra("key","value");
        if(resultCode==1)
        setResult(RESULT_OK,intent);
        else {
            setResult(RESULT_CANCELED,intent);
        }
        super.onBackPressed();
    }
}

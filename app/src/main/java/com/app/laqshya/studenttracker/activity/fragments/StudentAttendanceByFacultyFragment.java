package com.app.laqshya.studenttracker.activity.fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.appcompat.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.app.laqshya.studenttracker.R;
import com.app.laqshya.studenttracker.activity.adapter.StudentAdapter;
import com.app.laqshya.studenttracker.activity.factory.EditSchedulesViewModelFactory;
import com.app.laqshya.studenttracker.activity.listeners.StudentAttendanceListener;
import com.app.laqshya.studenttracker.activity.model.StudentInfo;
import com.app.laqshya.studenttracker.activity.utils.Constants;
import com.app.laqshya.studenttracker.activity.utils.SessionManager;
import com.app.laqshya.studenttracker.activity.utils.Utils;
import com.app.laqshya.studenttracker.activity.viewmodel.EditSchedulesViewModel;
import com.app.laqshya.studenttracker.databinding.EmptyViewBinding;
import com.app.laqshya.studenttracker.databinding.FacultyattendanceBinding;
import com.app.laqshya.studenttracker.databinding.SyllabussaveddialogBinding;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;
import timber.log.Timber;

public class StudentAttendanceByFacultyFragment extends Fragment implements StudentAttendanceListener {
    FacultyattendanceBinding facultyattendanceBinding;
    EditSchedulesViewModel editSchedulesViewModel;
    @Inject
    EditSchedulesViewModelFactory editSchedulesViewModelFactory;
    @Inject
    SessionManager sessionManager;
    private String selectedbatchid;
    private StudentAdapter studentAdapter;
    private ProgressDialog progressDialog;
    private Map<String, Integer> studentNos;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        facultyattendanceBinding = FacultyattendanceBinding.inflate(inflater, container, false);
        studentNos = new LinkedHashMap<>();
        editSchedulesViewModel = ViewModelProviders.of(this, editSchedulesViewModelFactory).get(EditSchedulesViewModel.class);
        return facultyattendanceBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        studentAdapter = new StudentAdapter(this);
        facultyattendanceBinding.recyclerViewAttendance.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        EmptyViewBinding emptyViewBinding = EmptyViewBinding.inflate(LayoutInflater.from(getActivity()), null, false);
        facultyattendanceBinding.recyclerViewAttendance.setAdapter(studentAdapter);
        facultyattendanceBinding.recyclerViewAttendance.setHasFixedSize(false);
        facultyattendanceBinding.recyclerViewAttendance.setEmptyView(emptyViewBinding.getRoot());
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Please wait");
        if (getActivity() != null) {
            Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
            toolbar.setTitle("Take Attendance");
        }
        getStudents();
        facultyattendanceBinding.saveAttendance.setOnClickListener(v -> {
            showSyllabusDialog();

        });
//        facultyattendanceBinding.fabAttendance.


    }

    private void showSyllabusDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        SyllabussaveddialogBinding syllabussaveddialogBinding = SyllabussaveddialogBinding.inflate(LayoutInflater.from(getActivity()), null, false);
        builder.setView(syllabussaveddialogBinding.getRoot());
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        syllabussaveddialogBinding.buttonCancel.setOnClickListener((v -> {
            alertDialog.dismiss();
        }));
        syllabussaveddialogBinding.buttonOk.setOnClickListener((v -> {
            if (!Utils.isEmpty(syllabussaveddialogBinding.todayTopic.getText().toString())) {
                alertDialog.dismiss();
                AlertDialog.Builder confirmBuilder = new AlertDialog.Builder(getActivity());
                confirmBuilder.setTitle("Are you sure?");
                confirmBuilder.setMessage("Please check before submitting!!");
                confirmBuilder.setPositiveButton("Okay", (dialog, which) -> {
                    dialog.dismiss();
                    saveAttendance(syllabussaveddialogBinding.todayTopic.getText().toString());
                }).setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
                confirmBuilder.create().show();
            } else {
                Toast.makeText(getActivity(), getString(R.string.fieldsEmpty), Toast.LENGTH_SHORT).show();
            }
        }));
    }

    private void saveAttendance(String topicName) {
        if (getActivity() != null && Utils.isNetworkConnected(getActivity())) {
            progressDialog.show();
            editSchedulesViewModel.saveAttendance(sessionManager.getLoggedInUserName(),
                    studentNos, topicName, selectedbatchid)
                    .observe(this, s -> {
                        progressDialog.dismiss();
                        if (s != null && s.contains("Error")) {
                            showSnackbar(getString(R.string.errorOccured));
                        } else {
                            showSnackbar("Attendance Saved Successfully");
                        }


                    });
        } else {
            showSnackbar(getString(R.string.internet_connection));
        }


    }

    private void getStudents() {

        if (getActivity() != null && Utils.isNetworkConnected(getActivity())) {
            progressDialog.show();
            selectedbatchid=getArguments().getString(Constants.BATCHID);
            editSchedulesViewModel.getStudentsForAttendance(selectedbatchid).observe(this, studentInfoList -> {
                progressDialog.dismiss();
                if (studentInfoList != null) {
                    Timber.d(String.valueOf(studentInfoList.getStudentInfo().size()));
                    if (studentInfoList.getThrowable() == null) {
                        studentNos.clear();

                        for (StudentInfo studentInfo : studentInfoList.getStudentInfo()) {

                            studentNos.put(studentInfo.getPhone(), 0);
                        }
                        Timber.d(String.valueOf(studentNos.size()));
                        studentAdapter.setList(studentInfoList.getStudentInfo());
                    } else {
                        showSnackbar(getString(R.string.errorwhilefetchingdata));
                    }
                } else {
                    showSnackbar(getString(R.string.errorwhilefetchingdata));
                }

            });
        } else {
            showSnackbar(getString(R.string.internet_connection));
        }
    }

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }


    private void showSnackbar(String message) {
        Snackbar.make(facultyattendanceBinding.attendanceMain, message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void markAttendance(int position, StudentInfo studentInfo) {
        if(studentInfo.isChecked()==0){
            studentInfo.setChecked(1);
        }
        else {
            studentInfo.setChecked(0);
        }
        studentNos.put(studentInfo.getPhone(), studentInfo.isChecked());
    }
}

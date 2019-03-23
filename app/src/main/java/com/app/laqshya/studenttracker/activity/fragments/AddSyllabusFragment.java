package com.app.laqshya.studenttracker.activity.fragments;

import android.Manifest;
import android.app.ProgressDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.app.laqshya.studenttracker.R;
import com.app.laqshya.studenttracker.activity.adapter.AddSyllabusAdapter;
import com.app.laqshya.studenttracker.activity.factory.PDFFactory;
import com.app.laqshya.studenttracker.activity.listeners.OnBookDeleteListener;
import com.app.laqshya.studenttracker.activity.model.CourseModuleList;
import com.app.laqshya.studenttracker.activity.model.PDFDoc;
import com.app.laqshya.studenttracker.activity.service.UploadService;
import com.app.laqshya.studenttracker.activity.utils.Constants;
import com.app.laqshya.studenttracker.activity.utils.SessionManager;
import com.app.laqshya.studenttracker.activity.utils.Utils;
import com.app.laqshya.studenttracker.activity.viewmodel.PDFViewModel;
import com.app.laqshya.studenttracker.databinding.UploadSyllabusBinding;
import com.github.angads25.filepicker.model.DialogConfigs;
import com.github.angads25.filepicker.model.DialogProperties;
import com.github.angads25.filepicker.view.FilePickerDialog;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.support.AndroidSupportInjection;
import timber.log.Timber;


public class AddSyllabusFragment extends Fragment implements View.OnClickListener, OnBookDeleteListener {

    UploadSyllabusBinding uploadSyllabusBinding;
    AddSyllabusAdapter addSyllabusAdapter;
    PDFViewModel pdfViewModel;
    ProgressDialog progressDialog;
    @Inject
    PDFFactory pdfFactory;
    @Inject
    SessionManager sessionManager;
    List<File> fileList;

    private List<PDFDoc> pdfDocList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        uploadSyllabusBinding = UploadSyllabusBinding.inflate(inflater, container, false);
        addSyllabusAdapter = new AddSyllabusAdapter(this);
        pdfDocList = new ArrayList<>();

        fileList = new ArrayList<>();
        if (!checkPermissionForReadExternalStorage()) {
            requestPermissionForReadExternalStorage();

        }
        return uploadSyllabusBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        uploadSyllabusBinding.addCourseModuleBtn.setOnClickListener(this);
        uploadSyllabusBinding.submitSyllabusBtn.setOnClickListener(this);
        uploadSyllabusBinding.syllabusList.setLayoutManager(new LinearLayoutManager(getActivity()));
        uploadSyllabusBinding.syllabusList.setHasFixedSize(false);
        uploadSyllabusBinding.syllabusList.setAdapter(addSyllabusAdapter);
        pdfViewModel = ViewModelProviders.of(this, pdfFactory).get(PDFViewModel.class);
        progressDialog = ProgressDialog.show(getActivity(), "Loading", "Please wait");
        showProgressDialog();
        if(sessionManager.getLoggedInType().equals(Constants.ADMIN)) {
            pdfViewModel.getModules().observe(this, courseModuleLists -> {
                ArrayList<String> list = new ArrayList<>();
                hidedialog();

                if (courseModuleLists != null && courseModuleLists.size() > 0) {
                    for (CourseModuleList courseModuleList : courseModuleLists) {
                        list.add(courseModuleList.getCourse_name());
                    }
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item,
                            list);
                    uploadSyllabusBinding.selectCourseModule.setAdapter(arrayAdapter);
                } else {
                    showToast("Error occurred while fetching data");
                }

            });
        }
        else {
            pdfViewModel.getModulesFaculty(sessionManager.getLoggedInUserName()).observe(this, courseModuleLists -> {
                ArrayList<String> list = new ArrayList<>();
                hidedialog();

                if (courseModuleLists != null && courseModuleLists.size() > 0) {
                    for (CourseModuleList courseModuleList : courseModuleLists) {
                        list.add(courseModuleList.getCourse_name());
                    }
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item,
                            list);
                    uploadSyllabusBinding.selectCourseModule.setAdapter(arrayAdapter);
                } else {
                    showToast("Error occurred while fetching data");
                }

            });

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_course_module_btn:
                getPdf();

                break;
            case R.id.submit_syllabus_btn:
                if (getActivity() != null && Utils.isNetworkConnected(getActivity())) {
                    uploadFile();

                } else {
                    Toast.makeText(getActivity(), getString(R.string.internet_connection), Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }

    private void uploadFile() {


//        List<MultipartBody.Part> parts = new ArrayList<>();


        String name = uploadSyllabusBinding.selectCourseModule.getSelectedItem().toString();
//


        Intent intent = new Intent(getActivity(), UploadService.class);
        intent.putExtra("fileList", (Serializable) fileList);
        intent.putExtra("coursename", name);
        intent.putExtra("uploaderId",sessionManager.getLoggedInUserName());
//        intent.putExtra('flaguploader',)
        String flag="0";
        if (!sessionManager.getLoggedInType().equalsIgnoreCase(Constants.ADMIN)) {
            flag="1";

        }
        intent.putExtra("uploadedFlag",flag);
        Context context = getActivity();
        if (context != null) {
            ContextCompat.startForegroundService(context, intent);
        }
//        startSe


    }

    private boolean checkPermissionForReadExternalStorage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int result = ContextCompat.checkSelfPermission(Objects.requireNonNull(getActivity()), Manifest.permission.READ_EXTERNAL_STORAGE);
            return result == PackageManager.PERMISSION_GRANTED;
        }
        return false;
    }

    private void requestPermissionForReadExternalStorage() {
        try {
            int READ_STORAGE_PERMISSION_REQUEST_CODE = 1;
            ActivityCompat.requestPermissions((Objects.requireNonNull(getActivity())), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    READ_STORAGE_PERMISSION_REQUEST_CODE);

//                pdfDocList=new PDf
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    private void getPdf() {
        DialogProperties properties = new DialogProperties();
        properties.selection_mode = DialogConfigs.SINGLE_MODE;
        properties.selection_type = DialogConfigs.SINGLE_MODE;

        properties.extensions = new String[]{"pdf"};
        properties.offset = new File(DialogConfigs.DEFAULT_DIR);
        FilePickerDialog dialog = new FilePickerDialog(getActivity(), properties);
        dialog.setTitle("Select a File");
        dialog.setDialogSelectionListener(files -> {
            Timber.d(files[0]);


            fileList.clear();
            pdfDocList.clear();
            for (String filename : files) {
                File file = new File(filename);
                fileList.add(file);
                createAdapter(file.getName());
            }


        });
        dialog.show();
//

    }


    private void createAdapter(String bookname) {
        PDFDoc pdfDoc = new PDFDoc();
        pdfDoc.setBookname(bookname);
        pdfDocList.add(pdfDoc);

        addSyllabusAdapter.setPdfDocList(pdfDocList);
        Timber.d(String.valueOf(addSyllabusAdapter.getItemCount()));

    }

    @Override
    public void deleteItem(int position) {
        pdfDocList.remove(position);
        addSyllabusAdapter.notifyItemRemoved(position);
//        pathList.remove(position);
        fileList.remove(position);

    }


    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
//        AndroidInjection.inject(this);
        super.onAttach(context);
    }
//

    private void showProgressDialog() {
        if (progressDialog != null && !progressDialog.isShowing()) {
            progressDialog.show();
        }
    }

    private void hidedialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    private void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

//

}

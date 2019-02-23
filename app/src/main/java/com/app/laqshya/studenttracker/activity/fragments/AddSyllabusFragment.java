package com.app.laqshya.studenttracker.activity.fragments;

import android.Manifest;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.app.laqshya.studenttracker.R;
import com.app.laqshya.studenttracker.activity.adapter.AddSyllabusAdapter;
import com.app.laqshya.studenttracker.activity.factory.PDFFactory;
import com.app.laqshya.studenttracker.activity.listeners.OnBookDeleteListener;
import com.app.laqshya.studenttracker.activity.model.PDFDoc;
import com.app.laqshya.studenttracker.activity.utils.Utils;
import com.app.laqshya.studenttracker.activity.viewmodel.PDFViewModel;
import com.app.laqshya.studenttracker.databinding.UploadSyllabusBinding;
import com.github.angads25.filepicker.model.DialogConfigs;
import com.github.angads25.filepicker.model.DialogProperties;
import com.github.angads25.filepicker.view.FilePickerDialog;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import timber.log.Timber;


public class AddSyllabusFragment extends Fragment implements View.OnClickListener, OnBookDeleteListener {

    private static int READ_STORAGE_PERMISSION_REQUEST_CODE = 1;
    UploadSyllabusBinding uploadSyllabusBinding;
    AddSyllabusAdapter addSyllabusAdapter;
    PDFViewModel pdfViewModel;
    @Inject
    PDFFactory pdfFactory;
    List<String> pathList;
    List<File> fileList;
    List<Uri> urilist;
    private List<PDFDoc> pdfDocList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        uploadSyllabusBinding = UploadSyllabusBinding.inflate(inflater, container, false);
        addSyllabusAdapter = new AddSyllabusAdapter(this);
        pdfDocList = new ArrayList<>();
        pathList = new ArrayList<>();
        urilist = new ArrayList<>();

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


        List<MultipartBody.Part> parts = new ArrayList<>();

        Timber.d(String.valueOf(pdfDocList.size()));
        HashMap<String, RequestBody> map = new HashMap<>();


        for (int i = 0; i < fileList.size(); i++) {
//            File file=new File(fileList.get(i));
            Timber.d(Uri.fromFile(fileList.get(i)).toString());
            parts.add(prepareFilePart(fileList.get(i), Uri.fromFile(fileList.get(i))));
            RequestBody description = createPartFromString(fileList.get(i).getName());

            map.put("files", description);
//            map.put()


        }
        Timber.d(Arrays.toString(parts.toArray()));




        pdfViewModel.uploadPDF(map, parts).observe(this, s -> {
            Timber.d(s);

        });
//        Call<ResponseBody> call = service.postMeme(requestBody);


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
        properties.selection_mode = DialogConfigs.MULTI_MODE;
        properties.selection_type = DialogConfigs.FILE_SELECT;

        properties.extensions = new String[]{"pdf"};
        properties.offset = new File(DialogConfigs.DEFAULT_DIR);
        FilePickerDialog dialog = new FilePickerDialog(getActivity(), properties);
        dialog.setTitle("Select a File");
        dialog.setDialogSelectionListener(files -> {
            Timber.d(files[0]);

            pathList.addAll(Arrays.asList(files));
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
        super.onAttach(context);
    }

    @NonNull
    private RequestBody createPartFromString(String filename) {
        return RequestBody.create(
                okhttp3.MultipartBody.FORM, filename);
    }


    @NonNull
    private MultipartBody.Part prepareFilePart(File file, Uri fileUri) {
        // https://github.com/iPaulPro/aFileChooser/blob/master/aFileChooser/src/com/ipaulpro/afilechooser/utils/FileUtils.java
        // use the FileUtils to get the actual file by uri


        // create RequestBody instance from file
        if(getActivity()!=null) {
            RequestBody requestFile =
                    RequestBody.create(
                            MediaType.parse("application/pdf"),
                            file

                    );
            return MultipartBody.Part.createFormData("files", file.getName(), requestFile);
        }
        return null;

        // MultipartBody.Part is used to send also the actual file name



    }
}

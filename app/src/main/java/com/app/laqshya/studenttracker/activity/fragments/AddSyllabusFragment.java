package com.app.laqshya.studenttracker.activity.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.laqshya.studenttracker.R;
import com.app.laqshya.studenttracker.activity.adapter.AddSyllabusAdapter;
import com.app.laqshya.studenttracker.databinding.UploadSyllabusBinding;


public class AddSyllabusFragment extends Fragment implements View.OnClickListener {

    UploadSyllabusBinding uploadSyllabusBinding;
    AddSyllabusAdapter addSyllabusAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        uploadSyllabusBinding = UploadSyllabusBinding.inflate(inflater, container, false);

        uploadSyllabusBinding.addCourseModuleBtn.setOnClickListener(this);
        uploadSyllabusBinding.submitSyllabusBtn.setOnClickListener(this);

        return uploadSyllabusBinding.getRoot();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_course_module_btn:

                break;
            case R.id.submit_syllabus_btn:

                break;
        }
    }
}

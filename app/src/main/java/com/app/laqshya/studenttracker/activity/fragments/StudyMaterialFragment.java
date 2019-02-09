package com.app.laqshya.studenttracker.activity.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.laqshya.studenttracker.R;
import com.app.laqshya.studenttracker.activity.factory.StudentDetailsFactory;
import com.app.laqshya.studenttracker.activity.model.PDFDoc;
import com.app.laqshya.studenttracker.activity.service.EduTrackerService;
import com.app.laqshya.studenttracker.activity.utils.SessionManager;
import com.app.laqshya.studenttracker.activity.utils.Utils;
import com.app.laqshya.studenttracker.activity.viewmodel.StudentDetailsViewModel;
import com.app.laqshya.studenttracker.databinding.FragmentStudyMaterialBinding;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;
import timber.log.Timber;

public class StudyMaterialFragment extends Fragment {
    FragmentStudyMaterialBinding fragmentStudyMaterialBinding;
    @Inject
    StudentDetailsFactory studentDetailsFactory;
    StudentDetailsViewModel studentDetailsViewModel;
    @Inject
    SessionManager sessionManager;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentStudyMaterialBinding=FragmentStudyMaterialBinding.inflate(inflater,container,false);
        return fragmentStudyMaterialBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        studentDetailsViewModel=ViewModelProviders.of(this,studentDetailsFactory).get(StudentDetailsViewModel.class);
        getPdfs();
    }

    private void getPdfs() {
        if (getActivity()!=null && Utils.isNetworkConnected(getActivity())) {
            studentDetailsViewModel.getPDFS(sessionManager.getLoggedInUserName())
                    .observe(this, pdfList -> {
                        if (pdfList==null || pdfList.getThrowable() == null) {
                            showSnackBar(getString(R.string.errorwhilefetchingdata));

                        } else {
                            Timber.d(pdfList.getPdfDocList().get(0).getBookname());

                        }
                    });

        }
        else {
            showSnackBar(getString(R.string.internet_connection));
        }
    }
    private void showSnackBar(String snackMessage) {
        Snackbar snackbar = Snackbar.make(fragmentStudyMaterialBinding.rootView,
                snackMessage, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }
}

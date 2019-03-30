package com.app.laqshya.studenttracker.activity.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.app.laqshya.studenttracker.R;
import com.app.laqshya.studenttracker.activity.adapter.ManageStudentAdapter;
import com.app.laqshya.studenttracker.activity.factory.ManageStudentFactory;
import com.app.laqshya.studenttracker.activity.listeners.OnActionClickForStudentsListener;
import com.app.laqshya.studenttracker.activity.utils.Constants;
import com.app.laqshya.studenttracker.activity.utils.SessionManager;
import com.app.laqshya.studenttracker.activity.utils.Utils;
import com.app.laqshya.studenttracker.activity.viewmodel.ManageStudentsViewModel;
import com.app.laqshya.studenttracker.databinding.FragmentNotificationBinding;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import dagger.android.support.AndroidSupportInjection;
import timber.log.Timber;

public class ManageStudentFragment extends Fragment implements OnActionClickForStudentsListener {
    @Inject
    ManageStudentFactory manageStudentFactory;
    ProgressDialog progressDialog;
    @Inject
    SessionManager sessionManager;
    private FragmentNotificationBinding fragmentNotificationBinding;
    private ManageStudentAdapter manageStudentAdapter;
    private ManageStudentsViewModel manageStudentsViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentNotificationBinding = FragmentNotificationBinding.inflate(inflater, container, false);
        manageStudentsViewModel = ViewModelProviders.of(this, manageStudentFactory).get(ManageStudentsViewModel.class);

        return fragmentNotificationBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        progressDialog = new ProgressDialog(getActivity());
        if (getActivity() != null && Utils.isNetworkConnected(getActivity())) {
            Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
            toolbar.setTitle("Manage Students");
            progressDialog.setTitle("Loading");

            progressDialog.setMessage("Please wait");
            progressDialog.show();
            if (sessionManager.getLoggedInType().equals(Constants.ADMIN)) {
                fetchForAdmin();
            } else if (sessionManager.getLoggedInType().equals(Constants.COUNSELLOR)) {
                fetchForCounsellor();


            }
        } else {
            Toast.makeText(getActivity(), getString(R.string.internet_connection), Toast.LENGTH_SHORT).show();
        }


    }

    private void fetchForAdmin() {
        manageStudentsViewModel.getStudents().observe(this, manageStudentInfoResponse -> {
            progressDialog.dismiss();
            manageStudentAdapter = new ManageStudentAdapter(this);
            if (manageStudentInfoResponse.getManageStudentInfoList() != null) {
                manageStudentAdapter.setManageStudentInfos(manageStudentInfoResponse.getManageStudentInfoList());

                fragmentNotificationBinding.recyclerViewNotification.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
                fragmentNotificationBinding.recyclerViewNotification.setAdapter(manageStudentAdapter);
                fragmentNotificationBinding.searchNotification.setVisibility(View.VISIBLE);
                fragmentNotificationBinding.searchNotification.setQueryHint(getString(R.string.searchstudents));
                setUpSearch();
            } else {
                Toast.makeText(getActivity(), getString(R.string.errorOccured), Toast.LENGTH_SHORT).show();
            }


        });
    }

    private void fetchForCounsellor() {
        manageStudentsViewModel.getStudentsForCounsellor(sessionManager.getLoggedInuserCenter()).observe(this, manageStudentInfoResponse -> {
            progressDialog.dismiss();
            manageStudentAdapter = new ManageStudentAdapter(this);
            if (manageStudentInfoResponse.getManageStudentInfoList() != null) {
                manageStudentAdapter.setManageStudentInfos(manageStudentInfoResponse.getManageStudentInfoList());

                fragmentNotificationBinding.recyclerViewNotification.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
                fragmentNotificationBinding.recyclerViewNotification.setAdapter(manageStudentAdapter);
                fragmentNotificationBinding.searchNotification.setVisibility(View.VISIBLE);
                fragmentNotificationBinding.searchNotification.setQueryHint(getString(R.string.searchstudents));
                setUpSearch();
            } else {
                Toast.makeText(getActivity(), getString(R.string.errorOccured), Toast.LENGTH_SHORT).show();
            }


        });
    }

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    private void setUpSearch() {
        fragmentNotificationBinding.searchNotification.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Timber.d(s);

                manageStudentAdapter.getFilter().filter(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                Timber.d(s);
                manageStudentAdapter.getFilter().filter(s);
//                adminNotificationAdapter.getFilter().filter("MacBook");
                return false;
            }
        });

    }

    @Override
    public void sendEmail(String emailid) {
        Toast.makeText(getActivity(), "Please wait...", Toast.LENGTH_SHORT).show();
        Intent sendIntent = new Intent("android.intent.action.SEND");
        sendIntent.setDataAndType(Uri.parse(":mailto"),"message/rfc822");
        sendIntent.putExtra("android.intent.extra.EMAIL", new String[]{"lalaqshya@gmail.com"});
        startActivity(Intent.createChooser(sendIntent, "Mail us"));


    }

    @Override
    public void call(String phone) {
        Intent sendIntent = new Intent(Intent.ACTION_DIAL);
        sendIntent.setData(Uri.parse("tel:" + phone));
        startActivity(sendIntent);

    }
}

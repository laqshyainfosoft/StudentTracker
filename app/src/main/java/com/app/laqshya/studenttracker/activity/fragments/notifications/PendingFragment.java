package com.app.laqshya.studenttracker.activity.fragments.notifications;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.laqshya.studenttracker.R;
import com.app.laqshya.studenttracker.databinding.BroadcastSingleStudentBinding;

public class PendingFragment extends Fragment {
    BroadcastSingleStudentBinding broadcastSingleStudentBinding;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        broadcastSingleStudentBinding=BroadcastSingleStudentBinding.inflate(inflater,container,false);
        broadcastSingleStudentBinding.messageBroadcast.setText(getString(R.string.select_pending_students));
        return broadcastSingleStudentBinding.getRoot();

    }
}

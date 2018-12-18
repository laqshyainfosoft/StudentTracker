package com.app.laqshya.studenttracker.activity.fragments;

import android.graphics.LinearGradient;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.app.laqshya.studenttracker.R;
import com.app.laqshya.studenttracker.activity.utils.Constants;

public class HomeFragmentFaculty extends Fragment implements View.OnClickListener {

    LinearLayout mAttendanceFaculty, mBroadcastFaculty, mReceivedNotificationFaculty;
    static String CURRENT_TAG = Constants.TAG_HOME;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_faculty, container, false);

        mAttendanceFaculty = view.findViewById(R.id.attendance_faculty);
        mBroadcastFaculty = view.findViewById(R.id.broadcast_faculty);
        mReceivedNotificationFaculty = view.findViewById(R.id.received_notification_faculty);

        mAttendanceFaculty.setOnClickListener(this);
        mBroadcastFaculty.setOnClickListener(this);
        mReceivedNotificationFaculty.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.attendance_faculty:
                CURRENT_TAG = Constants.TAG_ATTENDANCE;
                break;
            case R.id.broadcast_faculty:
                CURRENT_TAG = Constants.TAG_BROADCAST;
                break;
            case R.id.received_notification_faculty:
                CURRENT_TAG = Constants.TAG_NOTIFICATIONS;
                break;
        }
    }
}

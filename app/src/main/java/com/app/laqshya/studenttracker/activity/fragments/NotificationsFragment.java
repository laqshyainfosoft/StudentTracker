package com.app.laqshya.studenttracker.activity.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.laqshya.studenttracker.databinding.FragmentNotificationBinding;

public class NotificationsFragment extends Fragment {
    FragmentNotificationBinding fragmentNotificationBinding;
    TextView textView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentNotificationBinding=FragmentNotificationBinding.inflate(inflater,container,false);
        return fragmentNotificationBinding.getRoot();
    }
}

package com.app.laqshya.studenttracker.activity.viewmodel;

import android.arch.lifecycle.ViewModel;

import com.app.laqshya.studenttracker.activity.repository.AddBatchRepository;
import com.app.laqshya.studenttracker.activity.repository.NotificationRepository;

public class NotificationViewModel extends ViewModel {
    NotificationRepository notificationRepository;
    public NotificationViewModel(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }


}

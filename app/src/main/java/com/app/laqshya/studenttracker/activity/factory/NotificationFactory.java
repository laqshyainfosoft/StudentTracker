package com.app.laqshya.studenttracker.activity.factory;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.app.laqshya.studenttracker.activity.repository.NotificationRepository;
import com.app.laqshya.studenttracker.activity.viewmodel.NotificationAndStudentViewModel;

public class NotificationFactory extends ViewModelProvider.NewInstanceFactory {
    private NotificationRepository notificationRepository;

    public NotificationFactory(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new NotificationAndStudentViewModel(notificationRepository);

    }
}

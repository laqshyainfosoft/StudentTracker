package com.app.laqshya.studenttracker.activity.factory;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.annotation.NonNull;

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

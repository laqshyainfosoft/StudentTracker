package com.app.laqshya.studenttracker.activity.di;

import com.app.laqshya.studenttracker.activity.factory.EditSchedulesViewModelFactory;
import com.app.laqshya.studenttracker.activity.factory.NotificationFactory;
import com.app.laqshya.studenttracker.activity.repository.EditBatchRepository;
import com.app.laqshya.studenttracker.activity.repository.NotificationRepository;
import com.app.laqshya.studenttracker.activity.service.EduTrackerService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class NotificationModule {
    @Provides
    @Singleton
    NotificationFactory getRegFactory(NotificationRepository notificationRepository) {
        return new NotificationFactory(notificationRepository);
    }

    @Provides
    NotificationRepository getNotificationRepository(EduTrackerService eduTrackerService) {
        return new NotificationRepository(eduTrackerService);
    }
}

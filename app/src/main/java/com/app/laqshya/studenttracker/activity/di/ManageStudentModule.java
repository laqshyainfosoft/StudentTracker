package com.app.laqshya.studenttracker.activity.di;

import com.app.laqshya.studenttracker.activity.factory.ManageStudentFactory;
import com.app.laqshya.studenttracker.activity.factory.NotificationFactory;
import com.app.laqshya.studenttracker.activity.repository.ManageStudentInfoRepository;
import com.app.laqshya.studenttracker.activity.repository.NotificationRepository;
import com.app.laqshya.studenttracker.activity.service.EduTrackerService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ManageStudentModule {
    @Provides
    @Singleton
    ManageStudentFactory manageStudentFactory(ManageStudentInfoRepository studentInfoRepository) {
        return new ManageStudentFactory(studentInfoRepository);
    }

    @Provides
    ManageStudentInfoRepository notificationRepository(EduTrackerService eduTrackerService) {
        return new ManageStudentInfoRepository(eduTrackerService);
    }

}

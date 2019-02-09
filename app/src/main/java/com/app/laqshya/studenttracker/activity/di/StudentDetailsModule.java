package com.app.laqshya.studenttracker.activity.di;

import com.app.laqshya.studenttracker.activity.factory.BroadcastViewModelFactory;
import com.app.laqshya.studenttracker.activity.factory.StudentDetailsFactory;
import com.app.laqshya.studenttracker.activity.repository.BroadcastRepository;
import com.app.laqshya.studenttracker.activity.repository.StudentDetailsRepository;
import com.app.laqshya.studenttracker.activity.service.EduTrackerService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class StudentDetailsModule {
    @Provides
    @Singleton
    StudentDetailsFactory getBroadcastViewModelFactory(StudentDetailsRepository batchRepository) {
        return new StudentDetailsFactory(batchRepository);
    }

    @Provides
    StudentDetailsRepository getBroadcastRepository(EduTrackerService eduTrackerService) {
        return new StudentDetailsRepository(eduTrackerService);
    }
}

package com.app.laqshya.studenttracker.activity.di;

import com.app.laqshya.studenttracker.activity.factory.AddScheduleFactory;
import com.app.laqshya.studenttracker.activity.repository.AddBatchRepository;
import com.app.laqshya.studenttracker.activity.service.EduTrackerService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class    AddScheduleModule {
    @Provides
    @Singleton
    AddScheduleFactory getRegFactory(AddBatchRepository batchRepository) {
        return new AddScheduleFactory(batchRepository);
    }

    @Provides
    AddBatchRepository getRegRepository(EduTrackerService eduTrackerService) {
        return new AddBatchRepository(eduTrackerService);
    }
}

package com.app.laqshya.studenttracker.activity.di;

import com.app.laqshya.studenttracker.activity.factory.AddScheduleFactory;
import com.app.laqshya.studenttracker.activity.factory.EditSchedulesViewModelFactory;
import com.app.laqshya.studenttracker.activity.repository.AddBatchRepository;
import com.app.laqshya.studenttracker.activity.repository.EditBatchRepository;
import com.app.laqshya.studenttracker.activity.service.EduTrackerService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class EditScheduleModule {
    @Provides
    @Singleton
    EditSchedulesViewModelFactory getRegFactory(EditBatchRepository batchRepository) {
        return new EditSchedulesViewModelFactory(batchRepository);
    }

    @Provides
    EditBatchRepository getRegRepository(EduTrackerService eduTrackerService) {
        return new EditBatchRepository(eduTrackerService);
    }

}

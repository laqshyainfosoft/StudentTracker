package com.app.laqshya.studenttracker.activity.di;

import com.app.laqshya.studenttracker.activity.factory.BroadcastViewModelFactory;
import com.app.laqshya.studenttracker.activity.repository.BroadcastRepository;
import com.app.laqshya.studenttracker.activity.service.EduTrackerService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
@Module
public class BroadcastModule {
    @Provides
    @Singleton
    BroadcastViewModelFactory getBroadcastViewModelFactory(BroadcastRepository batchRepository) {
        return new BroadcastViewModelFactory(batchRepository);
    }

    @Provides
    BroadcastRepository getBroadcastRepository(EduTrackerService eduTrackerService) {
        return new BroadcastRepository(eduTrackerService);
    }
}

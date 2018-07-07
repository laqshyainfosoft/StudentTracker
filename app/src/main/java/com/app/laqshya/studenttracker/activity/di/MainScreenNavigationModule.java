package com.app.laqshya.studenttracker.activity.di;

import com.app.laqshya.studenttracker.activity.factory.LoginFactory;
import com.app.laqshya.studenttracker.activity.factory.RegistrationFactory;
import com.app.laqshya.studenttracker.activity.repository.LoginRepository;
import com.app.laqshya.studenttracker.activity.repository.RegistrationRepository;
import com.app.laqshya.studenttracker.activity.service.EduTrackerService;
import com.app.laqshya.studenttracker.activity.utils.SessionManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class MainScreenNavigationModule {
    @Provides
    @Singleton
    RegistrationFactory getRegFactory(RegistrationRepository loginRepository){
        return new RegistrationFactory(loginRepository);
    }
    @Provides
    RegistrationRepository getRegRepository(EduTrackerService eduTrackerService){
        return new RegistrationRepository(eduTrackerService);
    }

}

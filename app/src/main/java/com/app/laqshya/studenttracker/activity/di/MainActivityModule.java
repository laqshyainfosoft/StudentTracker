package com.app.laqshya.studenttracker.activity.di;

import android.content.SharedPreferences;

import com.app.laqshya.studenttracker.activity.factory.LoginFactory;
import com.app.laqshya.studenttracker.activity.repository.LoginRepository;
import com.app.laqshya.studenttracker.activity.service.EduTrackerService;
import com.app.laqshya.studenttracker.activity.utils.SessionManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class MainActivityModule {
    @Provides
    @Singleton
    LoginFactory getLoginFactory(LoginRepository loginRepository){
        return new LoginFactory(loginRepository);
    }
    @Provides
    LoginRepository getLoginRepository(SessionManager sessionManager,EduTrackerService eduTrackerService){
        return new LoginRepository(sessionManager,eduTrackerService);
    }
}

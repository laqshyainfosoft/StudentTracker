package com.app.laqshya.studenttracker.activity.di;

import android.app.Application;
import android.content.Context;
import androidx.multidex.MultiDex;

import com.app.laqshya.studenttracker.BuildConfig;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;
import timber.log.Timber;

public class StudentTrackerApplication extends DaggerApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
            Timber.uprootAll();
        }
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        StudentComponent studentComponent = DaggerStudentComponent.builder().buildapp(this).build();
        studentComponent.inject(this);
        return studentComponent;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}

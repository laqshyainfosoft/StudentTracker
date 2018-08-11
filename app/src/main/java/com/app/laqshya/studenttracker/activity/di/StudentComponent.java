package com.app.laqshya.studenttracker.activity.di;

import android.app.Application;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;
import dagger.android.support.DaggerApplication;

@Singleton
@Component(modules = {AndroidSupportInjectionModule.class, AndroidInjectionModule.class,StudentModule.class,StudentBindingModule.class})
public interface StudentComponent extends AndroidInjector<DaggerApplication> {
    @Component.Builder
    interface Builder{
        StudentComponent build();
        @BindsInstance
        Builder buildapp(Application application);
    }

}

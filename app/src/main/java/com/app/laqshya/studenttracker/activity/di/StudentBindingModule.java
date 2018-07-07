package com.app.laqshya.studenttracker.activity.di;

import com.app.laqshya.studenttracker.activity.MainActivity;
import com.app.laqshya.studenttracker.activity.MainScreenNavigationDrawer;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module(includes = {MainActivityModule.class,MainScreenNavigationModule.class})
public abstract class StudentBindingModule {
    @ContributesAndroidInjector
    abstract MainActivity getMainActivity();
    @ContributesAndroidInjector
    abstract MainScreenNavigationDrawer getMainScreenNavigationDrawer();
}

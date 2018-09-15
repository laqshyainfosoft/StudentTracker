package com.app.laqshya.studenttracker.activity.di;

import com.app.laqshya.studenttracker.activity.AddSchedules;
import com.app.laqshya.studenttracker.activity.EditSchedules;
import com.app.laqshya.studenttracker.activity.MainActivity;
import com.app.laqshya.studenttracker.activity.MainScreenNavigationDrawer;
import com.app.laqshya.studenttracker.activity.fragments.AttendanceFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module(includes = {MainActivityModule.class, MainScreenNavigationModule.class, AddScheduleModule.class,EditScheduleModule.class})
public abstract class StudentBindingModule {
    @ContributesAndroidInjector
    abstract MainActivity getMainActivity();
    @ContributesAndroidInjector
    abstract MainScreenNavigationDrawer getMainScreenNavigationDrawer();

    @ContributesAndroidInjector
    abstract AddSchedules getAddSchedules();
    @ContributesAndroidInjector
    abstract AttendanceFragment getAttendanceFragment();
    @ContributesAndroidInjector
    abstract EditSchedules getEditSchedules();
}

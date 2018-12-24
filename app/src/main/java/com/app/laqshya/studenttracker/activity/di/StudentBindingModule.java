package com.app.laqshya.studenttracker.activity.di;

import com.app.laqshya.studenttracker.activity.AddSchedules;
import com.app.laqshya.studenttracker.activity.EditSchedules;
import com.app.laqshya.studenttracker.activity.MainActivity;
import com.app.laqshya.studenttracker.activity.MainScreenNavigationDrawer;
import com.app.laqshya.studenttracker.activity.fragments.AttendanceFragment;
import com.app.laqshya.studenttracker.activity.fragments.CompletionBatchesFragment;
import com.app.laqshya.studenttracker.activity.fragments.DeletedBatchesFragment;
import com.app.laqshya.studenttracker.activity.fragments.notifications.SingleStudentNotificationFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module(includes = {MainActivityModule.class, MainScreenNavigationModule.class, AddScheduleModule.class,EditScheduleModule.class,
BroadcastModule.class})
abstract class StudentBindingModule {
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
    @ContributesAndroidInjector
    abstract DeletedBatchesFragment deletedBatchesFragment();
    @ContributesAndroidInjector
    abstract CompletionBatchesFragment completionBatchesFragment();
    @ContributesAndroidInjector
    abstract SingleStudentNotificationFragment singleStudentNotificationFragment();

}

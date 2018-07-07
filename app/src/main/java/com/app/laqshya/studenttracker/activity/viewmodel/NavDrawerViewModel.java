package com.app.laqshya.studenttracker.activity.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.v4.app.Fragment;

import com.app.laqshya.studenttracker.activity.fragments.AboutDevelopersFragment;
import com.app.laqshya.studenttracker.activity.fragments.AddCounsellorFragment;
import com.app.laqshya.studenttracker.activity.fragments.AddFacultyFragment;
import com.app.laqshya.studenttracker.activity.fragments.AddStudentFragment;
import com.app.laqshya.studenttracker.activity.fragments.BroadcastFragment;
import com.app.laqshya.studenttracker.activity.fragments.HomeFragmentAdmin;
import com.app.laqshya.studenttracker.activity.fragments.ManageStudentFragment;
import com.app.laqshya.studenttracker.activity.fragments.NotificationsFragment;
import com.app.laqshya.studenttracker.activity.fragments.PrivacyPolicyFragment;
import com.app.laqshya.studenttracker.activity.fragments.ScheduleBatchesFragment;
import com.app.laqshya.studenttracker.activity.model.CenterList;
import com.app.laqshya.studenttracker.activity.repository.RegistrationRepository;

import java.util.List;

public class NavDrawerViewModel extends ViewModel {
    public MutableLiveData<String> fragmentTitle=new MutableLiveData<>();
    public MutableLiveData<CenterList> centerList=new MutableLiveData<>();
    private RegistrationRepository registrationRepository;

    public NavDrawerViewModel(RegistrationRepository registrationRepository) {
        this.registrationRepository=registrationRepository;
    }

    public Fragment getAdminFragment(int navindex) {
        switch (navindex) {
            case 0:
                // home
                fragmentTitle.setValue("Home");
                return new HomeFragmentAdmin();
            case 1:
                fragmentTitle.setValue("Add Counsellor");
                return new AddCounsellorFragment();

            case 2:
                fragmentTitle.setValue("Add Faculty");
                // performance
                return new AddFacultyFragment();

            case 3:
                fragmentTitle.setValue("Add Student");
                // attendance fragment
                return new AddStudentFragment();
            case 4:
                fragmentTitle.setValue("Manage Students");
                // notifications fragment
                return new ManageStudentFragment();
            case 5:
                fragmentTitle.setValue("Schedules");
                return new ScheduleBatchesFragment();
            case 6:
                fragmentTitle.setValue("Notifications");
                return new NotificationsFragment();
            case 7:
                fragmentTitle.setValue("Broadcast");
                // broadcast fragment
                return new BroadcastFragment();

            case 8:

                fragmentTitle.setValue("About Developers");
                return new AboutDevelopersFragment();


            case 9:
                fragmentTitle.setValue("Privacy Policy");
                // privacypolicy fragment
                return new PrivacyPolicyFragment();


            default:
                fragmentTitle.setValue("Home");
                return new HomeFragmentAdmin();
        }
    }

    public LiveData<List<String>> getCenterList() {
        return registrationRepository.getCenterList();
    }
}
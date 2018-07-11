package com.app.laqshya.studenttracker.activity.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.v4.app.Fragment;

import com.app.laqshya.studenttracker.activity.fragments.AboutDevelopersFragment;
import com.app.laqshya.studenttracker.activity.fragments.AddCounsellorFragment;
import com.app.laqshya.studenttracker.activity.fragments.AddFacultyFragment;
import com.app.laqshya.studenttracker.activity.fragments.AddStudentFragment;
import com.app.laqshya.studenttracker.activity.fragments.BroadcastFragment;
import com.app.laqshya.studenttracker.activity.fragments.HomeFragmentAdmin;
import com.app.laqshya.studenttracker.activity.fragments.HomeFragmentFaculty;
import com.app.laqshya.studenttracker.activity.fragments.ManageStudentFragment;
import com.app.laqshya.studenttracker.activity.fragments.NotificationsFragment;
import com.app.laqshya.studenttracker.activity.fragments.PrivacyPolicyFragment;
import com.app.laqshya.studenttracker.activity.fragments.ScheduleBatchesFragment;
import com.app.laqshya.studenttracker.activity.model.CenterList;
import com.app.laqshya.studenttracker.activity.model.Installments;
import com.app.laqshya.studenttracker.activity.repository.RegistrationRepository;

import java.util.List;

import timber.log.Timber;

public class NavDrawerViewModel extends ViewModel {
    public MutableLiveData<String> fragmentTitle=new MutableLiveData<>();
    public MutableLiveData<CenterList> centerList=new MutableLiveData<>();
    public MutableLiveData<Boolean> isProgress = new MutableLiveData<>();
    public MutableLiveData<Integer> noOfInstallments = new MutableLiveData<>();
    public MutableLiveData<Integer> downPayment = new MutableLiveData<>();
    public MutableLiveData<Integer> totalFees = new MediatorLiveData<>();
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

    public LiveData<String> registerCounsellor(String email, String centerMobile, String counsellorMobile
            , String centername, String counsellorname) {
        isProgress.setValue(true);
        return registrationRepository.registerCounsellor(email, centerMobile, counsellorMobile
                , centername, counsellorname);
    }

    public LiveData<List<String>> getCenterList() {
        return registrationRepository.getCenterList();
    }

    public LiveData<List<String>> getCourseList() {
        return registrationRepository.getCourse();
    }

    public LiveData<String> registerFaculty(String username, String phoneNumber, String email, String courses) {
        isProgress.setValue(true);
        return registrationRepository.registerFaculty(email, phoneNumber, username, courses);
    }

    public void onInstallmentTextChanged(CharSequence noOfInstallments) {
        int noInstallment = 0;
        if (!noOfInstallments.toString().trim().isEmpty()) {
            try {
                noInstallment = Integer.parseInt(String.valueOf(noOfInstallments));

            } catch (NumberFormatException exception) {

                Timber.d("No of installments too large");
            }

        }
        this.noOfInstallments.setValue(noInstallment);

    }

    public void onFeesChanged(CharSequence noOfInstallments) {
        int noInstallment = 0;
        if (!noOfInstallments.toString().trim().isEmpty()) {
            try {
                noInstallment = Integer.parseInt(String.valueOf(noOfInstallments));

            } catch (NumberFormatException exception) {

                Timber.d("Fees Amount too large");
            }

        }
        this.totalFees.setValue(noInstallment);

    }

    public void onDownPaymentChanged(CharSequence noOfInstallments) {
        int noInstallment = 0;
        if (!noOfInstallments.toString().trim().isEmpty()) {
            try {
                noInstallment = Integer.parseInt(String.valueOf(noOfInstallments));

            } catch (NumberFormatException exception) {

                Timber.d("No of downpayments too large");
            }

        }
        this.downPayment.setValue(noInstallment);

    }

    public LiveData<String> registerStudent(String name, String phone, String email, String course, String fees,
                                            String downpayment, String noofinstalments, List<Installments> installmentsList) {
        return registrationRepository.registerStudent(name, phone, email, course, fees,
                downpayment, noofinstalments, installmentsList);

    }

    public Fragment getFacultyFragment(int navindex) {
        switch (navindex) {
            case 0:
                fragmentTitle.setValue("Home");
                return new HomeFragmentFaculty();

            case 1:
                fragmentTitle.setValue("Attendance");
                return new AttendanceFragment();


            case 2:
                fragmentTitle.setValue("Notification");
                return new NotificationsFragment();
            case 3:
                fragmentTitle.setValue("Broadcast");
                return new BroadcastFragment();
            case 4:
                fragmentTitle.setValue("About Developers");
                return new AboutDevelopersFragment();

            case 5:
                fragmentTitle.setValue("Privacy Policy");
                return new PrivacyPolicyFragment();
            default:
                fragmentTitle.setValue("Home");
                return new HomeFragmentFaculty();

        }
    }

    public Fragment getCounsellorFragment(int navindex) {
        switch (navindex) {
            case 0:
                fragmentTitle.setValue("Home");
                return new HomeFragmentCounsellor();
            case 1:
                fragmentTitle.setValue("Fees");
                return new FeesStatusFragment();

            case 2:
                fragmentTitle.setValue("Attendance");
                return new AttendanceFragment();
            case 3:
                fragmentTitle.setValue("Completed Batches");
                return new CompletionBatchesFragment();
            case 4:
                fragmentTitle.setValue("Notification");
                return new NotificationsFragment();

            case 5:
                fragmentTitle.setValue("Broadcast");
                return new BroadcastFragment();
            case 6:
                fragmentTitle.setValue("About Developers");
                return new AboutDevelopersFragment();
            case 7:
                fragmentTitle.setValue("Privacy Policy");
                return new PrivacyPolicyFragment();

            default:
                fragmentTitle.setValue("Home");
                return new HomeFragmentCounsellor();

        }
    }

    public Fragment getStudentFragment(int navindex) {
        switch (navindex) {
            case 0:
                fragmentTitle.setValue("Home");
                return new HomeFragmentStudent();
            case 1:
                //syllabus
                fragmentTitle.setValue("Syllabus");
                return new SyllabusFragment();

            case 2:
                fragmentTitle.setValue("Notification");
                // notifications fragment
                return new NotificationsFragment();


            case 3:
                // broadcast fragment
                fragmentTitle.setValue("Refer Friend");
                return new Refer_Friendfragment();
            case 4:
                fragmentTitle.setValue("Feedback");
                return new FeedbackFragment();
            case 5:
                fragmentTitle.setValue("Payment");
                return new PaymentFragment();
            case 6:
                //contactadmin
                fragmentTitle.setValue("Contact Us");
                return new ContactFragment();

            case 7:
                fragmentTitle.setValue("About Developers");
                // settings fragment
                return new AboutDevelopersFragment();
            case 8:
                fragmentTitle.setValue("Privacy Policy");
                //  privacypolicy fragment
                return new PrivacyPolicyFragment();
            default:
                fragmentTitle.setValue("Home");
                return new HomeFragmentStudent();
        }

    }

}


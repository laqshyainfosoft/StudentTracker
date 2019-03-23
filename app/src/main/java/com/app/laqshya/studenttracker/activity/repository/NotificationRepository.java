package com.app.laqshya.studenttracker.activity.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.app.laqshya.studenttracker.activity.model.AdminNotification;
import com.app.laqshya.studenttracker.activity.model.FacultyNotification;
import com.app.laqshya.studenttracker.activity.model.SyllabusList;
import com.app.laqshya.studenttracker.activity.service.EduTrackerService;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class NotificationRepository {
    private EduTrackerService eduTrackerService;

    public NotificationRepository(EduTrackerService eduTrackerService) {
        this.eduTrackerService = eduTrackerService;
    }

    public LiveData<List<FacultyNotification>> getNotificationForFaculty(String facultyid) {
        MutableLiveData<List<FacultyNotification>> facultyList = new MutableLiveData<>();
        eduTrackerService.getFacultyNotification(facultyid).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<List<FacultyNotification>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(List<FacultyNotification> facultyNotifications) {
                facultyList.postValue(facultyNotifications);

            }

            @Override
            public void onError(Throwable e) {

            }
        });
        return facultyList;


    }
    public LiveData<List<AdminNotification>> getAllnotificationsforadmin() {
        MutableLiveData<List<AdminNotification>> adminlist = new MutableLiveData<>();
        eduTrackerService.getallnotifications().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<List<AdminNotification>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(List<AdminNotification> adminNotifications) {
                adminlist.postValue(adminNotifications);

            }

            @Override
            public void onError(Throwable e) {

            }
        });
        return adminlist;


    }
    public LiveData<SyllabusList> getSyllabusForStudent(String student_mobile) {
        MutableLiveData<SyllabusList> liveData = new MutableLiveData<>();
      eduTrackerService.getSyllabusCovered(student_mobile).subscribeOn(Schedulers.io())
              .observeOn(AndroidSchedulers.mainThread())
              .subscribe(new SingleObserver<List<SyllabusList.Syllabus>>() {
                  @Override
                  public void onSubscribe(Disposable d) {

                  }

                  @Override
                  public void onSuccess(List<SyllabusList.Syllabus> syllabi) {
                            liveData.postValue(new SyllabusList(syllabi));
                  }

                  @Override
                  public void onError(Throwable e) {
                    liveData.postValue(new SyllabusList(e));
                  }
              });

        return liveData;


    }

    public LiveData<List<FacultyNotification>> getStudentNotification(String studentmobile) {
        MutableLiveData<List<FacultyNotification>> studentListMutableLiveData = new MutableLiveData<>();
        eduTrackerService.getStudentNotification(studentmobile).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<List<FacultyNotification>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(List<FacultyNotification> facultyNotifications) {
                studentListMutableLiveData.postValue(facultyNotifications);

            }

            @Override
            public void onError(Throwable e) {

            }
        });
        return studentListMutableLiveData;


    }
}

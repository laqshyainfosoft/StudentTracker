package com.app.laqshya.studenttracker.activity.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.app.laqshya.studenttracker.activity.model.CourseList;
import com.app.laqshya.studenttracker.activity.model.FacultyList;
import com.app.laqshya.studenttracker.activity.service.EduTrackerService;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class AddBatchRepository {
    private EduTrackerService eduTrackerService;

    public AddBatchRepository(EduTrackerService eduTrackerService) {
        this.eduTrackerService = eduTrackerService;
    }

    public LiveData<List<CourseList>> getCourseList() {
        MutableLiveData<List<CourseList>> listMutableLiveData = new MutableLiveData<>();
        eduTrackerService.getCoursesForFacultyRegistration().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<CourseList>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(List<CourseList> courseLists) {
                        if (courseLists != null && courseLists.size() > 0)
                            listMutableLiveData.setValue(courseLists);

                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
        return listMutableLiveData;

    }

    public LiveData<List<FacultyList>> getFacultyList() {
        MutableLiveData<List<FacultyList>> facultyLiveData = new MutableLiveData<>();

        return facultyLiveData;
    }
}

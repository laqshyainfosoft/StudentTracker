package com.app.laqshya.studenttracker.activity.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.app.laqshya.studenttracker.activity.model.CourseModuleList;
import com.app.laqshya.studenttracker.activity.model.Schedule;
import com.app.laqshya.studenttracker.activity.service.EduTrackerService;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import io.reactivex.Scheduler;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import timber.log.Timber;

public class PDFRepository {
   private EduTrackerService eduTrackerService;

    public PDFRepository(EduTrackerService eduTrackerService) {
        this.eduTrackerService = eduTrackerService;
    }

    public LiveData<List<CourseModuleList>> getModules(){


//        eduTrackerService.getAllModules().
        MutableLiveData<List<CourseModuleList>> course=new MutableLiveData<>();

        eduTrackerService.getAllModules().subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<List<CourseModuleList>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(List<CourseModuleList> courseModuleLists) {
                        course.postValue(courseModuleLists);


                    }

                    @Override
                    public void onError(Throwable e) {


                    }
                });
        return course;
    }
    public LiveData<List<CourseModuleList>> getModulesForFaculty(String facultyId){


//        eduTrackerService.getAllModules().
        MutableLiveData<List<CourseModuleList>> course=new MutableLiveData<>();

        eduTrackerService.getAllModulesForFaculty(facultyId).subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<List<CourseModuleList>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(List<CourseModuleList> courseModuleLists) {
                        course.postValue(courseModuleLists);


                    }

                    @Override
                    public void onError(Throwable e) {


                    }
                });
        return course;
    }
}

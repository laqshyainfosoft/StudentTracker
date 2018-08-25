package com.app.laqshya.studenttracker.activity.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.app.laqshya.studenttracker.activity.model.BatchDetails;
import com.app.laqshya.studenttracker.activity.model.CourseList;
import com.app.laqshya.studenttracker.activity.model.CourseModuleList;
import com.app.laqshya.studenttracker.activity.model.FacultyList;
import com.app.laqshya.studenttracker.activity.model.StudentInfo;
import com.app.laqshya.studenttracker.activity.service.EduTrackerService;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

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
        eduTrackerService.getFacultyList().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<FacultyList>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(List<FacultyList> facultyLists) {
                        if (facultyLists != null && facultyLists.size() > 0) {
                            facultyLiveData.setValue(facultyLists);
                        }

                    }

                    @Override
                    public void onError(Throwable e) {


                    }
                });

        return facultyLiveData;
    }

    public LiveData<List<CourseModuleList>> getCourseModule(String course) {
        MutableLiveData<List<CourseModuleList>> response = new MutableLiveData<>();
        eduTrackerService.getModuleForCourse(course)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<CourseModuleList>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(List<CourseModuleList> strings) {
                        response.setValue(strings);

                    }

                    @Override
                    public void onError(Throwable e) {


                    }
                });
        return response;
    }

    public LiveData<String> createBatch(BatchDetails batchDetails) {
        MutableLiveData<String> responseLiveData = new MutableLiveData<>();
        eduTrackerService.createBatch(batchDetails).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
//                .retryWhen(throwableFlowable ->
//                                throwableFlowable.flatMap(throwable -> Flowable.timer(4, TimeUnit.SECONDS))
//                                        .zipWith(Flowable.range(1, 3), (n, i) -> i))
                .subscribe(new SingleObserver<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(String s) {
                        responseLiveData.postValue(s);

                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.d(e);
                        responseLiveData.postValue("Error while creating batch");

                    }
                });
        return responseLiveData;

    }

    public LiveData<List<StudentInfo>> getStudentForBatch(String coursename, String coursemodulename) {
        MutableLiveData<List<StudentInfo>> studentListLive = new MutableLiveData<>();
        eduTrackerService.getStudentNameForBatches(coursename, coursemodulename)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).
                retryWhen(throwableFlowable ->
                        throwableFlowable.flatMap(throwable -> Flowable.timer(4, TimeUnit.SECONDS))
                                .zipWith(Flowable.range(1, 3), (n, i) -> i))
                .subscribe(new SingleObserver<List<StudentInfo>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(List<StudentInfo> strings) {
                        studentListLive.setValue(strings);

                    }

                    @Override
                    public void onError(Throwable e) {


                    }
                });
        return studentListLive;

    }
}

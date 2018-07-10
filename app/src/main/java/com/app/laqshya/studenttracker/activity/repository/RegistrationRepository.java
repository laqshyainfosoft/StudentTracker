package com.app.laqshya.studenttracker.activity.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.app.laqshya.studenttracker.activity.model.CenterList;
import com.app.laqshya.studenttracker.activity.model.CourseList;
import com.app.laqshya.studenttracker.activity.model.Installments;
import com.app.laqshya.studenttracker.activity.model.StudentInfo;
import com.app.laqshya.studenttracker.activity.service.EduTrackerService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import timber.log.Timber;

public class RegistrationRepository {
    private EduTrackerService eduTrackerService;

    public RegistrationRepository(EduTrackerService eduTrackerService) {
        this.eduTrackerService = eduTrackerService;
    }

    public LiveData<List<String>> getCenterList() {
        MutableLiveData<List<String>> listLiveData = new MutableLiveData<>();
        eduTrackerService.getCenterList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<CenterList>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onSuccess(List<CenterList> centerLists) {
                        if (centerLists != null && centerLists.size() > 0) {
                            List<String> namesList = new ArrayList<>();

                            for (int i = 0; i < centerLists.size(); i++) {
                                String name = centerLists.get(i).getCentername();
                                namesList.add(name);


                            }
                            listLiveData.setValue(namesList);

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.d(e);

                    }
                });
        return listLiveData;

    }

    public LiveData<String> registerFaculty(String email, String
            mobile, String Facultyname, String courses) {
        MutableLiveData<String> regResponse = new MutableLiveData<>();
        eduTrackerService.registerFaculty(email, mobile, Facultyname, courses)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(ResponseBody responseBody) {
                        try {
                            String response = responseBody.string();
                            regResponse.setValue(response);
                        } catch (IOException e) {
                            e.printStackTrace();
                            regResponse.setValue("error");
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        regResponse.setValue("error");

                    }
                });
        return regResponse;
    }


    public LiveData<String> registerCounsellor(String email, String centerMobile, String counsellorMobile
            , String centername, String counsellorname) {
        MutableLiveData<String> regResponse = new MutableLiveData<>();
        eduTrackerService.registerCounsellor(email, counsellorMobile, centerMobile, counsellorname, centername)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(ResponseBody responseBody) {
                        try {
                            String response = responseBody.string();
                            regResponse.setValue(response);
                        } catch (IOException e) {
                            e.printStackTrace();
                            regResponse.setValue("error");
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        regResponse.setValue("error");

                    }
                });
        return regResponse;
    }

    public LiveData<List<String>> getCourse() {
        MutableLiveData<List<String>> coursesList = new MutableLiveData<>();
        eduTrackerService.getCoursesForFacultyRegistration()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<CourseList>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(List<CourseList> courseLists) {
                        List<String> courseList = new ArrayList<>();
                        for (int i = 0; i < courseLists.size(); i++) {
                            String course = courseLists.get(i).getCourse_name();
                            courseList.add(course);

                        }
                        coursesList.setValue(courseList);

                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.d("%s", e.getLocalizedMessage());

                    }
                });
        return coursesList;
    }

    public LiveData<String> registerStudent(String name, String phone, String email, String course, String fees, String downpayment, String noofinstalments, List<Installments> installmentsList) {
        MutableLiveData<String> response = new MutableLiveData<>();
        StudentInfo studentInfo = new StudentInfo(name, email, phone, course, downpayment, fees, installmentsList);
        eduTrackerService.registerStudent(studentInfo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(ResponseBody responseBody) {
                        try {
                            response.setValue(responseBody.string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        response.setValue("Error");

                    }
                });
        return response;
    }
}

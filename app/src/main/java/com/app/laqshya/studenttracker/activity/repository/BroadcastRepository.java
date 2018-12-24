package com.app.laqshya.studenttracker.activity.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.app.laqshya.studenttracker.activity.model.StudentInfo;
import com.app.laqshya.studenttracker.activity.service.EduTrackerService;

import java.io.IOException;
import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class BroadcastRepository {
  private   EduTrackerService eduTrackerService;
    public BroadcastRepository(EduTrackerService eduTrackerService) {
        this.eduTrackerService=eduTrackerService;

    }
    public LiveData<StudentInfo.StudentInfoList> getBatchForCounsellor(String centername) {
        MutableLiveData<StudentInfo.StudentInfoList> liveData = new MutableLiveData<>();
        eduTrackerService.getStudentsForCentersNotification(centername).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<StudentInfo>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(List<StudentInfo> studentInfos) {
                        liveData.postValue(new StudentInfo.StudentInfoList(studentInfos));

                    }

                    @Override
                    public void onError(Throwable e) {
                        liveData.postValue(new StudentInfo.StudentInfoList(e));

                    }
                });

        return liveData;
    }
    public LiveData<String> sendSingleStudentNotiication(String counsellorphone,String phone, String title,
                                                               String message, String flag) {
        MutableLiveData<String> liveData = new MutableLiveData<>();
        eduTrackerService.sendNotificationtosingleStudent(counsellorphone,phone,title,message,flag).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

                .subscribe(new SingleObserver<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(ResponseBody responseBody) {
                        try {
                            liveData.postValue(responseBody.string());
                        } catch (IOException e) {
                            e.printStackTrace();
                            liveData.postValue("Error");
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        liveData.postValue(e.getMessage());

                    }
                });

        return liveData;
    }
}

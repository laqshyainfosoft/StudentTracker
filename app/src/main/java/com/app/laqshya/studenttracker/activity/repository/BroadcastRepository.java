package com.app.laqshya.studenttracker.activity.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.app.laqshya.studenttracker.activity.model.BatchInformationResponse;
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
    public LiveData<BatchInformationResponse> getBatchForFaculty(String facultyName) {
        MutableLiveData<BatchInformationResponse> liveData = new MutableLiveData<>();
        eduTrackerService.getBatchesForFaculty(facultyName).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<List<BatchInformationResponse.BatchInformation>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(List<BatchInformationResponse.BatchInformation> batchInformations) {
                liveData.postValue(new BatchInformationResponse(batchInformations));

            }

            @Override
            public void onError(Throwable e) {
                liveData.postValue(new BatchInformationResponse(e));

            }
        });

        return liveData;
    }
    public LiveData<String> sendSingleStudentNotiication(String counsellorphone,String batchid, String title,
                                                               String message, String flag) {
        MutableLiveData<String> liveData = new MutableLiveData<>();
        eduTrackerService.sendNotificationtosingleStudent(counsellorphone,batchid,title,message,flag).subscribeOn(Schedulers.io())
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
    public LiveData<String> sendSingleBatchNotification(String counsellorphone,String batchid, String title,
                                                         String message,String facultyid, String flag) {
        MutableLiveData<String> liveData = new MutableLiveData<>();
        eduTrackerService.sendNotificationtosingleBatch(counsellorphone,batchid,title,message,facultyid,flag).subscribeOn(Schedulers.io())
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
    public LiveData<String> sendSingleBatchFacultyNotification(String batchid, String title,
                                                        String message,String facultyid, String flag) {
        MutableLiveData<String> liveData = new MutableLiveData<>();
        eduTrackerService.sendNotificationFacultyBatch(batchid,title,message,facultyid,flag).subscribeOn(Schedulers.io())
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
    public LiveData<String> sendAllBatchNotification(String counsellorphone, String title,
                                                        String message) {
        MutableLiveData<String> liveData = new MutableLiveData<>();
        eduTrackerService.sendNotificationtoeveryone(counsellorphone,title,message).subscribeOn(Schedulers.io())
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
    public LiveData<String> sendDueFeesNotification(String counsellorphone, String title,
                                                     String message, String student_id) {
        MutableLiveData<String> liveData = new MutableLiveData<>();
        eduTrackerService.sendNotificationtopendingStudents(counsellorphone,title,message,student_id).subscribeOn(Schedulers.io())
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

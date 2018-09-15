package com.app.laqshya.studenttracker.activity.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;


import com.app.laqshya.studenttracker.activity.model.BatchInformationResponse;
import com.app.laqshya.studenttracker.activity.model.EditBatchScheduleList;
import com.app.laqshya.studenttracker.activity.model.FacultyList;
import com.app.laqshya.studenttracker.activity.service.EduTrackerService;

import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class EditBatchRepository {
    private EduTrackerService eduTrackerService;
    public EditBatchRepository(EduTrackerService eduTrackerService) {
        this.eduTrackerService=eduTrackerService;
    }
    public LiveData<BatchInformationResponse> getBatchForCounsellor(String center){
        MutableLiveData<BatchInformationResponse> liveData=new MutableLiveData<>();
        eduTrackerService.getBatch(center).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<BatchInformationResponse.BatchInformation>>() {
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
    public LiveData<EditBatchScheduleList> getBatchSchedule(String batchid){
        MutableLiveData<EditBatchScheduleList> editBatchScheduleListMutableLiveData=new MutableLiveData<>();
        eduTrackerService.getSchedule(batchid).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<EditBatchScheduleList.EditbatchSchedule>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(List<EditBatchScheduleList.EditbatchSchedule> editbatchSchedules) {
                        editBatchScheduleListMutableLiveData.postValue(new EditBatchScheduleList(editbatchSchedules));

                    }

                    @Override
                    public void onError(Throwable e) {
                        editBatchScheduleListMutableLiveData.postValue(new EditBatchScheduleList(e));
                    }
                });
        return editBatchScheduleListMutableLiveData;
    }
}

package com.app.laqshya.studenttracker.activity.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;


import com.app.laqshya.studenttracker.activity.model.BatchInformationResponse;
import com.app.laqshya.studenttracker.activity.service.EduTrackerService;

import java.util.List;

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
}

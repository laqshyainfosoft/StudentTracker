package com.app.laqshya.studenttracker.activity.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

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
    public LiveData<String> uploadFile(Map<String, RequestBody> description, List<MultipartBody.Part> files){

        MutableLiveData<String> stringLiveData=new MutableLiveData<>();
        eduTrackerService.uploadPdf(description,files).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<ResponseBody>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(ResponseBody responseBody) {
//                try {

                    Timber.d(responseBody.toString());
                    stringLiveData.postValue(responseBody.toString());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                    stringLiveData.postValue("Error");
//                }

            }

            @Override
            public void onError(Throwable e) {
                stringLiveData.postValue("Error"+e.getMessage());

            }
        });
        return stringLiveData;
    }
}

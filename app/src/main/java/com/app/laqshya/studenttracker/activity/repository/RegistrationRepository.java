package com.app.laqshya.studenttracker.activity.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.app.laqshya.studenttracker.activity.model.CenterList;
import com.app.laqshya.studenttracker.activity.service.EduTrackerService;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class RegistrationRepository {
    private EduTrackerService eduTrackerService;
    public RegistrationRepository(EduTrackerService eduTrackerService) {
        this.eduTrackerService=eduTrackerService;
    }

    public LiveData<List<String>> getCenterList(){
        MutableLiveData<List<String>> listLiveData=new MutableLiveData<>();
        eduTrackerService.getCenterList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<CenterList>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onSuccess(List<CenterList> centerLists) {
                        if(centerLists!=null && centerLists.size()>0){
                            List<String> namesList=new ArrayList<>();

                            for(int i=0;i<centerLists.size();i++){
                                String name=centerLists.get(i).getCentername();
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

}
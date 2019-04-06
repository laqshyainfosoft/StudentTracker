package com.app.laqshya.studenttracker.activity.repository;

import com.app.laqshya.studenttracker.activity.model.student_self.ManageStudentInfoResponse;
import com.app.laqshya.studenttracker.activity.service.EduTrackerService;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ManageStudentInfoRepository {
    private EduTrackerService eduTrackerService;

    public ManageStudentInfoRepository(EduTrackerService eduTrackerService) {
        this.eduTrackerService = eduTrackerService;
    }
    public LiveData<ManageStudentInfoResponse> getStudent(){
        MutableLiveData<ManageStudentInfoResponse> liveData=new MutableLiveData<>();
        eduTrackerService.getAdminStudentIInfo().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).
                subscribe(new SingleObserver<List<ManageStudentInfoResponse.ManageStudentInfo>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(List<ManageStudentInfoResponse.ManageStudentInfo> manageStudentInfos) {
                        liveData.postValue(new ManageStudentInfoResponse(manageStudentInfos));

                    }

                    @Override
                    public void onError(Throwable e) {
                        liveData.postValue(new ManageStudentInfoResponse(e));

                    }
                });
    return liveData;
    }
    public LiveData<ManageStudentInfoResponse> getStudentForCounsellor(String centername){
        MutableLiveData<ManageStudentInfoResponse> liveData=new MutableLiveData<>();
        eduTrackerService.getStudentIInfoForCounsellorManagement(centername).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).
                subscribe(new SingleObserver<List<ManageStudentInfoResponse.ManageStudentInfo>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(List<ManageStudentInfoResponse.ManageStudentInfo> manageStudentInfos) {
                        liveData.postValue(new ManageStudentInfoResponse(manageStudentInfos));

                    }

                    @Override
                    public void onError(Throwable e) {
                        liveData.postValue(new ManageStudentInfoResponse(e));

                    }
                });
        return liveData;
    }
}

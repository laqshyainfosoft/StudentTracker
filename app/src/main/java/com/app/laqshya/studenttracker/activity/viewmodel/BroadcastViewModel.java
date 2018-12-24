package com.app.laqshya.studenttracker.activity.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.app.laqshya.studenttracker.activity.model.StudentInfo;
import com.app.laqshya.studenttracker.activity.repository.BroadcastRepository;

import okhttp3.ResponseBody;

public class BroadcastViewModel extends ViewModel {
    private BroadcastRepository broadcastRepository;

    public BroadcastViewModel(BroadcastRepository broadcastRepository) {
        this.broadcastRepository=broadcastRepository;

    }
    public LiveData<StudentInfo.StudentInfoList> getStudents(String centername){
        return broadcastRepository.getBatchForCounsellor(centername);
    }
    public LiveData<String> sendSingleStudentNotification(String counsellorphone,String phone,String title,String message
    ,String flag){
            return broadcastRepository.sendSingleStudentNotiication(counsellorphone,phone,title,message,flag);
    }
}

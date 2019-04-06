package com.app.laqshya.studenttracker.activity.viewmodel;

import com.app.laqshya.studenttracker.activity.model.student_self.ManageStudentInfoResponse;
import com.app.laqshya.studenttracker.activity.repository.ManageStudentInfoRepository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class ManageStudentsViewModel extends ViewModel {
    private ManageStudentInfoRepository manageStudentInfoRepository;

    public ManageStudentsViewModel(ManageStudentInfoRepository manageStudentInfoRepository) {
        this.manageStudentInfoRepository = manageStudentInfoRepository;
    }
    public LiveData<ManageStudentInfoResponse> getStudents(){
        return manageStudentInfoRepository.getStudent();
    }
    public LiveData<ManageStudentInfoResponse> getStudentsForCounsellor(String centername){
        return manageStudentInfoRepository.getStudentForCounsellor(centername);

    }
}

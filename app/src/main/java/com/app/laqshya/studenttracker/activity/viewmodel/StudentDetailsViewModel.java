package com.app.laqshya.studenttracker.activity.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.app.laqshya.studenttracker.activity.model.Installments;
import com.app.laqshya.studenttracker.activity.model.PDFDoc;
import com.app.laqshya.studenttracker.activity.model.student_self.StudentDetailsModel;
import com.app.laqshya.studenttracker.activity.repository.StudentDetailsRepository;

import java.util.List;

public class StudentDetailsViewModel extends ViewModel {
    private StudentDetailsRepository studentDetailsRepository;

    public StudentDetailsViewModel(StudentDetailsRepository studentDetailsRepository) {
        this.studentDetailsRepository = studentDetailsRepository;
    }
    public LiveData<StudentDetailsModel> getStudentDetails(String mobile){
        return studentDetailsRepository.getStudent(mobile);
    }
    public LiveData<List<Installments>> getStudentDetailsInstallments(String mobile){
        return studentDetailsRepository.getStudentInstallments(mobile);
    }
    public LiveData<PDFDoc.PDFList> getPDFS(String studentid){
        return studentDetailsRepository.getStudentPDFS(studentid);

    }

}

package com.app.laqshya.studenttracker.activity.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.app.laqshya.studenttracker.activity.model.CourseList;
import com.app.laqshya.studenttracker.activity.model.CourseModuleList;
import com.app.laqshya.studenttracker.activity.model.FacultyList;
import com.app.laqshya.studenttracker.activity.model.StudentInfo;
import com.app.laqshya.studenttracker.activity.repository.AddBatchRepository;

import java.util.List;

public class AddSchedulesViewModel extends ViewModel {
    private AddBatchRepository addBatchRepository;


    public AddSchedulesViewModel(AddBatchRepository addBatchRepository) {
        this.addBatchRepository = addBatchRepository;
    }

    public LiveData<List<CourseList>> getCourseList() {
        return addBatchRepository.getCourseList();


    }

    public LiveData<List<FacultyList>> getFacultyList() {
        return addBatchRepository.getFacultyList();

    }
    public LiveData<List<CourseModuleList>> getCourseModule(String course){
        return addBatchRepository.getCourseModule(course);
    }
    public LiveData<List<StudentInfo>> showStudentsForBatch(String coursename,String coursemodulename)
    {
        return addBatchRepository.getStudentForBatch(coursename,coursemodulename);

    }

}

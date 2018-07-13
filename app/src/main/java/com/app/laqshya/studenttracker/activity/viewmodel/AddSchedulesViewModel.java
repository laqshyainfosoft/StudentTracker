package com.app.laqshya.studenttracker.activity.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.app.laqshya.studenttracker.activity.model.CourseList;
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
}

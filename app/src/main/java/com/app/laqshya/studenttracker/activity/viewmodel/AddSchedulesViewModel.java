package com.app.laqshya.studenttracker.activity.viewmodel;

import android.arch.lifecycle.ViewModel;

import com.app.laqshya.studenttracker.activity.repository.AddBatchRepository;

public class AddSchedulesViewModel extends ViewModel {
    private AddBatchRepository addBatchRepository;


    public AddSchedulesViewModel(AddBatchRepository addBatchRepository) {
        this.addBatchRepository = addBatchRepository;
    }
//    public LiveData<0>
}

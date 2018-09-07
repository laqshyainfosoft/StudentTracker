package com.app.laqshya.studenttracker.activity.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;


import com.app.laqshya.studenttracker.activity.model.BatchInformationResponse;
import com.app.laqshya.studenttracker.activity.repository.EditBatchRepository;

import java.util.List;

public class EditSchedulesViewModel extends ViewModel {
    private EditBatchRepository editBatchRepository;

    public EditSchedulesViewModel(EditBatchRepository editBatchRepository) {
        this.editBatchRepository=editBatchRepository;
    }
    public LiveData<BatchInformationResponse> getBatchesForCounsellor(String center){
        return editBatchRepository.getBatchForCounsellor(center);

    }
}

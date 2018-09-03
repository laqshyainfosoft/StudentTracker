package com.app.laqshya.studenttracker.activity.factory;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.NonNull;

import com.app.laqshya.studenttracker.activity.repository.EditBatchRepository;
import com.app.laqshya.studenttracker.activity.viewmodel.EditSchedulesViewModel;

public class EditSchedulesViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private EditBatchRepository editBatchRepository;
    public EditSchedulesViewModelFactory(EditBatchRepository batchRepository) {
        editBatchRepository=batchRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T)new EditSchedulesViewModel(editBatchRepository);
    }
}

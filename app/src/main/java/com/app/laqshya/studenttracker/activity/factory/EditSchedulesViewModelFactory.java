package com.app.laqshya.studenttracker.activity.factory;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.annotation.NonNull;

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

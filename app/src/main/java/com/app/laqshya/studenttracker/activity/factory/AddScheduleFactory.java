package com.app.laqshya.studenttracker.activity.factory;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.annotation.NonNull;

import com.app.laqshya.studenttracker.activity.repository.AddBatchRepository;
import com.app.laqshya.studenttracker.activity.viewmodel.AddSchedulesViewModel;

public class AddScheduleFactory extends ViewModelProvider.NewInstanceFactory {
    private AddBatchRepository addBatchRepository;

    public AddScheduleFactory(AddBatchRepository batchRepository) {
        addBatchRepository = batchRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new AddSchedulesViewModel(addBatchRepository);
    }
}

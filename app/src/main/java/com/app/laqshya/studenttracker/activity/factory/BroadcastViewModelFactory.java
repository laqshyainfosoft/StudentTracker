package com.app.laqshya.studenttracker.activity.factory;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.annotation.NonNull;

import com.app.laqshya.studenttracker.activity.repository.BroadcastRepository;
import com.app.laqshya.studenttracker.activity.viewmodel.BroadcastViewModel;

public class BroadcastViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private BroadcastRepository broadcastRepository;
    public BroadcastViewModelFactory(BroadcastRepository batchRepository) {
        broadcastRepository=batchRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new BroadcastViewModel(broadcastRepository);
    }
}

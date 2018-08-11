package com.app.laqshya.studenttracker.activity.factory;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.NonNull;

import com.app.laqshya.studenttracker.activity.repository.RegistrationRepository;
import com.app.laqshya.studenttracker.activity.viewmodel.NavDrawerViewModel;

public class RegistrationFactory  extends ViewModelProvider.NewInstanceFactory{
    private RegistrationRepository registrationRepository;

    public RegistrationFactory(RegistrationRepository registrationRepository) {
        this.registrationRepository = registrationRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T)new NavDrawerViewModel(registrationRepository);
    }
}

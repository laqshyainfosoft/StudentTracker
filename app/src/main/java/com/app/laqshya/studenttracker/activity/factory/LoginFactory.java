package com.app.laqshya.studenttracker.activity.factory;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.app.laqshya.studenttracker.activity.repository.LoginRepository;
import com.app.laqshya.studenttracker.activity.viewmodel.LoginViewModel;

public class LoginFactory extends ViewModelProvider.NewInstanceFactory {
    private LoginRepository loginRepository;

    public LoginFactory(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new LoginViewModel(loginRepository);
    }
}

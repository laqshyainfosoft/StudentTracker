package com.app.laqshya.studenttracker.activity.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.app.laqshya.studenttracker.activity.repository.LoginRepository;

public class LoginViewModel extends ViewModel {


    private LoginRepository loginRepository;

    public LoginViewModel(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    public LiveData<Integer> loginUser(String phone, String passsword, int flag) {
      return loginRepository.loginUser(phone,passsword,flag);


    }
}

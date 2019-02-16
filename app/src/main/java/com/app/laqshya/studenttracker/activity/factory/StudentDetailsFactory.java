package com.app.laqshya.studenttracker.activity.factory;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.app.laqshya.studenttracker.activity.repository.StudentDetailsRepository;
import com.app.laqshya.studenttracker.activity.viewmodel.StudentDetailsViewModel;

public class StudentDetailsFactory extends ViewModelProvider.NewInstanceFactory {
    private StudentDetailsRepository studentDetailsRepository;

    public StudentDetailsFactory(StudentDetailsRepository studentDetailsRepository) {
        this.studentDetailsRepository = studentDetailsRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new StudentDetailsViewModel(studentDetailsRepository);
    }
}

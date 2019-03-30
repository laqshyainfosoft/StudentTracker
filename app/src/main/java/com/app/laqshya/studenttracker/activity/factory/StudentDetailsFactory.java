package com.app.laqshya.studenttracker.activity.factory;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.annotation.NonNull;

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

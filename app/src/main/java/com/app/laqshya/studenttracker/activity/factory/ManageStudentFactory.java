package com.app.laqshya.studenttracker.activity.factory;

import com.app.laqshya.studenttracker.activity.repository.EditBatchRepository;
import com.app.laqshya.studenttracker.activity.repository.ManageStudentInfoRepository;
import com.app.laqshya.studenttracker.activity.viewmodel.EditSchedulesViewModel;
import com.app.laqshya.studenttracker.activity.viewmodel.ManageStudentsViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class ManageStudentFactory extends ViewModelProvider.NewInstanceFactory {
    private ManageStudentInfoRepository manageStudentInfoRepository;
    public ManageStudentFactory(ManageStudentInfoRepository manageStudentInfoRepository) {
        this.manageStudentInfoRepository=manageStudentInfoRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T)new ManageStudentsViewModel(manageStudentInfoRepository);
    }
}

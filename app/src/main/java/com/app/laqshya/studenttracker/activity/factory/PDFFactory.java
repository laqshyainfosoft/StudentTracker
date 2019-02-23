package com.app.laqshya.studenttracker.activity.factory;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.app.laqshya.studenttracker.activity.repository.PDFRepository;
import com.app.laqshya.studenttracker.activity.viewmodel.PDFViewModel;

public class PDFFactory extends ViewModelProvider.NewInstanceFactory {
    private PDFRepository pdfRepository;

    public PDFFactory(PDFRepository pdfRepository) {
        this.pdfRepository = pdfRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T)new PDFViewModel(pdfRepository);
    }
}

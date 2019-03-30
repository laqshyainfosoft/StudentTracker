package com.app.laqshya.studenttracker.activity.factory;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.annotation.NonNull;

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

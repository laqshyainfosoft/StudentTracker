package com.app.laqshya.studenttracker.activity.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.app.laqshya.studenttracker.activity.repository.PDFRepository;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class PDFViewModel extends ViewModel {
    PDFRepository pdfRepository;

    public PDFViewModel(PDFRepository pdfRepository) {
        this.pdfRepository = pdfRepository;
    }
    public LiveData<String> uploadPDF(Map<String, RequestBody> description, List<MultipartBody.Part> files){
        return pdfRepository.uploadFile(description,files);
    }
}

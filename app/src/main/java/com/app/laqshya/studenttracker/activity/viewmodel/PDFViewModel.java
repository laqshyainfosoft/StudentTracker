package com.app.laqshya.studenttracker.activity.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.app.laqshya.studenttracker.activity.model.CourseModuleList;
import com.app.laqshya.studenttracker.activity.repository.PDFRepository;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class PDFViewModel extends ViewModel {
    private static final String TAG_OUTPUT = "books";
    private PDFRepository pdfRepository;


    public PDFViewModel(PDFRepository pdfRepository) {


        this.pdfRepository = pdfRepository;


    }
//
//


    public LiveData<List<CourseModuleList>> getModules() {
        return pdfRepository.getModules();
    }
    public LiveData<List<CourseModuleList>> getModulesFaculty(String  facultyId) {
        return pdfRepository.getModulesForFaculty(facultyId);
    }
}

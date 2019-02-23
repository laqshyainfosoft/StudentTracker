package com.app.laqshya.studenttracker.activity.di;

import com.app.laqshya.studenttracker.activity.factory.BroadcastViewModelFactory;
import com.app.laqshya.studenttracker.activity.factory.PDFFactory;
import com.app.laqshya.studenttracker.activity.repository.BroadcastRepository;
import com.app.laqshya.studenttracker.activity.repository.PDFRepository;
import com.app.laqshya.studenttracker.activity.service.EduTrackerService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class PDFModule {
    @Provides
    @Singleton
    PDFFactory getPdfFactory(PDFRepository pdfRepository) {
        return new PDFFactory(pdfRepository);
    }

    @Provides
    PDFRepository getPdfRepository(EduTrackerService eduTrackerService) {
        return new PDFRepository(eduTrackerService);
    }

}

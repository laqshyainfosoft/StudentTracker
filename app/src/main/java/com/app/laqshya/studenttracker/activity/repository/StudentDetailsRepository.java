package com.app.laqshya.studenttracker.activity.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.app.laqshya.studenttracker.activity.model.Installments;
import com.app.laqshya.studenttracker.activity.model.PDFDoc;
import com.app.laqshya.studenttracker.activity.model.student_self.StudentDetailsModel;
import com.app.laqshya.studenttracker.activity.service.EduTrackerService;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class StudentDetailsRepository {
    private EduTrackerService eduTrackerService;

    public StudentDetailsRepository(EduTrackerService eduTrackerService) {
        this.eduTrackerService = eduTrackerService;
    }
    public LiveData<StudentDetailsModel> getStudent(String mobile){
        MutableLiveData<StudentDetailsModel> liveData=new MutableLiveData<>();
        eduTrackerService.getstudentSelfDetails(mobile).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<StudentDetailsModel>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(List<StudentDetailsModel> studentDetailsModel) {
                        liveData.postValue(studentDetailsModel.get(0));
//                        Timber.d(studentDetailsModel.get(0).getInstallments().toString());

                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.d(e);

                    }
                });
        return liveData;
    }
    public LiveData<List<Installments>> getStudentInstallments(String mobile){
        MutableLiveData<List<Installments>> liveData=new MutableLiveData<>();
        eduTrackerService.getstudentSelfDetailsInstallments(mobile).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
              .subscribe(new SingleObserver<List<Installments>>() {
                  @Override
                  public void onSubscribe(Disposable d) {

                  }

                  @Override

                  public void onSuccess(List<Installments> installments) {
                      liveData.postValue(installments);

                  }

                  @Override
                  public void onError(Throwable e) {
                      Timber.d(e);

                  }
              });
        return liveData;
    }
    public LiveData<PDFDoc.PDFList> getStudentPDFS(String mobile){
        MutableLiveData<PDFDoc.PDFList> liveData=new MutableLiveData<>();
        eduTrackerService.getPdfs(mobile).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<PDFDoc>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(List<PDFDoc> pdfDocs) {
                        liveData.postValue(new PDFDoc.PDFList(pdfDocs));

                    }

                    @Override
                    public void onError(Throwable e) {
                        liveData.postValue(new PDFDoc.PDFList(e));
                        Timber.d(e);

                    }
                });
        return liveData;
    }
}

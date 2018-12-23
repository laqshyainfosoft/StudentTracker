package com.app.laqshya.studenttracker.activity.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.app.laqshya.studenttracker.activity.model.LoginModel;
import com.app.laqshya.studenttracker.activity.service.EduTrackerService;
import com.app.laqshya.studenttracker.activity.utils.Constants;
import com.app.laqshya.studenttracker.activity.utils.SessionManager;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class LoginRepository {
    private MutableLiveData<Integer> loginStatus;
    private String  phone = "0";
    private int flag = 0;
    private SessionManager sessionManager;
    private EduTrackerService eduTrackerService;

    public LoginRepository(SessionManager sessionManager, EduTrackerService eduTrackerService) {
        this.sessionManager = sessionManager;
        this.eduTrackerService = eduTrackerService;
    }

    public LiveData<Integer> loginUser(String phone, String password, int flag) {
        loginStatus = new MutableLiveData<>();
        this.phone = phone;
        this.flag = flag;

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                String token=instanceIdResult.getToken();
                eduTrackerService.loginUSer(phone, password, flag, token)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new SingleObserver<LoginModel>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onSuccess(LoginModel loginModel) {


                                if (loginModel.getStatus() == 1) {
                                    loginStatus.setValue(1);
                                    saveValues(loginModel);
                                } else {
                                    loginStatus.setValue(0);
                                }

                            }


                            @Override
                            public void onError(Throwable e) {
                                loginStatus.setValue(0);

                            }

                        });
                Timber.d("Value of token %s",token);

            }
        }).addOnFailureListener(Timber::d);


        return loginStatus;

    }

    private void saveValues(LoginModel loginModel) {
        if (loginStatus != null && loginStatus.getValue() != null) {
            if (loginStatus.getValue() == 1) {
                sessionManager.saveUser(phone);

                sessionManager.saveLoggedInState(true);
                sessionManager.saveName(loginModel.getName());

            }
            switch (flag) {
                //Counsellor
                case 2:
                    sessionManager.saveUserCenter(loginModel.getCenter());
                    sessionManager.saveUser(phone);
                    sessionManager.saveType(Constants.COUNSELLOR);
                    break;
                case 1:
                    //Admin

                    sessionManager.saveType(Constants.ADMIN);
                    break;
                case 3:
                    //Faculty
                    sessionManager.saveType(Constants.FACULTY);
                    break;
                case 4:
                    //Student
                    sessionManager.saveType(Constants.STUDENT);
                    break;
            }
            Timber.d(sessionManager.getLoggedInType());
        }
    }
}

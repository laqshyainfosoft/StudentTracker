package com.app.laqshya.studenttracker.activity.service;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import timber.log.Timber;

public class MyFirebaseInstanceIdService extends FirebaseInstanceIdService {
    @Override
    public void onTokenRefresh() {
       String refreshedToken= FirebaseInstanceId.getInstance().getToken();
        Timber.d(refreshedToken);
    }
}

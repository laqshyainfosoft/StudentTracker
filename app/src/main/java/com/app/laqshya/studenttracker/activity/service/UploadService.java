package com.app.laqshya.studenttracker.activity.service;

import android.app.Service;
import android.arch.lifecycle.MutableLiveData;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;

import com.app.laqshya.studenttracker.activity.utils.Constants;
import com.app.laqshya.studenttracker.activity.utils.NotificationUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import dagger.android.AndroidInjection;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import timber.log.Timber;

public class UploadService extends Service {
    @Inject
    EduTrackerService eduTrackerService;
    private NotificationUtils notificationUtils;
    private ArrayList<File> fileList;
    private String coursename;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        AndroidInjection.inject(this);
        super.onCreate();
        notificationUtils = new NotificationUtils(this);
        NotificationCompat.Builder notificationCompatBuilder = notificationUtils.getAndroidChannelNotification("Uploading", "Please wait");
        notificationCompatBuilder.setProgress(100, 0, false);
        startForeground(Constants.NOTIFICATIONID, notificationCompatBuilder.build());


    }

    @Override

    public int onStartCommand(Intent intent, int flags, int startId) {


        fileList = (ArrayList<File>) intent.getSerializableExtra("fileList");
        coursename = intent.getStringExtra("coursename");
        Timber.d(coursename);
        Timber.d(fileList.get(0).getName());
        uploadFile();





        return START_STICKY;
    }

    @NonNull
    private RequestBody createPartFromString(String filename) {
        return RequestBody.create(
                okhttp3.MultipartBody.FORM, filename);
    }

    @NonNull
    private MultipartBody.Part prepareFilePart(File file) {

        // use the FileUtils to get the actual file by uri


        // create RequestBody instance from file
//        if(getActivity()!=null) {
        RequestBody requestFile =
                RequestBody.create(
                        MediaType.parse("application/pdf"),
                        file

                );
        return MultipartBody.Part.createFormData("files", file.getName(), requestFile);


        // MultipartBody.Part is used to send also the actual file name


    }

    private void uploadFile() {


        List<MultipartBody.Part> parts = new ArrayList<>();


        HashMap<String, RequestBody> map = new HashMap<>();


        if (coursename != null && !coursename.trim().isEmpty()) {


            for (int i = 0; i < fileList.size(); i++) {
//            File file=new File(fileList.get(i));
                Timber.d(Uri.fromFile(fileList.get(i)).toString());
                parts.add(prepareFilePart(fileList.get(i)));
                RequestBody description = createPartFromString(fileList.get(i).getName());
                map.put("files", description);

//            map.put()


            }
            for (Map.Entry mapEntry : map.entrySet()) {
                Timber.d(mapEntry.getKey() + " " + mapEntry.getValue());
            }
            uploadFile(createPartFromString(coursename), map, parts);

        } else {
            Timber.d("Error Occurred");

//            showToast("No book selected");
        }
//        Timber.d(Arrays.toString(parts.toArray()));


    }

    public void uploadFile(RequestBody name, Map<String, RequestBody> description, List<MultipartBody.Part> files) {


        eduTrackerService.uploadPdf(name, description, files).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<ResponseBody>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(ResponseBody responseBody) {
//                try {

                Timber.d(responseBody.toString());

                NotificationCompat.Builder notificationCompatBuilder = notificationUtils.getAndroidWorkCompleteChannelNotification("Successful", "Upload Completed");
                startForeground(Constants.NOTIFICATIONID, notificationCompatBuilder.build());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    stopForeground(STOP_FOREGROUND_DETACH);
                }
                else
                {
                    stopForeground(false);
                }
                stopSelf();
//

            }

            @Override
            public void onError(Throwable e) {

                NotificationCompat.Builder notificationCompatBuilder = notificationUtils.getAndroidWorkCompleteChannelNotification("Error Occurred", "Could not Upload File");


                startForeground(Constants.NOTIFICATIONID, notificationCompatBuilder.build());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    stopForeground(STOP_FOREGROUND_DETACH);
                }
                else {
                    stopForeground(false);
                }
                stopSelf();
//


            }
        });
//        return stringLiveData.getValue();
    }

}

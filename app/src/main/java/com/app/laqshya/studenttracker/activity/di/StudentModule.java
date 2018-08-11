package com.app.laqshya.studenttracker.activity.di;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.app.laqshya.studenttracker.activity.service.EduTrackerService;
import com.app.laqshya.studenttracker.activity.utils.SessionManager;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

@Module
public class StudentModule {
    @Provides
    @Singleton
    static Context getContext(Application application) {
        return application.getApplicationContext();
    }

    @Provides
    @Singleton
    static SharedPreferences getSharedPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Provides
    @Singleton
    static HttpLoggingInterceptor getHttpLoggingInterceptor() {

        return new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
    }

    @Provides
    @Singleton
    static OkHttpClient okHttpClient(HttpLoggingInterceptor loggingInterceptor) {
        return new OkHttpClient.Builder().addInterceptor(loggingInterceptor).
                connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS).build();
    }

    @Provides
    @Singleton
    static Retrofit getRetrofit(OkHttpClient okHttpClient) {
        return new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .addConverterFactory(ScalarsConverterFactory.create())
                .baseUrl(EduTrackerService.ENDPOINT)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build();

    }

    @Provides
    @Singleton
    static EduTrackerService getEdutrackerService(Retrofit retrofit) {
        return retrofit.create(EduTrackerService.class);
    }
    @Provides
    @Singleton
    static SessionManager getSessionManager(SharedPreferences sharedPreferences){
        return new SessionManager(sharedPreferences);
    }

}

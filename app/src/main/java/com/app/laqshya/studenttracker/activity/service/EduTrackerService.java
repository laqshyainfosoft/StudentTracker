package com.app.laqshya.studenttracker.activity.service;

import com.app.laqshya.studenttracker.activity.model.CenterList;
import com.app.laqshya.studenttracker.activity.model.LoginModel;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface EduTrackerService {
    String ENDPOINT = "http://10.0.0.6/student_tracker/";
    @FormUrlEncoded
    @POST("login.php")
    Single<LoginModel> loginUSer(@Field("mobile")String mobile, @Field("password")String password,
                                 @Field("flag")int flag);
    @GET("getCenterList.php")
    Single<List<CenterList>> getCenterList();

}

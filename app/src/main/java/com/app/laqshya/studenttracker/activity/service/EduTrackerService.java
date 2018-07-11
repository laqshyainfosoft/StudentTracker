package com.app.laqshya.studenttracker.activity.service;

import com.app.laqshya.studenttracker.activity.model.CenterList;
import com.app.laqshya.studenttracker.activity.model.CourseList;
import com.app.laqshya.studenttracker.activity.model.LoginModel;
import com.app.laqshya.studenttracker.activity.model.StudentInfo;

import java.util.List;

import io.reactivex.Single;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface EduTrackerService {
    String ENDPOINT = "http://192.168.0.113/student_tracker/";

    @FormUrlEncoded
    @POST("login.php")
    Single<LoginModel> loginUSer(@Field("mobile") String mobile, @Field("password") String password,
                                 @Field("flag") int flag);

    @GET("getCenterList.php")
    Single<List<CenterList>> getCenterList();

    @FormUrlEncoded
    @POST("registercounsellor.php")
    Single<ResponseBody> registerCounsellor(@Field("email") String email, @Field("counsellorNumber") String counsellorNumber
            , @Field("centernumber") String centerNumber, @Field("counsellorName") String counsellorName,
                                            @Field("centername") String centername);


    @GET("getCoursesForFacultyRegistration.php")
    Single<List<CourseList>> getCoursesForFacultyRegistration();

    @FormUrlEncoded
    @POST("registerFaculty.php")
    Single<ResponseBody> registerFaculty(@Field("email") String email, @Field("facultyNumber") String
            facultyNumber, @Field("facultyName") String facultyName
            , @Field("courses") String courses);

    @POST("registerstudent.php")
    Single<ResponseBody> registerStudent(@Body StudentInfo studentInfo);


}

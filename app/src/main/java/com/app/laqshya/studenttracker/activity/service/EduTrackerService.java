package com.app.laqshya.studenttracker.activity.service;

import com.app.laqshya.studenttracker.activity.model.BatchDetails;
import com.app.laqshya.studenttracker.activity.model.BatchInformationResponse;
import com.app.laqshya.studenttracker.activity.model.CenterList;
import com.app.laqshya.studenttracker.activity.model.CourseList;
import com.app.laqshya.studenttracker.activity.model.CourseModuleList;
import com.app.laqshya.studenttracker.activity.model.CoursesStudent;
import com.app.laqshya.studenttracker.activity.model.EditBatchScheduleList;
import com.app.laqshya.studenttracker.activity.model.FacultyList;
import com.app.laqshya.studenttracker.activity.model.LoginModel;
import com.app.laqshya.studenttracker.activity.model.StudentInfo;

import java.util.List;

import io.reactivex.Single;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface EduTrackerService {
    String ENDPOINT = "http://192.168.0.128/student_tracker/";

    @FormUrlEncoded
    @POST("login.php")
    Single<LoginModel> loginUSer(@Field("mobile") String mobile, @Field("password") String password,
                                 @Field("flag") int flag, @Field("token") String token);

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

    @POST("course_register.php")
    Call<ResponseBody> saveCourse(@Body CoursesStudent coursesStudent);

    @GET("getFacultyList.php")
    Single<List<FacultyList>> getFacultyList();

    @FormUrlEncoded
    @POST("getModule.php")
    Single<List<CourseModuleList>> getModuleForCourse(@Field("course_name") String course_name);

    @FormUrlEncoded
    @POST("getStudentToCreateBatch.php")
    Single<List<StudentInfo>> getStudentNameForBatches(@Field("coursename")String coursename,@Field("courseModuleName")
                                                       String courseModuleName);

    @POST("createBatch.php")
    Single<ResponseBody> createBatch(@Body BatchDetails batchDetails);
    @FormUrlEncoded
    @POST("getBatchinfo.php")
    Single<List<BatchInformationResponse.BatchInformation>> getBatch(@Field("centername")String centername);
    @FormUrlEncoded
    @POST("getExistingBatchSchedules.php")
    Single<List<EditBatchScheduleList.EditbatchSchedule>> getSchedule(@Field("batchid")String batchid);

}

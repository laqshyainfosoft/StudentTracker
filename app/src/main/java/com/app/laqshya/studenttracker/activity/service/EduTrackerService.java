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
    String ENDPOINT = "http://192.168.43.166/student_tracker/";

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
    Single<List<StudentInfo>> getStudentNameForBatches(@Field("coursename") String coursename, @Field("courseModuleName")
            String courseModuleName);

    @POST("createBatch.php")
    Single<ResponseBody> createBatch(@Body BatchDetails batchDetails);

    @FormUrlEncoded
    @POST("getBatchinfo.php")
    Single<List<BatchInformationResponse.BatchInformation>> getBatch(@Field("centername") String centername);

    @FormUrlEncoded
    @POST("getBatchesForCenter.php")
    Single<List<BatchInformationResponse.BatchInformation>> getBatchForNotification(@Field("centername") String centername);

    @FormUrlEncoded
    @POST("getExistingBatchSchedules.php")
    Single<List<EditBatchScheduleList.EditbatchSchedule>> getSchedule(@Field("batchid") String batchid);

    @FormUrlEncoded
    @POST("getStudentsToEditBatch.php")
    Single<List<StudentInfo>> getStudentNameForEditBatches(@Field("coursename") String coursename, @Field("courseModuleName")
            String courseModuleName);

    //    @POST("editBatches.php")
//    Single<ResponseBody> editBatch(@Body BatchDetails batchDetails);
    @FormUrlEncoded
    @POST("deleteBatches.php")
    Single<ResponseBody> deleteBatch(@Field("scheduleId") String scheduleId);

    @FormUrlEncoded
    @POST("updateBatches.php")
    Single<ResponseBody> updateBatch(@Field("scheduleId") String scheduleId, @Field("facultyId") String facultyId,
                                     @Field("startTime") String startTime, @Field("endTime") String endTime,
                                     @Field("dayId") String dayId, @Field("bid") String bid);

    @FormUrlEncoded
    @POST("insertSchedulesOnEditing.php")
    Single<ResponseBody> insertnewSchedules(@Field("startTime") String startTime, @Field("endTime") String endTime,
                                            @Field("dayId") String dayId, @Field("bid") String bid, @Field("batchSwitched") int batchSwitched);

    @FormUrlEncoded
    @POST("markBatches.php")
    Single<ResponseBody> markBatchesasCompleted(@Field("bid") String bid, @Field("deleteOrComplete") boolean deleteOrComplete);

    @FormUrlEncoded
    @POST("fetchDeletedBatches.php")
    Single<List<BatchInformationResponse.BatchInformation>> getdeletedbatches(@Field("centername") String centername);

    @FormUrlEncoded
    @POST("fetchCompletedBatches.php")
    Single<List<BatchInformationResponse.BatchInformation>> getCompletedBatches(@Field("centername") String centername);

    @FormUrlEncoded
    @POST("getStudentsForCenter.php")
    Single<List<StudentInfo>> getStudentsForCentersNotification(@Field("centername") String centername);

    @FormUrlEncoded
    @POST("sendSingleStudentNotification.php")
    Single<ResponseBody> sendNotificationtosingleStudent(@Field("counsellor_id") String counsellorphone, @Field("phone") String phone, @Field("title")
            String title, @Field("message") String message,
                                                         @Field("flag") String flag);

    @FormUrlEncoded
    @POST("sendSingleBatchNotification.php")
    Single<ResponseBody> sendNotificationtosingleBatch(@Field("counsellor_id") String counsellorphone, @Field("batch_id") String batchid, @Field("title")
            String title, @Field("message") String message, @Field("faculty_id") String faculty_id,
                                                       @Field("flag") String flag);

    @FormUrlEncoded
    @POST("sendNotificationtoallbatches.php")
    Single<ResponseBody> sendNotificationtoeveryone(@Field("counsellor_id") String counsellorphone, @Field("title") String title, @Field("message") String message,
                                                    @Field("flag") String flag);


}

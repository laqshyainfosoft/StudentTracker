package com.app.laqshya.studenttracker.activity.service;

import com.app.laqshya.studenttracker.activity.model.AdminNotification;
import com.app.laqshya.studenttracker.activity.model.BatchDetails;
import com.app.laqshya.studenttracker.activity.model.BatchInformationResponse;
import com.app.laqshya.studenttracker.activity.model.CenterList;
import com.app.laqshya.studenttracker.activity.model.CourseList;
import com.app.laqshya.studenttracker.activity.model.CourseModuleList;
import com.app.laqshya.studenttracker.activity.model.CoursesStudent;
import com.app.laqshya.studenttracker.activity.model.EditBatchScheduleList;
import com.app.laqshya.studenttracker.activity.model.FacultyCourse;
import com.app.laqshya.studenttracker.activity.model.FacultyList;
import com.app.laqshya.studenttracker.activity.model.FacultyNotification;
import com.app.laqshya.studenttracker.activity.model.Installments;
import com.app.laqshya.studenttracker.activity.model.LoginModel;
import com.app.laqshya.studenttracker.activity.model.PDFDoc;
import com.app.laqshya.studenttracker.activity.model.StudentInfo;
import com.app.laqshya.studenttracker.activity.model.SyllabusList;
import com.app.laqshya.studenttracker.activity.model.student_self.StudentDetailsModel;

import java.util.List;
import java.util.Map;

import io.reactivex.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

public interface EduTrackerService {
    String ENDPOINT = "http://192.168.0.120/student_tracker/";

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
    @POST("getBatchesForFaculty.php")
    Single<List<BatchInformationResponse.BatchInformation>> getBatchesForFaculty(@Field("faculty_id") String faculty_id);

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
    Single<ResponseBody> sendNotificationtoeveryone(@Field("counsellor_id") String counsellorphone, @Field("title") String title, @Field("message") String message
    );

    @FormUrlEncoded
    @POST("sendNotificationtopendingstudents.php")
    Single<ResponseBody> sendNotificationtopendingStudents(@Field("counsellor_id") String counsellorphone, @Field("title") String title, @Field("message") String message,
                                                           @Field("student_id") String student_id);

    @POST("getStudentsWithFeesDue.php")
    Single<List<StudentInfo>> getListofFeesdueStudents();

    @FormUrlEncoded
    @POST("sendSingleFacultyBatchNotification.php")
    Single<ResponseBody> sendNotificationFacultyBatch(@Field("batch_id") String batchid, @Field("title")
            String title, @Field("message") String message, @Field("faculty_id") String faculty_id,
                                                      @Field("flag") String flag);

    @FormUrlEncoded
    @POST("facultybatches.php")
    Single<List<BatchInformationResponse.BatchInformation>> getBatchesForFacultyAttendance( @Field("faculty_id") String faculty_id);

    @FormUrlEncoded
    @POST("getStudentForAttendance.php")
    Single<List<StudentInfo>>   getStudentInfoForAttendance(@Field("batch_id")String  batch_id);

    @FormUrlEncoded
    @POST("saveAttendance.php")
    Single<ResponseBody> saveAttendance(@Field("faculty_id")String faculty_id,@Field("status")String status,
                                        @Field("batch_id")String batchid,@Field("topic")String  topic,
                                        @Field("phone")String phone);
    @FormUrlEncoded
    @POST("getfacultyreceivednotifications.php")
    Single<List<FacultyNotification>> getFacultyNotification(@Field("faculty_id")String facultyid);
    @FormUrlEncoded
    @POST("students_received_notifications.php")
    Single<List<FacultyNotification>> getStudentNotification(@Field("student_id")String studentid);
    @FormUrlEncoded
    @POST("syllabus.php")
    Single<List<SyllabusList.Syllabus>> getSyllabusCovered(@Field("student_mobile")String student_mobile);
    @FormUrlEncoded
    @POST("getfacultybatchinfo.php")
    Single<List<FacultyCourse>> getFacultyCourseBatches(@Field("faculty_id")String facultymobile);
    @FormUrlEncoded
    @POST("student_self_details.php")
    Single<List<StudentDetailsModel>> getstudentSelfDetails(@Field("student_mobile")String student_mobile);
    @FormUrlEncoded
    @POST("student_installment_details.php")
    Single<List<Installments>> getstudentSelfDetailsInstallments(@Field("student_mobile")String student_mobile);
    @FormUrlEncoded
    @POST("fetchpdfs.php")
    Single<List<PDFDoc>> getPdfs(@Field("studentid")String studentid);
    @FormUrlEncoded
    @POST("getStudentDetails.php")
    Single<List<ResponseBody>> getStudentinfoCounsellor(@Field("counsellorid")String  counsellorid);
    @Multipart
    @POST("uploadpdf.php")
    Single<ResponseBody> uploadPdf(@Part("course_module_name")RequestBody name, @PartMap() Map<String, RequestBody> description, @Part List<MultipartBody.Part> files
    ,@Part("uploaderId")RequestBody uploaderId,@Part("flagUploader")RequestBody flaguploader);
    @POST("getallmodules.php")
    Single<List<CourseModuleList>> getAllModules();
    @FormUrlEncoded
    @POST("getallmodulesForfaculty.php")
    Single<List<CourseModuleList>> getAllModulesForFaculty(@Field("facultyId")String facultyid);
    @POST("getallnotifications.php")
    Single<List<AdminNotification>> getallnotifications();




}

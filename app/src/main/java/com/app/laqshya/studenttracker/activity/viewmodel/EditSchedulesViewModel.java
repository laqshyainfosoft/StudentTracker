package com.app.laqshya.studenttracker.activity.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.app.laqshya.studenttracker.activity.model.BatchInformationResponse;
import com.app.laqshya.studenttracker.activity.model.EditBatchScheduleList;
import com.app.laqshya.studenttracker.activity.model.FacultyList;
import com.app.laqshya.studenttracker.activity.model.StudentInfo;
import com.app.laqshya.studenttracker.activity.repository.EditBatchRepository;

import java.util.List;
import java.util.Map;

public class EditSchedulesViewModel extends ViewModel {
    private EditBatchRepository editBatchRepository;

    public EditSchedulesViewModel(EditBatchRepository editBatchRepository) {
        this.editBatchRepository = editBatchRepository;
    }

    public LiveData<BatchInformationResponse> getBatchesForCounsellor(String center) {
        return editBatchRepository.getBatchForCounsellor(center);

    }

    public LiveData<BatchInformationResponse> getBatchesForCounsellorNotification(String center) {
        return editBatchRepository.getBatchForCounsellorNotification(center);

    }

    public LiveData<BatchInformationResponse> getDeletedBatches(String center) {
        return editBatchRepository.getDeletedbatches(center);

    }

    public LiveData<BatchInformationResponse> getCompletedBatches(String center) {
        return editBatchRepository.getCompletedBatches(center);

    }

    public LiveData<List<FacultyList>> getFacultyList() {
        return editBatchRepository.getFacultyList();

    }

    public LiveData<EditBatchScheduleList> getSchedule(String batchid) {
        return editBatchRepository.getBatchSchedule(batchid);
    }

    public LiveData<List<StudentInfo>> getStudents(String coursename, String coursemodulename) {
        return editBatchRepository.getStudents(coursename, coursemodulename);
    }

    //    public LiveData<String> editBatches(BatchDetails batchDetails){
//        return editBatchRepository.editBatches(batchDetails);
//
//    }
    public LiveData<String> deleteBatches(String scheduleId) {
        return editBatchRepository.deleteBatches(scheduleId);

    }

    public LiveData<String> modifyBatches(String scheduleId, String facultyId, String startTime, String endTime,
                                          String dayId, String bid) {
        return editBatchRepository.modifyBatches(scheduleId, facultyId, startTime, endTime, dayId, bid);

    }

    public LiveData<String> insertEditedBatches(String startTime, String endTime,
                                                String dayId, String bid, int flagSwitched) {
        return editBatchRepository.insertEditedBatches(startTime, endTime, dayId, bid, flagSwitched);

    }

    public LiveData<String> markEditedBatches(String bid, boolean deleteOrComplete) {
        return editBatchRepository.markBatches(bid, deleteOrComplete);

    }

    public LiveData<StudentInfo.StudentInfoList> getDueFeesStudent() {
        return editBatchRepository.getStudentsForDueBatch();

    }

    public LiveData<BatchInformationResponse> getBatchForFacultyAttendance(String facultyid) {
        return editBatchRepository.getBatchForFacultyId(facultyid);
    }

    public LiveData<StudentInfo.StudentInfoList> getStudentsForAttendance(String batchid) {
        return editBatchRepository.getStudentsForFacultyAttendance(batchid);
    }

    public LiveData<String> saveAttendance(String facultyid, Map<String, Integer> map, String topic, String batchid) {
        return editBatchRepository.saveAttendance(facultyid, map, topic, batchid);

    }

}

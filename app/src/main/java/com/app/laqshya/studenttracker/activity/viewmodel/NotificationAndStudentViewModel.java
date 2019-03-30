package com.app.laqshya.studenttracker.activity.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.app.laqshya.studenttracker.activity.model.AdminNotification;
import com.app.laqshya.studenttracker.activity.model.FacultyNotification;
import com.app.laqshya.studenttracker.activity.model.SyllabusList;
import com.app.laqshya.studenttracker.activity.repository.NotificationRepository;

import java.util.List;

public class NotificationAndStudentViewModel extends ViewModel {
    private NotificationRepository notificationRepository;


    public NotificationAndStudentViewModel(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;

    }

    public LiveData<List<FacultyNotification>> getFaculty(String facultyid) {
        return notificationRepository.getNotificationForFaculty(facultyid);

    }
    public LiveData<List<AdminNotification>> getAllNotifications(){
        return notificationRepository.getAllnotificationsforadmin();

    }

    public LiveData<List<FacultyNotification>> getStudent(String studentid) {
        return notificationRepository.getStudentNotification(studentid);


    }
    public LiveData<SyllabusList> getSyllabus(String sname){
        return notificationRepository.getSyllabusForStudent(sname);
    }

}

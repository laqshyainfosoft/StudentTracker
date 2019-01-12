package com.app.laqshya.studenttracker.activity.listeners;

import com.app.laqshya.studenttracker.activity.model.StudentInfo;

public interface StudentAttendanceListener {
    void markAttendance(int position, StudentInfo studentInfo);
}

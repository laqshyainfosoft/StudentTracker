package com.app.laqshya.studenttracker.activity.model;

import java.util.List;

public class StudentInfo {

    private String name;
    private String email;
    private String phone;

    private int marker = 0;



    public StudentInfo(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;

    }

    public StudentInfo(String name, String email, String phone, int marker) {
        this(name, email, phone);
        this.marker = marker;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public int getMarker() {
        return marker;
    }

    @Override
    public String toString() {
        return name;
    }
  static public   class StudentInfoList{
        private List<StudentInfo> studentInfo;
        private Throwable throwable;

        public StudentInfoList(Throwable throwable) {
            this.throwable = throwable;
            studentInfo=null;
        }

      public List<StudentInfo> getStudentInfo() {
          return studentInfo;
      }

      public Throwable getThrowable() {
          return throwable;
      }

      public StudentInfoList(List<StudentInfo> studentInfo) {

            this.studentInfo = studentInfo;
            throwable=null;
        }
    }
}

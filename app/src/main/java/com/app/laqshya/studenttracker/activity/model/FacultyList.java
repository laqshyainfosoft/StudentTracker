package com.app.laqshya.studenttracker.activity.model;

public class FacultyList {
    private String faculty_name;
    private String mobile;

    public String getMobile() {
        return mobile;
    }

    public FacultyList(String faculty_name, String mobile) {

        this.faculty_name = faculty_name;
        this.mobile=mobile;

    }

    public String getCourse_name() {
        return faculty_name;
    }

    @Override
    public String toString() {
        return faculty_name;
    }
}

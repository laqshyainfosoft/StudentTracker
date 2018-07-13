package com.app.laqshya.studenttracker.activity.model;

public class FacultyList {
    private String faculty_name;

    public FacultyList(String faculty_name) {

        this.faculty_name = faculty_name;
    }

    public String getCourse_name() {
        return faculty_name;
    }

    @Override
    public String toString() {
        return faculty_name;
    }
}

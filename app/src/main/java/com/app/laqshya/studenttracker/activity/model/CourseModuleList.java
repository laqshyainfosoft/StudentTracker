package com.app.laqshya.studenttracker.activity.model;

public class CourseModuleList {
    private String course_module_name;

    public CourseModuleList(String course_module_name) {

        this.course_module_name = course_module_name;
    }

    public String getCourse_name() {
        return course_module_name;
    }

    @Override
    public String toString() {
        return course_module_name;
    }
}

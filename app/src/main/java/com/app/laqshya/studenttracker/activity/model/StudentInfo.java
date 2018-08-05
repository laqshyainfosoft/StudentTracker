package com.app.laqshya.studenttracker.activity.model;

import java.util.List;

public class StudentInfo {
    private String name;
    private String email;
    private String phone;


    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public StudentInfo(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;

    }

    @Override
    public String toString() {
        return name;
    }
}

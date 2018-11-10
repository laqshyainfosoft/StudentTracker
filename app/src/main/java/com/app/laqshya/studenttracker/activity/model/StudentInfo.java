package com.app.laqshya.studenttracker.activity.model;

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
}

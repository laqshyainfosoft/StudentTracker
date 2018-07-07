package com.app.laqshya.studenttracker.activity.model;

public class LoginModel {
    private int status;
    private String name;
    private String center;

    public int getStatus() {
        return status;
    }



    public String getCenter() {
        return center;
    }

    public String getName() {
        return name;
    }

    public LoginModel(int status, String name, String center) {

        this.status = status;
        this.name = name;
        this.center = center;
    }
}

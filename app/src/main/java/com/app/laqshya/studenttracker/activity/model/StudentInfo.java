package com.app.laqshya.studenttracker.activity.model;

import java.util.List;

public class StudentInfo {
    private String name;
    private String email;
    private String phone;
    private String course;
    private String downpayment;
    private String fees;
    private List<Installments> installmentsList;

    public StudentInfo(String name, String email, String phone, String course, String downpayment, String fees, List<Installments> installmentsList) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.course = course;
        this.downpayment = downpayment;
        this.fees = fees;
        this.installmentsList = installmentsList;
    }
}

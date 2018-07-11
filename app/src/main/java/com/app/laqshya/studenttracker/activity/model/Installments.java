package com.app.laqshya.studenttracker.activity.model;

public class Installments {
    private int installmentNo;
    private String installmentDate;
    private String installmentAmnt;

    public Installments(int installmentNo, String installmentDate, String installmentAmnt) {
        this.installmentNo = installmentNo;
        this.installmentDate = installmentDate;
        this.installmentAmnt = installmentAmnt;
    }
}

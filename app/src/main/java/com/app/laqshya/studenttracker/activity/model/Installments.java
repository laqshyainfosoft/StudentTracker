package com.app.laqshya.studenttracker.activity.model;

public class Installments {
    private String installmentNo;

    private String installmentDate;
        private String installmentAmnt;

    public String getInstallmentNo() {
        return installmentNo;
    }

    public String getInstallmentDate() {
        return installmentDate;
    }

    public String getInstallmentAmnt() {
        return installmentAmnt;
    }

    public Installments(String installmentNo, String installmentDate, String installmentAmnt) {
        this.installmentNo = installmentNo;
        this.installmentDate = installmentDate;
        this.installmentAmnt = installmentAmnt;
    }
}

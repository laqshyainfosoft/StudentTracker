package com.app.laqshya.studenttracker.activity.model;

public class Installments {
    private int installmentNo;

    private String installmentDate;
        private String installmentAmnt;

    public int getInstallmentNo() {
        return installmentNo;
    }

    public String getInstallmentDate() {
        return installmentDate;
    }

    public String getInstallmentAmnt() {
        return installmentAmnt;
    }

    public Installments(int installmentNo, String installmentDate, String installmentAmnt) {
        this.installmentNo = installmentNo;
        this.installmentDate = installmentDate;
        this.installmentAmnt = installmentAmnt;
    }
}

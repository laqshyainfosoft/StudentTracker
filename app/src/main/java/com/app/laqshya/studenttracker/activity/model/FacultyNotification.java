package com.app.laqshya.studenttracker.activity.model;

public class FacultyNotification {
    private String counsellor_name;
    private String centername;
    private String message;
    private String notificationDate;

    public String getCounsellor_name() {
        return counsellor_name;
    }

    public void setCounsellor_name(String counsellor_name) {
        this.counsellor_name = counsellor_name;
    }

    public String getCentername() {
        return centername;
    }

    public void setCentername(String centername) {
        this.centername = centername;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNotificationDate() {
        return notificationDate;
    }

    public void setNotificationDate(String notificationDate) {
        this.notificationDate = notificationDate;
    }
}

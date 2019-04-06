package com.app.laqshya.studenttracker.activity.model.student_self;

import java.util.List;

public class ManageStudentInfoResponse {
    private List<ManageStudentInfo> manageStudentInfoList;
    private Throwable throwable;

    public ManageStudentInfoResponse(Throwable throwable) {
        this.throwable = throwable;
        manageStudentInfoList=null;
    }

    public List<ManageStudentInfo> getManageStudentInfoList() {
        return manageStudentInfoList;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public ManageStudentInfoResponse(List<ManageStudentInfo> manageStudentInfoList) {
        this.manageStudentInfoList = manageStudentInfoList;
        throwable=null;
    }

    public  static  class ManageStudentInfo {
        private String student_name;
        private String mobile;
        private String totaldownpayment;
        private String total_fees;
        private String course_module_name;
        private String email;

        public String getStudent_name() {
            return student_name;
        }

        public void setStudent_name(String student_name) {
            this.student_name = student_name;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getTotaldownpayment() {
            return totaldownpayment;
        }

        public void setTotaldownpayment(String totaldownpayment) {
            this.totaldownpayment = totaldownpayment;
        }

        public String getTotal_fees() {
            return total_fees;
        }

        public void setTotal_fees(String total_fees) {
            this.total_fees = total_fees;
        }

        public String getCourse_module_name() {
            return course_module_name;
        }

        public void setCourse_module_name(String course_module_name) {
            this.course_module_name = course_module_name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }

}

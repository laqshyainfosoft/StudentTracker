package com.app.laqshya.studenttracker.activity.model;

import java.util.ArrayList;
import java.util.List;

public class BatchInformationResponse {
    private List<BatchInformation> batchInformationList;
    private Throwable throwable;

    public BatchInformationResponse(Throwable throwable) {
        this.throwable = throwable;
        this.batchInformationList = null;
    }

    public BatchInformationResponse(List<BatchInformation> batchInformationList) {

        this.batchInformationList = batchInformationList;
        this.throwable = null;
    }

    public List<BatchInformation> getBatchInformationList() {
        return batchInformationList;
    }

    public void setBatchInformationList(List<BatchInformation> batchInformationList) {
        this.batchInformationList = batchInformationList;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }

    public  static class BatchInformation {

        private String batchid;
        private String startDate;
        private String coursename;

        public String getCoursename() {
            return coursename;
        }

        public void setCoursename(String coursename) {
            this.coursename = coursename;
        }

        public String getStartDate() {
            return startDate;
        }

        public void setStartDate(String startDate) {
            this.startDate = startDate;
        }

        private String course_module_name;

        public String getBatchid() { return "Batch " +this.batchid; }

        public void setBatchid(String batchid) { this.batchid = batchid; }

        private String faculty_name;

        public String getCourse_module_name() {
            return course_module_name;
        }

        public void setCourse_module_name(String course_module_name) {
            this.course_module_name = course_module_name;
        }

        public String getFacultyName() { return this.faculty_name; }

        public void setFacultyName(String faculty_name) { this.faculty_name = faculty_name; }

        private ArrayList<Schedule> schedule;

        public ArrayList<Schedule> getSchedule() { return this.schedule; }

        public void setSchedule(ArrayList<Schedule> schedule) { this.schedule = schedule; }


    }

}

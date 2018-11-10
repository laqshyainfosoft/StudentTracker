package com.app.laqshya.studenttracker.activity.model;

import java.util.List;

public class EditBatchScheduleList {
    private List<EditbatchSchedule> editbatchScheduleList;
    private Throwable throwable;

    public EditBatchScheduleList(Throwable throwable) {
        this.throwable = throwable;
        editbatchScheduleList=null;
    }

    public EditBatchScheduleList(List<EditbatchSchedule> editbatchScheduleList) {

        this.editbatchScheduleList = editbatchScheduleList;
        throwable=null;
    }

    public List<EditbatchSchedule> getEditbatchScheduleList() {

        return editbatchScheduleList;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public static class EditbatchSchedule {
        private String startTime;
        private String scheduleId;

        public String getScheduleId() {
            return scheduleId;
        }

        public void setScheduleId(String scheduleId) {
            this.scheduleId = scheduleId;
        }

        public String getDay_id() {
            return day_id;
        }

        public void setDay_id(String day_id) {
            this.day_id = day_id;
        }

        private String endTime;
        private String day_id;

        public String getStartTime() {
            return this.startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getDay() {
            return this.day_id;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public void setDay(String day) {
            this.day_id = day;
        }
    }
}

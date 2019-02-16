package com.app.laqshya.studenttracker.activity.model;

import java.util.List;

public class SyllabusList {
    private List<Syllabus> syllabus;
    private Throwable throwable;

    public List<Syllabus> getSyllabus() {
        return syllabus;
    }

    public SyllabusList(Throwable throwable) {
        this.throwable = throwable;
        syllabus=null;

    }

    public SyllabusList(List<Syllabus> syllabus) {

        this.syllabus = syllabus;
        throwable=null;
    }

  static public   class Syllabus{
        private String syllabus_date;
        private String topic;

        private String status;

      public String getStatus() {
          return status;
      }

      public void setStatus(String status) {
          this.status = status;
      }

      public String getSyllabus_date() {
            return syllabus_date;
        }

        public void setSyllabus_date(String syllabus_date) {
            this.syllabus_date = syllabus_date;
        }

        public String getTopic() {
            return topic;
        }

        public void setTopic(String topic) {
            this.topic = topic;
        }
    }
}

package com.app.laqshya.studenttracker.activity.model;

import java.util.List;

public class BatchDetails {
    private String center;

    private List<BatchList> batchList;

    private String startDate;

    private List<StudentNames> studentNames;

    private String facultyMobile;

    private String courseModuleName;

    private String courseName;

    public String getCenter ()
    {
        return center;
    }

    public void setCenter (String center)
    {
        this.center = center;
    }

    public List<BatchList> getBatchList ()
    {
        return batchList;
    }

    public void setBatchList (List<BatchList> batchList)
    {
        this.batchList = batchList;
    }

    public String getStartDate ()
    {
        return startDate;
    }

    public void setStartDate (String startDate)
    {
        this.startDate = startDate;
    }

    public List<StudentNames>  getStudentNames ()
    {
        return studentNames;
    }

    public void setStudentNames (List<StudentNames> studentNames)
    {
        this.studentNames = studentNames;
    }

    public String getFacultyMobile ()
    {
        return facultyMobile;
    }

    public void setFacultyMobile (String facultyMobile)
    {
        this.facultyMobile = facultyMobile;
    }

    public String getCourseModuleName ()
    {
        return courseModuleName;
    }

    public void setCourseModuleName (String courseModuleName)
    {
        this.courseModuleName = courseModuleName;
    }

    public String getCourseName ()
    {
        return courseName;
    }

    public void setCourseName (String courseName)
    {
        this.courseName = courseName;
    }


}

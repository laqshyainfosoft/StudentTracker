package com.app.laqshya.studenttracker.activity.model;

import java.util.List;

public class CoursesStudent {
    private List<InstallmentsList> installmentsList;

    private String downpayment;

    private List<CourseModuleList> courseModule;

    private String fees;

    private String mobile;

    public List<InstallmentsList> getInstallmentsList ()
    {
        return installmentsList;
    }

    public void setInstallmentsList (List<InstallmentsList> installmentsList)
    {
        this.installmentsList = installmentsList;
    }

    public String getDownpayment ()
    {
        return downpayment;
    }

    public void setDownpayment (String downpayment)
    {
        this.downpayment = downpayment;
    }

    public List<CourseModuleList> getCourseModule ()
    {
        return courseModule;
    }

    public void setCourseModule (List<CourseModuleList> courseModule)
    {
        this.courseModule = courseModule;
    }

    public String getFees ()
    {
        return fees;
    }

    public void setFees (String fees)
    {
        this.fees = fees;
    }

    public String getMobile ()
    {
        return mobile;
    }

    public void setMobile (String mobile)
    {
        this.mobile = mobile;
    }
}

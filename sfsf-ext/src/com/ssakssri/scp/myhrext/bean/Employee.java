package com.ssakssri.scp.myhrext.bean;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class Employee {

    @JsonSerialize
    private String empName;
    private String comName;
    private String orgName;
    private String empType;
    private String jobGroup;
    private String callingTitle;

    public Employee(String empName, String comName, String orgName, String empType, String jobGroup, String callingTitle) {
        this.empName = empName;
        this.comName = comName;
        this.orgName = orgName;
        this.empType = empType;
        this.jobGroup = jobGroup;
        this.callingTitle = callingTitle;
    }

    public String getEmpName() {
        return empName;
    }

    public String getComName() {
        return comName;
    }

    public String getOrgName() {
        return orgName;
    }

    public String getEmpType() {
        return empType;
    }

    public String getJobGroup() {
        return jobGroup;
    }

    public String getCallingTitle() {
        return callingTitle;
    }
}

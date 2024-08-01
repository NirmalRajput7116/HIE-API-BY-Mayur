package com.cellbeans.hspa.mis.misLab;

import com.cellbeans.hspa.mis.misbillingreport.KeyValueDto;

import java.util.List;

public class LabOrderListDTO {

    public String unitName;
    public String date;
    public String mrNo;
    public String patientName;
    public String groupName;
    public String subGroupName;
    public String testName;
    public String status;
    public String userName;
    public long count;
    List<KeyValueDto> subgroupWise;

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMrNo() {
        return mrNo;
    }

    public void setMrNo(String mrNo) {
        this.mrNo = mrNo;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getSubGroupName() {
        return subGroupName;
    }

    public void setSubGroupName(String subGroupName) {
        this.subGroupName = subGroupName;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public List<KeyValueDto> getSubgroupWise() {
        return subgroupWise;
    }

    public void setSubgroupWise(List<KeyValueDto> subgroupWise) {
        this.subgroupWise = subgroupWise;
    }

    @Override
    public String toString() {
        return "LabOrderListDTO{" +
                "unitName='" + unitName + '\'' +
                ", date='" + date + '\'' +
                ", mrNo='" + mrNo + '\'' +
                ", patientName='" + patientName + '\'' +
                ", groupName='" + groupName + '\'' +
                ", subGroupName='" + subGroupName + '\'' +
                ", testName='" + testName + '\'' +
                ", status='" + status + '\'' +
                ", userName='" + userName + '\'' +
                ", count=" + count +
                ", subgroupWise=" + subgroupWise +
                '}';
    }

}

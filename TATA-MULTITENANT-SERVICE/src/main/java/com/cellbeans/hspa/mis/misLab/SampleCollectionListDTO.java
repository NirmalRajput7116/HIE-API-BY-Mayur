package com.cellbeans.hspa.mis.misLab;

import com.cellbeans.hspa.mstunit.MstUnit;

public class SampleCollectionListDTO {

    public String patientName;
    public String mrNo;
    public String testName;
    public String services;
    public String groupName;
    public String subGroupName;
    public String sampleNumber;
    public String sampleType;
    public String containerType;
    public String sampleCollectedBy;
    public String sampleCollectedDate;
    public String unitName;
    public long count;
    public MstUnit objHeaderData;

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getMrNo() {
        return mrNo;
    }

    public void setMrNo(String mrNo) {
        this.mrNo = mrNo;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getServices() {
        return services;
    }

    public void setServices(String services) {
        this.services = services;
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

    public String getSampleNumber() {
        return sampleNumber;
    }

    public void setSampleNumber(String sampleNumber) {
        this.sampleNumber = sampleNumber;
    }

    public String getSampleType() {
        return sampleType;
    }

    public void setSampleType(String sampleType) {
        this.sampleType = sampleType;
    }

    public String getContainerType() {
        return containerType;
    }

    public void setContainerType(String containerType) {
        this.containerType = containerType;
    }

    public String getSampleCollectedBy() {
        return sampleCollectedBy;
    }

    public void setSampleCollectedBy(String sampleCollectedBy) {
        this.sampleCollectedBy = sampleCollectedBy;
    }

    public String getSampleCollectedDate() {
        return sampleCollectedDate;
    }

    public void setSampleCollectedDate(String sampleCollectedDate) {
        this.sampleCollectedDate = sampleCollectedDate;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public MstUnit getObjHeaderData() {
        return objHeaderData;
    }

    public void setObjHeaderData(MstUnit objHeaderData) {
        this.objHeaderData = objHeaderData;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }
}

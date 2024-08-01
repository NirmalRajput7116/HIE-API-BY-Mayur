package com.cellbeans.hspa.mis.misopdreports;

import com.cellbeans.hspa.mstunit.MstUnit;

public class LabBillTestReportListDTO {

    public String unit;
    public String visitDate;
    public String mRNo;
    public String patientName;
    public String mobileNo;
    public String doctorName;
    public String department;
    public String subDepartment;
    public String serviceName;
    public String visitType;
    public String visitCancelReason;
    public String cancelDate;
    public long count;
    public MstUnit objHeaderData;

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(String visitDate) {
        this.visitDate = visitDate;
    }

    public String getmRNo() {
        return mRNo;
    }

    public void setmRNo(String mRNo) {
        this.mRNo = mRNo;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getSubDepartment() {
        return subDepartment;
    }

    public void setSubDepartment(String subDepartment) {
        this.subDepartment = subDepartment;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getVisitType() {
        return visitType;
    }

    public void setVisitType(String visitType) {
        this.visitType = visitType;
    }

    public String getVisitCancelReason() {
        return visitCancelReason;
    }

    public void setVisitCancelReason(String visitCancelReason) {
        this.visitCancelReason = visitCancelReason;
    }

    public String getCancelDate() {
        return cancelDate;
    }

    public void setCancelDate(String cancelDate) {
        this.cancelDate = cancelDate;
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

}

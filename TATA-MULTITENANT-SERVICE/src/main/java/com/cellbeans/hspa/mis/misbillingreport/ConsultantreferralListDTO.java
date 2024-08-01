package com.cellbeans.hspa.mis.misbillingreport;

import com.cellbeans.hspa.mstunit.MstUnit;

public class ConsultantreferralListDTO {

    public String opClassName;
    public String billNo;
    public String billDate;
    public String mrNo;
    public String patientName;
    public String serviceName;
    public String doctorName;
    public String userName;
    public String serviceAmount;
    public String hospitalAmount;
    public Double doctorTotal;
    public Double serviceTotal;
    public Double hospitalTotal;
    public long bsServiceId;
    public long count;
    public long bsStaffId;
    public String unitName;
    public long unitId;
    public Double doctorAmount;
    public MstUnit objHeaderData;
    public Integer showType;

    public Integer getShowType() {
        return showType;
    }

    public void setShowType(Integer showType) {
        this.showType = showType;
    }

    public String getOpClassName() {
        return opClassName;
    }

    public void setOpClassName(String opClassName) {
        this.opClassName = opClassName;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public String getBillDate() {
        return billDate;
    }

    public void setBillDate(String billDate) {
        this.billDate = billDate;
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

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getServiceAmount() {
        return serviceAmount;
    }

    public void setServiceAmount(String serviceAmount) {
        this.serviceAmount = serviceAmount;
    }

    public String getHospitalAmount() {
        return hospitalAmount;
    }

    public void setHospitalAmount(String hospitalAmount) {
        this.hospitalAmount = hospitalAmount;
    }

    public Double getDoctorTotal() {
        return doctorTotal;
    }

    public void setDoctorTotal(Double doctorTotal) {
        this.doctorTotal = doctorTotal;
    }

    public Double getServiceTotal() {
        return serviceTotal;
    }

    public void setServiceTotal(Double serviceTotal) {
        this.serviceTotal = serviceTotal;
    }

    public Double getHospitalTotal() {
        return hospitalTotal;
    }

    public void setHospitalTotal(Double hospitalTotal) {
        this.hospitalTotal = hospitalTotal;
    }

    public long getBsServiceId() {
        return bsServiceId;
    }

    public void setBsServiceId(long bsServiceId) {
        this.bsServiceId = bsServiceId;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public long getBsStaffId() {
        return bsStaffId;
    }

    public void setBsStaffId(long bsStaffId) {
        this.bsStaffId = bsStaffId;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public long getUnitId() {
        return unitId;
    }

    public void setUnitId(long unitId) {
        this.unitId = unitId;
    }

    public Double getDoctorAmount() {
        return doctorAmount;
    }

    public void setDoctorAmount(Double doctorAmount) {
        this.doctorAmount = doctorAmount;
    }

    public MstUnit getObjHeaderData() {
        return objHeaderData;
    }

    public void setObjHeaderData(MstUnit objHeaderData) {
        this.objHeaderData = objHeaderData;
    }
}

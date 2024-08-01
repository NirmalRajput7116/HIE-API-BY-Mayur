package com.cellbeans.hspa.mis.misbillingreport;

import com.cellbeans.hspa.mstunit.MstUnit;

public class DoctorShareReportListDTO {

    public String mrNo;
    public String patientName;
    public String drName;
    public double drAmount;
    public double totaldrAmount;
    public double totalbillAmount;
    public String billNo;
    public String billAmount;
    public String serviceName;
    public long count;
    public double grandTotal;
    public MstUnit objHeaderData;

    public String getMrNo() {
        return mrNo;
    }

    public void setMrNo(String mrNo) {
        this.mrNo = mrNo;
    }

    public double getDrAmount() {
        return drAmount;
    }

    public void setDrAmount(double drAmount) {
        this.drAmount = drAmount;
    }

    public double getTotalbillAmount() {
        return totalbillAmount;
    }

    public void setTotalbillAmount(double totalbillAmount) {
        this.totalbillAmount = totalbillAmount;
    }

    public double getTotaldrAmount() {
        return totaldrAmount;
    }

    public void setTotaldrAmount(double totaldrAmount) {
        this.totaldrAmount = totaldrAmount;
    }

    public String getBillAmount() {
        return billAmount;
    }

    public void setBillAmount(String billAmount) {
        this.billAmount = billAmount;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public MstUnit getObjHeaderData() {
        return objHeaderData;
    }

    public void setObjHeaderData(MstUnit objHeaderData) {
        this.objHeaderData = objHeaderData;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getDrName() {
        return drName;
    }

    public void setDrName(String drName) {
        this.drName = drName;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public double getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(double grandTotal) {
        this.grandTotal = grandTotal;
    }
}

package com.cellbeans.hspa.mis.misbillingreport;

public class RefundBillListDTO {

    public String RefundReciptDate;
    public String RefundReciptNo;
    public String MrNo;
    public String Billno;
    public String OpIpNo;
    public String PatientName;
    public String UserName;
    public String UnitName;
    public String RefundAmount;
    public long unitId;
    public long ServiceId;
    public long StaffId;
    public long count;

    public long getUnitId() {
        return unitId;
    }

    public void setUnitId(long unitId) {
        this.unitId = unitId;
    }

    public long getServiceId() {
        return ServiceId;
    }

    public void setServiceId(long serviceId) {
        ServiceId = serviceId;
    }

    public long getStaffId() {
        return StaffId;
    }

    public void setStaffId(long staffId) {
        StaffId = staffId;
    }

    public String getRefundReciptDate() {
        return RefundReciptDate;
    }

    public void setRefundReciptDate(String refundReciptDate) {
        RefundReciptDate = refundReciptDate;
    }

    public String getRefundReciptNo() {
        return RefundReciptNo;
    }

    public void setRefundReciptNo(String refundReciptNo) {
        RefundReciptNo = refundReciptNo;
    }

    public String getMrNo() {
        return MrNo;
    }

    public void setMrNo(String mrNo) {
        MrNo = mrNo;
    }

    public String getBillno() {
        return Billno;
    }

    public void setBillno(String billno) {
        Billno = billno;
    }

    public String getOpIpNo() {
        return OpIpNo;
    }

    public void setOpIpNo(String opIpNo) {
        OpIpNo = opIpNo;
    }

    public String getPatientName() {
        return PatientName;
    }

    public void setPatientName(String patientName) {
        PatientName = patientName;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUnitName() {
        return UnitName;
    }

    public void setUnitName(String unitName) {
        UnitName = unitName;
    }

    public String getRefundAmount() {
        return RefundAmount;
    }

    public void setRefundAmount(String refundAmount) {
        RefundAmount = refundAmount;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

}

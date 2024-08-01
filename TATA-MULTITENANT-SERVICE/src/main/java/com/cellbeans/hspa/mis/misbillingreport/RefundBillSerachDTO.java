package com.cellbeans.hspa.mis.misbillingreport;

public class RefundBillSerachDTO {
    public String mrNo;
    public boolean todaydate;
    public String patientName;
    public String refundreciptNo;
    public String billNo;
    public long userId;
    public long unitId;
    public String fromdate;
    public String todate;
    public int limit;
    public int offset;
    public boolean print;
    public boolean IPDFlag;

    public boolean getIPDFlag() {
        return IPDFlag;
    }

    public void setIPDFlag(boolean IPDFlag) {
        this.IPDFlag = IPDFlag;
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

    public String getRefundreciptNo() {
        return refundreciptNo;
    }

    public void setRefundreciptNo(String refundreciptNo) {
        this.refundreciptNo = refundreciptNo;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getFromdate() {
        return fromdate;
    }

    public void setFromdate(String fromdate) {
        this.fromdate = fromdate;
    }

    public String getTodate() {
        return todate;
    }

    public void setTodate(String todate) {
        this.todate = todate;
    }

    public boolean isTodaydate() {
        return todaydate;
    }

    public void setTodaydate(boolean todaydate) {
        this.todaydate = todaydate;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public boolean isPrint() {
        return print;
    }

    public void setPrint(boolean print) {
        this.print = print;
    }

    public long getUnitId() {
        return unitId;
    }

    public void setUnitId(long unitId) {
        this.unitId = unitId;
    }

}

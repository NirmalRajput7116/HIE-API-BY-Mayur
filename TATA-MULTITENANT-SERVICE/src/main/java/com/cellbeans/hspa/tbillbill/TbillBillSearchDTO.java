package com.cellbeans.hspa.tbillbill;

public class TbillBillSearchDTO {

    private String fromdate;
    private String todate;
    private int limit;
    private int offset;
    private String mRNO;
    private String patientName;
    private String billNo;

    public String getmRNO() {
        return mRNO;
    }

    public void setmRNO(String mRNO) {
        this.mRNO = mRNO;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
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
}

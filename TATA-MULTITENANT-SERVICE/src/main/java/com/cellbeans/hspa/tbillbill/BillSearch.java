package com.cellbeans.hspa.tbillbill;

public class BillSearch {

    private String type;

    private String billNumber;

    private String billOpdNumber;

    private String billIpdNumber;

    private String patientMrNo;

    private String titleName;

    private String userFirstname;

    private String userMiddlename;

    private String userLastname;

    private long classId;

    private long tariffId;

    private long unitId;

    private String fromdate;

    private String todate;

    private int limit;

    private int offset;

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

    public long getUnitId() {
        return unitId;
    }

    public void setUnitId(long unitId) {
        this.unitId = unitId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBillNumber() {
        return billNumber;
    }

    public void setBillNumber(String billNumber) {
        this.billNumber = billNumber;
    }

    public String getBillOpdNumber() {
        return billOpdNumber;
    }

    public void setBillOpdNumber(String billOpdNumber) {
        this.billOpdNumber = billOpdNumber;
    }

    public String getBillIpdNumber() {
        return billIpdNumber;
    }

    public void setBillIpdNumber(String billIpdNumber) {
        this.billIpdNumber = billIpdNumber;
    }

    public String getPatientMrNo() {
        return patientMrNo;
    }

    public void setPatientMrNo(String patientMrNo) {
        this.patientMrNo = patientMrNo;
    }

    public String getUserFirstname() {
        return userFirstname;
    }

    public void setUserFirstname(String userFirstname) {
        this.userFirstname = userFirstname;
    }

    public String getUserMiddlename() {
        return userMiddlename;
    }

    public void setUserMiddlename(String userMiddlename) {
        this.userMiddlename = userMiddlename;
    }

    public String getUserLastname() {
        return userLastname;
    }

    public void setUserLastname(String userLastname) {
        this.userLastname = userLastname;
    }

    public long getClassId() {
        return classId;
    }

    public void setClassId(long classId) {
        this.classId = classId;
    }

    public long getTariffId() {
        return tariffId;
    }

    public void setTariffId(long tariffId) {
        this.tariffId = tariffId;
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

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    @Override
    public String toString() {
        return "BillSearch{" +
                "type='" + type + '\'' +
                ", billNumber='" + billNumber + '\'' +
                ", billOpdNumber='" + billOpdNumber + '\'' +
                ", billIpdNumber='" + billIpdNumber + '\'' +
                ", patientMrNo='" + patientMrNo + '\'' +
                ", titleName='" + titleName + '\'' +
                ", userFirstname='" + userFirstname + '\'' +
                ", userMiddlename='" + userMiddlename + '\'' +
                ", userLastname='" + userLastname + '\'' +
                ", classId=" + classId +
                ", tariffId=" + tariffId +
                ", unitId=" + unitId +
                ", fromdate='" + fromdate + '\'' +
                ", todate='" + todate + '\'' +
                ", limit=" + limit +
                ", offset=" + offset +
                '}';
    }
}

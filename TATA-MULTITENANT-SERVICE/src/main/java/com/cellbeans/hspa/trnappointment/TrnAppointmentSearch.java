package com.cellbeans.hspa.trnappointment;

public class TrnAppointmentSearch {

    private int aptstatus;

    private String mrno;

    private String ptname;

    private String uid;

    private String fromdate;

    private String todate;

    private int dept;

    private String bookedfromdate;

    private String bookedtodate;

    private long rofapt;

    private String mobile;

    private int staff;

    private int limit;

    private int offset;

    private int unitid;

    public String[] unitList;

    public String staffId1;     // for  doctor

    public int consultType;

    public int getAptstatus() {
        return aptstatus;
    }

    public void setAptstatus(int aptstatus) {
        this.aptstatus = aptstatus;
    }

    public String getMrno() {
        return mrno;
    }

    public void setMrno(String mrno) {
        this.mrno = mrno;
    }

    public String getPtname() {
        return ptname;
    }

    public void setPtname(String ptname) {
        this.ptname = ptname;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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

    public int getDept() {
        return dept;
    }

    public void setDept(int dept) {
        this.dept = dept;
    }

    public String getBookedfromdate() {
        return bookedfromdate;
    }

    public void setBookedfromdate(String bookedfromdate) {
        this.bookedfromdate = bookedfromdate;
    }

    public String getBookedtodate() {
        return bookedtodate;
    }

    public void setBookedtodate(String bookedtodate) {
        this.bookedtodate = bookedtodate;
    }

    public long getRofapt() {
        return rofapt;
    }

    public void setRofapt(long rofapt) {
        this.rofapt = rofapt;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getStaff() {
        return staff;
    }

    public void setStaff(int staff) {
        this.staff = staff;
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

    public int getUnitid() {
        return unitid;
    }

    public void setUnitid(int unitid) {
        this.unitid = unitid;
    }

    public String[] getUnitList() {
        return unitList;
    }

    public void setUnitList(String[] unitList) {
        this.unitList = unitList;
    }

    public String getStaffId1() {
        return staffId1;
    }

    public void setStaffId1(String staffId1) {
        this.staffId1 = staffId1;
    }

    public int getConsultType() {
        return consultType;
    }

    public void setConsultType(int consultType) {
        this.consultType = consultType;
    }
}

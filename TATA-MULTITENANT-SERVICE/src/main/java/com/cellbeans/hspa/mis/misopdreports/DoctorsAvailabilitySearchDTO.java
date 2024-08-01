package com.cellbeans.hspa.mis.misopdreports;

public class DoctorsAvailabilitySearchDTO {

    public String departmentId;
    public String sdId;
    public String staffId;
    public String dayName;
    public long unitId;
    public String fromdate;
    public String todate;
    public String[] unitList;
    public int limit;
    public int offset;
    public boolean print;
    public long userId;
    public boolean todaydate;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getDepartment() {
        return departmentId;
    }

    public void setDepartment(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getSubDepartment() {
        return sdId;
    }

    public void setSubDepartment(String subDepartment) {
        this.sdId = subDepartment;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getDayName() {
        return dayName;
    }

    public void setDayName(String dayName) {
        this.dayName = dayName;
    }

    public long getUnitId() {
        return unitId;
    }

    public void setUnitId(long unitId) {
        this.unitId = unitId;
    }

    public String[] getUnitList() {
        return unitList;
    }

    public void setUnitList(String[] unitList) {
        this.unitList = unitList;
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

    public boolean getPrint() {
        return print;
    }

    public void setPrint(boolean print) {
        this.print = print;
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

    public boolean getTodaydate() {
        return todaydate;
    }

    public void setTodaydate(boolean todaydate) {
        this.todaydate = todaydate;
    }
}

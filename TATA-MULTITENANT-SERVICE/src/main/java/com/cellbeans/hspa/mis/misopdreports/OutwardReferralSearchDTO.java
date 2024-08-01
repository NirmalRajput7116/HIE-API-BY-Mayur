package com.cellbeans.hspa.mis.misopdreports;

public class OutwardReferralSearchDTO {
    public String refentityTypeId;
    public String refentityId;
    public String specialityId;
    public String genderId;
    public String fromdate;
    public String todate;
    public boolean todaydate;
    public String[] unitList;
    public String[] mstgenderlist;
    public int limit;
    public int offset;
    public boolean print;
    public long unitId;

    public long getUnitId() {
        return unitId;
    }

    public void setUnitId(long unitId) {
        this.unitId = unitId;
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

    public String getRefentityTypeId() {
        return refentityTypeId;
    }

    public void setRefentityTypeId(String refentityTypeId) {
        this.refentityTypeId = refentityTypeId;
    }

    public String getRefentityId() {
        return refentityId;
    }

    public void setRefentityId(String refentityId) {
        this.refentityId = refentityId;
    }

    public String getSpecialityId() {
        return specialityId;
    }

    public void setSpecialityId(String specialityId) {
        this.specialityId = specialityId;
    }

    public String getGenderId() {
        return genderId;
    }

    public void setGenderId(String genderId) {
        this.genderId = genderId;
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

    public String[] getUnitList() {
        return unitList;
    }

    public void setUnitList(String[] unitList) {
        this.unitList = unitList;
    }

    public String[] getMstgenderlist() {
        return mstgenderlist;
    }

    public void setMstgenderlist(String[] mstgenderlist) {
        this.mstgenderlist = mstgenderlist;
    }

}

package com.cellbeans.hspa.mis.misopdreports;

import com.cellbeans.hspa.mstunit.MstUnit;

public class OpdListOfRegistrationSearchDTO {

    public String agerangeId;
    public String genderId;
    public String maritalstatusId;
    public String bloodgroupId;
    public String DORfromdate;
    public String DORtodate;
    public String DOBfromdate;
    public String DOBtodate;
    public boolean todaydate;
    public String[] unitList;
    public String[] mstgenderlist;
    public String[] MstBloodgrouplist;
    public String[] mstmaritalstatuslist;
    public String[] Mstagerangelist;
    public int limit;
    public int offset;
    public boolean print;
    public long unitId;
    public long userId;
    public MstUnit objHeaderData;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public MstUnit getObjHeaderData() {
        return objHeaderData;
    }

    public void setObjHeaderData(MstUnit objHeaderData) {
        this.objHeaderData = objHeaderData;
    }

    public long getUnitId() {
        return unitId;
    }

    public void setUnitId(long unitId) {
        this.unitId = unitId;
    }
    //    public int limit;
//    public int offset;
//    public boolean print;

    public String[] getMstagerangelist() {
        return Mstagerangelist;
    }

    public void setMstagerangelist(String[] mstagerangelist) {
        Mstagerangelist = mstagerangelist;
    }

    public String[] getMstmaritalstatuslist() {
        return mstmaritalstatuslist;
    }

    public void setMstmaritalstatuslist(String[] mstmaritalstatuslist) {
        this.mstmaritalstatuslist = mstmaritalstatuslist;
    }

    public String[] getMstBloodgrouplist() {
        return MstBloodgrouplist;
    }

    public void setMstBloodgrouplist(String[] mstBloodgrouplist) {
        MstBloodgrouplist = mstBloodgrouplist;
    }

    public String[] getMstgenderlist() {
        return mstgenderlist;
    }

    public void setMstgenderlist(String[] mstgenderlist) {
        this.mstgenderlist = mstgenderlist;
    }

    public String[] getUnitList() {
        return unitList;
    }

    public void setUnitList(String[] unitList) {
        this.unitList = unitList;
    }

    public String getAgerangeId() {
        return agerangeId;
    }

    public void setAgerangeId(String agerangeId) {
        this.agerangeId = agerangeId;
    }

    public String getGenderId() {
        return genderId;
    }

    public void setGenderId(String genderId) {
        this.genderId = genderId;
    }

    public String getMaritalstatusId() {
        return maritalstatusId;
    }

    public void setMaritalstatusId(String maritalstatusId) {
        this.maritalstatusId = maritalstatusId;
    }

    public String getBloodgroupId() {
        return bloodgroupId;
    }

    public void setBloodgroupId(String bloodgroupId) {
        this.bloodgroupId = bloodgroupId;
    }

    public String getDORfromdate() {
        return DORfromdate;
    }

    public void setDORfromdate(String DORfromdate) {
        this.DORfromdate = DORfromdate;
    }

    public String getDORtodate() {
        return DORtodate;
    }

    public void setDORtodate(String DORtodate) {
        this.DORtodate = DORtodate;
    }

    public String getDOBfromdate() {
        return DOBfromdate;
    }

    public void setDOBfromdate(String DOBfromdate) {
        this.DOBfromdate = DOBfromdate;
    }

    public String getDOBtodate() {
        return DOBtodate;
    }

    public void setDOBtodate(String DOBtodate) {
        this.DOBtodate = DOBtodate;
    }

    public boolean getTodaydate() {
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

    public boolean getPrint() {
        return print;
    }

    public void setPrint(boolean print) {
        this.print = print;
    }

}



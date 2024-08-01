package com.cellbeans.hspa.mis.misbillingreport;

public class OpdIpdServiceWiseBillSearchDTO {

    public String mrNo;
    public String ctId; // Company Type
    public String companyId; //Company Name /coarogory
    public String ptId; //patient type
    public String psId;  // patient source
    public String fromdate;
    public String todate;
    public boolean todaydate;
    public String serviceId;
    public String staffId;
    // public boolean IPDFlag;
    public int limit;
    public int offset;
    public boolean print;
    public long unitId;
    public long userId;
    public String refentityTypeId;
    public String refentityId;

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getMrNo() {
        return mrNo;
    }

    public void setMrNo(String mrNo) {
        this.mrNo = mrNo;
    }

    public String getCtId() {
        return ctId;
    }

    public void setCtId(String ctId) {
        this.ctId = ctId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getPtId() {
        return ptId;
    }

    public void setPtId(String ptId) {
        this.ptId = ptId;
    }

    public String getPsId() {
        return psId;
    }

    public void setPsId(String psId) {
        this.psId = psId;
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

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
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

    public long getUnitId() {
        return unitId;
    }

    public void setUnitId(long unitId) {
        this.unitId = unitId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
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
}


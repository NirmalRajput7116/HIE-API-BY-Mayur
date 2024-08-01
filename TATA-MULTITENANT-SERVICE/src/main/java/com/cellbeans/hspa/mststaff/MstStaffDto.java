package com.cellbeans.hspa.mststaff;

public class MstStaffDto {

    public Long staffId;
    public String fullname;
    public String unitName;
    public String departmentName;
    public String sdName;
    public int count;

    /* public MstStaffDto(Long staffId, String fullname, String unitName, String departmentName, String sdName, int count) {
         this.staffId = staffId;
         this.fullname = fullname;
         this.unitName = unitName;
         this.departmentName = departmentName;
         this.sdName = sdName;
         this.count = count;
     }*/
    public MstStaffDto(Long staffId, String fullname) {
        this.staffId = staffId;
        this.fullname = fullname;
    }

    public MstStaffDto(String fullname, String unitName, String departmentName, String sdName, Long staffId) {
        this.fullname = fullname;
        this.unitName = unitName;
        this.departmentName = departmentName;
        this.sdName = sdName;
        this.staffId = staffId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Long getStaffId() {
        return staffId;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getSdName() {
        return sdName;
    }

    public void setSdName(String sdName) {
        this.sdName = sdName;
    }
}

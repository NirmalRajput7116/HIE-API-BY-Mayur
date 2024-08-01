package com.cellbeans.hspa.mis.misinventoryexpiryreport;

import com.cellbeans.hspa.mstunit.MstUnit;
import com.cellbeans.hspa.mstuser.MstUser;

public class MisservicecountDTO {

    public String service_code;
    public String service_name;
    public String bs_service_id;
    public String countData;
    //    public string
    public long count;
    public boolean print;
    public long unitId;
    public String unit_name;
    public String Staff_name;
    public String dept_name;
    public MstUnit objHeaderData;
    public MstUser headerObjectUser;

    public MstUser getHeaderObjectUser() {
        return headerObjectUser;
    }

    public void setHeaderObjectUser(MstUser headerObjectUser) {
        this.headerObjectUser = headerObjectUser;
    }

    public MstUnit getObjHeaderData() {
        return objHeaderData;
    }

    public void setObjHeaderData(MstUnit objHeaderData) {
        this.objHeaderData = objHeaderData;
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

    public String getUnit_name() {
        return unit_name;
    }

    public void setUnit_name(String unit_name) {
        this.unit_name = unit_name;
    }

    public String getStaff_name() {
        return Staff_name;
    }

    public void setStaff_name(String staff_name) {
        Staff_name = staff_name;
    }

    public String getDept_name() {
        return dept_name;
    }

    public void setDept_name(String dept_name) {
        this.dept_name = dept_name;
    }

    public String getCountData() {
        return countData;
    }

    public void setCountData(String countData) {
        this.countData = countData;
    }

    public String getService_code() {
        return service_code;
    }

    public void setService_code(String service_code) {
        this.service_code = service_code;
    }

    public String getService_name() {
        return service_name;
    }

    public void setService_name(String service_name) {
        this.service_name = service_name;
    }

    public String getBs_service_id() {
        return bs_service_id;
    }

    public void setBs_service_id(String bs_service_id) {
        this.bs_service_id = bs_service_id;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

}

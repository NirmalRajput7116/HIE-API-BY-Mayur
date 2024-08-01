package com.cellbeans.hspa.mis.CountReprotMIS;

import java.math.BigInteger;
import java.util.Map;

public class AppoinmentwisePatientCountDto {

    public String unitName;
    private Map<String, BigInteger> appoinmentcount;

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public Map<String, BigInteger> getAppoinmentcount() {
        return appoinmentcount;
    }

    public void setAppoinmentcount(Map<String, BigInteger> appoinmentcount) {
        this.appoinmentcount = appoinmentcount;
    }

}

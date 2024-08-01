package com.cellbeans.hspa.mis.CountReprotMIS;

import java.math.BigInteger;
import java.util.Map;

public class UnitGenderWisePatientCountDto {

    public String unitName;
    private Map<String, BigInteger> agecount;

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public Map<String, BigInteger> getAgecount() {
        return agecount;
    }

    public void setAgecount(Map<String, BigInteger> agecount) {
        this.agecount = agecount;
    }

}

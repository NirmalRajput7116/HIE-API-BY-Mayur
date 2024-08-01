package com.cellbeans.hspa.mis.CountReprotMIS;

import java.math.BigInteger;

public class NoOfBillGeneratedCountDto {

    public String unitName;
    public BigInteger count;

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public BigInteger getCount() {
        return count;
    }

    public void setCount(BigInteger count) {
        this.count = count;
    }

}

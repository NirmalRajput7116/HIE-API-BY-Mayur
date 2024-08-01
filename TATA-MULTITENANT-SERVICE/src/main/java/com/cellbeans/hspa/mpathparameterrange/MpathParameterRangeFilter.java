package com.cellbeans.hspa.mpathparameterrange;

import java.io.Serializable;

public class MpathParameterRangeFilter implements Serializable {
    private static final long serialVersionUID = 1L;

    private long prAge;

    private long prAgeFrom;

    private long prAgeTo;

    private long prParameterId;

    private long prGenderId;

    public long getPrAge() {
        return prAge;
    }

    public void setPrAge(long prAge) {
        this.prAge = prAge;
    }

    public long getPrAgeFrom() {
        return prAgeFrom;
    }

    public void setPrAgeFrom(long prAgeFrom) {
        this.prAgeFrom = prAgeFrom;
    }

    public long getPrAgeTo() {
        return prAgeTo;
    }

    public void setPrAgeTo(long prAgeTo) {
        this.prAgeTo = prAgeTo;
    }

    public long getPrParameterId() {
        return prParameterId;
    }

    public void setPrParameterId(long prParameterId) {
        this.prParameterId = prParameterId;
    }

    public long getPrGenderId() {
        return prGenderId;
    }

    public void setPrGenderId(long prGenderId) {
        this.prGenderId = prGenderId;
    }

    @Override
    public String toString() {
        return "MpathParameterRangeFilter{" + "prAge=" + prAge + ", prAgeFrom=" + prAgeFrom + ", prAgeTo=" + prAgeTo + ", prParameterId=" + prParameterId + ", prGenderId=" + prGenderId + '}';
    }

}

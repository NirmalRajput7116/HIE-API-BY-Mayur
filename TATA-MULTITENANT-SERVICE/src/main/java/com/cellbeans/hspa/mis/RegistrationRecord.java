package com.cellbeans.hspa.mis;

public class RegistrationRecord {

    String strUnitname;
    long patientCount;
    long maleCount;
    long femaleCount;
    long othersCount;
    long notspeCount;

    public String getStrUnitname() {
        return strUnitname;
    }

    public void setStrUnitname(String strUnitname) {
        this.strUnitname = strUnitname;
    }

    public long getPatientCount() {
        return patientCount;
    }

    public void setPatientCount(long patientCount) {
        this.patientCount = patientCount;
    }

    public long getMaleCount() {
        return maleCount;
    }

    public void setMaleCount(long maleCount) {
        this.maleCount = maleCount;
    }

    public long getFemaleCount() {
        return femaleCount;
    }

    public void setFemaleCount(long femaleCount) {
        this.femaleCount = femaleCount;
    }

    public long getOthersCount() {
        return othersCount;
    }

    public void setOthersCount(long othersCount) {
        this.othersCount = othersCount;
    }

    public long getNotspeCount() {
        return notspeCount;
    }

    public void setNotspeCount(long notspeCount) {
        this.notspeCount = notspeCount;
    }

    @Override
    public String toString() {
        return "RegistrationRecord{" + "strUnitname='" + strUnitname + '\'' + ", patientCount=" + patientCount + ", maleCount=" + maleCount + ", femaleCount=" + femaleCount + ", othersCount=" + othersCount + ", notspeCount=" + notspeCount + '}';
    }

}

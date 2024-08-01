package com.cellbeans.hspa.trnadmission;

public class WardWiseBedSummary {
    String wardName;
    String occBed = "0";
    String availableBed = "0";
    String houseKeepingBed = "0";
    String underMaintenanceBed = "0";

    public String getWardName() {
        return wardName;
    }

    public void setWardName(String wardName) {
        this.wardName = wardName;
    }

    public String getOccBed() {
        return occBed;
    }

    public void setOccBed(String occBed) {
        this.occBed = occBed;
    }

    public String getAvailableBed() {
        return availableBed;
    }

    public void setAvailableBed(String availableBed) {
        this.availableBed = availableBed;
    }

    public String getHouseKeepingBed() {
        return houseKeepingBed;
    }

    public void setHouseKeepingBed(String houseKeepingBed) {
        this.houseKeepingBed = houseKeepingBed;
    }

    public String getUnderMaintenanceBed() {
        return underMaintenanceBed;
    }

    public void setUnderMaintenanceBed(String underMaintenanceBed) {
        this.underMaintenanceBed = underMaintenanceBed;
    }
}

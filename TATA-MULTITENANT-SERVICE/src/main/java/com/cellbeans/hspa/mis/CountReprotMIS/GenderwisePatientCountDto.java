package com.cellbeans.hspa.mis.CountReprotMIS;

import java.util.List;

public class GenderwisePatientCountDto {

    public String unitName;
    private List<GenderDetailsDto> genderCount;

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public List<GenderDetailsDto> getGenderCount() {
        return genderCount;
    }

    public void setGenderCount(List<GenderDetailsDto> genderCount) {
        this.genderCount = genderCount;
    }

}

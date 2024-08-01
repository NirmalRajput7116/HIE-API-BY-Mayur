package com.cellbeans.hspa.mis.CountReprotMIS;

import java.util.List;

public class RegistrationLandmarkatientCountDto {

    public String unitName;
    private List<RegistrationLandmarkDetailsDto> regLandmarkcount;

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public List<RegistrationLandmarkDetailsDto> getRegLandmarkcount() {
        return regLandmarkcount;
    }

    public void setRegLandmarkcount(List<RegistrationLandmarkDetailsDto> regLandmarkcount) {
        this.regLandmarkcount = regLandmarkcount;
    }

}

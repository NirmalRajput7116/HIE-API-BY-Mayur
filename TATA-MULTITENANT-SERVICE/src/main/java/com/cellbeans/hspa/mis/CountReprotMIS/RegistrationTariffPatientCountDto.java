package com.cellbeans.hspa.mis.CountReprotMIS;

import java.util.List;

public class RegistrationTariffPatientCountDto {

    public String unitName;
    private List<RegistrationTariffDetailsDto> registrationCount;

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public List<RegistrationTariffDetailsDto> getRegistrationCount() {
        return registrationCount;
    }

    public void setRegistrationCount(List<RegistrationTariffDetailsDto> registrationCount) {
        this.registrationCount = registrationCount;
    }

}

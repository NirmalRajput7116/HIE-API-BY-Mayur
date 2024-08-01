package com.cellbeans.hspa.mis.CountReprotMIS;

import java.util.List;

public class DiagnosisWisePatientCountDto {

    public String unitName;
    private List<DiagnosisDetailsDto> dignosisDetails;

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public List<DiagnosisDetailsDto> getDignosisDetails() {
        return dignosisDetails;
    }

    public void setDignosisDetails(List<DiagnosisDetailsDto> dignosisDetails) {
        this.dignosisDetails = dignosisDetails;
    }

}

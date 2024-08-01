package com.cellbeans.hspa.mis.CountReprotMIS;

public class DiagnosisDetailsDto {

    public String diagnosisDescriptionName;
    public String diagnosisCode;
    public int diagnosisCount;

    public String getDiagnosisDescriptionName() {
        return diagnosisDescriptionName;
    }

    public void setDiagnosisDescriptionName(String diagnosisDescriptionName) {
        this.diagnosisDescriptionName = diagnosisDescriptionName;
    }

    public String getDiagnosisCode() {
        return diagnosisCode;
    }

    public void setDiagnosisCode(String diagnosisCode) {
        this.diagnosisCode = diagnosisCode;
    }

    public int getDiagnosisCount() {
        return diagnosisCount;
    }

    public void setDiagnosisCount(int diagnosisCount) {
        this.diagnosisCount = diagnosisCount;
    }

}


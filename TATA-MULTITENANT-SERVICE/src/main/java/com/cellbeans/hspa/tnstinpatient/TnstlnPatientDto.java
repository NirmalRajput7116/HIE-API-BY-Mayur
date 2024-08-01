package com.cellbeans.hspa.tnstinpatient;

public class TnstlnPatientDto {
    private String pharmacyType;
    private String searchFromDate;
    private String searchMrNo;
    // private  String searchPatientName;
    private String patientFirstName;
    private String patientLastName;
    private String searchToDate;

    public String getPharmacyType() {
        return pharmacyType;
    }

    public String getPatientFirstName() {
        return patientFirstName;
    }

    public void setPatientFirstName(String patientFirstName) {
        this.patientFirstName = patientFirstName;
    }

    public String getPatientLastName() {
        return patientLastName;
    }

    public void setPatientLastName(String patientLastName) {
        this.patientLastName = patientLastName;
    }

    public void setPharmacyType(String pharmacyType) {
        this.pharmacyType = pharmacyType;
    }

    public String getSearchFromDate() {
        return searchFromDate;
    }

    public void setSearchFromDate(String searchFromDate) {
        this.searchFromDate = searchFromDate;
    }

    public String getSearchMrNo() {
        return searchMrNo;
    }

    public void setSearchMrNo(String searchMrNo) {
        this.searchMrNo = searchMrNo;
    }
//    public String getSearchPatientName() {
//        return searchPatientName;
//    }
//
//    public void setSearchPatientName(String searchPatientName) {
//        this.searchPatientName = searchPatientName;
//    }

    public String getSearchToDate() {
        return searchToDate;
    }

    public void setSearchToDate(String searchToDate) {
        this.searchToDate = searchToDate;
    }
}

package com.cellbeans.hspa.trnadmission;

public class TrnAdmissionSearchDTO {

    private String searchMrNo;

    private String searchPatientName;

    private Long searchGenderId;

    private String searchFromDate;

    private String searchToDate;

    private String searchUserName;

    private String searchDoctorName;

    private int searchAnyPageNo;

    private int searchAnyLimit;

    private Boolean print;

    private String searchAnySearch;

    public String getSearchMrNo() {
        return searchMrNo;
    }

    public void setSearchMrNo(String searchMrNo) {
        this.searchMrNo = searchMrNo;
    }

    public String getSearchPatientName() {
        return searchPatientName;
    }

    public void setSearchPatientName(String searchPatientName) {
        this.searchPatientName = searchPatientName;
    }

    public Long getSearchGenderId() {
        return searchGenderId;
    }

    public void setSearchGenderId(Long searchGenderId) {
        this.searchGenderId = searchGenderId;
    }

    public String getSearchFromDate() {
        return searchFromDate;
    }

    public void setSearchFromDate(String searchFromDate) {
        this.searchFromDate = searchFromDate;
    }

    public String getSearchToDate() {
        return searchToDate;
    }

    public void setSearchToDate(String searchToDate) {
        this.searchToDate = searchToDate;
    }

    public String getSearchUserName() {
        return searchUserName;
    }

    public void setSearchUserName(String searchUserName) {
        this.searchUserName = searchUserName;
    }

    public String getSearchDoctorName() {
        return searchDoctorName;
    }

    public void setSearchDoctorName(String searchDoctorName) {
        this.searchDoctorName = searchDoctorName;
    }

    public int getSearchAnyPageNo() {
        return searchAnyPageNo;
    }

    public void setSearchAnyPageNo(int searchAnyPageNo) {
        this.searchAnyPageNo = searchAnyPageNo;
    }

    public int getSearchAnyLimit() {
        return searchAnyLimit;
    }

    public void setSearchAnyLimit(int searchAnyLimit) {
        this.searchAnyLimit = searchAnyLimit;
    }

    public Boolean getPrint() {
        return print;
    }

    public void setPrint(Boolean print) {
        this.print = print;
    }

    public String getSearchAnySearch() {
        return searchAnySearch;
    }

    public void setSearchAnySearch(String searchAnySearch) {
        this.searchAnySearch = searchAnySearch;
    }

}
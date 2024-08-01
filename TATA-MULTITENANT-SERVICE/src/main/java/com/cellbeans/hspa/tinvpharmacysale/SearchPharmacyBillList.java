package com.cellbeans.hspa.tinvpharmacysale;

import com.cellbeans.hspa.mbilltariff.MbillTariff;

public class SearchPharmacyBillList {
    private String searchMrNo;
    private String searchPatientName;
    private String searchMobileNo;
    private String searchFromDate;
    private String searchToDate;
    private String searchItemName;
    private MbillTariff searchmbillTariffId;
    private int offset;
    private int limit;
    private long unitId;
    private int pharmacyType;

    public int getPharmacyType() {
        return pharmacyType;
    }

    public void setPharmacyType(int pharmacyType) {
        this.pharmacyType = pharmacyType;
    }

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

    public String getSearchItemName() {
        return searchItemName;
    }

    public void setSearchItemName(String searchItemName) {
        this.searchItemName = searchItemName;
    }

    public String getSearchMobileNo() {
        return searchMobileNo;
    }

    public void setSearchMobileNo(String searchMobileNo) {
        this.searchMobileNo = searchMobileNo;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public MbillTariff getSearchmbillTariffId() {
        return searchmbillTariffId;
    }

    public void setSearchmbillTariffId(MbillTariff searchmbillTariffId) {
        this.searchmbillTariffId = searchmbillTariffId;
    }

    public long getUnitId() {
        return unitId;
    }

    public void setUnitId(long unitId) {
        this.unitId = unitId;
    }

}

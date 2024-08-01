package com.cellbeans.hspa.mbilltariff;

public class MBillTariffDTO {

    private long tariffId;
    private String tariffName;
    private String tariffCode;
    private double tariffCoPay;
    private double tariffDiscount;
    private Boolean isActive;
    private Boolean isDeleted;

    public MBillTariffDTO() {
    }

    public MBillTariffDTO(long tariffId, String tariffName, String tariffCode, double tariffCoPay, double tariffDiscount, Boolean isActive, Boolean isDeleted) {
        this.tariffId = tariffId;
        this.tariffName = tariffName;
        this.tariffCode = tariffCode;
        this.tariffCoPay = tariffCoPay;
        this.tariffDiscount = tariffDiscount;
        this.isActive = isActive;
        this.isDeleted = isDeleted;
    }

    public long getTariffId() {
        return tariffId;
    }

    public void setTariffId(long tariffId) {
        this.tariffId = tariffId;
    }

    public String getTariffName() {
        return tariffName;
    }

    public void setTariffName(String tariffName) {
        this.tariffName = tariffName;
    }

    public String getTariffCode() {
        return tariffCode;
    }

    public void setTariffCode(String tariffCode) {
        this.tariffCode = tariffCode;
    }

    public double getTariffCoPay() {
        return tariffCoPay;
    }

    public void setTariffCoPay(double tariffCoPay) {
        this.tariffCoPay = tariffCoPay;
    }

    public double getTariffDiscount() {
        return tariffDiscount;
    }

    public void setTariffDiscount(double tariffDiscount) {
        this.tariffDiscount = tariffDiscount;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

}
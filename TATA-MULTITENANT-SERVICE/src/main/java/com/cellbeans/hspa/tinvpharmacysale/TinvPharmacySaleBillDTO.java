package com.cellbeans.hspa.tinvpharmacysale;

public class TinvPharmacySaleBillDTO {
    private long psId;

    private double psTotalAmt;
    private double psConcsssionPercentage;

    private double psConcsssionAmount;

    private String psConcessionReason;

    private String psNetAmt;

    private double psBalance;

    private double psTax;

    private String createdDate;
    private long psUnitId;
    private long psPatientId;
    private long psVisitTariffId;
    private int type;

    public TinvPharmacySaleBillDTO(long psId, double psTotalAmt, double psConcsssionPercentage, double psConcsssionAmount, String psConcessionReason, String psNetAmt, double psBalance, double psTax, String createdDate, long psUnitId, long psPatientId, long psVisitTariffId, int type) {
        this.psId = psId;
        this.psTotalAmt = psTotalAmt;
        this.psConcsssionPercentage = psConcsssionPercentage;
        this.psConcsssionAmount = psConcsssionAmount;
        this.psConcessionReason = psConcessionReason;
        this.psNetAmt = psNetAmt;
        this.psBalance = psBalance;
        this.psTax = psTax;
        this.createdDate = createdDate;
        this.psUnitId = psUnitId;
        this.psPatientId = psPatientId;
        this.psVisitTariffId = psVisitTariffId;
        this.type = type;
    }

    public long getPsId() {
        return psId;
    }

    public void setPsId(long psId) {
        this.psId = psId;
    }

    public double getPsTotalAmt() {
        return psTotalAmt;
    }

    public void setPsTotalAmt(double psTotalAmt) {
        this.psTotalAmt = psTotalAmt;
    }

    public double getPsConcsssionPercentage() {
        return psConcsssionPercentage;
    }

    public void setPsConcsssionPercentage(double psConcsssionPercentage) {
        this.psConcsssionPercentage = psConcsssionPercentage;
    }

    public double getPsConcsssionAmount() {
        return psConcsssionAmount;
    }

    public void setPsConcsssionAmount(double psConcsssionAmount) {
        this.psConcsssionAmount = psConcsssionAmount;
    }

    public String getPsConcessionReason() {
        return psConcessionReason;
    }

    public void setPsConcessionReason(String psConcessionReason) {
        this.psConcessionReason = psConcessionReason;
    }

    public String getPsNetAmt() {
        return psNetAmt;
    }

    public void setPsNetAmt(String psNetAmt) {
        this.psNetAmt = psNetAmt;
    }

    public double getPsBalance() {
        return psBalance;
    }

    public void setPsBalance(double psBalance) {
        this.psBalance = psBalance;
    }

    public double getPsTax() {
        return psTax;
    }

    public void setPsTax(double psTax) {
        this.psTax = psTax;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public long getPsUnitId() {
        return psUnitId;
    }

    public void setPsUnitId(long psUnitId) {
        this.psUnitId = psUnitId;
    }

    public long getPsPatientId() {
        return psPatientId;
    }

    public void setPsPatientId(long psPatientId) {
        this.psPatientId = psPatientId;
    }

    public long getPsVisitTariffId() {
        return psVisitTariffId;
    }

    public void setPsVisitTariffId(long psVisitTariffId) {
        this.psVisitTariffId = psVisitTariffId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}

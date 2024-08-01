package com.cellbeans.hspa.mis.misbillingreport;

public class InvoiceSummaryDTO {

    public String UnitName;
    public String NoofBillGenerated;
    public String TotalGrossBillAmount;
    public String ToatalDiscountOnService;
    public String TotalConcessionsonBill;
    public String TotalNetBillAmount;
    public String TotalPaymentReceived;

    public String getUnitName() {
        return UnitName;
    }

    public void setUnitName(String unitName) {
        UnitName = unitName;
    }

    public String getNoofBillGenerated() {
        return NoofBillGenerated;
    }

    public void setNoofBillGenerated(String noofBillGenerated) {
        NoofBillGenerated = noofBillGenerated;
    }

    public String getTotalGrossBillAmount() {
        return TotalGrossBillAmount;
    }

    public void setTotalGrossBillAmount(String totalGrossBillAmount) {
        TotalGrossBillAmount = totalGrossBillAmount;
    }

    public String getToatalDiscountOnService() {
        return ToatalDiscountOnService;
    }

    public void setToatalDiscountOnService(String toatalDiscountOnService) {
        ToatalDiscountOnService = toatalDiscountOnService;
    }

    public String getTotalConcessionsonBill() {
        return TotalConcessionsonBill;
    }

    public void setTotalConcessionsonBill(String totalConcessionsonBill) {
        TotalConcessionsonBill = totalConcessionsonBill;
    }

    public String getTotalNetBillAmount() {
        return TotalNetBillAmount;
    }

    public void setTotalNetBillAmount(String totalNetBillAmount) {
        TotalNetBillAmount = totalNetBillAmount;
    }

    public String getTotalPaymentReceived() {
        return TotalPaymentReceived;
    }

    public void setTotalPaymentReceived(String totalPaymentReceived) {
        TotalPaymentReceived = totalPaymentReceived;
    }

}

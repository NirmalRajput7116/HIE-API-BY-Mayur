package com.cellbeans.hspa.mis.payoutreport;

import com.cellbeans.hspa.mstunit.MstUnit;

public class PayoutReportListDTO {

    public String payoutId;
    public String dateAndTime;
    public String TransactionType;
    public String fromCashCounter;
    public String fromStaff;
    public String toCashCounter;
    public String toStaff;
    public String amount;
    public String remarks;
    public long count;
    public MstUnit objHeaderData;

    public String getPayoutId() {
        return payoutId;
    }

    public void setPayoutId(String payoutId) {
        this.payoutId = payoutId;
    }

    public String getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(String dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public String getTransactionType() {
        return TransactionType;
    }

    public void setTransactionType(String transactionType) {
        TransactionType = transactionType;
    }

    public String getFromCashCounter() {
        return fromCashCounter;
    }

    public void setFromCashCounter(String fromCashCounter) {
        this.fromCashCounter = fromCashCounter;
    }

    public String getFromStaff() {
        return fromStaff;
    }

    public void setFromStaff(String fromStaff) {
        this.fromStaff = fromStaff;
    }

    public String getToCashCounter() {
        return toCashCounter;
    }

    public void setToCashCounter(String toCashCounter) {
        this.toCashCounter = toCashCounter;
    }

    public String getToStaff() {
        return toStaff;
    }

    public void setToStaff(String toStaff) {
        this.toStaff = toStaff;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public MstUnit getObjHeaderData() {
        return objHeaderData;
    }

    public void setObjHeaderData(MstUnit objHeaderData) {
        this.objHeaderData = objHeaderData;
    }

}

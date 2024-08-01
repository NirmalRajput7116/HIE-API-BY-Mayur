package com.cellbeans.hspa.tbillbill;

import java.util.Date;

public class TBillBillDto {

    private long billId;
    private String billNumber;
    private Date createdDate;
    private Boolean ipdBill;
    private String patientMrNo;
    private String titleName;
    private String userFirstname;
    private String userMiddlename;
    private String userLastname;
    private String billOpdNumber;
    private double billTotCoPay;
    private double billDiscountAmount;
    private double billTotCoPayOutstanding;
    private Boolean finalBill;
    private double billSubTotal;
    private double billNetPayable;
    private double billAmountPaid;
    private double billOutstanding;
    private Boolean billNill;
    private long tariffId;
    private Boolean isCancelled;
    private int total;
    private String admissionIpdNo;
    private double refundAmount;
    private double billCompanyPayable;
    private double billCompanyPaid;
    private double billCompanyOutstanding;
    private double companyNetPay;
    private double grossAmount;
    private Boolean companyBillApproval;
    private Boolean isApprove;
    private Boolean isRejected;
    private Boolean freezed;

    public TBillBillDto(long billId, String billNumber, Date createdDate, Boolean ipdBill, String patientMrNo,
                        String titleName, String userFirstname, String userMiddlename, String userLastname, String billOpdNumber,
                        double billTotCoPay, double billTotCoPayOutstanding, Boolean finalBill, double billSubTotal,
                        double billNetPayable, double billAmountPaid, double billOutstanding, Boolean billNill, long tariffId,
                        String admissionIpdNo, Boolean isCancelled, double grossAmount) {
        this.billId = billId;
        this.billNumber = billNumber;
        this.createdDate = createdDate;
        this.ipdBill = ipdBill;
        this.patientMrNo = patientMrNo;
        this.titleName = titleName;
        this.userFirstname = userFirstname;
        this.userMiddlename = userMiddlename;
        this.userLastname = userLastname;
        this.billOpdNumber = billOpdNumber;
        this.billTotCoPay = billTotCoPay;
        this.billTotCoPayOutstanding = billTotCoPayOutstanding;
        this.finalBill = finalBill;
        this.billSubTotal = billSubTotal;
        this.billNetPayable = billNetPayable;
        this.billAmountPaid = billAmountPaid;
        this.billOutstanding = billOutstanding;
        this.billNill = billNill;
        this.tariffId = tariffId;
        this.admissionIpdNo = admissionIpdNo;
        this.isCancelled = isCancelled;
        this.grossAmount = grossAmount;
    }

    public TBillBillDto(long billId, String billNumber, Date createdDate, Boolean ipdBill, String patientMrNo,
                        String titleName, String userFirstname, String userMiddlename, String userLastname, String billOpdNumber,
                        double billTotCoPay, double billTotCoPayOutstanding, Boolean finalBill, double billSubTotal,
                        double billNetPayable, double billAmountPaid, double billOutstanding, Boolean billNill, long tariffId,
                        String admissionIpdNo, double refundAmount, double companyNetPay, Boolean isCancelled, double grossAmount) {
        this.billId = billId;
        this.billNumber = billNumber;
        this.createdDate = createdDate;
        this.ipdBill = ipdBill;
        this.patientMrNo = patientMrNo;
        this.titleName = titleName;
        this.userFirstname = userFirstname;
        this.userMiddlename = userMiddlename;
        this.userLastname = userLastname;
        this.billOpdNumber = billOpdNumber;
        this.billTotCoPay = billTotCoPay;
        this.billTotCoPayOutstanding = billTotCoPayOutstanding;
        this.finalBill = finalBill;
        this.billSubTotal = billSubTotal;
        this.billNetPayable = billNetPayable;
        this.billAmountPaid = billAmountPaid;
        this.billOutstanding = billOutstanding;
        this.billNill = billNill;
        this.tariffId = tariffId;
        this.admissionIpdNo = admissionIpdNo;
        this.refundAmount = refundAmount;
        this.companyNetPay = companyNetPay;
        this.isCancelled = isCancelled;
        this.grossAmount = grossAmount;
    }

    public TBillBillDto(long billId, String billNumber, Date createdDate, Boolean ipdBill, String patientMrNo,
                        String titleName, String userFirstname, String userMiddlename, String userLastname, String billOpdNumber,
                        double billTotCoPay, double billTotCoPayOutstanding, Boolean finalBill, double billSubTotal,
                        double billNetPayable, double billAmountPaid, double billOutstanding, Boolean billNill, long tariffId,
                        String admissionIpdNo, double refundAmount) {
        this.billId = billId;
        this.billNumber = billNumber;
        this.createdDate = createdDate;
        this.ipdBill = ipdBill;
        this.patientMrNo = patientMrNo;
        this.titleName = titleName;
        this.userFirstname = userFirstname;
        this.userMiddlename = userMiddlename;
        this.userLastname = userLastname;
        this.billOpdNumber = billOpdNumber;
        this.billTotCoPay = billTotCoPay;
        this.billTotCoPayOutstanding = billTotCoPayOutstanding;
        this.finalBill = finalBill;
        this.billSubTotal = billSubTotal;
        this.billNetPayable = billNetPayable;
        this.billAmountPaid = billAmountPaid;
        this.billOutstanding = billOutstanding;
        this.billNill = billNill;
        this.tariffId = tariffId;
        this.admissionIpdNo = admissionIpdNo;
        this.refundAmount = refundAmount;
    }

    public TBillBillDto(long billId, String billNumber, Date createdDate, Boolean ipdBill, String patientMrNo,
                        String titleName, String userFirstname, String userMiddlename, String userLastname, String billOpdNumber,
                        double billTotCoPay, double billTotCoPayOutstanding, Boolean finalBill, double billSubTotal,
                        double billNetPayable, double billAmountPaid, double billOutstanding, Boolean billNill, long tariffId,
                        String admissionIpdNo, double refundAmount, double companyNetPay, double grossAmount) {
        this.billId = billId;
        this.billNumber = billNumber;
        this.createdDate = createdDate;
        this.ipdBill = ipdBill;
        this.patientMrNo = patientMrNo;
        this.titleName = titleName;
        this.userFirstname = userFirstname;
        this.userMiddlename = userMiddlename;
        this.userLastname = userLastname;
        this.billOpdNumber = billOpdNumber;
        this.billTotCoPay = billTotCoPay;
        this.billTotCoPayOutstanding = billTotCoPayOutstanding;
        this.finalBill = finalBill;
        this.billSubTotal = billSubTotal;
        this.billNetPayable = billNetPayable;
        this.billAmountPaid = billAmountPaid;
        this.billOutstanding = billOutstanding;
        this.billNill = billNill;
        this.tariffId = tariffId;
        this.admissionIpdNo = admissionIpdNo;
        this.refundAmount = refundAmount;
        this.companyNetPay = companyNetPay;
        this.grossAmount = grossAmount;
    }

    public TBillBillDto(long billId, String billNumber, Date createdDate, Boolean ipdBill, String patientMrNo,
                        String titleName, String userFirstname, String userMiddlename, String userLastname, String billOpdNumber,
                        double billTotCoPay, double billTotCoPayOutstanding, Boolean finalBill, double billSubTotal,
                        double billNetPayable, double billAmountPaid, double billOutstanding, Boolean billNill, long tariffId,
                        String admissionIpdNo, double refundAmount, double companyNetPay, double grossAmount, boolean isApprove, boolean isRejected) {
        System.out.println("1");
        this.billId = billId;
        this.billNumber = billNumber;
        this.createdDate = createdDate;
        this.ipdBill = ipdBill;
        this.patientMrNo = patientMrNo;
        this.titleName = titleName;
        this.userFirstname = userFirstname;
        this.userMiddlename = userMiddlename;
        this.userLastname = userLastname;
        this.billOpdNumber = billOpdNumber;
        this.billTotCoPay = billTotCoPay;
        this.billTotCoPayOutstanding = billTotCoPayOutstanding;
        this.finalBill = finalBill;
        this.billSubTotal = billSubTotal;
        this.billNetPayable = billNetPayable;
        this.billAmountPaid = billAmountPaid;
        this.billOutstanding = billOutstanding;
        this.billNill = billNill;
        this.tariffId = tariffId;
        this.admissionIpdNo = admissionIpdNo;
        this.refundAmount = refundAmount;
        this.companyNetPay = companyNetPay;
        this.grossAmount = grossAmount;
        this.isApprove = isApprove;
        this.isRejected = isRejected;

    }

    public TBillBillDto(long billId, String billNumber, Date createdDate, Boolean ipdBill, String patientMrNo,
                        String titleName, String userFirstname, String userMiddlename, String userLastname, String billOpdNumber,
                        double billTotCoPay, double billTotCoPayOutstanding, Boolean finalBill, double billSubTotal,
                        double billNetPayable, double billAmountPaid, double billOutstanding, Boolean billNill, long tariffId,
                        String admissionIpdNo, double refundAmount, double companyNetPay, double grossAmount, boolean isApprove, boolean isRejected, boolean freezed, boolean isCancelled) {
        System.out.println("1");
        this.billId = billId;
        this.billNumber = billNumber;
        this.createdDate = createdDate;
        this.ipdBill = ipdBill;
        this.patientMrNo = patientMrNo;
        this.titleName = titleName;
        this.userFirstname = userFirstname;
        this.userMiddlename = userMiddlename;
        this.userLastname = userLastname;
        this.billOpdNumber = billOpdNumber;
        this.billTotCoPay = billTotCoPay;
        this.billTotCoPayOutstanding = billTotCoPayOutstanding;
        this.finalBill = finalBill;
        this.billSubTotal = billSubTotal;
        this.billNetPayable = billNetPayable;
        this.billAmountPaid = billAmountPaid;
        this.billOutstanding = billOutstanding;
        this.billNill = billNill;
        this.tariffId = tariffId;
        this.admissionIpdNo = admissionIpdNo;
        this.refundAmount = refundAmount;
        this.companyNetPay = companyNetPay;
        this.grossAmount = grossAmount;
        this.isApprove = isApprove;
        this.isRejected = isRejected;
        this.freezed = freezed;
        this.isCancelled = isCancelled;
    }

    public TBillBillDto(long billId, String billNumber, Date createdDate, Boolean ipdBill, String patientMrNo,
                        String titleName, String userFirstname, String userMiddlename, String userLastname, String billOpdNumber,
                        double billTotCoPay, double billTotCoPayOutstanding, Boolean finalBill, double billSubTotal,
                        double billNetPayable, double billAmountPaid, double billOutstanding, Boolean billNill, long tariffId,
                        double companyNetPay, double grossAmount) {
        this.billId = billId;
        this.billNumber = billNumber;
        this.createdDate = createdDate;
        this.ipdBill = ipdBill;
        this.patientMrNo = patientMrNo;
        this.titleName = titleName;
        this.userFirstname = userFirstname;
        this.userMiddlename = userMiddlename;
        this.userLastname = userLastname;
        this.billOpdNumber = billOpdNumber;
        this.billTotCoPay = billTotCoPay;
        this.billTotCoPayOutstanding = billTotCoPayOutstanding;
        this.finalBill = finalBill;
        this.billSubTotal = billSubTotal;
        this.billNetPayable = billNetPayable;
        this.billAmountPaid = billAmountPaid;
        this.billOutstanding = billOutstanding;
        this.billNill = billNill;
        this.tariffId = tariffId;
        this.refundAmount = refundAmount;
        this.companyNetPay = companyNetPay;
        this.grossAmount = grossAmount;
    }

    public TBillBillDto(long billId, String billNumber, Date createdDate, Boolean ipdBill, String patientMrNo,
                        String titleName, String userFirstname, String userMiddlename, String userLastname, String billOpdNumber,
                        double billTotCoPay, double billTotCoPayOutstanding, Boolean finalBill, double billSubTotal,
                        double billNetPayable, double billAmountPaid, double billOutstanding, Boolean billNill, long tariffId,
                        double companyNetPay, double grossAmount, Boolean isApprove, Boolean isRejected) {
        this.billId = billId;
        this.billNumber = billNumber;
        this.createdDate = createdDate;
        this.ipdBill = ipdBill;
        this.patientMrNo = patientMrNo;
        this.titleName = titleName;
        this.userFirstname = userFirstname;
        this.userMiddlename = userMiddlename;
        this.userLastname = userLastname;
        this.billOpdNumber = billOpdNumber;
        this.billTotCoPay = billTotCoPay;
        this.billTotCoPayOutstanding = billTotCoPayOutstanding;
        this.finalBill = finalBill;
        this.billSubTotal = billSubTotal;
        this.billNetPayable = billNetPayable;
        this.billAmountPaid = billAmountPaid;
        this.billOutstanding = billOutstanding;
        this.billNill = billNill;
        this.tariffId = tariffId;
        this.refundAmount = refundAmount;
        this.companyNetPay = companyNetPay;
        this.grossAmount = grossAmount;
        this.isApprove = isApprove;
        this.isRejected = isRejected;
    }

    public TBillBillDto(long billId, String billNumber, Date createdDate, Boolean ipdBill, String patientMrNo,
                        String titleName, String userFirstname, String userMiddlename, String userLastname, String billOpdNumber,
                        double billTotCoPay, double billTotCoPayOutstanding, Boolean finalBill, double billSubTotal,
                        double billNetPayable, double billAmountPaid, double billOutstanding, Boolean billNill, long tariffId,
                        double refundAmount, double companyNetPay, double grossAmount, Boolean isApprove, Boolean isRejected) {
        this.billId = billId;
        this.billNumber = billNumber;
        this.createdDate = createdDate;
        this.ipdBill = ipdBill;
        this.patientMrNo = patientMrNo;
        this.titleName = titleName;
        this.userFirstname = userFirstname;
        this.userMiddlename = userMiddlename;
        this.userLastname = userLastname;
        this.billOpdNumber = billOpdNumber;
        this.billTotCoPay = billTotCoPay;
        this.billTotCoPayOutstanding = billTotCoPayOutstanding;
        this.finalBill = finalBill;
        this.billSubTotal = billSubTotal;
        this.billNetPayable = billNetPayable;
        this.billAmountPaid = billAmountPaid;
        this.billOutstanding = billOutstanding;
        this.billNill = billNill;
        this.tariffId = tariffId;
        this.refundAmount = refundAmount;
        this.companyNetPay = companyNetPay;
        this.grossAmount = grossAmount;
        this.isApprove = isApprove;
        this.isRejected = isRejected;
    }

    public TBillBillDto(long billId, String billNumber, Date createdDate, Boolean ipdBill, String patientMrNo,
                        String titleName, String userFirstname, String userMiddlename, String userLastname, String billOpdNumber,
                        double billTotCoPay, double billTotCoPayOutstanding, Boolean finalBill, double billSubTotal,
                        double billNetPayable, double billAmountPaid, double billOutstanding, Boolean billNill, long tariffId,
                        double refundAmount, double companyNetPay, double grossAmount) {
        this.billId = billId;
        this.billNumber = billNumber;
        this.createdDate = createdDate;
        this.ipdBill = ipdBill;
        this.patientMrNo = patientMrNo;
        this.titleName = titleName;
        this.userFirstname = userFirstname;
        this.userMiddlename = userMiddlename;
        this.userLastname = userLastname;
        this.billOpdNumber = billOpdNumber;
        this.billTotCoPay = billTotCoPay;
        this.billTotCoPayOutstanding = billTotCoPayOutstanding;
        this.finalBill = finalBill;
        this.billSubTotal = billSubTotal;
        this.billNetPayable = billNetPayable;
        this.billAmountPaid = billAmountPaid;
        this.billOutstanding = billOutstanding;
        this.billNill = billNill;
        this.tariffId = tariffId;
        this.refundAmount = refundAmount;
        this.companyNetPay = companyNetPay;
        this.grossAmount = grossAmount;
    }

    public TBillBillDto(long billId, String billNumber, Date createdDate, Boolean ipdBill, String patientMrNo,
                        String titleName, String userFirstname, String userMiddlename, String userLastname, String billOpdNumber,
                        double billTotCoPay, double billTotCoPayOutstanding, Boolean finalBill, double billSubTotal,
                        double billNetPayable, double billAmountPaid, double billOutstanding, Boolean billNill, long tariffId,
                        String admissionIpdNo, Boolean isCancelled, double companyNetPay, double grossAmount) {
        this.billId = billId;
        this.billNumber = billNumber;
        this.createdDate = createdDate;
        this.ipdBill = ipdBill;
        this.patientMrNo = patientMrNo;
        this.titleName = titleName;
        this.userFirstname = userFirstname;
        this.userMiddlename = userMiddlename;
        this.userLastname = userLastname;
        this.billOpdNumber = billOpdNumber;
        this.billTotCoPay = billTotCoPay;
        this.billTotCoPayOutstanding = billTotCoPayOutstanding;
        this.finalBill = finalBill;
        this.billSubTotal = billSubTotal;
        this.billNetPayable = billNetPayable;
        this.billAmountPaid = billAmountPaid;
        this.billOutstanding = billOutstanding;
        this.billNill = billNill;
        this.tariffId = tariffId;
        this.admissionIpdNo = admissionIpdNo;
        this.isCancelled = isCancelled;
        this.companyNetPay = companyNetPay;
        this.grossAmount = grossAmount;
    }

    public TBillBillDto(long billId, String billNumber, Date createdDate, Boolean ipdBill, String patientMrNo,
                        String titleName, String userFirstname, String userMiddlename, String userLastname, String billOpdNumber,
                        double billTotCoPay, double billTotCoPayOutstanding, Boolean finalBill, double billSubTotal,
                        double billNetPayable, double billAmountPaid, double billOutstanding, Boolean billNill, long tariffId,
                        Boolean isCancelled, double billDiscountAmount, double companyNetPay, double grossAmount) {
        this.billId = billId;
        this.billNumber = billNumber;
        this.createdDate = createdDate;
        this.ipdBill = ipdBill;
        this.patientMrNo = patientMrNo;
        this.titleName = titleName;
        this.userFirstname = userFirstname;
        this.userMiddlename = userMiddlename;
        this.userLastname = userLastname;
        this.billOpdNumber = billOpdNumber;
        this.billTotCoPay = billTotCoPay;
        this.billTotCoPayOutstanding = billTotCoPayOutstanding;
        this.finalBill = finalBill;
        this.billSubTotal = billSubTotal;
        this.billNetPayable = billNetPayable;
        this.billAmountPaid = billAmountPaid;
        this.billOutstanding = billOutstanding;
        this.billNill = billNill;
        this.tariffId = tariffId;
        this.isCancelled = isCancelled;
        this.billDiscountAmount = billDiscountAmount;
        this.companyNetPay = companyNetPay;
        this.grossAmount = grossAmount;
    }

    public double getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(double refundAmount) {
        this.refundAmount = refundAmount;
    }

    public long getBillId() {
        return billId;
    }

    public void setBillId(long billId) {
        this.billId = billId;
    }

    public String getBillNumber() {
        return billNumber;
    }

    public void setBillNumber(String billNumber) {
        this.billNumber = billNumber;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Boolean getIpdBill() {
        return ipdBill;
    }

    public void setIpdBill(Boolean ipdBill) {
        this.ipdBill = ipdBill;
    }

    public String getPatientMrNo() {
        return patientMrNo;
    }

    public void setPatientMrNo(String patientMrNo) {
        this.patientMrNo = patientMrNo;
    }

    public String getUserFirstname() {
        return userFirstname;
    }

    public void setUserFirstname(String userFirstname) {
        this.userFirstname = userFirstname;
    }

    public String getUserMiddlename() {
        return userMiddlename;
    }

    public void setUserMiddlename(String userMiddlename) {
        this.userMiddlename = userMiddlename;
    }

    public String getUserLastname() {
        return userLastname;
    }

    public void setUserLastname(String userLastname) {
        this.userLastname = userLastname;
    }

    public String getBillOpdNumber() {
        return billOpdNumber;
    }

    public void setBillOpdNumber(String billOpdNumber) {
        this.billOpdNumber = billOpdNumber;
    }

    public double getBillTotCoPay() {
        return billTotCoPay;
    }

    public void setBillTotCoPay(double billTotCoPay) {
        this.billTotCoPay = billTotCoPay;
    }

    public double getBillTotCoPayOutstanding() {
        return billTotCoPayOutstanding;
    }

    public void setBillTotCoPayOutstanding(double billTotCoPayOutstanding) {
        this.billTotCoPayOutstanding = billTotCoPayOutstanding;
    }

    public Boolean getFinalBill() {
        return finalBill;
    }

    public void setFinalBill(Boolean finalBill) {
        this.finalBill = finalBill;
    }

    public double getBillSubTotal() {
        return billSubTotal;
    }

    public void setBillSubTotal(double billSubTotal) {
        this.billSubTotal = billSubTotal;
    }

    public double getBillNetPayable() {
        return billNetPayable;
    }

    public void setBillNetPayable(double billNetPayable) {
        this.billNetPayable = billNetPayable;
    }

    public double getBillAmountPaid() {
        return billAmountPaid;
    }

    public void setBillAmountPaid(double billAmountPaid) {
        this.billAmountPaid = billAmountPaid;
    }

    public double getBillOutstanding() {
        return billOutstanding;
    }

    public void setBillOutstanding(double billOutstanding) {
        this.billOutstanding = billOutstanding;
    }

    public Boolean getBillNill() {
        return billNill;
    }

    public void setBillNill(Boolean billNill) {
        this.billNill = billNill;
    }

    public long getTariffId() {
        return tariffId;
    }

    public void setTariffId(long tariffId) {
        this.tariffId = tariffId;
    }

    public Boolean getIsCancelled() {
        return isCancelled;
    }

    public void setIsCancelled(Boolean isCancelled) {
        this.isCancelled = isCancelled;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public double getBillCompanyPayable() {
        return billCompanyPayable;
    }

    public double getGrossAmount() {
        return grossAmount;
    }

    public void setGrossAmount(double grossAmount) {
        this.grossAmount = grossAmount;
    }

    public void setBillCompanyPayable(double billCompanyPayable) {
        this.billCompanyPayable = billCompanyPayable;
    }

    public double getBillCompanyPaid() {
        return billCompanyPaid;
    }

    public void setBillCompanyPaid(double billCompanyPaid) {
        this.billCompanyPaid = billCompanyPaid;
    }

    public double getBillCompanyOutstanding() {
        return billCompanyOutstanding;
    }

    public void setBillCompanyOutstanding(double billCompanyOutstanding) {
        this.billCompanyOutstanding = billCompanyOutstanding;
    }

    public double getBillDiscountAmount() {
        return billDiscountAmount;
    }

    public void setBillDiscountAmount(double billDiscountAmount) {
        this.billDiscountAmount = billDiscountAmount;
    }

    public String getAdmissionIpdNo() {
        return admissionIpdNo;
    }

    public void setAdmissionIpdNo(String admissionIpdNo) {
        this.admissionIpdNo = admissionIpdNo;
    }

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    public double getCompanyNetPay() {
        return companyNetPay;
    }

    public void setCompanyNetPay(double companyNetPay) {
        this.companyNetPay = companyNetPay;
    }

    public Boolean getCompanyBillApproval() {
        return companyBillApproval;
    }

    public void setCompanyBillApproval(Boolean companyBillApproval) {
        this.companyBillApproval = companyBillApproval;
    }

    public Boolean getIsApprove() {
        return isApprove;
    }

    public void setIsApprove(Boolean isApprove) {
        this.isApprove = isApprove;
    }

    public Boolean getIsRejected() {
        return isRejected;
    }

    public void setIsRejected(Boolean isRejected) {
        this.isRejected = isRejected;
    }

    public Boolean getFreezed() {
        return freezed;
    }

    public void setFreezed(Boolean freezed) {
        this.freezed = freezed;
    }

    @Override
    public String toString() {
        return "TBillBillDto{" + "billId=" + billId + ", billNumber='" + billNumber + '\'' + ", createdDate="
                + createdDate + ", ipdBill=" + ipdBill + ", patientMrNo='" + patientMrNo + '\'' + ", titleName='"
                + titleName + '\'' + ", userFirstname='" + userFirstname + '\'' + ", userMiddlename='" + userMiddlename
                + '\'' + ", userLastname='" + userLastname + '\'' + ", billOpdNumber='" + billOpdNumber + '\''
                + ", billTotCoPay=" + billTotCoPay + ", billTotCoPayOutstanding=" + billTotCoPayOutstanding
                + ", finalBill=" + finalBill + ", billSubTotal=" + billSubTotal + ", billNetPayable=" + billNetPayable
                + ", billAmountPaid=" + billAmountPaid + ", billOutstanding=" + billOutstanding + ", billNill="
                + billNill + ", tariffId=" + tariffId + ", isCancelled=" + isCancelled + '}';
    }

}

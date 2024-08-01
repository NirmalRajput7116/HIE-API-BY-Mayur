package com.cellbeans.hspa.misbillcumtestreport;

import com.cellbeans.hspa.mstunit.MstUnit;

public class BillCumTestReportListDTO {

    public String patientId;
    public String performedDate;
    public String billNo;
    public String patientName;
    public String patientAge;
    public String patientGender;
    public String contact;
    public String testName;
    public String referral;
    public String services;
    public String paymentType;
    public String organization;
    public String gross;
    public String discount;
    public String net;
    public String paid;
    public String billOutstanding;
    public String outsourced;
    public String accessionDate;
    public String sampleCollectionDate;
    public String sampleAcceptanceDate;
    public String modifiedDate;
    public String submissionDate;
    public String finalizedDate;
    public String testStatus;
    public String SampleCollectionName;
    public String SampleAcceptanceName;
    public String SubmissionName;
    public String FinalizedName;
    public long count;
    public double totalGross;
    public double totalDiscount;
    public double totalNet;
    public double totalPaid;
    public double totalOutstanding;
    public double testAmount;
    public MstUnit objHeaderData;

    public double getTestAmount() {
        return testAmount;
    }

    public void setTestAmount(double testAmount) {
        this.testAmount = testAmount;
    }

    public MstUnit getObjHeaderData() {
        return objHeaderData;
    }

    public void setObjHeaderData(MstUnit objHeaderData) {
        this.objHeaderData = objHeaderData;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getPerformedDate() {
        return performedDate;
    }

    public void setPerformedDate(String performedDate) {
        this.performedDate = performedDate;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientAge() {
        return patientAge;
    }

    public void setPatientAge(String patientAge) {
        this.patientAge = patientAge;
    }

    public String getPatientGender() {
        return patientGender;
    }

    public void setPatientGender(String patientGender) {
        this.patientGender = patientGender;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getReferral() {
        return referral;
    }

    public void setReferral(String referral) {
        this.referral = referral;
    }

    public String getServices() {
        return services;
    }

    public void setServices(String services) {
        this.services = services;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getGross() {
        return gross;
    }

    public void setGross(String gross) {
        this.gross = gross;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getNet() {
        return net;
    }

    public void setNet(String net) {
        this.net = net;
    }

    public String getPaid() {
        return paid;
    }

    public void setPaid(String paid) {
        this.paid = paid;
    }

    public String getBillOutstanding() {
        return billOutstanding;
    }

    public void setBillOutstanding(String billOutstanding) {
        this.billOutstanding = billOutstanding;
    }

    public String getOutsourced() {
        return outsourced;
    }

    public void setOutsourced(String outsourced) {
        this.outsourced = outsourced;
    }

    public String getAccessionDate() {
        return accessionDate;
    }

    public void setAccessionDate(String accessionDate) {
        this.accessionDate = accessionDate;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getSampleCollectionDate() {
        return sampleCollectionDate;
    }

    public void setSampleCollectionDate(String sampleCollectionDate) {
        this.sampleCollectionDate = sampleCollectionDate;
    }

    public String getSampleAcceptanceDate() {
        return sampleAcceptanceDate;
    }

    public void setSampleAcceptanceDate(String sampleAcceptanceDate) {
        this.sampleAcceptanceDate = sampleAcceptanceDate;
    }

    public String getTestStatus() {
        return testStatus;
    }

    public void setTestStatus(String testStatus) {
        this.testStatus = testStatus;
    }

    public String getSampleCollectionName() {
        return SampleCollectionName;
    }

    public void setSampleCollectionName(String sampleCollectionName) {
        SampleCollectionName = sampleCollectionName;
    }

    public String getSampleAcceptanceName() {
        return SampleAcceptanceName;
    }

    public void setSampleAcceptanceName(String sampleAcceptanceName) {
        SampleAcceptanceName = sampleAcceptanceName;
    }

    public String getSubmissionName() {
        return SubmissionName;
    }

    public void setSubmissionName(String submissionName) {
        SubmissionName = submissionName;
    }

    public String getFinalizedName() {
        return FinalizedName;
    }

    public void setFinalizedName(String finalizedName) {
        FinalizedName = finalizedName;
    }

    public String getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(String submissionDate) {
        this.submissionDate = submissionDate;
    }

    public String getFinalizedDate() {
        return finalizedDate;
    }

    public void setFinalizedDate(String finalizedDate) {
        this.finalizedDate = finalizedDate;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public double getTotalGross() {
        return totalGross;
    }

    public void setTotalGross(double totalGross) {
        this.totalGross = totalGross;
    }

    public double getTotalDiscount() {
        return totalDiscount;
    }

    public void setTotalDiscount(double totalDiscount) {
        this.totalDiscount = totalDiscount;
    }

    public double getTotalNet() {
        return totalNet;
    }

    public void setTotalNet(double totalNet) {
        this.totalNet = totalNet;
    }

    public double getTotalPaid() {
        return totalPaid;
    }

    public void setTotalPaid(double totalPaid) {
        this.totalPaid = totalPaid;
    }

    public double getTotalOutstanding() {
        return totalOutstanding;
    }

    public void setTotalOutstanding(double totalOutstanding) {
        this.totalOutstanding = totalOutstanding;
    }
}

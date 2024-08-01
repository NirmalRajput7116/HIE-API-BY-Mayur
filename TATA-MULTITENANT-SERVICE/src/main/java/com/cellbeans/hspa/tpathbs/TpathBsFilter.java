package com.cellbeans.hspa.tpathbs;

import java.io.Serializable;

public class TpathBsFilter implements Serializable {
    private static final long serialVersionUID = 1L;

    private String qString;

    private String unitId;

    private String multiUnit;

    private String userRoleName;

    private String createdDate;

    private String dateFrom;

    private String dateTo;

    private String isPerformedBy;

    private String isPerformedByUnitId;

    private String isLOAccepted;

    private String isSampleCollected;

    private String isSampleAccepted;

    private String isSampleRejected;

    private String isResultEntry;

    private String isFinalized;

    private String isReported;

    private String subGroup;

    private String isIPD;

    private String patientName;

    private String patientMrno;

    private String workOrderNo;

    private String billNo;

    private String isCompleted;

    private String empNo;

    public String getIsIPD() {
        return isIPD;
    }

    public void setIsIPD(String isIPD) {
        this.isIPD = isIPD;
    }

    public String getUserRoleName() {
        return userRoleName;
    }

    public void setUserRoleName(String userRoleName) {
        this.userRoleName = userRoleName;
    }

    public String getSubGroup() {
        return subGroup;
    }

    public void setSubGroup(String subGroup) {
        this.subGroup = subGroup;
    }

    public String getMultiUnit() {
        return multiUnit;
    }

    public void setMultiUnit(String multiUnit) {
        this.multiUnit = multiUnit;
    }

    public String getIsLOAccepted() {
        return isLOAccepted;
    }

    public void setIsLOAccepted(String isLOAccepted) {
        this.isLOAccepted = isLOAccepted;
    }

    public String getIsSampleCollected() {
        return isSampleCollected;
    }

    public void setIsSampleCollected(String isSampleCollected) {
        this.isSampleCollected = isSampleCollected;
    }

    public String getIsSampleAccepted() {
        return isSampleAccepted;
    }

    public void setIsSampleAccepted(String isSampleAccepted) {
        this.isSampleAccepted = isSampleAccepted;
    }

    public String getIsSampleRejected() {
        return isSampleRejected;
    }

    public void setIsSampleRejected(String isSampleRejected) {
        this.isSampleRejected = isSampleRejected;
    }

    public String getIsResultEntry() {
        return isResultEntry;
    }

    public void setIsResultEntry(String isResultEntry) {
        this.isResultEntry = isResultEntry;
    }

    public String getIsPerformedByUnitId() {
        return isPerformedByUnitId;
    }

    public void setIsPerformedByUnitId(String isPerformedByUnitId) {
        this.isPerformedByUnitId = isPerformedByUnitId;
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    public String getDateTo() {
        return dateTo;
    }

    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }

    public String getIsPerformedBy() {
        return isPerformedBy;
    }

    public void setIsPerformedBy(String isPerformedBy) {
        this.isPerformedBy = isPerformedBy;
    }

    public String getqString() {
        return qString;
    }

    public void setqString(String qString) {
        this.qString = qString;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getIsFinalized() {
        return isFinalized;
    }

    public void setIsFinalized(String isFinalized) {
        this.isFinalized = isFinalized;
    }

    public String getIsReported() {
        return isReported;
    }

    public void setIsReported(String isReported) {
        this.isReported = isReported;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientMrno() {
        return patientMrno;
    }

    public void setPatientMrno(String patientMrno) {
        this.patientMrno = patientMrno;
    }

    public String getWorkOrderNo() {
        return workOrderNo;
    }

    public void setWorkOrderNo(String workOrderNo) {
        this.workOrderNo = workOrderNo;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public String getIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(String isCompleted) {
        this.isCompleted = isCompleted;
    }

    public String getEmpNo() {
        return empNo;
    }

    public void setEmpNo(String empNo) {
        this.empNo = empNo;
    }
    //    @Override
//    public String toString() {
//        return "TpathBsFilter{" + "qString='" + qString + '\'' + ", unitId='" + unitId + '\'' + ", multiUnit='" + multiUnit + '\'' + ", userRoleName='" + userRoleName + '\'' + ", createdDate='" + createdDate + '\'' + ", dateFrom='" + dateFrom + '\'' + ", dateTo='" + dateTo + '\'' + ", isPerformedBy='" + isPerformedBy + '\'' + ", isPerformedByUnitId='" + isPerformedByUnitId + '\'' + ", isLOAccepted='" + isLOAccepted + '\'' + ", isSampleCollected='" + isSampleCollected + '\'' + ", isSampleAccepted='" + isSampleAccepted + '\'' + ", isSampleRejected='" + isSampleRejected + '\'' + ", isResultEntry='" + isResultEntry + '\'' + ", isFinalized='" + isFinalized + '\'' + ", isReported='" + isReported + '\'' + ", subGroup='" + subGroup + '\'' + ", isIPD='" + isIPD + '\'' + '}';
//    }

    @Override
    public String toString() {
        return "TpathBsFilter{" +
                "qString='" + qString + '\'' +
                ", unitId='" + unitId + '\'' +
                ", multiUnit='" + multiUnit + '\'' +
                ", userRoleName='" + userRoleName + '\'' +
                ", createdDate='" + createdDate + '\'' +
                ", dateFrom='" + dateFrom + '\'' +
                ", dateTo='" + dateTo + '\'' +
                ", isPerformedBy='" + isPerformedBy + '\'' +
                ", isPerformedByUnitId='" + isPerformedByUnitId + '\'' +
                ", isLOAccepted='" + isLOAccepted + '\'' +
                ", isSampleCollected='" + isSampleCollected + '\'' +
                ", isSampleAccepted='" + isSampleAccepted + '\'' +
                ", isSampleRejected='" + isSampleRejected + '\'' +
                ", isResultEntry='" + isResultEntry + '\'' +
                ", isFinalized='" + isFinalized + '\'' +
                ", isReported='" + isReported + '\'' +
                ", subGroup='" + subGroup + '\'' +
                ", isIPD='" + isIPD + '\'' +
                ", patientName='" + patientName + '\'' +
                '}';
    }
}

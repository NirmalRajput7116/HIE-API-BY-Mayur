package com.cellbeans.hspa.tatalabanatomyreport;

import java.io.Serializable;

public class TataAnatomyLabReportFilter implements Serializable {
    private static final long serialVersionUID = 1L;

    private String qString;

    private String unitId;

    private String createdDate;

    private String dateFrom;

    private String dateTo;

    private String isFinalized;

    private String isReported;

    private String isPerformedBy;

    private String isPerformedByUnitId;

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

    @Override
    public String toString() {
        return "TpathBsFilter{" + "qString='" + qString + '\'' + ", unitId='" + unitId + '\'' + ", createdDate='" + createdDate + '\'' + ", dateFrom='" + dateFrom + '\'' + ", dateTo='" + dateTo + '\'' + ", isFinalized='" + isFinalized + '\'' + ", isReported='" + isReported + '\'' + ", isPerformedBy='" + isPerformedBy + '\'' + ", isPerformedByUnitId='" + isPerformedByUnitId + '\'' + '}';
    }

}

package com.cellbeans.hspa.mpathTrnOutSource;

import java.io.Serializable;

public class MpathTrnOutSourceFilter implements Serializable {
    private static final long serialVersionUID = 1L;

    private String qString;

    private long unitId;

    private String createdDate;

    private String isFinalized;

    private String isReported;

    private long isPerformedBy;

    private String searchMrno;
    private String searchAgencyName;
    private String searchStartdate;
    private String searchEnddate;

    public long getIsPerformedBy() {
        return isPerformedBy;
    }

    public void setIsPerformedBy(long isPerformedBy) {
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

    public long getUnitId() {
        return unitId;
    }

    public void setUnitId(long unitId) {
        this.unitId = unitId;
    }

    public String getSearchMrno() {
        return searchMrno;
    }

    public void setSearchMrno(String searchMrno) {
        this.searchMrno = searchMrno;
    }

    public String getSearchAgencyName() {
        return searchAgencyName;
    }

    public void setSearchAgencyName(String searchAgencyName) {
        this.searchAgencyName = searchAgencyName;
    }

    public String getSearchStartdate() {
        return searchStartdate;
    }

    public void setSearchStartdate(String searchStartdate) {
        this.searchStartdate = searchStartdate;
    }

    public String getSearchEnddate() {
        return searchEnddate;
    }

    public void setSearchEnddate(String searchEnddate) {
        this.searchEnddate = searchEnddate;
    }

    @Override
    public String toString() {
        return "MpathTrnOutSourceFilter{" + "qString='" + qString + '\'' + ", unitId=" + unitId + ", createdDate='" + createdDate + '\'' + ", isFinalized='" + isFinalized + '\'' + ", isReported='" + isReported + '\'' + ", isPerformedBy=" + isPerformedBy + ", searchMrno='" + searchMrno + '\'' + ", searchAgencyName='" + searchAgencyName + '\'' + ", searchStartdate=" + searchStartdate + ", searchEnddate=" + searchEnddate + '}';
    }

}

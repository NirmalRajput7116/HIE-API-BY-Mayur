package com.cellbeans.hspa.mis.misLab;

public class MisLabCountDTO {

    public String unitName;
    public String samCollectionCount;
    public String samAcceptanceCount;
    public String samRejectionCount;
    public String labFinalizedCount;
    public String labSubmissionCount;
    public long count;

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getSamCollectionCount() {
        return samCollectionCount;
    }

    public void setSamCollectionCount(String samCollectionCount) {
        this.samCollectionCount = samCollectionCount;
    }

    public String getSamAcceptanceCount() {
        return samAcceptanceCount;
    }

    public void setSamAcceptanceCount(String samAcceptanceCount) {
        this.samAcceptanceCount = samAcceptanceCount;
    }

    public String getSamRejectionCount() {
        return samRejectionCount;
    }

    public void setSamRejectionCount(String samRejectionCount) {
        this.samRejectionCount = samRejectionCount;
    }

    public String getLabFinalizedCount() {
        return labFinalizedCount;
    }

    public void setLabFinalizedCount(String labFinalizedCount) {
        this.labFinalizedCount = labFinalizedCount;
    }

    public String getLabSubmissionCount() {
        return labSubmissionCount;
    }

    public void setLabSubmissionCount(String labSubmissionCount) {
        this.labSubmissionCount = labSubmissionCount;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

}

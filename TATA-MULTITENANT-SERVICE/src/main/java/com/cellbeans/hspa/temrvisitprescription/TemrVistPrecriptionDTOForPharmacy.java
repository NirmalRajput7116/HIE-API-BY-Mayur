package com.cellbeans.hspa.temrvisitprescription;

import com.cellbeans.hspa.mststaff.MstStaff;

import java.util.Date;

public class TemrVistPrecriptionDTOForPharmacy {

    private long vpId;

    private long vpVisitId;

    private MstStaff vpPrescribedStaffId;

    private Date createdDate;

    public TemrVistPrecriptionDTOForPharmacy(long vpId, long vpVisitId, MstStaff vpPrescribedStaffId, Date createdDate) {
        this.vpId = vpId;
        this.vpVisitId = vpVisitId;
        this.vpPrescribedStaffId = vpPrescribedStaffId;
        this.createdDate = createdDate;
    }

    public long getVpId() {
        return vpId;
    }

    public void setVpId(long vpId) {
        this.vpId = vpId;
    }

    public long getVpVisitId() {
        return vpVisitId;
    }

    public void setVpVisitId(long vpVisitId) {
        this.vpVisitId = vpVisitId;
    }

    public MstStaff getVpPrescribedStaffId() {
        return vpPrescribedStaffId;
    }

    public void setVpPrescribedStaffId(MstStaff vpPrescribedStaffId) {
        this.vpPrescribedStaffId = vpPrescribedStaffId;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

}

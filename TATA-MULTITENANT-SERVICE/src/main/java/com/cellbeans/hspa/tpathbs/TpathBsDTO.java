package com.cellbeans.hspa.tpathbs;

import com.cellbeans.hspa.mststaff.MstStaff;
import com.cellbeans.hspa.tbillbill.TBillBill;

public class TpathBsDTO {

    private long psId;

    private TBillBill psBillId;

    private Boolean isResultEntry;

    private Boolean isFinalized;

    private MstStaff isPerformedBy;

    private Boolean isLabOrderAcceptance;

    private Boolean isLabOrderCancel;

    public TpathBsDTO(long psId, TBillBill psBillId, Boolean isResultEntry, Boolean isFinalized, MstStaff isPerformedBy, Boolean isLabOrderAcceptance, Boolean isLabOrderCancel) {
        this.psId = psId;
        this.psBillId = psBillId;
        this.isResultEntry = isResultEntry;
        this.isFinalized = isFinalized;
        this.isPerformedBy = isPerformedBy;
        this.isLabOrderAcceptance = isLabOrderAcceptance;
        this.isLabOrderCancel = isLabOrderCancel;

    }

    public long getPsId() {
        return psId;
    }

    public void setPsId(long psId) {
        this.psId = psId;
    }

    public TBillBill getPsBillId() {
        return psBillId;
    }

    public void setPsBillId(TBillBill psBillId) {
        this.psBillId = psBillId;
    }

    public Boolean getIsResultEntry() {
        return isResultEntry;
    }

    public void setIsResultEntry(Boolean isResultEntry) {
        this.isResultEntry = isResultEntry;
    }

    public Boolean getIsFinalized() {
        return isFinalized;
    }

    public void setIsFinalized(Boolean isFinalized) {
        this.isFinalized = isFinalized;
    }

    public MstStaff getIsPerformedBy() {
        return isPerformedBy;
    }

    public void setIsPerformedBy(MstStaff isPerformedBy) {
        this.isPerformedBy = isPerformedBy;
    }

    public Boolean getIsLabOrderAcceptance() {
        return isLabOrderAcceptance;
    }

    public void setIsLabOrderAcceptance(Boolean islabOrderAcceptance) {
        this.isLabOrderAcceptance = islabOrderAcceptance;
    }

    public Boolean getIsLabOrderCancel() {
        return isLabOrderCancel;
    }

    public void setIsLabOrderCancel(Boolean islabOrderCancel) {
        this.isLabOrderCancel = islabOrderCancel;
    }

    @Override
    public String toString() {
        return "TpathBsDTO{" + "psId=" + psId + ", psBillId=" + psBillId + ", isResultEntry=" + isResultEntry + ", isFinalized=" + isFinalized + ", isPerformedBy=" + isPerformedBy + ", isLabOrderAcceptance=" + isLabOrderAcceptance + ", isLabOrderCancel=" + isLabOrderCancel + '}';
    }

}



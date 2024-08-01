package com.cellbeans.hspa.mstvisit;

import com.cellbeans.hspa.mbilltariff.MbillTariff;
import com.cellbeans.hspa.mstpatient.MstPatient;

public class MstVisitDTO {
    private static final long serialVersionUID = 1L;

    private Long visitId;

    private MstPatient visitPatientId;

    private MbillTariff visitTariffId;

    private String admissionIpdNo;

    public MstVisitDTO(Long visitId, MstPatient visitPatientId, MbillTariff visitTariffId) {
        this.visitId = visitId;
        this.visitPatientId = visitPatientId;
        this.visitTariffId = visitTariffId;
    }

    public MstVisitDTO(Long visitId, MstPatient visitPatientId) {
        this.visitId = visitId;
        this.visitPatientId = visitPatientId;
    }

    public MstVisitDTO(Long visitId, MstPatient visitPatientId, MbillTariff visitTariffId, String admissionIpdNo) {
        this.visitId = visitId;
        this.visitPatientId = visitPatientId;
        this.visitTariffId = visitTariffId;
        this.admissionIpdNo = admissionIpdNo;
    }

    public String getAdmissionIpdNo() {
        return admissionIpdNo;
    }

    public void setAdmissionIpdNo(String admissionIpdNo) {
        this.admissionIpdNo = admissionIpdNo;
    }

    public Long getVisitId() {
        return visitId;
    }

    public void setVisitId(Long visitId) {
        this.visitId = visitId;
    }

    public MstPatient getVisitPatientId() {
        return visitPatientId;
    }

    public void setVisitPatientId(MstPatient visitPatientId) {
        this.visitPatientId = visitPatientId;
    }

    public MbillTariff getVisitTariffId() {
        return visitTariffId;
    }

    public void setVisitTariffId(MbillTariff visitTariffId) {
        this.visitTariffId = visitTariffId;
    }

    @Override
    public String toString() {
        return "MstVisitDTO{" + "visitId=" + visitId + ", visitPatientId=" + visitPatientId + ", visitTariffId=" + visitTariffId + '}';
    }

}



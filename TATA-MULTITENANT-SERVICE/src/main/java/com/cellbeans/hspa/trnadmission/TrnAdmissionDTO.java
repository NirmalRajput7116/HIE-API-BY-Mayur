package com.cellbeans.hspa.trnadmission;

import com.cellbeans.hspa.mipdbed.MipdBed;
import com.cellbeans.hspa.mstpatient.MstPatient;

import java.io.Serializable;

public class TrnAdmissionDTO implements Serializable {

    private Long ipdadvancedId;
    private Long admissionId;
    private String admissionIpdNo;
    private MstPatient admissionPatientId;
    private Long currentBedId;
    private String currentBedName;
    private double advanceAmount;
    private MipdBed admissionPatientBedId;
    private Long roomId;
    private Long wordId;
    private String bedNme;

    public TrnAdmissionDTO() {
    }

    public TrnAdmissionDTO(Long admissionId, String admissionIpdNo, MstPatient admissionPatientId, Long currentBedId, String currentBedName) {
        this.admissionId = admissionId;
        this.admissionIpdNo = admissionIpdNo;
        this.admissionPatientId = admissionPatientId;
        this.currentBedId = currentBedId;
        this.currentBedName = currentBedName;
        this.advanceAmount = 0;
    }

    public MipdBed getAdmissionPatientBedId() {
        return admissionPatientBedId;
    }

    public void setAdmissionPatientBedId(MipdBed admissionPatientBedId) {
        this.admissionPatientBedId = admissionPatientBedId;
    }

    public String getAdmissionIpdNo() {
        return admissionIpdNo;
    }

    public void setAdmissionIpdNo(String admissionIpdNo) {
        this.admissionIpdNo = admissionIpdNo;
    }

    public MstPatient getAdmissionPatientId() {
        return admissionPatientId;
    }

    public void setAdmissionPatientId(MstPatient admissionPatientId) {
        this.admissionPatientId = admissionPatientId;
    }

    public Long getCurrentBedId() {
        return currentBedId;
    }

    public void setCurrentBedId(Long currentBedId) {
        this.currentBedId = currentBedId;
    }

    public String getCurrentBedName() {
        return currentBedName;
    }

    public void setCurrentBedName(String currentBedName) {
        this.currentBedName = currentBedName;
    }

    public double getAdvanceAmount() {
        return advanceAmount;
    }

    public void setAdvanceAmount(double advanceAmount) {
        this.advanceAmount = advanceAmount;
    }

    public Long getIpdadvancedId() {
        return ipdadvancedId;
    }

    public void setIpdadvancedId(Long ipdadvancedId) {
        this.ipdadvancedId = ipdadvancedId;
    }

    public Long getAdmissionId() {
        return admissionId;
    }

    public void setAdmissionId(Long admissionId) {
        this.admissionId = admissionId;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public Long getWordId() {
        return wordId;
    }

    public void setWordId(Long wordId) {
        this.wordId = wordId;
    }

    public String getBedNme() {
        return bedNme;
    }

    public void setBedNme(String bedNme) {
        this.bedNme = bedNme;
    }
}

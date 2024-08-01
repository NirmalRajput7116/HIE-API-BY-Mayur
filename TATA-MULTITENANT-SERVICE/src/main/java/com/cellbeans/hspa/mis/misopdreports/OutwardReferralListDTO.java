package com.cellbeans.hspa.mis.misopdreports;

import com.cellbeans.hspa.mstunit.MstUnit;

public class OutwardReferralListDTO {

    public String unitName;
    public String patientName;
    public String mrNO;
    public String dOB;
    public String age;
    public String gender;
    public String idProof;
    public String idProofDocNo;
    public String referralDate;
    public String referralEntityType;
    public String referralEntity;
    public String speciality;
    public String reason;
    public String visitDate;
    public String referralDoctor;
    public String visitReason;
    public long unitId;
    public long count;

    public MstUnit objHeaderData;

    public MstUnit getObjHeaderData() {
        return objHeaderData;
    }

    public void setObjHeaderData(MstUnit objHeaderData) {
        this.objHeaderData = objHeaderData;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getMrNO() {
        return mrNO;
    }

    public void setMrNO(String mrNO) {
        this.mrNO = mrNO;
    }

    public String getdOB() {
        return dOB;
    }

    public void setdOB(String dOB) {
        this.dOB = dOB;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getIdProof() {
        return idProof;
    }

    public void setIdProof(String idProof) {
        this.idProof = idProof;
    }

    public String getIdProofDocNo() {
        return idProofDocNo;
    }

    public void setIdProofDocNo(String idProofDocNo) {
        this.idProofDocNo = idProofDocNo;
    }

    public String getReferralDate() {
        return referralDate;
    }

    public void setReferralDate(String referralDate) {
        this.referralDate = referralDate;
    }

    public String getReferralEntityType() {
        return referralEntityType;
    }

    public void setReferralEntityType(String referralEntityType) {
        this.referralEntityType = referralEntityType;
    }

    public String getReferralEntity() {
        return referralEntity;
    }

    public void setReferralEntity(String referralEntity) {
        this.referralEntity = referralEntity;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(String visitDate) {
        this.visitDate = visitDate;
    }

    public String getReferralDoctor() {
        return referralDoctor;
    }

    public void setReferralDoctor(String referralDoctor) {
        this.referralDoctor = referralDoctor;
    }

    public String getVisitReason() {
        return visitReason;
    }

    public void setVisitReason(String visitReason) {
        this.visitReason = visitReason;
    }

    public long getUnitId() {
        return unitId;
    }

    public void setUnitId(long unitId) {
        this.unitId = unitId;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "OutwardReferralListDTO{" + "unitName='" + unitName + '\'' + ", patientName='" + patientName + '\'' + ", mrNO='" + mrNO + '\'' + ", dOB='" + dOB + '\'' + ", age='" + age + '\'' + ", gender='" + gender + '\'' + ", idProof='" + idProof + '\'' + ", idProofDocNo='" + idProofDocNo + '\'' + ", referralDate='" + referralDate + '\'' + ", referralEntityType='" + referralEntityType + '\'' + ", referralEntity='" + referralEntity + '\'' + ", speciality='" + speciality + '\'' + ", reason='" + reason + '\'' + ", visitDate='" + visitDate + '\'' + ", referralDoctor='" + referralDoctor + '\'' + ", visitReason='" + visitReason + '\'' + ", unitId=" + unitId + ", count=" + count + '}';
    }

}

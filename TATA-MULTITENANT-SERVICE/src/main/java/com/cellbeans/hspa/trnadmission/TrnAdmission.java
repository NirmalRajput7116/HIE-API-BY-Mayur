package com.cellbeans.hspa.trnadmission;

import com.cellbeans.hspa.mbilltariff.MbillTariff;
import com.cellbeans.hspa.mipdadmissionpurpose.MipdAdmissionPurpose;
import com.cellbeans.hspa.mipdadmissiontype.MipdAdmissionType;
import com.cellbeans.hspa.mipdbed.MipdBed;
import com.cellbeans.hspa.mstclass.MstClass;
import com.cellbeans.hspa.mstdepartment.MstDepartment;
import com.cellbeans.hspa.mstpatient.MstPatient;
import com.cellbeans.hspa.mstpatientsource.MstPatientSource;
import com.cellbeans.hspa.mstreferringentity.MstReferringEntity;
import com.cellbeans.hspa.mstreferringentitytype.MstReferringEntityType;
import com.cellbeans.hspa.mstrelation.MstRelation;
import com.cellbeans.hspa.mststaff.MstStaff;
import com.cellbeans.hspa.mstunit.MstUnit;
import com.cellbeans.hspa.trnsponsorcombination.TrnSponsorCombination;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "trn_admission")
public class TrnAdmission implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admissionId", unique = true, nullable = true)
    private long admissionId;

    @JsonInclude(NON_NULL)
    @Column(name = "admissionIpdNo")
    private String admissionIpdNo;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "admissionPatientId")
    private MstPatient admissionPatientId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "admissionUnitId")
    private MstUnit admissionUnitId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "admissionClassId")
    private MstClass admissionClassId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "admissionAtId")
    private MipdAdmissionType admissionAtId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "admissionDepartmentId")
    private MstDepartment admissionDepartmentId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "admissionStaffId")
    private MstStaff admissionStaffId;

    @JsonInclude(NON_NULL)
    @Column(name = "admissionDate")
    private String admissionDate;

    @JsonInclude(NON_NULL)
    @Column(name = "admissionDatePersion")
    private String admissionDatePersion;

    @JsonInclude(NON_NULL)
    @Column(name = "admissionTime", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date admissionTime;

    @JsonIgnore
    @Column(name = "isActive", columnDefinition = "binary(1) default true", nullable = true)
    private Boolean isActive = true;

    @Column(name = "isDeleted", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isDeleted = false;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "admissionPurposeId")
    private MipdAdmissionPurpose admissionPurposeId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "admissionRetId")
    private MstReferringEntityType admissionRetId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "admissionReId")
    private MstReferringEntity admissionReId;

    @JsonInclude(NON_NULL)
    @Column(name = "referByText")
    private String referByText;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "admissionPsId")
    private MstPatientSource admissionPsId;

    @JsonInclude(NON_NULL)
    @Column(name = "admissionDiagnosis")
    private String admissionDiagnosis;

    @JsonInclude(NON_NULL)
    @Column(name = "admissionRemark")
    private String admissionRemark;

    @JsonInclude(NON_NULL)
    @Column(name = "isNonPresenceAdmission", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isNonPresenceAdmission = false;

    @JsonInclude(NON_NULL)
    @Column(name = "AutoCharge")
    private Boolean AutoCharge;

    @JsonInclude(NON_NULL)
    @Column(name = "admissionAutoChargeFrequency")
    private String admissionAutoChargeFrequency;

    @JsonInclude(NON_NULL)
    @Column(name = "admissionAutoChargeType")
    private String admissionAutoChargeType;

    @JsonInclude(NON_NULL)
    @Column(name = "admissionAutoChargeTime")
    private String admissionAutoChargeTime;

    @JsonInclude(NON_NULL)
    @Column(name = "createduser")
    private String createdUser;

    @JsonInclude(NON_NULL)
    @Column(name = "admissionExpectedDischargeDate")
    private String admissionExpectedDischargeDate;
//    @ManyToOne
//    @JoinColumn(name = "admissionCurrentBedId")
//    private MipdBed admissionCurrentBedId;

    @JsonInclude(NON_NULL)
    @ManyToMany
    private List<MipdBed> admissionCurrentBedId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "admissionReservedBedId")
    private MipdBed admissionReservedBedId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "admissionPatientBedId")
    private MipdBed admissionPatientBedId;

    @JsonIgnore
    @CreatedBy
    @Column(nullable = true, updatable = true)
    private String createdBy;

    @JsonIgnore
    @LastModifiedBy
    private String lastModifiedBy;

    @CreatedDate
    @Column(nullable = true, updatable = true)
    private Date createdDate;

    @JsonIgnore
    @LastModifiedDate
    private Date lastModifiedDate;

    @JsonInclude
    @Transient
    private MbillTariff mbillTariff;

    @JsonInclude
    @Transient
    private List<TrnSponsorCombination> trnSponsorCombinationList;

    @JsonInclude(NON_NULL)
    @Column(name = "admissionStatus", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean admissionStatus = false;

    @JsonInclude(NON_NULL)
    @Column(name = "admissionDischargeStatus")
    private String admissionDischargeStatus;

    @JsonInclude(NON_NULL)
    @Column(name = "admissionKinName")
    private String admissionKinName;

    @JsonInclude(NON_NULL)
    @Column(name = "admissionKinAddress")
    private String admissionKinAddress;

    @JsonInclude(NON_NULL)
    @Column(name = "admissionKinContactNo")
    private String admissionKinContactNo;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "admissionKinRelation")
    private MstRelation admissionKinRelation;

    @JsonInclude(NON_NULL)
    @Column(name = "admissionDischargeDate")
    private String admissionDischargeDate;

    @JsonInclude(NON_NULL)
    @Column(name = "admissionReasion")
    private String admissionReasion;

    @JsonInclude(NON_NULL)
    @Column(name = "admissionCancelReason")
    private String admissionCancelReason;
    @JsonInclude(NON_NULL)
    @Column(name = "admissionIsCancel", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean admissionIsCancel = false;

    @JsonInclude(NON_NULL)
    @Column(name = "admissionCancelDate")
    private String admissionCancelDate;

    @JsonInclude(NON_NULL)
    @Column(name = "isFinalized", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isFinalized = false;

    @JsonInclude(NON_NULL)
    @Column(name = "sponsorCombination")
    private String sponsorCombination;

    @JsonInclude(NON_NULL)
    @Column(name = "estimatedAmt", columnDefinition = "Decimal(10,2) default '00.00'", nullable = true)
    private double estimatedAmt;

    @JsonInclude(NON_NULL)
    @Column(name = "approvedAmt", columnDefinition = "Decimal(10,2) default '00.00'", nullable = true)
    private double approvedAmt;

    public String getAdmissionReasion() {
        return admissionReasion;
    }

    public void setAdmissionReasion(String admissionReasion) {
        this.admissionReasion = admissionReasion;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getAdmissionDischargeDate() {
        return admissionDischargeDate;
    }

    public void setAdmissionDischargeDate(String admissionDischargeDate) {
        this.admissionDischargeDate = admissionDischargeDate;
    }

    public String getAdmissionDischargeStatus() {
        return admissionDischargeStatus;
    }

    public void setAdmissionDischargeStatus(String admissionDischargeStatus) {
        this.admissionDischargeStatus = admissionDischargeStatus;
    }

    public MipdBed getAdmissionPatientBedId() {
        return admissionPatientBedId;
    }

    public void setAdmissionPatientBedId(MipdBed admissionPatientBedId) {
        this.admissionPatientBedId = admissionPatientBedId;
    }

    public long getAdmissionId() {
        return admissionId;
    }

    public void setAdmissionId(long admissionId) {
        this.admissionId = admissionId;
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

    public MipdAdmissionType getAdmissionAtId() {
        return admissionAtId;
    }

    public void setAdmissionAtId(MipdAdmissionType admissionAtId) {
        this.admissionAtId = admissionAtId;
    }

    public MstDepartment getAdmissionDepartmentId() {
        return admissionDepartmentId;
    }

    public void setAdmissionDepartmentId(MstDepartment admissionDepartmentId) {
        this.admissionDepartmentId = admissionDepartmentId;
    }

    public MstStaff getAdmissionStaffId() {
        return admissionStaffId;
    }

    public void setAdmissionStaffId(MstStaff admissionStaffId) {
        this.admissionStaffId = admissionStaffId;
    }

    public String getAdmissionDate() {
        return admissionDate;
    }

    public void setAdmissionDate(String admissionDate) {
        this.admissionDate = admissionDate;
    }

    public Date getAdmissionTime() {
        return admissionTime;
    }

    public void setAdmissionTime(Date admissionTime) {
        this.admissionTime = admissionTime;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public MipdAdmissionPurpose getAdmissionPurposeId() {
        return admissionPurposeId;
    }

    public void setAdmissionPurposeId(MipdAdmissionPurpose admissionPurposeId) {
        this.admissionPurposeId = admissionPurposeId;
    }

    public MstReferringEntityType getAdmissionRetId() {
        return admissionRetId;
    }

    public void setAdmissionRetId(MstReferringEntityType admissionRetId) {
        this.admissionRetId = admissionRetId;
    }

    public MstReferringEntity getAdmissionReId() {
        return admissionReId;
    }

    public void setAdmissionReId(MstReferringEntity admissionReId) {
        this.admissionReId = admissionReId;
    }

    public MstPatientSource getAdmissionPsId() {
        return admissionPsId;
    }

    public void setAdmissionPsId(MstPatientSource admissionPsId) {
        this.admissionPsId = admissionPsId;
    }

    public String getAdmissionDiagnosis() {
        return admissionDiagnosis;
    }

    public void setAdmissionDiagnosis(String admissionDiagnosis) {
        this.admissionDiagnosis = admissionDiagnosis;
    }

    public String getAdmissionRemark() {
        return admissionRemark;
    }

    public void setAdmissionRemark(String admissionRemark) {
        this.admissionRemark = admissionRemark;
    }

    public boolean getIsNonPresenceAdmission() {
        return isNonPresenceAdmission;
    }

    public void setIsNonPresenceAdmission(boolean isNonPresenceAdmission) {
        this.isNonPresenceAdmission = isNonPresenceAdmission;
    }

    public List<MipdBed> getAdmissionCurrentBedId() {
        return admissionCurrentBedId;
    }
    //    public MipdBed getAdmissionCurrentBedId() {
//        return admissionCurrentBedId;
//    }
//
//    public void setAdmissionCurrentBedId(MipdBed admissionCurrentBedId) {
//        this.admissionCurrentBedId = admissionCurrentBedId;
//    }

    public void setAdmissionCurrentBedId(List<MipdBed> admissionCurrentBedId) {
        this.admissionCurrentBedId = admissionCurrentBedId;
    }

    public MipdBed getAdmissionReservedBedId() {
        return admissionReservedBedId;
    }

    public void setAdmissionReservedBedId(MipdBed admissionReservedBedId) {
        this.admissionReservedBedId = admissionReservedBedId;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public MbillTariff getMbillTariff() {
        return mbillTariff;
    }

    public void setMbillTariff(MbillTariff mbillTariff) {
        this.mbillTariff = mbillTariff;
    }

    public List<TrnSponsorCombination> getTrnSponsorCombinationList() {
        return trnSponsorCombinationList;
    }

    public void setTrnSponsorCombinationList(List<TrnSponsorCombination> trnSponsorCombinationList) {
        this.trnSponsorCombinationList = trnSponsorCombinationList;
    }

    public MstClass getAdmissionClassId() {
        return admissionClassId;
    }

    public void setAdmissionClassId(MstClass admissionClassId) {
        this.admissionClassId = admissionClassId;
    }

    public String getAdmissionDatePersion() {
        return admissionDatePersion;
    }

    public void setAdmissionDatePersion(String admissionDatePersion) {
        this.admissionDatePersion = admissionDatePersion;
    }

    public MstUnit getAdmissionUnitId() {
        return admissionUnitId;
    }

    public void setAdmissionUnitId(MstUnit admissionUnitId) {
        this.admissionUnitId = admissionUnitId;
    }

    public Boolean getNonPresenceAdmission() {
        return isNonPresenceAdmission;
    }

    public void setNonPresenceAdmission(Boolean nonPresenceAdmission) {
        isNonPresenceAdmission = nonPresenceAdmission;
    }

    public Boolean getAdmissionStatus() {
        return admissionStatus;
    }

    public void setAdmissionStatus(Boolean admissionStatus) {
        this.admissionStatus = admissionStatus;
    }

    public Boolean getAutoCharge() {
        return AutoCharge;
    }

    public void setAutoCharge(Boolean autoCharge) {
        AutoCharge = autoCharge;
    }

    public String getAdmissionAutoChargeFrequency() {
        return admissionAutoChargeFrequency;
    }

    public void setAdmissionAutoChargeFrequency(String admissionAutoChargeFrequency) {
        this.admissionAutoChargeFrequency = admissionAutoChargeFrequency;
    }

    public String getAdmissionAutoChargeType() {
        return admissionAutoChargeType;
    }

    public void setAdmissionAutoChargeType(String admissionAutoChargeType) {
        this.admissionAutoChargeType = admissionAutoChargeType;
    }

    public String getAdmissionAutoChargeTime() {
        return admissionAutoChargeTime;
    }

    public void setAdmissionAutoChargeTime(String admissionAutoChargeTime) {
        this.admissionAutoChargeTime = admissionAutoChargeTime;
    }

    public String getAdmissionExpectedDischargeDate() {
        return admissionExpectedDischargeDate;
    }

    public void setAdmissionExpectedDischargeDate(String admissionExpectedDischargeDate) {
        this.admissionExpectedDischargeDate = admissionExpectedDischargeDate;
    }

    public String getAdmissionKinName() {
        return admissionKinName;
    }

    public void setAdmissionKinName(String admissionKinName) {
        this.admissionKinName = admissionKinName;
    }

    public String getAdmissionKinAddress() {
        return admissionKinAddress;
    }

    public void setAdmissionKinAddress(String admissionKinAddress) {
        this.admissionKinAddress = admissionKinAddress;
    }

    public String getAdmissionKinContactNo() {
        return admissionKinContactNo;
    }

    public void setAdmissionKinContactNo(String admissionKinContactNo) {
        this.admissionKinContactNo = admissionKinContactNo;
    }

    public MstRelation getAdmissionKinRelation() {
        return admissionKinRelation;
    }

    public void setAdmissionKinRelation(MstRelation admissionKinRelation) {
        this.admissionKinRelation = admissionKinRelation;
    }

    public String getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(String createdUser) {
        this.createdUser = createdUser;
    }

    public String getAdmissionCancelReason() {
        return admissionCancelReason;
    }

    public void setAdmissionCancelReason(String admissionCancelReason) {
        this.admissionCancelReason = admissionCancelReason;
    }

    public Boolean getAdmissionIsCancel() {
        return admissionIsCancel;
    }

    public void setAdmissionIsCancel(Boolean admissionIsCancel) {
        this.admissionIsCancel = admissionIsCancel;
    }

    public String getAdmissionCancelDate() {
        return admissionCancelDate;
    }

    public void setAdmissionCancelDate(String admissionCancelDate) {
        this.admissionCancelDate = admissionCancelDate;
    }

    public Boolean getIsFinalized() {
        return isFinalized;
    }

    public void setIsFinalized(Boolean finalized) {
        isFinalized = finalized;
    }

    public String getReferByText() {
        return referByText;
    }

    public void setReferByText(String referByText) {
        this.referByText = referByText;
    }

    public String getSponsorCombination() {
        return sponsorCombination;
    }

    public void setSponsorCombination(String sponsorCombination) {
        this.sponsorCombination = sponsorCombination;
    }

    public double getApprovedAmt() {
        return approvedAmt;
    }

    public void setApprovedAmt(double approvedAmt) {
        this.approvedAmt = approvedAmt;
    }

    public double getEstimatedAmt() {
        return estimatedAmt;
    }

    public void setEstimatedAmt(double estimatedAmt) {
        this.estimatedAmt = estimatedAmt;
    }

    @Override
    public String toString() {
        return "TrnAdmission{" + "admissionId=" + admissionId + ", admissionIpdNo='" + admissionIpdNo + '\'' + ", admissionPatientId=" + admissionPatientId + ", admissionUnitId=" + admissionUnitId + ", admissionClassId=" + admissionClassId + ", admissionAtId=" + admissionAtId + ", admissionDepartmentId=" + admissionDepartmentId + ", admissionStaffId=" + admissionStaffId + ", admissionDate='" + admissionDate + '\'' + ", admissionDatePersion='" + admissionDatePersion + '\'' + ", admissionTime=" + admissionTime + ", isActive=" + isActive + ", isDeleted=" + isDeleted + ", admissionPurposeId=" + admissionPurposeId + ", admissionRetId=" + admissionRetId + ", admissionReId=" + admissionReId + ", admissionPsId=" + admissionPsId + ", admissionDiagnosis='" + admissionDiagnosis + '\'' + ", admissionRemark='" + admissionRemark + '\'' + ", isNonPresenceAdmission=" + isNonPresenceAdmission + ", admissionCurrentBedId=" + admissionCurrentBedId + ", admissionReservedBedId=" + admissionReservedBedId + ", admissionPatientBedId=" + admissionPatientBedId + ", createdBy='" + createdBy + '\'' + ", lastModifiedBy='" + lastModifiedBy + '\'' + ", createdDate=" + createdDate + ", lastModifiedDate=" + lastModifiedDate + ", mbillTariff=" + mbillTariff + ", trnSponsorCombinationList=" + trnSponsorCombinationList + ", admissionStatus=" + admissionStatus + ", admissionDischargeStatus='" + admissionDischargeStatus + '\'' + '}';
    }

}

package com.cellbeans.hspa.mstpatient;

import com.cellbeans.hspa.mstoccupation.MstOccupation;
import com.cellbeans.hspa.mstpatienttype.MstPatientType;
import com.cellbeans.hspa.mstpincode.MstPinCode;
import com.cellbeans.hspa.mstpolicestation.MstPoliceStation;
import com.cellbeans.hspa.mstrelation.MstRelation;
import com.cellbeans.hspa.mstuser.MstUser;
import com.cellbeans.hspa.nmstcamp.NmstCamp;
import com.cellbeans.hspa.nmstcampvisit.NmstCampVisit;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "mst_patient")
public class MstPatient implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "patientId", unique = true, nullable = true)
    private long patientId;


    public String getPuuId() {
        return puuId;
    }

    public void setPuuId(String puuId) {
        this.puuId = puuId;
    }

    @Column(name = "puuId")
    private String puuId;

    @JsonInclude(NON_NULL)
    @Column(name = "patientMrNo")
    private String patientMrNo;

    @JsonInclude(NON_NULL)
    @Column(name = "patientErNo")
    private String patientErNo;

    @JsonInclude(NON_NULL)
    @Column(name = "patientHeight")
    private String patientHeight;

    @JsonInclude(NON_NULL)
    @Column(name = "patientWeight")
    private String patientWeight;

    @JsonInclude(NON_NULL)
    @Column(name = "patienBMI")
    private String patienBMI;

    @JsonInclude(NON_NULL)
    @Column(name = "userDrivingNo")
    private String userDrivingNo;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "userOccupation")
    private MstOccupation userOccupation;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "patientType")
    private MstPatientType patientType;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "patientRelationId")
    private MstRelation patientRelationId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "userPoliceStation")
    private MstPoliceStation userPoliceStation;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "patientPinCode")
    private MstPinCode patientPinCode;


    @JsonInclude(NON_NULL)
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "patientUserId")
    private MstUser patientUserId;

    //for identifying RMHN Patient
    @JsonInclude(NON_NULL)
    @Column(name = "patientIsRmhn", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean patientIsRmhn = false;

    @JsonInclude(NON_NULL)
    @Column(name = "isRegisterAtCamp", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isRegisterAtCamp = false;

    @OneToOne
    @JoinColumn(name = "patientCampId")
    private NmstCamp patientCampId;
    @OneToOne
    @JoinColumn(name = "patientCampVisitId")
    private NmstCampVisit patientCampVisitId;

    @JsonIgnore
    @Column(name = "isActive", columnDefinition = "binary(1) default true", nullable = true)
    private Boolean isActive = true;

    @JsonIgnore
    @Column(name = "isDeleted", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isDeleted = false;

    //    @CreatedBy
    @Column(nullable = true, updatable = true)
    private String createdBy;

    //    @JsonIgnore
//    @LastModifiedBy
    private String lastModifiedBy;

    @CreatedDate
    @Column(nullable = true, updatable = false)
    private Date createdDate;

    @JsonIgnore
    @LastModifiedDate
    private Date lastModifiedDate;

    @JsonInclude
    @Transient
    private double advanceAmount = 0;

    @JsonInclude
    @Transient
    private Long ipdadvancedId = 0l;

    @JsonInclude
    @Transient
    private String ipdAdvanceRemarks;

    @JsonInclude(NON_NULL)
    @Column(name = "patientIsBroughtDead")
    private Boolean patientIsBroughtDead = false;

    @JsonInclude(NON_NULL)
    @Column(name = "patientIsDependant")
    private Boolean patientIsDependant = false;

    @JsonInclude(NON_NULL)
    @Column(name = "isEmergency", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isEmergency = false;

    @JsonInclude(NON_NULL)
    @Column(name = "firstFingure")
    private String firstFingure;

    @JsonInclude(NON_NULL)
    @Column(name = "secondFingure")
    private String secondFingure;

    @JsonInclude(NON_NULL)
    @Column(name = "isCardIssued", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isCardIssued = false;

    @JsonInclude(NON_NULL)
    @Column(name = "cardIssuedDate")
    private Date cardIssuedDate;

    public String getFirstFingure() {
        return firstFingure;
    }

    public void setFirstFingure(String firstFingure) {
        this.firstFingure = firstFingure;
    }

    public String getSecondFingure() {
        return secondFingure;
    }

    public void setSecondFingure(String secondFingure) {
        this.secondFingure = secondFingure;
    }

    public long getPatientId() {
        return patientId;
    }

    public void setPatientId(long patientId) {
        this.patientId = patientId;
    }

    public String getPatientMrNo() {
        return patientMrNo;
    }

    public void setPatientMrNo(String patientMrNo) {
        this.patientMrNo = patientMrNo;
    }

    public MstUser getPatientUserId() {
        return patientUserId;
    }

    public void setPatientUserId(MstUser patientUserId) {
        this.patientUserId = patientUserId;
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

    public String getIpdAdvanceRemarks() {
        return ipdAdvanceRemarks;
    }

    public void setIpdAdvanceRemarks(String ipdAdvanceRemarks) {
        this.ipdAdvanceRemarks = ipdAdvanceRemarks;
    }

    public Boolean getPatientIsBroughtDead() {
        return patientIsBroughtDead;
    }

    public void setPatientIsBroughtDead(Boolean patientIsBroughtDead) {
        this.patientIsBroughtDead = patientIsBroughtDead;
    }

    public Boolean getEmergency() {
        return isEmergency;
    }

    public void setEmergency(Boolean emergency) {
        isEmergency = emergency;
    }

    public String getPatientErNo() {
        return patientErNo;
    }

    public void setPatientErNo(String patientErNo) {
        this.patientErNo = patientErNo;
    }

    public MstOccupation getUserOccupation() {
        return userOccupation;
    }

    public void setUserOccupation(MstOccupation userOccupation) {
        this.userOccupation = userOccupation;
    }

    public MstPoliceStation getUserPoliceStation() {
        return userPoliceStation;
    }

    public void setUserPoliceStation(MstPoliceStation userPoliceStation) {
        this.userPoliceStation = userPoliceStation;
    }

    public Boolean getIsCardIssued() {
        return isCardIssued;
    }

    public void setIsCardIssued(Boolean cardIssued) {
        isCardIssued = cardIssued;
    }

    public Date getCardIssuedDate() {
        return cardIssuedDate;
    }

    public void setCardIssuedDate(Date cardIssuedDate) {
        this.cardIssuedDate = cardIssuedDate;
    }

    public String getPatientHeight() {
        return patientHeight;
    }

    public void setPatientHeight(String patientHeight) {
        this.patientHeight = patientHeight;
    }

    public String getPatientWeight() {
        return patientWeight;
    }

    public void setPatientWeight(String patientWeight) {
        this.patientWeight = patientWeight;
    }

    public String getPatienBMI() {
        return patienBMI;
    }

    public void setPatienBMI(String patienBMI) {
        this.patienBMI = patienBMI;
    }

    public MstPatientType getPatientType() {
        return patientType;
    }

    public void setPatientType(MstPatientType patientType) {
        this.patientType = patientType;
    }

    public MstRelation getPatientRelationId() {
        return patientRelationId;
    }

    public void setPatientRelationId(MstRelation patientRelationId) {
        this.patientRelationId = patientRelationId;
    }

    public Boolean getPatientIsDependant() {
        return patientIsDependant;
    }

    public void setPatientIsDependant(Boolean patientIsDependant) {
        this.patientIsDependant = patientIsDependant;
    }

    public String getUserDrivingNo() {
        return userDrivingNo;
    }

    public void setUserDrivingNo(String userDrivingNo) {
        this.userDrivingNo = userDrivingNo;
    }

    public MstPinCode getPatientPinCode() {
        return patientPinCode;
    }

    public void setPatientPinCode(MstPinCode patientPinCode) {
        this.patientPinCode = patientPinCode;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public Boolean getCardIssued() {
        return isCardIssued;
    }

    public void setCardIssued(Boolean cardIssued) {
        isCardIssued = cardIssued;
    }

    //for identifying RMHN Patient
    public Boolean getPatientIsRmhn() {
        return patientIsRmhn;
    }

    public void setPatientIsRmhn(Boolean patientIsRmhn) {
        this.patientIsRmhn = patientIsRmhn;
    }

    public NmstCamp getPatientCampId() {
        return patientCampId;
    }

    public void setPatientCampId(NmstCamp patientCampId) {
        this.patientCampId = patientCampId;
    }

    public NmstCampVisit getPatientCampVisitId() {
        return patientCampVisitId;
    }

    public void setPatientCampVisitId(NmstCampVisit patientCampVisitId) {
        this.patientCampVisitId = patientCampVisitId;
    }

    public Boolean getIsRegisterAtCamp() {        return isRegisterAtCamp;    }

    public void setIsRegisterAtCamp(Boolean registerAtCamp) {        isRegisterAtCamp = registerAtCamp;    }
}

package com.cellbeans.hspa.trnotproceduredetails;

import com.cellbeans.hspa.mstanaesthesiatype.MstAnaesthesiaType;
import com.cellbeans.hspa.mstoperationstatus.MstOperationStatus;
import com.cellbeans.hspa.mstoperationtheatre.MstOperationTheatre;
import com.cellbeans.hspa.mstoperationtheatretable.MstOperationTheatreTable;
import com.cellbeans.hspa.mstpatient.MstPatient;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "trn_ot_procedure_details")
public class TrnOtProcedureDetails {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "opdId", unique = true, nullable = true)
    private long opdId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "opdPatientId")
    private MstPatient opdPatientId;

    @JsonInclude(NON_NULL)
    @Column(name = "opdOpsId")
    private long opdOpsId;

    @JsonInclude(NON_NULL)
    // 0 - from OT Incharge, 1 - from EMR
    @Column(name = "opdRequestFrom")
    private String opdRequestFrom;

    @JsonInclude(NON_NULL)
    @Column(name = "opdScheduleDate")
    private String opdScheduleDate;

    @JsonInclude(NON_NULL)
    @Column(name = "opdScheduleTime")
    private String opdScheduleTime;

    @JsonInclude(NON_NULL)
    @Column(name = "opdStartDate")
    private String opdStartDate;

    @JsonInclude(NON_NULL)
    @Column(name = "opdStartTime")
    private String opdStartTime;

    @JsonInclude(NON_NULL)
    @Column(name = "opdEndDate")
    private String opdEndDate;

    @JsonInclude(NON_NULL)
    @Column(name = "opdEndTime")
    private String opdEndTime;

    @JsonInclude(NON_NULL)
    @Column(name = "opdDuration")
    private String opdDuration;

    @JsonInclude(NON_NULL)
    @Column(name = "opdStatus")
    private String opdStatus;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "opdOtTableId")
    private MstOperationTheatreTable opdOtTableId;

    @JsonInclude(NON_NULL)
    @Column(name = "opdRemark")
    private String opdRemark;

    @JsonInclude(NON_NULL)
    @Column(name = "opdPriority")
    private Boolean opdPriority;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "opdOtId")
    private MstOperationTheatre opdOtId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "opdAtId")
    private MstAnaesthesiaType opdAtId;

    @JsonInclude(NON_NULL)
    @Column(name = "opdSpecialRemark")
    private String opdSpecialRemark;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "opdProcedureStatus")
    private MstOperationStatus opdProcedureStatus;

    @JsonInclude(NON_NULL)
    @Column(name = "opdIsPerformed", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean opdIsPerformed = false;
    @JsonIgnore
    @Column(name = "isActive", columnDefinition = "binary(1) default true", nullable = true)
    private Boolean isActive = true;
    @JsonIgnore
    @Column(name = "isDeleted", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isDeleted = false;
    @JsonIgnore
    @CreatedBy
    @Column(nullable = true, updatable = true)
    private String createdBy;
    @JsonIgnore
    @LastModifiedBy
    private String lastModifiedBy;
@CreatedDate     @Column(nullable = true, updatable = true)     private Date createdDate;
    @JsonIgnore
    @LastModifiedDate
    private Date lastModifiedDate;

    public long getOpdId() {
        return opdId;
    }

    public void setOpdId(long opdId) {
        this.opdId = opdId;
    }

    public MstPatient getOpdPatientId() {
        return opdPatientId;
    }

    public void setOpdPatientId(MstPatient opdPatientId) {
        this.opdPatientId = opdPatientId;
    }

    public long getOpdOpsId() {
        return opdOpsId;
    }

    public void setOpdOpsId(long opdOpsId) {
        this.opdOpsId = opdOpsId;
    }

    public String getOpdRequestFrom() {
        return opdRequestFrom;
    }

    public void setOpdRequestFrom(String opdRequestFrom) {
        this.opdRequestFrom = opdRequestFrom;
    }

    public String getOpdScheduleDate() {
        return opdScheduleDate;
    }

    public void setOpdScheduleDate(String opdScheduleDate) {
        this.opdScheduleDate = opdScheduleDate;
    }

    public String getOpdScheduleTime() {
        return opdScheduleTime;
    }

    public void setOpdScheduleTime(String opdScheduleTime) {
        this.opdScheduleTime = opdScheduleTime;
    }

    public String getOpdStartDate() {
        return opdStartDate;
    }

    public void setOpdStartDate(String opdStartDate) {
        this.opdStartDate = opdStartDate;
    }

    public String getOpdStartTime() {
        return opdStartTime;
    }

    public void setOpdStartTime(String opdStartTime) {
        this.opdStartTime = opdStartTime;
    }

    public String getOpdEndDate() {
        return opdEndDate;
    }

    public void setOpdEndDate(String opdEndDate) {
        this.opdEndDate = opdEndDate;
    }

    public String getOpdEndTime() {
        return opdEndTime;
    }

    public void setOpdEndTime(String opdEndTime) {
        this.opdEndTime = opdEndTime;
    }

    public String getOpdDuration() {
        return opdDuration;
    }

    public void setOpdDuration(String opdDuration) {
        this.opdDuration = opdDuration;
    }

    public String getOpdStatus() {
        return opdStatus;
    }

    public void setOpdStatus(String opdStatus) {
        this.opdStatus = opdStatus;
    }

    public MstOperationTheatreTable getOpdOtTableId() {
        return opdOtTableId;
    }

    public void setOpdOtTableId(MstOperationTheatreTable opdOtTableId) {
        this.opdOtTableId = opdOtTableId;
    }

    public String getOpdRemark() {
        return opdRemark;
    }

    public void setOpdRemark(String opdRemark) {
        this.opdRemark = opdRemark;
    }

    public Boolean getOpdPriority() {
        return opdPriority;
    }

    public void setOpdPriority(Boolean opdPriority) {
        this.opdPriority = opdPriority;
    }

    public MstOperationTheatre getOpdOtId() {
        return opdOtId;
    }

    public void setOpdOtId(MstOperationTheatre opdOtId) {
        this.opdOtId = opdOtId;
    }

    public MstAnaesthesiaType getOpdAtId() {
        return opdAtId;
    }

    public void setOpdAtId(MstAnaesthesiaType opdAtId) {
        this.opdAtId = opdAtId;
    }

    public String getOpdSpecialRemark() {
        return opdSpecialRemark;
    }

    public void setOpdSpecialRemark(String opdSpecialRemark) {
        this.opdSpecialRemark = opdSpecialRemark;
    }

    public MstOperationStatus getOpdProcedureStatus() {
        return opdProcedureStatus;
    }

    public void setOpdProcedureStatus(MstOperationStatus opdProcedureStatus) {
        this.opdProcedureStatus = opdProcedureStatus;
    }

    public Boolean getOpdIsPerformed() {
        return opdIsPerformed;
    }

    public void setOpdIsPerformed(Boolean opdIsPerformed) {
        this.opdIsPerformed = opdIsPerformed;
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
}

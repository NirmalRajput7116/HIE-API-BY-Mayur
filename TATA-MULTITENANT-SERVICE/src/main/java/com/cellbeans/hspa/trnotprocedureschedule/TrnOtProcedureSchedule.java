package com.cellbeans.hspa.trnotprocedureschedule;

import com.cellbeans.hspa.mstanaesthesiatype.MstAnaesthesiaType;
import com.cellbeans.hspa.mstoperationstatus.MstOperationStatus;
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
import java.io.Serializable;
import java.util.Date;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "trn_ot_procedure_schedule")
public class TrnOtProcedureSchedule implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "opsId", unique = true, nullable = true)
    private long opsId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "opsPatientId")
    private MstPatient opsPatientId;

    @JsonInclude(NON_NULL)
    // 0 - from OT Incharge, 1 - from EMR
    @Column(name = "opsRequestFrom")
    private String opsRequestFrom;

    //    @Column(name = "opsProcedureName")
//    private String opsProcedureName;
    @JsonInclude(NON_NULL)
    @Column(name = "opsScheduleDate")
    private String opsScheduleDate;

    @JsonInclude(NON_NULL)
    @Column(name = "opsScheduleTime")
    private String opsScheduleTime;

    @JsonInclude(NON_NULL)
    @Column(name = "opsPerformedDate")
    private Date opsPerformedDate;

    @JsonInclude(NON_NULL)
    @Column(name = "opsPerformedTime")
    private Date opsPerformedTime;

    @JsonInclude(NON_NULL)
    @Column(name = "opsFromTime")
    private String opsFromTime;

    @JsonInclude(NON_NULL)
    @Column(name = "opsToTime")
    private String opsToTime;
//    @Column(name = "opsStatus", columnDefinition = "binary(1) default false", nullable = true)
//    private Boolean opsStatus = false;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "opsOtTableId")
    private MstOperationTheatreTable opsOtTableId;

    @JsonInclude(NON_NULL)
    @Column(name = "opsRemark")
    private String opsRemark;

    @JsonInclude(NON_NULL)
    @Column(name = "opsPriority")
    private Boolean opsPriority;
//    @ManyToOne
//    @JoinColumn(name = "opsOtId")
//    private MstOperationTheatre opsOtId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "opsAtId")
    private MstAnaesthesiaType opsAtId;

    @JsonInclude(NON_NULL)
    @Column(name = "opsSpecialRemark")
    private String opsSpecialRemark;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "opsProcedureStatus")
    private MstOperationStatus opsProcedureStatus;

    @JsonInclude(NON_NULL)
    @Column(name = "opsIsSchedule", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean opsIsSchedule = false;
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

    @CreatedDate
    @Column(nullable = true, updatable = true)
    private Date createdDate;
    @JsonIgnore
    @LastModifiedDate
    private Date lastModifiedDate;

    public long getOpsId() {
        return opsId;
    }

    public void setOpsId(long opsId) {
        this.opsId = opsId;
    }

    public MstPatient getOpsPatientId() {
        return opsPatientId;
    }

    public void setOpsPatientId(MstPatient opsPatientId) {
        this.opsPatientId = opsPatientId;
    }

    public String getOpsRequestFrom() {
        return opsRequestFrom;
    }

    public void setOpsRequestFrom(String opsRequestFrom) {
        this.opsRequestFrom = opsRequestFrom;
    }

    public String getOpsScheduleDate() {
        return opsScheduleDate;
    }

    public void setOpsScheduleDate(String opsScheduleDate) {
        this.opsScheduleDate = opsScheduleDate;
    }

    public String getOpsScheduleTime() {
        return opsScheduleTime;
    }

    public void setOpsScheduleTime(String opsScheduleTime) {
        this.opsScheduleTime = opsScheduleTime;
    }

    public Date getOpsPerformedDate() {
        return opsPerformedDate;
    }

    public void setOpsPerformedDate(Date opsPerformedDate) {
        this.opsPerformedDate = opsPerformedDate;
    }

    public Date getOpsPerformedTime() {
        return opsPerformedTime;
    }

    public void setOpsPerformedTime(Date opsPerformedTime) {
        this.opsPerformedTime = opsPerformedTime;
    }

    public String getOpsFromTime() {
        return opsFromTime;
    }

    public void setOpsFromTime(String opsFromTime) {
        this.opsFromTime = opsFromTime;
    }

    public String getOpsToTime() {
        return opsToTime;
    }

    public void setOpsToTime(String opsToTime) {
        this.opsToTime = opsToTime;
    }
//    public Boolean getOpsStatus() {
//        return opsStatus;
//    }
//
//    public void setOpsStatus(Boolean opsStatus) {
//        this.opsStatus = opsStatus;
//    }

    public MstOperationTheatreTable getOpsOtTableId() {
        return opsOtTableId;
    }

    public void setOpsOtTableId(MstOperationTheatreTable opsOtTableId) {
        this.opsOtTableId = opsOtTableId;
    }

    public String getOpsRemark() {
        return opsRemark;
    }

    public void setOpsRemark(String opsRemark) {
        this.opsRemark = opsRemark;
    }

    public Boolean getOpsPriority() {
        return opsPriority;
    }

    public void setOpsPriority(Boolean opsPriority) {
        this.opsPriority = opsPriority;
    }

    public MstAnaesthesiaType getOpsAtId() {
        return opsAtId;
    }

    public void setOpsAtId(MstAnaesthesiaType opsAtId) {
        this.opsAtId = opsAtId;
    }

    public String getOpsSpecialRemark() {
        return opsSpecialRemark;
    }

    public void setOpsSpecialRemark(String opsSpecialRemark) {
        this.opsSpecialRemark = opsSpecialRemark;
    }

    public MstOperationStatus getOpsProcedureStatus() {
        return opsProcedureStatus;
    }

    public void setOpsProcedureStatus(MstOperationStatus opsProcedureStatus) {
        this.opsProcedureStatus = opsProcedureStatus;
    }

    public Boolean getOpsIsSchedule() {
        return opsIsSchedule;
    }

    public void setOpsIsSchedule(Boolean opsIsSchedule) {
        this.opsIsSchedule = opsIsSchedule;
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
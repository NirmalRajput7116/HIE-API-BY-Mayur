package com.cellbeans.hspa.trndischarge;

import com.cellbeans.hspa.mbilltariff.MbillTariff;
import com.cellbeans.hspa.mipddischargedestination.MipdDischargeDestination;
import com.cellbeans.hspa.mipddischargetype.MipdDischargeType;
import com.cellbeans.hspa.mstreferringentity.MstReferringEntity;
import com.cellbeans.hspa.mststaff.MstStaff;
import com.cellbeans.hspa.mstunit.MstUnit;
import com.cellbeans.hspa.trnadmission.TrnAdmission;
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
@Table(name = "trn_discharge")
public class TrnDischarge implements Serializable {
    private static final long serialVersionUID = 1L;
    @JsonInclude(NON_NULL)
    @Column(name = "isDeathDischarge")
    protected String isDeathDischarge;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dischargeId", unique = true, nullable = true)
    private long dischargeId;
    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "dischargeAdmissionId")
    private TrnAdmission dischargeAdmissionId;
    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "dischargeStaffId")
    private MstStaff dischargeStaffId;
    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "dischargeType")
    private MipdDischargeType dischargeType;
    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "dischargeDestination")
    private MipdDischargeDestination dischargeDestination;
    @JsonInclude(NON_NULL)
    @Column(name = "dischargeComment")
    private String dischargeComment;
    @JsonInclude(NON_NULL)
    @Column(name = "dischargeDate")
    private String dischargeDate;
    @JsonInclude(NON_NULL)
    @Column(name = "persionDischargeDate")
    private String persionDischargeDate;
    @JsonInclude(NON_NULL)
    @Column(name = "dischargeTime")
    private String dischargeTime;
    @JsonInclude(NON_NULL)
    @Column(name = "dischargeStatus")
    private String dischargeStatus;
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

    @Transient
    private long count;

    @Transient
    private MbillTariff objMbillTariff;

    @Transient
    private MstUnit ObjHeaderData;

    @Transient
    private MstReferringEntity ObjReferYBy;

    public MbillTariff getObjMbillTariff() {
        return objMbillTariff;
    }

    public void setObjMbillTariff(MbillTariff objMbillTariff) {
        this.objMbillTariff = objMbillTariff;
    }

    public MstUnit getObjHeaderData() {
        return ObjHeaderData;
    }

    public void setObjHeaderData(MstUnit objHeaderData) {
        ObjHeaderData = objHeaderData;
    }

    public long getDischargeId() {
        return dischargeId;
    }

    public void setDischargeId(int dischargeId) {
        this.dischargeId = dischargeId;
    }

    public TrnAdmission getDischargeAdmissionId() {
        return dischargeAdmissionId;
    }

    public void setDischargeAdmissionId(TrnAdmission dischargeAdmissionId) {
        this.dischargeAdmissionId = dischargeAdmissionId;
    }

    public MstStaff getDischargeStaffId() {
        return dischargeStaffId;
    }

    public void setDischargeStaffId(MstStaff dischargeStaffId) {
        this.dischargeStaffId = dischargeStaffId;
    }

    public MipdDischargeType getDischargeType() {
        return dischargeType;
    }

    public void setDischargeType(MipdDischargeType dischargeType) {
        this.dischargeType = dischargeType;
    }

    public MipdDischargeDestination getDischargeDestination() {
        return dischargeDestination;
    }

    public void setDischargeDestination(MipdDischargeDestination dischargeDestination) {
        this.dischargeDestination = dischargeDestination;
    }

    public String getDischargeComment() {
        return dischargeComment;
    }

    public void setDischargeComment(String dischargeComment) {
        this.dischargeComment = dischargeComment;
    }

    public String getDischargeDate() {
        return dischargeDate;
    }

    public void setDischargeDate(String dischargeDate) {
        this.dischargeDate = dischargeDate;
    }

    public String getDischargeTime() {
        return dischargeTime;
    }

    public void setDischargeTime(String dischargeTime) {
        this.dischargeTime = dischargeTime;
    }

    public String getDischargeStatus() {
        return dischargeStatus;
    }

    public void setDischargeStatus(String dischargeStatus) {
        this.dischargeStatus = dischargeStatus;
    }

    public String getIsDeathDischarge() {
        return isDeathDischarge;
    }

    public void setIsDeathDischarge(String isDeathDischarge) {
        this.isDeathDischarge = isDeathDischarge;
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

    public String getPersionDischargeDate() {
        return persionDischargeDate;
    }

    public void setPersionDischargeDate(String persionDischargeDate) {
        this.persionDischargeDate = persionDischargeDate;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public MstReferringEntity getObjReferYBy() {
        return ObjReferYBy;
    }

    public void setObjReferYBy(MstReferringEntity objReferYBy) {
        ObjReferYBy = objReferYBy;
    }
}

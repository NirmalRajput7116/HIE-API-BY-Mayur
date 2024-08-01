package com.cellbeans.hspa.trnotpreoperativeinstructiondetails;

import com.cellbeans.hspa.mstpreoperativeinstructions.MstPreOperativeInstructions;
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
@Table(name = "trn_ot_pre_operative_instruction_details")
public class TrnOtPreOperativeInstructionDetails implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "opoidId", unique = true, nullable = true)
    private long opoidId;

    @JsonInclude(NON_NULL)
    @Column(name = "opoidOpsId")
    private long opoidOpsId;

    @JsonInclude(NON_NULL)
    @Column(name = "opoidOpdId")
    private long opoidOpdId;

    @JsonInclude(NON_NULL)
    @Column(name = "opoidProcedureId")
    private long opoidProcedureId;

    @JsonInclude(NON_NULL)
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "opoidPoiId")
    private MstPreOperativeInstructions opoidPoiId;

    @JsonInclude(NON_NULL)
    @Column(name = "opoidResult", columnDefinition = "binary(1) default true", nullable = true)
    private Boolean opoidResult = false;

    @JsonInclude(NON_NULL)
    @Column(name = "opoidRemark")
    private String opoidRemark;

    @JsonIgnore
    @Column(name = "isActive", columnDefinition = "binary(1) default true", nullable = true)
    private Boolean isActive = true;

    @JsonIgnore
    @Column(name = "isDeleted", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isDeleted = false;

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

    public long getOpoidId() {
        return opoidId;
    }

    public void setOpoidId(long opoidId) {
        this.opoidId = opoidId;
    }

    public long getOpoidOpsId() {
        return opoidOpsId;
    }

    public void setOpoidOpsId(long opoidOpsId) {
        this.opoidOpsId = opoidOpsId;
    }

    public long getOpoidOpdId() {
        return opoidOpdId;
    }

    public void setOpoidOpdId(long opoidOpdId) {
        this.opoidOpdId = opoidOpdId;
    }

    public long getOpoidProcedureId() {
        return opoidProcedureId;
    }

    public void setOpoidProcedureId(long opoidProcedureId) {
        this.opoidProcedureId = opoidProcedureId;
    }

    public MstPreOperativeInstructions getOpoidPoiId() {
        return opoidPoiId;
    }

    public void setOpoidPoiId(MstPreOperativeInstructions opoidPoiId) {
        this.opoidPoiId = opoidPoiId;
    }

    public Boolean getOpoidResult() {
        return opoidResult;
    }

    public void setOpoidResult(Boolean opoidResult) {
        this.opoidResult = opoidResult;
    }

    public String getOpoidRemark() {
        return opoidRemark;
    }

    public void setOpoidRemark(String opoidRemark) {
        this.opoidRemark = opoidRemark;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean active) {
        isActive = active;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean deleted) {
        isDeleted = deleted;
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

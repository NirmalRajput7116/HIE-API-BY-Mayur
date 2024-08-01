package com.cellbeans.hspa.trnotintraoperativeinstructiondetails;

import com.cellbeans.hspa.mstintraoperativeinstructions.MstIntraOperativeInstructions;
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
@Table(name = "trn_ot_intra_operative_instruction_details")
public class TrnOtIntraOperativeInstructionDetails implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "oioidId", unique = true, nullable = true)
    private long oioidId;

    @JsonInclude(NON_NULL)
    @Column(name = "oioidOpsId")
    private long oioidOpsId;

    @JsonInclude(NON_NULL)
    @Column(name = "oioidOpdId")
    private long oioidOpdId;

    @JsonInclude(NON_NULL)
    @Column(name = "oioidProcedureId")
    private long oioidProcedureId;

    @JsonInclude(NON_NULL)
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "oioidIoiId")
    private MstIntraOperativeInstructions oioidIoiId;

    @JsonInclude(NON_NULL)
    @Column(name = "oioidResult", columnDefinition = "binary(1) default true", nullable = true)
    private Boolean oioidResult = false;

    @JsonInclude(NON_NULL)
    @Column(name = "oioidRemark")
    private String oioidRemark;

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

    public long getOioidId() {
        return oioidId;
    }

    public void setOioidId(long oioidId) {
        this.oioidId = oioidId;
    }

    public long getOioidOpsId() {
        return oioidOpsId;
    }

    public void setOioidOpsId(long oioidOpsId) {
        this.oioidOpsId = oioidOpsId;
    }

    public long getOioidOpdId() {
        return oioidOpdId;
    }

    public void setOioidOpdId(long oioidOpdId) {
        this.oioidOpdId = oioidOpdId;
    }

    public long getOioidProcedureId() {
        return oioidProcedureId;
    }

    public void setOioidProcedureId(long oioidProcedureId) {
        this.oioidProcedureId = oioidProcedureId;
    }

    public MstIntraOperativeInstructions getOioidIoiId() {
        return oioidIoiId;
    }

    public void setOioidIoiId(MstIntraOperativeInstructions oioidIoiId) {
        this.oioidIoiId = oioidIoiId;
    }

    public Boolean getOioidResult() {
        return oioidResult;
    }

    public void setOioidResult(Boolean oioidResult) {
        this.oioidResult = oioidResult;
    }

    public String getOioidRemark() {
        return oioidRemark;
    }

    public void setOioidRemark(String oioidRemark) {
        this.oioidRemark = oioidRemark;
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

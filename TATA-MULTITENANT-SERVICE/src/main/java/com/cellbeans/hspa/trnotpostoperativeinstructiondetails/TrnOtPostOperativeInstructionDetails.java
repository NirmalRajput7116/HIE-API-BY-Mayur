package com.cellbeans.hspa.trnotpostoperativeinstructiondetails;

import com.cellbeans.hspa.mstpostoperativeinstructions.MstPostOperativeInstructions;
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
@Table(name = "trn_ot_post_operative_instruction_details")
public class TrnOtPostOperativeInstructionDetails implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "opooidId", unique = true, nullable = true)
    private long opooidId;

    @JsonInclude(NON_NULL)
    @Column(name = "opooidOpsId")
    private long opooidOpsId;

    @JsonInclude(NON_NULL)
    @Column(name = "opooidOpdId")
    private long opooidOpdId;

    @JsonInclude(NON_NULL)
    @Column(name = "opooidProcedureId")
    private long opooidProcedureId;

    @JsonInclude(NON_NULL)
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "opooidPooiId")
    private MstPostOperativeInstructions opooidPooiId;

    @JsonInclude(NON_NULL)
    @Column(name = "opooidResult", columnDefinition = "binary(1) default true", nullable = true)
    private Boolean opooidResult = false;

    @JsonInclude(NON_NULL)
    @Column(name = "opooidRemark")
    private String opooidRemark;

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

    public long getOpooidId() {
        return opooidId;
    }

    public void setOpooidId(long opooidId) {
        this.opooidId = opooidId;
    }

    public long getOpooidOpsId() {
        return opooidOpsId;
    }

    public void setOpooidOpsId(long opooidOpsId) {
        this.opooidOpsId = opooidOpsId;
    }

    public long getOpooidOpdId() {
        return opooidOpdId;
    }

    public void setOpooidOpdId(long opooidOpdId) {
        this.opooidOpdId = opooidOpdId;
    }

    public long getOpooidProcedureId() {
        return opooidProcedureId;
    }

    public void setOpooidProcedureId(long opooidProcedureId) {
        this.opooidProcedureId = opooidProcedureId;
    }

    public MstPostOperativeInstructions getOpooidPooiId() {
        return opooidPooiId;
    }

    public void setOpooidPooiId(MstPostOperativeInstructions opooidPooiId) {
        this.opooidPooiId = opooidPooiId;
    }

    public Boolean getOpooidResult() {
        return opooidResult;
    }

    public void setOpooidResult(Boolean opooidResult) {
        this.opooidResult = opooidResult;
    }

    public String getOpooidRemark() {
        return opooidRemark;
    }

    public void setOpooidRemark(String opooidRemark) {
        this.opooidRemark = opooidRemark;
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

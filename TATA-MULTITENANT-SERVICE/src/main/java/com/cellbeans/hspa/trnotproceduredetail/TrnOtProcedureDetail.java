package com.cellbeans.hspa.trnotproceduredetail;

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
@Table(name = "trn_ot_procedure")
public class TrnOtProcedureDetail implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "opId", unique = true, nullable = true)
    private long opId;

    @JsonInclude(NON_NULL)
    @Column(name = "opOpsId")
    private long opOpsId;

    @JsonInclude(NON_NULL)
    @Column(name = "opOpdId")
    private long opOpdId;

    @JsonInclude(NON_NULL)
    @Column(name = "opProcedureId")
    private long opProcedureId;

    @JsonInclude(NON_NULL)
    @Column(name = "opProcedureName")
    private String opProcedureName;

    @JsonInclude(NON_NULL)
    @Column(name = "opResult", columnDefinition = "binary(1) default true", nullable = true)
    private Boolean opResult = false;

    @JsonInclude(NON_NULL)
    @Column(name = "opRemark")
    private String opRemark;

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

    public long getOpId() {
        return opId;
    }

    public void setOpId(long opId) {
        this.opId = opId;
    }

    public long getOpOpsId() {
        return opOpsId;
    }

    public void setOpOpsId(long opOpsId) {
        this.opOpsId = opOpsId;
    }

    public long getOpOpdId() {
        return opOpdId;
    }

    public void setOpOpdId(long opOpdId) {
        this.opOpdId = opOpdId;
    }

    public long getOpProcedureId() {
        return opProcedureId;
    }

    public void setOpProcedureId(long opProcedureId) {
        this.opProcedureId = opProcedureId;
    }

    public String getOpProcedureName() {
        return opProcedureName;
    }

    public void setOpProcedureName(String opProcedureName) {
        this.opProcedureName = opProcedureName;
    }

    public Boolean getOpResult() {
        return opResult;
    }

    public void setOpResult(Boolean opResult) {
        this.opResult = opResult;
    }

    public String getOpRemark() {
        return opRemark;
    }

    public void setOpRemark(String opRemark) {
        this.opRemark = opRemark;
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

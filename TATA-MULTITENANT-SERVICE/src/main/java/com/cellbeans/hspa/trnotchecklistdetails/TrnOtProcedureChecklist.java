package com.cellbeans.hspa.trnotchecklistdetails;

import com.cellbeans.hspa.mstprocedurechecklist.MstProcedureChecklist;
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
@Table(name = "trn_ot_procedure_checklist")
public class TrnOtProcedureChecklist implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "opcId", unique = true, nullable = true)
    private long opcId;

    @JsonInclude(NON_NULL)
    @Column(name = "opcOpsId")
    private long opcOpsId;

    @JsonInclude(NON_NULL)
    @Column(name = "opcOpdId")
    private long opcOpdId;

    @JsonInclude(NON_NULL)
    @Column(name = "opcProcedureId")
    private long opcProcedureId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    private MstProcedureChecklist opcChecklistId;

    @JsonInclude(NON_NULL)
    @Column(name = "opcChecklistIsPerformed", columnDefinition = "binary(1) default true", nullable = true)
    private Boolean opcChecklistIsPerformed = false;

    @JsonInclude(NON_NULL)
    @Column(name = "opcChecklistRemark")
    private String opcChecklistRemark;

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

    public long getOpcId() {
        return opcId;
    }

    public void setOpcId(long opcId) {
        this.opcId = opcId;
    }

    public long getOpcOpsId() {
        return opcOpsId;
    }

    public void setOpcOpsId(long opcOpsId) {
        this.opcOpsId = opcOpsId;
    }

    public long getOpcOpdId() {
        return opcOpdId;
    }

    public void setOpcOpdId(long opcOpdId) {
        this.opcOpdId = opcOpdId;
    }

    public long getOpcProcedureId() {
        return opcProcedureId;
    }

    public void setOpcProcedureId(long opcProcedureId) {
        this.opcProcedureId = opcProcedureId;
    }

    public MstProcedureChecklist getOpcChecklistId() {
        return opcChecklistId;
    }

    public void setOpcChecklistId(MstProcedureChecklist opcChecklistId) {
        this.opcChecklistId = opcChecklistId;
    }

    public Boolean getOpcChecklistIsPerformed() {
        return opcChecklistIsPerformed;
    }

    public void setOpcChecklistIsPerformed(Boolean opcChecklistIsPerformed) {
        this.opcChecklistIsPerformed = opcChecklistIsPerformed;
    }

    public String getOpcChecklistRemark() {
        return opcChecklistRemark;
    }

    public void setOpcChecklistRemark(String opcChecklistRemark) {
        this.opcChecklistRemark = opcChecklistRemark;
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

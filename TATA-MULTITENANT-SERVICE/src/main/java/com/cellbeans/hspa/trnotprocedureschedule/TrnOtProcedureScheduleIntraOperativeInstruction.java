package com.cellbeans.hspa.trnotprocedureschedule;

import com.cellbeans.hspa.mstintraoperativeinstructions.MstIntraOperativeInstructions;
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
@Table(name = "trn_ot_procedure_schedule_intra_operative_instruction")
public class TrnOtProcedureScheduleIntraOperativeInstruction {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "opsioiId", unique = true, nullable = true)
    private long opsioiId;

    @JsonInclude(NON_NULL)
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "opsioiIoiId")
    private MstIntraOperativeInstructions opsioiIoiId;

    @JsonInclude(NON_NULL)
    @Column(name = "opsioiResult")
    private Boolean opsioiResult;

    @JsonInclude(NON_NULL)
    @Column(name = "opsioiRemark")
    private String opsioiRemark;

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

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public MstIntraOperativeInstructions getOpsioiIoiId() {
        return opsioiIoiId;
    }

    public void setOpsioiIoiId(MstIntraOperativeInstructions opsioiIoiId) {
        this.opsioiIoiId = opsioiIoiId;
    }

    public Boolean getOpsioiResult() {
        return opsioiResult;
    }

    public void setOpsioiResult(Boolean opsioiResult) {
        this.opsioiResult = opsioiResult;
    }

    public String getOpsioiRemark() {
        return opsioiRemark;
    }

    public void setOpsioiRemark(String opsioiRemark) {
        this.opsioiRemark = opsioiRemark;
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

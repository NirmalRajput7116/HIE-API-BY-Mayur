package com.cellbeans.hspa.trnotprocedureschedule;

import com.cellbeans.hspa.mstpostoperativeinstructions.MstPostOperativeInstructions;
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
@Table(name = "trn_ot_procedure_schedule_post_operative_instruction")
public class TrnOtProcedureSchedulePostOperativeInstruction {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "opspooiId", unique = true, nullable = true)
    private long opspooiId;

    @JsonInclude(NON_NULL)
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "opspooiPooiId")
    private MstPostOperativeInstructions opspooiPooiId;

    @JsonInclude(NON_NULL)
    @Column(name = "opspooiResult")
    private Boolean opspooiResult;

    @JsonInclude(NON_NULL)
    @Column(name = "opspooiRemark")
    private String opspooiRemark;

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

    public MstPostOperativeInstructions getOpspooiPooiId() {
        return opspooiPooiId;
    }

    public void setOpspooiPooiId(MstPostOperativeInstructions opspooiPooiId) {
        this.opspooiPooiId = opspooiPooiId;
    }

    public Boolean getOpspooiResult() {
        return opspooiResult;
    }

    public void setOpspooiResult(Boolean opspooiResult) {
        this.opspooiResult = opspooiResult;
    }

    public String getOpspooiRemark() {
        return opspooiRemark;
    }

    public void setOpspooiRemark(String opspooiRemark) {
        this.opspooiRemark = opspooiRemark;
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
